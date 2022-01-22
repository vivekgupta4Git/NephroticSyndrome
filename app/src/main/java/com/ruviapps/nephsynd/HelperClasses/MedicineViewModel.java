package com.ruviapps.nephsynd.HelperClasses;



import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ruviapps.nephsynd.DbHelper.DatabaseHelper;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class MedicineViewModel extends AndroidViewModel {

    private MutableLiveData<List<String>> medicineShareableList;
    public void setMedicineShareableList(List<String> list)
    {
        if(medicineShareableList !=null)
        medicineShareableList.setValue(list);
    }
   /*
   //if we need to update ui in background thread then only we need postValue Method but for
   //main thread both work equally same .ie. setValue and postValue.

   public void putShareAbleList(List<String> list)
    {
        shareAbleList.postValue(list);

    }*/
    public LiveData<List<String>> getMedicineShareableList()
    {
    if(medicineShareableList ==null)
        {
        medicineShareableList =  new MutableLiveData<>();
            loadMedicine();

        }
    return medicineShareableList;
    }


    public MedicineViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    void loadMedicine()
    {
        DatabaseHelper obj = new DatabaseHelper(getApplication());
        ArrayList<Medicine> all_medicine_object = obj.getAllMedicines();

        ArrayList<String>  medicine_list =new ArrayList<>();
        for(Medicine medicine: all_medicine_object)
        {
            medicine_list.add(medicine.getMedicine_name());
        }

        medicineShareableList.setValue(medicine_list);

    }
}

