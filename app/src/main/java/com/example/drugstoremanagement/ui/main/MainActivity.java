package com.example.drugstoremanagement.ui.main;

import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.drugstoremanagement.R;
import com.example.drugstoremanagement.data.DataManager;
import com.example.drugstoremanagement.data.db.model.HistorySearch;

public class MainActivity extends AppCompatActivity {

    TextView textView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.txt);

        DataManager.getInstance(this).insertHistorySearch(new HistorySearch("thuoc"));

        DataManager.getInstance(this).getHistorySearch().observe(this, historySearches -> {
            if (historySearches != null && historySearches.size() > 0)textView.setText(historySearches.get(0).search);
        });
    }
}