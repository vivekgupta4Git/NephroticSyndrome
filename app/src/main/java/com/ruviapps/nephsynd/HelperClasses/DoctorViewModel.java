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


public class DoctorViewModel extends AndroidViewModel {

    private MutableLiveData<List<String>> doctorShareableList;
    public void setDoctorShareableList(List<String> list)
    {
        if(doctorShareableList !=null)
        doctorShareableList.setValue(list);
    }
   /*
   //if we need to update ui in background thread then only we need postValue Method but for
   //main thread both work equally same .ie. setValue and postValue.

   public void putShareAbleList(List<String> list)
    {
        shareAbleList.postValue(list);

    }*/
    public LiveData<List<String>> getDoctorShareableList()
    {
    if(doctorShareableList ==null)
        {
        doctorShareableList =  new MutableLiveData<>();
            loadDoctors();

        }
    return doctorShareableList;
    }


    public DoctorViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    void loadDoctors()
    {
        DatabaseHelper obj = new DatabaseHelper(getApplication());
    ArrayList<Doctor>    all_doctor_object = obj.getAllDoctors();
        ArrayList<String> doctor_list =new ArrayList<>();
        for(Doctor doctor : all_doctor_object)
        {

            doctor_list.add(doctor.getDoctor_last_name());

        }

        doctorShareableList.setValue(doctor_list);

    }
}

