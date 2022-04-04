package com.example.drugstoremanagement.data.viewmodel;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.example.drugstoremanagement.data.DataManager;
import com.example.drugstoremanagement.data.db.model.HistorySearchDrugstore;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class HistorySearchDrugstoreViewModel extends ViewModel {

    private Context context;
    private final MutableLiveData<List<HistorySearchDrugstore>> mHistorySearchDrugstores = new MutableLiveData<>();

    public HistorySearchDrugstoreViewModel(Context context) {
        this.context = context;
        mHistorySearchDrugstores.postValue(DataManager.getInstance(context).getHistorySearchDrugstore());
    }

    public MutableLiveData<List<HistorySearchDrugstore>> getHistorySearchDrugstores() {
        return mHistorySearchDrugstores;
    }

    public void setHistorySearchDrugstores(List<HistorySearchDrugstore> mHistorySearchDrugstores) {
        this.mHistorySearchDrugstores.setValue(mHistorySearchDrugstores);
    }

    public boolean insertHistorySearchDrugstore(HistorySearchDrugstore mHistorySearchDrugstore) {
        boolean result = DataManager.getInstance(context).insertHistorySearchDrugstore(mHistorySearchDrugstore);
        if (result) {
            Objects.requireNonNull(mHistorySearchDrugstores.getValue()).add(mHistorySearchDrugstore);
            mHistorySearchDrugstores.postValue(Objects.requireNonNull(mHistorySearchDrugstores.getValue()));
        }
        return result;
    }

    public boolean deleteHistorySearchDrugstore(HistorySearchDrugstore mHistorySearchDrugstore) {
        boolean result = DataManager.getInstance(context).deleteHistorySearchDrugstore(mHistorySearchDrugstore);
        if (result) {
            List<HistorySearchDrugstore> list = Objects.requireNonNull(mHistorySearchDrugstores.getValue());
            for (int i=0; i<list.size(); i++) {
                if (list.get(i).getSearch().equals(mHistorySearchDrugstore.getSearch())) {
                    mHistorySearchDrugstores.getValue().remove(i);
                    mHistorySearchDrugstores.postValue(Objects.requireNonNull(mHistorySearchDrugstores.getValue()));
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
            return (T) new HistorySearchDrugstoreViewModel(this.context);
        }
    }
}
