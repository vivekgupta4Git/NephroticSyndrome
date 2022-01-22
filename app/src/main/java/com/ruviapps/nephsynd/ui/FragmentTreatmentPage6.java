package com.ruviapps.nephsynd.ui;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ruviapps.nephsynd.DbHelper.DatabaseHelper;
import com.ruviapps.nephsynd.HelperClasses.Consultation;
import com.ruviapps.nephsynd.HelperClasses.ConsultedForDiseases;
import com.ruviapps.nephsynd.HelperClasses.Diseases;
import com.ruviapps.nephsynd.HelperClasses.Patient;
import com.ruviapps.nephsynd.HelperClasses.Prescribtion;
import com.ruviapps.nephsynd.HelperClasses.Utility;
import com.ruviapps.nephsynd.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;

import static com.ruviapps.nephsynd.DbHelper.DatabaseHelper.CONSULTATION_TABLE_NAME;
import static com.ruviapps.nephsynd.DbHelper.DatabaseHelper.CONSULTED_FOR_DIESEASE_TABLE_NAME;
import static com.ruviapps.nephsynd.DbHelper.DatabaseHelper.PRESCRIBTION_TABLE_NAME;

public class FragmentTreatmentPage6 extends Fragment {
    private static final String PATIENTOBJECT = "Patientobject";
    private static final String VISIT_DATE = "visitedDate";
    private static final String DOCTOR = "visitedDoctor";
    private static final String DISEASES_LIST = "diseases_list";
    private static final String MEDICINES_PRESCBRIED = "medicinePrescribed";
    private static final String FOLLOW_UP = "followUpDate";

    Patient patientObject;
    int visitedDate,followUpDate;
    long doctor_id,consultation_id;
    long[] disease_id_list;
    ArrayList<Prescribtion> prescribtionArrayList;


    TextView visitedDateTextView,visitedDcotorTextView,selected_diseasesTextview,
    medicineListTextview,followupTextView;
    Button saveAll;


    public static FragmentTreatmentPage6 newInstance(Patient patientObject,
                                       int patientVisitedDoctor_onDate_in_integer,
                                       long selectedDoctorId,
                                       long[] selected_diseases_ids_byUser,
                                       ArrayList<Prescribtion> prescibedMedicines,
                                       int nextFollowUpDate) {
        FragmentTreatmentPage6 fragment = new FragmentTreatmentPage6();
        Bundle Bundleargs = new Bundle();
        Bundleargs.putSerializable(PATIENTOBJECT,patientObject );
        Bundleargs.putInt(VISIT_DATE,patientVisitedDoctor_onDate_in_integer);
        Bundleargs.putLong(DOCTOR,selectedDoctorId);
        Bundleargs.putLongArray(DISEASES_LIST,selected_diseases_ids_byUser);
        Bundleargs.putSerializable(MEDICINES_PRESCBRIED,prescibedMedicines);
        Bundleargs.putInt(FOLLOW_UP,nextFollowUpDate);
        fragment.setArguments(Bundleargs);
        return fragment;


    }


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        patientObject = (Patient) getArguments().getSerializable(PATIENTOBJECT) ;
        visitedDate = getArguments().getInt(VISIT_DATE);
        doctor_id = getArguments().getLong(DOCTOR);
        disease_id_list = getArguments().getLongArray(DISEASES_LIST);
        prescribtionArrayList = (ArrayList<Prescribtion>) getArguments().getSerializable(MEDICINES_PRESCBRIED);
        followUpDate = getArguments().getInt(FOLLOW_UP);

        Log.v("RuviApps","In Treatment Page 6 : " + followUpDate);
        View view = inflater.inflate(R.layout.treatment_child_page6,container,false);

        visitedDateTextView = view.findViewById(R.id.tv_visitDate);

        visitedDcotorTextView = view.findViewById(R.id.tv_visitedToDoctor);
        selected_diseasesTextview = view.findViewById(R.id.tv_diseaseList);
        medicineListTextview = view.findViewById(R.id.tv_prescribed_Medicine_List);
        followupTextView = view.findViewById(R.id.tv_followUpDate);



        SpannableString spannableString = new SpannableString(Utility.DaysToDateString(visitedDate));
        spannableString.setSpan(new UnderlineSpan(),0,spannableString.length(),0);
        visitedDateTextView.setText(  spannableString  );


