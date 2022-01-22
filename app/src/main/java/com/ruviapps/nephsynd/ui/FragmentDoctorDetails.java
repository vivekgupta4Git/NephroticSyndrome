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
import com.ruviapps.nephsynd.HelperClasses.Doctor;
import com.ruviapps.nephsynd.HelperClasses.DoctorViewModel;
import com.ruviapps.nephsynd.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FragmentDoctorDetails extends Fragment {
    EditText newDoctorName;
    Button saveDoctorBtn;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragmentlayout_doctor_details,container,false);
        newDoctorName = view.findViewById(R.id.et_doctor_details_editName);
        saveDoctorBtn = view.findViewById(R.id.bt_doctor_details_saveBtn);

        saveDoctorBtn.setOnClickListener(v->{
            saveNewSideEffect();
        });

        return view;
    }

    void saveNewSideEffect()
    {
        DatabaseHelper databaseHelper= new DatabaseHelper(getContext());
        if(newDoctorName.getText().toString().matches(""))
        {
            Toast.makeText(getContext(),"Please Enter a Name First!",Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            String newName = newDoctorName.getText().toString();
            databaseHelper.addDoctor("Dr. " ,newName);
            newDoctorName.setText("");
            Toast.makeText(getContext(),"Entry Done",Toast.LENGTH_SHORT).show();
            updateList();
        }

    }

    private void updateList() {
        DoctorViewModel viewModel;

        viewModel = new ViewModelProvider(requireActivity()).get(DoctorViewModel.class);

        ArrayList<Doctor> all_doctor_objects;
        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        all_doctor_objects =databaseHelper.getAllDoctors();

        List<String> stringList = new ArrayList<>();

        for(Doctor doctor : all_doctor_objects)
        {
            stringList.add(doctor.getDoctor_last_name());
        }
        viewModel.setDoctorShareableList(stringList);


    }


}
