package com.ruviapps.nephsynd.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.ruviapps.nephsynd.HelperClasses.Patient;
import com.ruviapps.nephsynd.R;

import org.jetbrains.annotations.NotNull;

public class FragmentCreateNew extends Fragment {
    private final String SIDE_EFFECT_LAYOUT = "sideEffectlayout";
    private final String DISEASE_LAYOUT = "diseaselayout";
    private final String DOCTOR_LAYOUT = "doctorlayout";
    private final String MEDICINE_LAYOUT = "medicinelayout";
    private final String PATIENT_LAYOUT = "patientlayout";
    private final String NO_LAYOUT_VISIBLE = "default";

    Button btnCreateNewSideEffect;
  //  Patient patientObject;
    Button btnCreateNewMedicine;
    Button btnCreateDisease;
    Button btnCreateDoctor;
    Button btnCreatePatient;


    FrameLayout sideEffectLayout;
    FrameLayout medicineLayout;
    FrameLayout DiseaseLayout;
    FrameLayout doctorLayout ;
    FrameLayout patientLayout ;

    public static FragmentCreateNew newInstance(Patient po)
    {
        FragmentCreateNew fragmentCreateNew = new FragmentCreateNew();
   //     Bundle bundle = new Bundle();
    //    bundle.putSerializable(ARG_FRAG,po);
     //   fragmentCreateNew.setArguments(bundle);
        return fragmentCreateNew;
    }


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
     //   patientObject = (Patient) getArguments().getSerializable(ARG_FRAG);
        View view = inflater.inflate(R.layout.fragmentlayout_create_new,container,false);
       sideEffectLayout = view.findViewById(R.id.framelayout_createNewSideEffects);
        medicineLayout = view.findViewById(R.id.framelayout_createNewMedicine);
         DiseaseLayout = view.findViewById(R.id.framelayout_createNewDisease);
        doctorLayout = view.findViewById(R.id.framelayout_createNewDoctor);
        patientLayout = view.findViewById(R.id.framelayout_createNewPatient);
        setVisibility_of_layout(NO_LAYOUT_VISIBLE);


        btnCreateNewSideEffect = view.findViewById(R.id.bt_TabSideEffects);
        btnCreateNewSideEffect.setOnClickListener(v -> {
            setVisibility_of_layout(SIDE_EFFECT_LAYOUT);
            replaceFragment_SideEffectDetails();

        });

        btnCreateNewMedicine = view.findViewById(R.id.bt_TabMedicine);
        btnCreateNewMedicine.setOnClickListener(v -> {
            setVisibility_of_layout(MEDICINE_LAYOUT);
            replaceFragment_MedicineDetails();
        });

        btnCreateDisease = view.findViewById(R.id.bt_TabDisease);
        btnCreateDisease.setOnClickListener(v->{
            setVisibility_of_layout(DISEASE_LAYOUT);
            replaceFragment_DiseaseDetails();
        });

        btnCreateDoctor = view.findViewById(R.id.bt_TabDoctor);
        btnCreateDoctor.setOnClickListener(v -> {
            setVisibility_of_layout(DOCTOR_LAYOUT);
            replaceFragment_DoctorDetails();


        });

        btnCreatePatient = view.findViewById(R.id.bt_TabPatient);
        btnCreatePatient.setOnClickListener(v -> {
            setVisibility_of_layout(PATIENT_LAYOUT);
            replaceFragment_PatientDetails();
        });

        return view;
    }

    void replaceFragment_PatientDetails()
    {
        FragmentPatientDetails fragmentPatientDetails = new FragmentPatientDetails();
        FragmentManager fragmentManager = getChildFragmentManager();
        if(fragmentManager.findFragmentByTag("PAT")==null)
        {
            fragmentManager.beginTransaction().replace(R.id.framelayout_createNewPatient,
                    fragmentPatientDetails,"PAT").commit();
        }
    }

    void replaceFragment_SideEffectDetails()
    {
        FragmentSideEffectDetails fragmentSideEffectDetails  = new FragmentSideEffectDetails();
        FragmentManager fragmentManager = getChildFragmentManager();
        if(fragmentManager.findFragmentByTag("SEF")== null)
        {
            fragmentManager.beginTransaction().replace(R.id.framelayout_createNewSideEffects,
                    fragmentSideEffectDetails,"SEF").commit();
        }
    }
    void replaceFragment_MedicineDetails()
    {
        FragmentMedicineDetails fragmentMedicineDetails = new FragmentMedicineDetails();
        FragmentManager fragmentManager = getChildFragmentManager();
        if(fragmentManager.findFragmentByTag("MED")==null)
        {
            fragmentManager.beginTransaction().replace(R.id.framelayout_createNewMedicine,
                    fragmentMedicineDetails,"MED").commit();
        }
    }

    void replaceFragment_DiseaseDetails()
    {
        FragmentDiseaseDetails fragmentDiseaseDetails = new FragmentDiseaseDetails();
        FragmentManager fragmentManager = getChildFragmentManager();
        if(fragmentManager.findFragmentByTag("DIS")== null)
        {
            fragmentManager.beginTransaction().replace(R.id.framelayout_createNewDisease,
                    fragmentDiseaseDetails,"DIS").commit();
        }
    }
    void replaceFragment_DoctorDetails()
    {
        FragmentDoctorDetails fragmentDiseaseDetails = new FragmentDoctorDetails();
        FragmentManager fragmentManager = getChildFragmentManager();
        if(fragmentManager.findFragmentByTag("DOC")== null)
        {
            fragmentManager.beginTransaction().replace(R.id.framelayout_createNewDoctor,
                    fragmentDiseaseDetails,"DOC").commit();
        }



    }

    void setVisibility_of_layout(String layout_to_visible)
    {
        sideEffectLayout.setVisibility(View.INVISIBLE);
        medicineLayout.setVisibility(View.INVISIBLE);
        DiseaseLayout.setVisibility(View.INVISIBLE);
        patientLayout.setVisibility(View.INVISIBLE);
        doctorLayout.setVisibility(View.INVISIBLE);

        switch(layout_to_visible)
        {

            case SIDE_EFFECT_LAYOUT:
                sideEffectLayout.setVisibility(View.VISIBLE);
                break;
            case MEDICINE_LAYOUT:
                medicineLayout.setVisibility(View.VISIBLE);
                break;
            case DISEASE_LAYOUT:
                DiseaseLayout.setVisibility(View.VISIBLE);
                break;
            case DOCTOR_LAYOUT:
                doctorLayout.setVisibility(View.VISIBLE);
                break;
            case PATIENT_LAYOUT:
                patientLayout.setVisibility(View.VISIBLE);
                break;

        }

    }







}
