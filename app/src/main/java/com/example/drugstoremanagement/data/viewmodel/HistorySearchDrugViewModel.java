package com.example.drugstoremanagement.data.viewmodel;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.example.drugstoremanagement.data.DataManager;
import com.example.drugstoremanagement.data.db.model.HistorySearchDrug;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class HistorySearchDrugViewModel extends ViewModel {

    private Context context;
    private final MutableLiveData<List<HistorySearchDrug>> mHistorySearchDrugs = new MutableLiveData<>();

    public HistorySearchDrugViewModel(Context context) {
        this.context = context;
        mHistorySearchDrugs.postValue(DataManager.getInstance(context).getHistorySearchDrug());
    }

    public MutableLiveData<List<HistorySearchDrug>> getHistorySearchDrugs() {
        return mHistorySearchDrugs;
    }

    public void setHistorySearchDrugs(List<HistorySearchDrug> mHistorySearchDrugs) {
        this.mHistorySearchDrugs.setValue(mHistorySearchDrugs);
    }

    public boolean insertHistorySearchDrug(HistorySearchDrug mHistorySearchDrug) {
        boolean result = DataManager.getInstance(context).insertHistorySearchDrug(mHistorySearchDrug);
        if (result) {
            Objects.requireNonNull(mHistorySearchDrugs.getValue()).add(mHistorySearchDrug);
            mHistorySearchDrugs.postValue(Objects.requireNonNull(mHistorySearchDrugs.getValue()));
        }
        return result;
    }

    public boolean deleteHistorySearchDrug(HistorySearchDrug mHistorySearchDrug) {
        boolean result = DataManager.getInstance(context).deleteHistorySearchDrug(mHistorySearchDrug);
        if (result) {
            List<HistorySearchDrug> list = Objects.requireNonNull(mHistorySearchDrugs.getValue());
            for (int i=0; i<list.size(); i++) {
                if (list.get(i).getSearch().equals(mHistorySearchDrug.getSearch())) {
                    mHistorySearchDrugs.getValue().remove(i);
                    mHistorySearchDrugs.postValue(Objects.requireNonNull(mHistorySearchDrugs.getValue()));
                    break;
                }
            }
        }
        return result;
    }

    public static class Factory implements ViewModelProvider.Factory {

        private Context context;

        public Factory(Context context) {
            this.context = context;
        }

        @NotNull
        @Override
        public <T extends ViewModel> T create(@NotNull Class<T> aClass) {
            return (T) new HistorySearchDrugViewModel(this.context);
        }
    }
}
