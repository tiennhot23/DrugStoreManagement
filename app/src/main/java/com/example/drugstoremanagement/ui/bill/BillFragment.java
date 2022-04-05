package com.example.drugstoremanagement.ui.bill;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.*;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.example.drugstoremanagement.R;
import com.example.drugstoremanagement.data.DataManager;
import com.example.drugstoremanagement.data.db.model.Bill;
import com.example.drugstoremanagement.data.db.model.Drug;
import com.example.drugstoremanagement.data.db.model.DrugStore;
import com.example.drugstoremanagement.data.viewmodel.DrugStoreViewModel;
import com.example.drugstoremanagement.data.viewmodel.DrugViewModel;
import com.example.drugstoremanagement.ui.base.BaseFragment;
import com.example.drugstoremanagement.ui.statistic.DrugStoreNameAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BillFragment extends BaseFragment implements DrugInBillDialog.Callback{

    private List<Drug> drugs;
    private List<Drug> drugsSelected;
    private List<DrugStore> drugStores;
    private DrugStoreNameAdapter drugStoreAdapter;
    private DetailBillAdapter detailBillAdapter;
    private DrugStoreViewModel drugStoreViewModel;
    private DrugViewModel drugViewModel;

    private Spinner spinnerDrugStore;
    private TextView txtTotal, txtBillId, txtDate;
    private ListView listItem;
    private Button btnCreateBill;
    private ImageButton btnAdd;

    private String currentDrugStoreID;

    public BillFragment() {

    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill, container, false);
        return view;
    }

    @Override
    protected void setup(View view) {
        drugs = new ArrayList<>();
        drugsSelected = new ArrayList<>();
        drugStores = new ArrayList<>();
        setupView(view);
        setupEvent();
        setupSpinner();

        detailBillAdapter = new DetailBillAdapter(getContext(), R.layout.item_detail_bill, drugsSelected);
        listItem.setAdapter(detailBillAdapter);
        drugs = DataManager.getInstance(getContext()).getDrug();

        initData();
    }

    private void setupView(View view) {
        spinnerDrugStore = view.findViewById(R.id.spinner);
        txtTotal = view.findViewById(R.id.txt_total);
        txtDate = view.findViewById(R.id.txt_date);
        txtBillId = view.findViewById(R.id.txt_bill_id);
        listItem = view.findViewById(R.id.list_item);
        btnCreateBill = view.findViewById(R.id.btn_create_bill);
        btnAdd = view.findViewById(R.id.btn_add);
    }

    private void setupEvent() {
        btnAdd.setOnClickListener(v -> {
            DrugInBillDialog drugInBillDialog = new DrugInBillDialog(getContext(), drugs, drugsSelected, this);
            drugInBillDialog.show();
        });
        btnCreateBill.setOnClickListener(v -> {
            if (isWriteExternalStorage()) exportPDF();
        });
    }

    private void setupSpinner() {
        drugStoreAdapter = new DrugStoreNameAdapter(getContext(), R.layout.spinner_item, drugStores);
        drugStoreAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerDrugStore.setAdapter(drugStoreAdapter);
        spinnerDrugStore.setDropDownVerticalOffset(160);
        spinnerDrugStore.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentDrugStoreID = drugStoreAdapter.getItemID(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        drugStoreViewModel = ViewModelProviders.of(getBaseActivity(), new DrugStoreViewModel.Factory(getContext())).get(DrugStoreViewModel.class);
        drugStoreViewModel.getDrugstores().observe(getViewLifecycleOwner(), drugStoresLiveData -> {
            this.drugStores.clear();
            this.drugStores.addAll(drugStoresLiveData);
            if (drugStoreAdapter != null) drugStoreAdapter.notifyDataSetChanged();
        });
    }

    @SuppressLint("SimpleDateFormat")
    private void initData() {
        int total = 0;
        for (Drug drug : drugsSelected) {
            total += drug.getAmount() * drug.getPrice();
        }
        txtTotal.setText(String.valueOf(total));
        txtDate.setText(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
        txtBillId.setText(generateBillId());
    }

    @Override
    public void onButtonSaveClick() {
        if (detailBillAdapter != null) detailBillAdapter.notifyDataSetChanged();
        int total = 0;
        for (Drug drug : drugsSelected) {
            total += drug.getAmount() * drug.getPrice();
        }
        txtTotal.setText(String.valueOf(total));
    }

    @SuppressLint("DefaultLocale")
    private String generateBillId() {
//        int n = DataManager.getInstance(getContext()).getbi().size() + 1;
//        Toast.makeText(getContext(),DataManager.getInstance(context).getDrug().size()+"X" , Toast.LENGTH_SHORT).show();
//        return String.format("D%03d", n);
        return "";
    }

    private void exportPDF() {
        int pageHeight = 600;
        int pageWidth = 300;

        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();
        Bitmap bmp, scaledBitmap;

        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.img);
        scaledBitmap = Bitmap.createScaledBitmap(bmp, 300, 200, false);

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        canvas.drawBitmap(scaledBitmap, 0, 0, paint);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(20f);
        paint.setColor(Color.BLACK);
        canvas.drawText("HÓA ĐƠN " + "HD0001", 150, 220, paint);

        paint.setTextAlign(Paint.Align.RIGHT);
        paint.setTextSize(10f);
        paint.setColor(Color.BLACK);
        canvas.drawText("Ngày lập: " + "24-07-2000", 280, 250, paint);

        int startY = 260;
        for (int i=0; i<2; i++) {
            paint.setTextAlign(Paint.Align.LEFT);
            paint.setTextSize(14f);
            paint.setColor(Color.BLACK);
            canvas.drawText("Thuốc ho " + " x " + " 5 ", 20, startY + 20, paint);
            paint.setTextAlign(Paint.Align.RIGHT);
            paint.setTextSize(14f);
            paint.setColor(Color.BLACK);
            canvas.drawText(String.valueOf("30" + " VND"), 280, startY + 30, paint);
            paint.setTextAlign(Paint.Align.LEFT);
            paint.setTextSize(10f);
            paint.setColor(Color.BLACK);
            canvas.drawText("Giá: 15 VND    Đơn vị: Chai", 30, startY + 40, paint);
            startY += 50;
        }

        paint.setTextAlign(Paint.Align.RIGHT);
        paint.setTextSize(15f);
        paint.setColor(Color.BLACK);
        canvas.drawText("Tổng cộng: " + "60" + " VND", 280, 580, paint);

        pdfDocument.finishPage(page);

        File file = new File(getBaseActivity().getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "bill.pdf");
        if (file.exists()) file.delete();
        try {
            pdfDocument.writeTo(new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            pdfDocument.close();
        }
    }

    public boolean isWriteExternalStorage() {
        int hasWriteExternalStorage = ContextCompat.checkSelfPermission(getBaseActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteExternalStorage != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getBaseActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 69);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 69) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                exportPDF();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
