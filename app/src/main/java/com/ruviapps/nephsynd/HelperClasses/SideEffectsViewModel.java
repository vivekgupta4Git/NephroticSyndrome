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


public class SideEffectsViewModel extends AndroidViewModel {

    private MutableLiveData<List<String>> sideEffectsShareableList;
    public void setSideEffectsShareableList(List<String> list)
    {
        if(sideEffectsShareableList !=null)
        sideEffectsShareableList.setValue(list);
    }
   /*
   //if we need to update ui in background thread then only we need postValue Method but for
   //main thread both work equally same .ie. setValue and postValue.

   public void putShareAbleList(List<String> list)
    {
        shareAbleList.postValue(list);

    }*/
    public LiveData<List<String>> getSideEffectsShareableList()
    {
    if(sideEffectsShareableList ==null)
        {
        sideEffectsShareableList =  new MutableLiveData<>();
            loadSideEffects();

        }
    return sideEffectsShareableList;
    }


    public SideEffectsViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    void loadSideEffects()
    {
        DatabaseHelper obj = new DatabaseHelper(getApplication());
    ArrayList<SideEffect>    sideEffects_list = obj.getAllSideEffects();

        ArrayList<String> sideEffectName =new ArrayList<>();
        for(SideEffect sideEffect : sideEffects_list)
        {
            sideEffectName.add(sideEffect.getSideEffect_name());
        }

        sideEffectsShareableList.setValue(sideEffectName);

    }
}

