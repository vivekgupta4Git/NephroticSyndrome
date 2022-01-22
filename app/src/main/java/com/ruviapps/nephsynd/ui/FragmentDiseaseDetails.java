package com.ruviapps.nephsynd.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ruviapps.nephsynd.DbHelper.DatabaseHelper;
import com.ruviapps.nephsynd.HelperClasses.Diseases;
import com.ruviapps.nephsynd.HelperClasses.DiseasesViewModel;
import com.ruviapps.nephsynd.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FragmentDiseaseDetails extends Fragment {
    EditText enteredDiseaseName;
    Button saveDiseaseBtn;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragmentlayout_diseases_details,container,false);
        enteredDiseaseName = view.findViewById(R.id.et_disease_details_editName);
        saveDiseaseBtn = view.findViewById(R.id.bt_disease_details_saveBtn);

        saveDiseaseBtn.setOnClickListener(v->{
            saveDiseases();
        });

        return view;
    }

    void saveDiseases()
    {
        DatabaseHelper databaseHelper= new DatabaseHelper(getContext());
        if(enteredDiseaseName.getText().toString().matches(""))
        {
            Toast.makeText(getContext(),"Please Enter a Name First!",Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            String newName = enteredDiseaseName.getText().toString();
            databaseHelper.addDisease(newName);
            enteredDiseaseName.setText("");
            Toast.makeText(getContext(),"Entry Done",Toast.LENGTH_SHORT).show();

            //updating list of disease using shared View Model
            updateList();

        }


    }

    private void updateList() {
        DiseasesViewModel viewModel;

        viewModel = new ViewModelProvider(requireActivity()).get(DiseasesViewModel.class);

        ArrayList<Diseases> all_diseases_objects;
        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        all_diseases_objects =databaseHelper.getAllDiseases();

        List<String> stringList = new ArrayList<>();

        for(Diseases d :all_diseases_objects)
        {
            stringList.add(d.getDisease_name());
        }
        viewModel.setDiseaseShareAbleList(stringList);


    }



}
