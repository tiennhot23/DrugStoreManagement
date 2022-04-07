package com.example.drugstoremanagement.ui.detailbill;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.*;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.widget.*;
import androidx.annotation.NonNull;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.example.drugstoremanagement.R;
import com.example.drugstoremanagement.data.DataManager;
import com.example.drugstoremanagement.data.db.model.Bill;
import com.example.drugstoremanagement.data.db.model.Drug;
import com.example.drugstoremanagement.ui.base.BaseActivity;
import com.example.drugstoremanagement.ui.bill.DetailBillAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DetailBillActivity extends BaseActivity {

    private Bill bill;
    private DetailBillAdapter detailBillAdapter;

    private TextView txtTotal, txtBillId, txtDate, txtDrugStore;
    private ListView listItem;
    private Button btnPrintBill;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bill);
        setup();
    }

    @Override
    protected void setup() {
        setupView();
        setupEvent();
        String billId = getIntent().getStringExtra("billId");
        bill = DataManager.getInstance(this).getBill(billId);

        detailBillAdapter = new DetailBillAdapter(this, R.layout.item_detail_bill, bill.getDrugs());
        listItem.setAdapter(detailBillAdapter);

        initData();
    }

    private void setupView() {
        txtTotal = findViewById(R.id.txt_total);
        txtDate = findViewById(R.id.txt_date);
        txtBillId = findViewById(R.id.txt_bill_id);
        txtDrugStore = findViewById(R.id.txt_drugstore);
        listItem = findViewById(R.id.list_item);
        btnPrintBill = findViewById(R.id.btn_print_bill);
        btnBack = findViewById(R.id.btn_back);
    }

    private void setupEvent() {
        btnPrintBill.setOnClickListener(v -> {
            if (isWriteExternalStorage()) exportPDF();
        });
        btnBack.setOnClickListener(v -> finish());
    }

    @SuppressLint("SimpleDateFormat")
    private void initData() {
        int total = 0;
        for (Drug drug : bill.getDrugs()) {
            total += drug.getAmount() * drug.getPrice();
        }
        txtTotal.setText(String.valueOf(total));
        txtDate.setText(bill.getDate());
        txtBillId.setText(bill.getBillID());
        txtDrugStore.setText(bill.getDrugStore().getDrugStoreName());
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
        canvas.drawText("HÓA ĐƠN " + bill.getBillID(), 150, 220, paint);

        paint.setTextAlign(Paint.Align.RIGHT);
        paint.setTextSize(10f);
        paint.setColor(Color.BLACK);
        canvas.drawText("Ngày lập: " + bill.getDate(), 280, 250, paint);

        int startY = 260;
        for (Drug drug : bill.getDrugs()) {
            paint.setTextAlign(Paint.Align.LEFT);
            paint.setTextSize(14f);
            paint.setColor(Color.BLACK);
            canvas.drawText(drug.getDrugName() + "  x  " + drug.getAmount(), 20, startY + 20, paint);
            paint.setTextAlign(Paint.Align.RIGHT);
            paint.setTextSize(14f);
            paint.setColor(Color.BLACK);
            canvas.drawText(String.valueOf(drug.getPrice() * drug.getAmount()) + " VND", 280, startY + 30, paint);
            paint.setTextAlign(Paint.Align.LEFT);
            paint.setTextSize(10f);
            paint.setColor(Color.BLACK);
            canvas.drawText("Giá: " + drug.getPrice() + " VND    Đơn vị: " + drug.getUnit(), 30, startY + 40, paint);
            startY += 50;
        }

        paint.setTextAlign(Paint.Align.RIGHT);
        paint.setTextSize(15f);
        paint.setColor(Color.BLACK);
        canvas.drawText("Tổng cộng: " + bill.calTotal() + " VND", 280, 580, paint);

        pdfDocument.finishPage(page);

        File file = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "bill.pdf");
        if (file.exists()) file.delete();
        try {
            pdfDocument.writeTo(new FileOutputStream(file));
            showMessage("Đã xuất hóa đơn");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            pdfDocument.close();
        }
    }

    public boolean isWriteExternalStorage() {
        int hasWriteExternalStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteExternalStorage != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 69);
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