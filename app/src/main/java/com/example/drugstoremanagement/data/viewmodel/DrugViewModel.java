package com.example.drugstoremanagement.data.viewmodel;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.example.drugstoremanagement.data.DataManager;
import com.example.drugstoremanagement.data.db.model.Drug;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class DrugViewModel extends ViewModel {

    private Context context;
    private final MutableLiveData<List<Drug>> drugs = new MutableLiveData<>();

    public DrugViewModel(Context context) {
        this.context = context;
        drugs.postValue(DataManager.getInstance(context).getDrug());
    }

    public MutableLiveData<List<Drug>> getDrugs() {
        return drugs;
    }

    public void setDrugs(List<Drug> drugs) {
        this.drugs.setValue(drugs);
    }

    public boolean insertDrug(Drug drug) {
        boolean result = DataManager.getInstance(context).insertDrug(drug);
        if (result) {
            Objects.requireNonNull(drugs.getValue()).add(drug);
            drugs.postValue(Objects.requireNonNull(drugs.getValue()));
        }
        return result;
    }

    public boolean updateDrug(Drug drug) {
        boolean result = DataManager.getInstance(context).updateDrug(drug);
        if (result) {
            List<Drug> list = Objects.requireNonNull(drugs.getValue());
            for (int i=0; i<list.size(); i++) {
                if (list.get(i).getDrugID().equals(drug.getDrugID())) {
                    drugs.getValue().set(i, drug);
                    drugs.postValue(Objects.requireNonNull(drugs.getValue()));
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
            return (T) new DrugViewModel(this.context);
        }
    }
}
