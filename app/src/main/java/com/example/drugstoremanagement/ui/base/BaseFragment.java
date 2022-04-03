package com.example.drugstoremanagement.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import com.example.drugstoremanagement.R;

public abstract class BaseFragment extends Fragment {

    private BaseActivity activity;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setup(view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) context;
            this.activity = activity;
        }
    }

    public void showLoading() {
        hideLoading();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    public void hideLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }

    public void onError(String message) {
        if (activity != null) {
            activity.onError(message);
        }
    }

    public void onError(@StringRes int resId) {
        if (activity != null) {
            activity.onError(resId);
        }
    }

    public void showMessage(String message) {
        if (activity != null) {
            activity.showMessage(message);
        }
    }

    public void showMessage(@StringRes int resId) {
        if (activity != null) {
            activity.showMessage(resId);
        }
    }

    public void showPopupMessage(String message, int animation) {
        if (activity != null) {
            activity.showPopupMessage(message, animation);
        }
    }

    public void showPopupMessage(int resId, int animation) {
        showPopupMessage(getString(resId), animation);
    }

    @Override
    public void onDetach() {
        activity = null;
        super.onDetach();
    }

    public BaseActivity getBaseActivity() {
        return activity;
    }

    protected abstract void setup(View view);


}
