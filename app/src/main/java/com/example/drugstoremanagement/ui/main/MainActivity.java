package com.example.drugstoremanagement.ui.main;

import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.drugstoremanagement.R;
import com.example.drugstoremanagement.data.DataManager;
import com.example.drugstoremanagement.data.db.model.HistorySearch;
import com.example.drugstoremanagement.ui.base.BaseActivity;

public class MainActivity extends BaseActivity {

    TextView textView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setup();

    }

    @Override
    protected void setup() {
        textView = findViewById(R.id.txt);
        textView.setOnClickListener(v -> {
            showLoading();
        });
    }
}