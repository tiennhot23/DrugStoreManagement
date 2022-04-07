package com.example.drugstoremanagement.data.viewmodel;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.example.drugstoremanagement.data.DataManager;
import com.example.drugstoremanagement.data.db.model.DrugStore;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class DrugStoreViewModel extends ViewModel {

    private Context context;
    private final MutableLiveData<List<DrugStore>> drugstores = new MutableLiveData<>();

    public DrugStoreViewModel(Context context) {
        this.context = context;
        drugstores.postValue(DataManager.getInstance(context).getDrugStore());
    }

    public MutableLiveData<List<DrugStore>> getDrugstores() {
        return drugstores;
    }

    public void setDrugstores(List<DrugStore> drugstores) {
        this.drugstores.setValue(drugstores);
    }

    public boolean insertDrugstore(DrugStore drugStore) {
        boolean result = DataManager.getInstance(context).insertDrugStore(drugStore);;
        if (result) {
            Objects.requireNonNull(drugstores.getValue()).add(drugStore);
            drugstores.postValue(Objects.requireNonNull(drugstores.getValue()));
        }
        return result;
    }

    public boolean updateDrugstore(DrugStore drugStore) {
        boolean result = DataManager.getInstance(context).updateDrugStore(drugStore);
        if (result) {
            List<DrugStore> list = Objects.requireNonNull(drugstores.getValue());
            for (int i=0; i<list.size(); i++) {
                if (list.get(i).getDrugStoreID().equals(drugStore.getDrugStoreID())) {
                    drugstores.getValue().set(i, drugStore);
                    drugstores.postValue(Objects.requireNonNull(drugstores.getValue()));
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
            return (T) new DrugStoreViewModel(this.context);
        }
    }
}