        spannableString = new SpannableString(new DatabaseHelper(getContext()).getDoctorName(doctor_id));
        spannableString.setSpan(new UnderlineSpan(),0,spannableString.length(),0);
        visitedDcotorTextView.setText(spannableString);

        for(long id : disease_id_list)
            selected_diseasesTextview.append(new DatabaseHelper(getContext()).getDiseaseName(id) + "\n");

        for(Prescribtion p : prescribtionArrayList)
            medicineListTextview.append(new DatabaseHelper(getContext()).getMedicineName(p.getMedicine_id())
                            + " " + p.getPrescribed_dosage()
                            + " " + p.getUnit()
                            + "\t" + p.getFrequency()
                            + "\n"
                            );

        followupTextView.setText(Utility.DaysToDateString(followUpDate));


        visitedDateTextView.setOnClickListener(v->{
            Bundle bundle = new Bundle();
            bundle.putInt("VisitDate",0);
            getParentFragmentManager().setFragmentResult("ReviewRequestKey", bundle);
        });

        visitedDcotorTextView.setOnClickListener(v->{
            Bundle bundle = new Bundle();
            bundle.putInt("Doctor",1);
            getParentFragmentManager().setFragmentResult("ReviewRequestKey", bundle);
        });



        return view;
    }


    boolean flag= false;
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        saveAll = view.findViewById(R.id.btn_yesSaveAll);
        saveAll.setOnClickListener(v->{
            DatabaseHelper helper = new DatabaseHelper(getContext());

    //        database.beginTransaction();
            ContentValues values = new ContentValues();

            if( patientObject== null || doctor_id<0 || followUpDate <0 || visitedDate <0)
                return;
            values.put(Consultation.COLUMN_DOCTOR_ID,doctor_id);
            values.put(Consultation.COLUMN_PATIENT_ID,patientObject.getPatient_id());
            values.put(Consultation.COLUMN_NEXT_FOLLOWUP,String.valueOf(followUpDate));
            values.put(Consultation.COLUMN_DATE,String.valueOf(visitedDate));
            boolean gotConsultationID = false;
try
{

consultation_id =   helper.addConsultation(doctor_id,patientObject.getPatient_id(),visitedDate,followUpDate);
  //  consultation_id = database.insert(CONSULTATION_TABLE_NAME,null,values);
    gotConsultationID = true;
}
catch (SQLException sqlException)
{

    Log.e("RuviApps",sqlException.toString());

}

if(gotConsultationID) {
    SQLiteDatabase database = helper.getWritableDatabase();
    database.beginTransaction();

    try {

        for (long d : disease_id_list) {
            ContentValues values2 = new ContentValues();
            values2.put(ConsultedForDiseases.COLUMN_CONSULTATION_ID, consultation_id);
            values2.put(ConsultedForDiseases.COLUMN_ON_DATE, String.valueOf(visitedDate));
            values2.put(ConsultedForDiseases.COLUMN_DISEASE_ID, d);
            database.insert(CONSULTED_FOR_DIESEASE_TABLE_NAME, null, values2);
        }

        for (Prescribtion p : prescribtionArrayList) {
            ContentValues values3 = new ContentValues();
            values3.put(Prescribtion.COLUMN_CONSULT_ID, consultation_id);
            values3.put(Prescribtion.COLUMN_MEDICINE_ID, p.getMedicine_id());
            values3.put(Prescribtion.COLUMN_PRESCRIBED_DOSAGE, p.getPrescribed_dosage());
            values3.put(Prescribtion.COLUMN_PRESCRIBED_FREQ, p.getFrequency());
            values3.put(Prescribtion.COLUMN_PRESCRIBED_UNIT, p.getUnit());
            database.insert(PRESCRIBTION_TABLE_NAME, null, values3);
        }


        Toast.makeText(getContext(), "Consultation Added ", Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        bundle.putSerializable("PatientObject", patientObject);
        getParentFragmentManager().setFragmentResult("SaveAll", bundle);
        database.setTransactionSuccessful();

    } catch (SQLException sqlException) {
        Log.e("RuviApps", sqlException.toString());
        flag = true;
    } finally {
        database.endTransaction();
    }

}
else
{
    Toast.makeText(getContext(),"Sorry!, Entry Failed",Toast.LENGTH_SHORT).show();
}
            if(flag) {
                try {
                    if(gotConsultationID) //just to make sure
                        helper.deleteConsultation(consultation_id);
                }
                catch (SQLException sqlException)
                {
                    Log.v("RuviApps",sqlException.toString());
                }
            }


        });

    }
}
