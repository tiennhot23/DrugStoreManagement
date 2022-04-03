package com.example.drugstoremanagement.ui.messagedialog;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;
import android.widget.*;


import androidx.annotation.NonNull;

import com.airbnb.lottie.LottieAnimationView;
import com.example.drugstoremanagement.R;
import com.example.drugstoremanagement.ui.base.BaseDialog;

public class MessageDialog extends BaseDialog {
    TextView txtMessage;
    LottieAnimationView animationView;

    public MessageDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setup();
        txtMessage = findViewById(R.id.txt_message);
        animationView = findViewById(R.id.animationView);
    }

    @Override
    protected void setup() {
        setContentView(R.layout.message_dialog);
        getWindow().setBackgroundDrawableResource(R.color.transparent);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().getAttributes().windowAnimations = R.style.ZoomAnimation;
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    public void showMessage(String message, int animation) {
        show();
        txtMessage.setText(message);
        animationView.setAnimation(animation);
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            dismiss();
        }, 2000);
    }
}
