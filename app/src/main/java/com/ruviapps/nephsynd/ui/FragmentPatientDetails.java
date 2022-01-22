package com.ruviapps.nephsynd.ui;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.navigation.NavigationView;
import com.ruviapps.nephsynd.DbHelper.DatabaseHelper;
import com.ruviapps.nephsynd.HelperClasses.Patient;
import com.ruviapps.nephsynd.HelperClasses.PatientViewModel;
import com.ruviapps.nephsynd.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FragmentPatientDetails extends Fragment {
    EditText newPatientFirstName;
    Button saveNewPatientButton;
    EditText newPatientLastName;
    EditText newPatientAge;
    EditText newPatientWeight;
    EditText newPatientSnap;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragmentlayout_patient_details,container,false);
        newPatientFirstName = view.findViewById(R.id.et_patient_firstName_editName);
        saveNewPatientButton = view.findViewById(R.id.bt_patient_details_saveBtn);
        newPatientLastName = view.findViewById(R.id.et_patient_lastName_editName);
        newPatientAge = view.findViewById(R.id.et_patient_age_editName);
        newPatientWeight = view.findViewById(R.id.et_patient_weight_editName);


        saveNewPatientButton.setOnClickListener(v->{
            saveNewPatient();
        });

        return view;
    }

    void saveNewPatient()
    {
        String newFirstName = "Default Name";
        String newLastName = "";
        int newAge;
        Float newWeight;
        byte[] newPic = null;
        DatabaseHelper databaseHelper= new DatabaseHelper(getContext());


        if(!newPatientLastName.getText().toString().matches(""))
        newLastName = newPatientLastName.getText().toString();
        else
            newLastName = " ";




        if(newPatientFirstName.getText().toString().matches(""))
        {
            Toast.makeText(getContext(),"Please Enter a Name First!",Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            newFirstName = newPatientFirstName.getText().toString();
        }
        if(newPatientAge.getText().toString().matches("") || newPatientWeight.getText().toString().matches(""))
        {
            Toast.makeText(getContext(),"Please Enter Age & Weight !",Toast.LENGTH_SHORT).show();
            return;
        }

        newAge= Integer.parseInt(newPatientAge.getText().toString());
        newWeight = Float.parseFloat(newPatientWeight.getText().toString());


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("New Patient Record");
        builder.setMessage("Please Note : All your unsaved Data will be lost");
        String finalNewFirstName = newFirstName;
        String finalNewLastName = newLastName;
        builder.setPositiveButton("Sure!! Go Ahead!! ", (dialog, which) -> {
            databaseHelper.addPatient(finalNewFirstName, finalNewLastName,newAge,newWeight,newPic);
            Toast.makeText(getContext(),"Entry Done",Toast.LENGTH_SHORT).show();
            updateListOfPatient();
            dialog.dismiss();

        });
        builder.setNegativeButton("No, Let me Save First Other Data", (dialog, which) -> {
            dialog.dismiss();
        });

        AlertDialog dialog = builder.create();
        dialog.show();

            //PatientViewModel model = new ViewModelProvider(this).get(PatientViewModel.class);



    }

    void updateListOfPatient()
    {
        PatientViewModel viewModel;

        viewModel = new ViewModelProvider(requireActivity()).get(PatientViewModel.class);

        ArrayList<Patient> all_Patients_Objects;
        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        all_Patients_Objects =databaseHelper.getAllPatients();

        List<String> stringList = new ArrayList<>();

        for(Patient p :all_Patients_Objects)
        {
            stringList.add(p.getPatient_first_name());
        }
       viewModel.setShareAbleList(stringList);



    }




}
