package com.example.drugstoremanagement.ui.base;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import com.example.drugstoremanagement.R;
import com.google.android.material.snackbar.Snackbar;

public abstract class BaseDialog extends Dialog {

    public BaseDialog(@NonNull Context context) {
        super(context);
    }

    private void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                message, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView
                .findViewById(R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        snackbar.show();
    }


    public void openLoginActivityOnTokenExpire() {

    }

    public void onError(int resId) {
        onError(getContext().getString(resId));
    }

    public void onError(String message) {
        if (message != null) {
            showSnackBar(message);
        } else {
            showSnackBar(getContext().getString(R.string.something_wrong));
        }
    }

    public void showMessage(String message) {
        showSnackBar(message);
    }

    public void showMessage(int resId) {
        showMessage(getContext().getString(resId));
    }

    protected abstract void setup();
}
