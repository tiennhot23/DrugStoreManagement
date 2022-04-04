package com.example.drugstoremanagement.ui.main;

import com.example.drugstoremanagement.R;

public enum CurrentFragment {
    DRUG (0, R.string.drug),
    DRUGSTORE (1, R.string.drugstore),
    BILL (2, R.string.bill),
    STATISTIC (3, R.string.statistic);

    public final int position;
    public final int labelID;

    CurrentFragment(int position, int labelID) {
        this.position = position;
        this.labelID = labelID;
    }
}
