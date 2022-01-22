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


public class PatientViewModel extends AndroidViewModel {

    private MutableLiveData<List<String>> shareAbleList ;
    public void setShareAbleList(List<String> list)
    {

        shareAbleList.setValue(list);
    }
   /*
   //if we need to update ui in background thread then only we need postValue Method but for
   //main thread both work equally same .ie. setValue and postValue.

   public void putShareAbleList(List<String> list)
    {
        shareAbleList.postValue(list);

    }*/
    public LiveData<List<String>> getShareAbleList()
    {
    if(shareAbleList==null)
        {
        shareAbleList =  new MutableLiveData<>();
        loadPatients();
        }
    return shareAbleList;
    }


    public PatientViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    void loadPatients()
    {
        ArrayList<Patient> all_Patients_Objects;

        DatabaseHelper databaseHelper = new DatabaseHelper(getApplication());
        all_Patients_Objects =databaseHelper.getAllPatients();

        List<String> stringList = new ArrayList<>();

        for(Patient p :all_Patients_Objects)
        {
            stringList.add(p.getPatient_first_name());
        }

        shareAbleList.setValue(stringList);
    }
}

