package com.ruviapps.nephsynd.ui;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.ruviapps.nephsynd.HelperClasses.Medicine;
import com.ruviapps.nephsynd.HelperClasses.MedicineViewModel;
import com.ruviapps.nephsynd.HelperClasses.Patient;
import com.ruviapps.nephsynd.HelperClasses.Utility;
import com.ruviapps.nephsynd.R;

import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class FragmentDosage extends Fragment {
    private static final String ARG_FRAG = "Patientobject";
    Context context;
    Spinner medicine_chooser;
    Spinner doc_chooser;
    Spinner disease_chooser;
    EditText et_dosage_value1;
    //  EditText et_doage_value2;
    Button saveBtn;
    ImageView dateimageView;
    TextView datetextView;
    ArrayList<Diseases> all_disease_objects;
    ArrayList<Doctor> all_doctor_objects;
    ArrayList<Medicine> all_medicine_objects;
    Patient patient_object;
    Diseases currentSelectedDiseasesObject;
    Medicine currentSelectedMedicineObject;
    Doctor currentSelectedDoctorObject;

    //  long latestConsulationId;
    // int RunningCountDiseaseSpinner;
    // int RunningCountMedidineSpinner;
    // int RunningCountDoctorSpinner;
    //  TextView dosageRecommendadation;
    //   ArrayAdapter<String> list_of_diseasesAdapter;
                //  ArrayAdapter<String> doctorAdapter;
    //  int oldListDiseases,oldListDoctors;

    public static FragmentDosage newInstance(Patient p) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_FRAG, p);
        FragmentDosage fragment = new FragmentDosage();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        context = getContext();
        return inflater.inflate(R.layout.fragmentlayout_dosage, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View v, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        Calendar myCalender = Calendar.getInstance();
        TimeZone timeZone = TimeZone.getTimeZone("GMT+0530");
        myCalender.setTimeZone(timeZone);
        datetextView = v.findViewById(R.id.dateTextview);
        datetextView.setText(Utility.FormattedDate(myCalender.getTime()));
         DatePickerDialog.OnDateSetListener dateSetListener = (view1, year, month, dayOfMonth) -> {
             myCalender.set(Calendar.YEAR, year);
             myCalender.set(Calendar.MONTH, month);
             myCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
             datetextView.setText(Utility.FormattedDate(myCalender.getTime()));
         };


        dateimageView = v.findViewById(R.id.imageView);
        dateimageView.setOnClickListener(v12 -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getContext(),dateSetListener,  myCalender.get(Calendar.YEAR),
                    myCalender.get(Calendar.MONTH),
                    myCalender.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();

        });


        patient_object = (Patient) getArguments().getSerializable(ARG_FRAG);

        saveBtn = v.findViewById(R.id.saveBtn_dosage);

        medicine_chooser = v.findViewById(R.id.sp_cm);
        et_dosage_value1 = v.findViewById(R.id.et_dosage1);
        loadMedicine();

        doc_chooser = v.findViewById(R.id.sp_doc);
        disease_chooser = v.findViewById(R.id.sp_cd);
        //   dosageRecommendadation = v.findViewById(R.id.tv_recDosage);
        //  dosageRecommendadation.setVisibility(View.GONE);
        loadDisease();
        loadDoctors();

        saveBtn.setOnClickListener(v1 -> {
            if (et_dosage_value1.getText().toString().matches("")) {
                Toast.makeText(context, "Please Enter Value", Toast.LENGTH_SHORT).show();
            } else {
                if(currentSelectedMedicineObject==null || patient_object==null || currentSelectedDoctorObject==null || currentSelectedDiseasesObject ==null)
                    return;
                DatabaseHelper databaseHelper = new DatabaseHelper(context);
                try {
                    datetextView.getText();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

                 Date date =   dateFormat.parse(datetextView.getText().toString());

                    databaseHelper.addDosage(currentSelectedMedicineObject.getMedicine_id(), currentSelectedDiseasesObject.getDisease_id(),
                            et_dosage_value1.getText().toString(),
                            Utility.DateToDaysInInteger(date),
                            currentSelectedDoctorObject.getDoctor_id(), patient_object.getPatient_id());
                    Toast.makeText(context,"Successfully inserted",Toast.LENGTH_SHORT).show();
                    ResetFields();

                }catch (Exception e)
                {
                    Toast.makeText(context,"Error Occured, Check Log",Toast.LENGTH_SHORT).show();
                    Log.e("RuviApps",e.toString());
                    e.printStackTrace();
                }

            }
        });


        medicine_chooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                DatabaseHelper o = new DatabaseHelper(getContext());
                all_medicine_objects = o.getAllMedicines();
                if (all_medicine_objects != null && all_medicine_objects.size() > 0) {
                    currentSelectedMedicineObject = all_medicine_objects.get(position);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        disease_chooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DatabaseHelper o = new DatabaseHelper(getContext());
                all_disease_objects = o.getAllDiseases();

                if(all_disease_objects!=null && all_disease_objects.size()>0)
                {
                    currentSelectedDiseasesObject = all_disease_objects.get(position);


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        doc_chooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()

        {
            @Override
            public void onItemSelected (AdapterView < ? > parent, View view,int position, long id) {
                DatabaseHelper o = new DatabaseHelper(getContext());
                all_doctor_objects = o.getAllDoctors();
                if (all_doctor_objects != null && all_doctor_objects.size() > 0) {
                    currentSelectedDoctorObject = all_doctor_objects.get(position);
                }
            }

            @Override
            public void onNothingSelected (AdapterView < ? > parent){

            }
        });

    }

    void ResetFields()
    {
        et_dosage_value1.setText("");
        doc_chooser.setSelection(0);
        medicine_chooser.setSelection(0);
        disease_chooser.setSelection(0);

    }

    void loadMedicine() {
        DatabaseHelper obj = new DatabaseHelper(getContext());
        all_medicine_objects = obj.getAllMedicines();

        MedicineViewModel model = new ViewModelProvider(requireActivity()).get(MedicineViewModel.class);
        model.getMedicineShareableList().observe(getViewLifecycleOwner(), medicineShareableList -> {
            ArrayAdapter<String> list_ofMedicineAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, medicineShareableList);
            list_ofMedicineAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            medicine_chooser.setAdapter(list_ofMedicineAdapter);
        });

    }

    void loadDisease() {
        DatabaseHelper obj = new DatabaseHelper(getContext());
        all_disease_objects = obj.getAllDiseases();

        DiseasesViewModel model = new ViewModelProvider(requireActivity()).get(DiseasesViewModel.class);
        model.getDiseaseShareAbleList().observe(getViewLifecycleOwner(), diseaseShareAbleList ->{
         ArrayAdapter<String>    list_of_diseasesAdapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, diseaseShareAbleList);
            list_of_diseasesAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            disease_chooser.setAdapter(list_of_diseasesAdapter);
        });



    }

    void loadDoctors() {
        DatabaseHelper obj = new DatabaseHelper(getContext());

        all_doctor_objects = obj.getAllDoctors();

        DoctorViewModel model  = new ViewModelProvider(requireActivity()).get(DoctorViewModel.class);
        model.getDoctorShareableList().observe(getViewLifecycleOwner(),doctorShareableList->{
            ArrayAdapter<String>   doctorAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, doctorShareableList);
            doctorAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            doc_chooser.setAdapter(doctorAdapter);
        });


    }


}

 /*   private void getRecommendedDosage() {

        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        long newDoctorId = currentSelectedDoctorObject.getDoctor_id();
        boolean flag = false;


        ArrayList<Consultation> arrayList = databaseHelper.getAllConsultation(null, Consultation.COLUMN_DOCTOR_ID + " =? " + " AND " + Consultation.COLUMN_PATIENT_ID + " =? ",
                new String[]{String.valueOf(newDoctorId), String.valueOf(patient_object.getPatient_id())}, null, null, null, null);

        if (arrayList.size() > 0) {
            for (Consultation c : arrayList) {
                if (c.getConsultation_id() == latestConsulationId) {
                    flag = true;
                }
            }
        }

        //     currentSelectedDoctorObject.getDoctor_id();
        if (flag) {
            long selectedMedicine = currentSelectedMedicineObject.getMedicine_id();
            ArrayList<Prescribtion> list = databaseHelper.getAllPrescribtion(null,
                    Prescribtion.COLUMN_CONSULT_ID + " =? " + " AND " + Prescribtion.COLUMN_MEDICINE_ID + " = ? ",
                    new String[]{String.valueOf(latestConsulationId),
                            String.valueOf(selectedMedicine)}, null, null, null, String.valueOf(1));
            if (list.size() > 0) {
                for (Prescribtion p : list) {
                    dosageRecommendadation.setVisibility(View.VISIBLE);
                    dosageRecommendadation.setText("Prescribed Daoge Is : " + p.getPrescribed_dosage() + " " + p.getUnit() + " " + p.getFrequency());
                }
            }
        }
    }

*/



        /*



    boolean getConsulationIfAny(long id)
    {
        DatabaseHelper o = new DatabaseHelper(getContext());
        latestConsulationId =o.getLatestConsulation(id, Utility.DateToDaysInInteger( Calendar.getInstance().getTime()));
        ArrayList<Long> diseaseList =   o.getAllDiseaseConsultedForGivenConsultation(latestConsulationId);
        for(long d : diseaseList)
        {
            if(currentSelectedDiseasesObject.getDisease_id() == d)
            {
                return  true;
            }

        }
        return false;
    }

    */
/*
    void setDoctorForSelectedDiseases(long id)
    {
        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
     //   String[] projection = new String[] { Consultation.COLUMN_DOCTOR_ID};
        String selection = Consultation.COLUMN_CONSULTATION_ID + " = ?" + " AND " + Consultation.COLUMN_PATIENT_ID + " = ? ";
        String[] selectionArgs = new String[] {
                                                String.valueOf(id),String.valueOf(patient_object.getPatient_id())
                                                };

        ArrayList<Consultation> obj = databaseHelper.getAllConsultation(null,selection,selectionArgs,null,null,null,null);
       long doctorId = -1;
        for(Consultation c : obj)
        {
            doctorId = c.getDoctor_id();
        }

        for(int i=0;i<all_doctor_objects.size();++i)
        {
            if(all_doctor_objects.get(i).getDoctor_id()==doctorId)
            {
                doc_chooser.setSelection(i);
                break;
            }
        }
    }


*/



