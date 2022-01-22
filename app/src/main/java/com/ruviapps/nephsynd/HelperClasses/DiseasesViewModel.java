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


public class DiseasesViewModel extends AndroidViewModel {

    private MutableLiveData<List<String>> diseaseShareAbleList;
    public void setDiseaseShareAbleList(List<String> list)
    {
        if(diseaseShareAbleList!=null)
        diseaseShareAbleList.setValue(list);
    }
   /*
   //if we need to update ui in background thread then only we need postValue Method but for
   //main thread both work equally same .ie. setValue and postValue.

   public void putShareAbleList(List<String> list)
    {
        shareAbleList.postValue(list);

    }*/
    public LiveData<List<String>> getDiseaseShareAbleList()
    {
    if(diseaseShareAbleList ==null)
        {
        diseaseShareAbleList =  new MutableLiveData<>();
            loadDiseases();

        }
    return diseaseShareAbleList;
    }


    public DiseasesViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    void loadDiseases()
    {
        DatabaseHelper obj = new DatabaseHelper(getApplication());
    ArrayList<Diseases>    all_disease_object = obj.getAllDiseases();
        ArrayList<String> disease_list =new ArrayList<>();
        for(Diseases diseases : all_disease_object)
        {
            disease_list.add(diseases.getDisease_name());
        }

        diseaseShareAbleList.setValue(disease_list);

    }
}

