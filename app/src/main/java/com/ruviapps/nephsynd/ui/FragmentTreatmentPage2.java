package com.ruviapps.nephsynd.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ruviapps.nephsynd.DbHelper.DatabaseHelper;
import com.ruviapps.nephsynd.HelperClasses.Doctor;
import com.ruviapps.nephsynd.HelperClasses.DoctorViewModel;
import com.ruviapps.nephsynd.HelperClasses.Utility;
import com.ruviapps.nephsynd.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FragmentTreatmentPage2 extends Fragment {
    Spinner doctorSpinner;
    ArrayAdapter<String> adapter;
    ArrayList<Doctor> doctorArrayList;
    Doctor selectedDoctorObject;
    Button doneBtn;
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.treatment_child_page2,container,false);

        doctorSpinner = view.findViewById(R.id.spinner_treatment_child);
        doneBtn = view.findViewById(R.id.btn_treatmentPage2);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DoctorViewModel model  = new ViewModelProvider(requireActivity()).get(DoctorViewModel.class);
        model.getDoctorShareableList().observe(getViewLifecycleOwner(),doctorShareableList->{
            adapter = new ArrayAdapter<>(getContext(),R.layout.support_simple_spinner_dropdown_item,doctorShareableList);
            adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            doctorSpinner.setAdapter(adapter);


        });


        doctorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DatabaseHelper o = new DatabaseHelper(getContext());
                doctorArrayList = o.getAllDoctors();
                selectedDoctorObject = doctorArrayList.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(selectedDoctorObject==null )
                    return;
                Bundle result = new Bundle();
                result.putInt("PagerCount",2);
                result.putLong("SelectedDoctor",selectedDoctorObject.getDoctor_id());
                // The child fragment needs to still set the result on its parent fragment manager
                getParentFragmentManager().setFragmentResult("secondRequestKey", result);

            }
        });
    }
}
