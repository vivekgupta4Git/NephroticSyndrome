package com.ruviapps.nephsynd.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ruviapps.nephsynd.DbHelper.DatabaseHelper;
import com.ruviapps.nephsynd.HelperClasses.Diseases;
import com.ruviapps.nephsynd.HelperClasses.DiseasesViewModel;
import com.ruviapps.nephsynd.HelperClasses.Dosage;
import com.ruviapps.nephsynd.HelperClasses.Patient;
import com.ruviapps.nephsynd.HelperClasses.SideEffect;
import com.ruviapps.nephsynd.HelperClasses.SideEffectsViewModel;
import com.ruviapps.nephsynd.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FragmentSideEffects extends Fragment {
    private static final String ARG_FRAG = "Patientobject";
    EditText sideEffectExplain;
    Button saveBtn;
    Spinner spinnerSideEffects;
    ArrayList<SideEffect> all_sideEffect_object;
    Patient patientObject;
    ArrayList<SideEffect> sideEffectObjects;
    SideEffect currenSideEffectObject;

    public static FragmentSideEffects newInstance(Patient p) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_FRAG,p);
        FragmentSideEffects fragment = new FragmentSideEffects();
        fragment.setArguments(args);
        return fragment;
    }



    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

    patientObject = (Patient) getArguments().getSerializable(ARG_FRAG);
        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        sideEffectObjects = databaseHelper.getAllSideEffects();

        View v =  inflater.inflate(R.layout.fragmentlayout_sideeffect,container,false);
        spinnerSideEffects = v.findViewById(R.id.sp_sideEffects);
        saveBtn = v.findViewById(R.id.saveBtn_SideEffects);
        sideEffectExplain = v.findViewById(R.id.et_sideEffectsExplationText);
        spinnerSideEffects.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
all_sideEffect_object = databaseHelper.getAllSideEffects();
                if(sideEffectObjects == null || all_sideEffect_object.size()<=0)
                    return;
                currenSideEffectObject = all_sideEffect_object.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return  v;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View v, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        loadSideEffectName();

        saveBtn.setOnClickListener(v1 -> {
            AddSideEffectToPatient();
        });


    }

void AddSideEffectToPatient(){
    if(patientObject==null)
        return;
    String remarks;
    if( !sideEffectExplain.getText().toString().matches("")) {
        remarks = sideEffectExplain.getText().toString();
    }
    else {
        Toast.makeText(getContext(),"Enter Few words to explain side Effects",Toast.LENGTH_LONG).show();
        return;
    }

    DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
    long rowID = databaseHelper.addSideEffectToPatient(currenSideEffectObject.getSideEffect_id(),patientObject.getPatient_id(),remarks);
if(rowID>0)
{
    Toast.makeText(getContext(),"Success !!!",Toast.LENGTH_SHORT).show();
    sideEffectExplain.setText("");
    spinnerSideEffects.setSelection(0);
}else
{
    Toast.makeText(getContext(),"Failure",Toast.LENGTH_SHORT).show();
}
    }


    void loadSideEffectName()
    {
        DatabaseHelper obj = new DatabaseHelper(getContext());

        all_sideEffect_object = obj.getAllSideEffects(null,null,null,null,null,null,null);

        SideEffectsViewModel model = new ViewModelProvider(requireActivity()).get(SideEffectsViewModel.class);
        model.getSideEffectsShareableList().observe(getViewLifecycleOwner(), sideEffectsShareableList ->{

            ArrayAdapter<String> list_of_SideEffectAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item,sideEffectsShareableList);
            list_of_SideEffectAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            spinnerSideEffects.setAdapter(list_of_SideEffectAdapter);


        });


    }

}


