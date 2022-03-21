package com.example.drugstoremanagement.ui.statistic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;
import com.example.drugstoremanagement.R;
import com.example.drugstoremanagement.data.db.model.Bill;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ExpandableListBillAdapter extends BaseExpandableListAdapter {

    private Context context;
    private Map<String, List<Bill>> data;
    private List<String> dates;

    public ExpandableListBillAdapter(Context context, List<String> dates, Map<String, List<Bill>> data) {
        this.context = context;
        this.data = data;
        this.dates = dates;
    }

    @Override
    public int getGroupCount() {
        return dates.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return data.size();
    }

    @Override
    public String getGroup(int i) {
        return dates.get(i);
    }

    @Override
    public Bill getChild(int i, int i1) {
        return data.get(dates.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.parent_item, null);
        TextView txtDate = view.findViewById(R.id.txt_date);
        txtDate.setText(dates.get(i));
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.child_item, null);
        TextView txtBillId = view.findViewById(R.id.txt_bill_id);
        txtBillId.setText(data.get(dates.get(i)).get(i1).getBillID());
        TextView txtMoney = view.findViewById(R.id.txt_money);
        txtMoney.setText(data.get(dates.get(i)).get(i1).getDate());
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
