package com.ruviapps.nephsynd.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ruviapps.nephsynd.DbHelper.DatabaseHelper;
import com.ruviapps.nephsynd.HelperClasses.Diseases;
import com.ruviapps.nephsynd.HelperClasses.DiseasesToPatient;
import com.ruviapps.nephsynd.HelperClasses.DiseasesViewModel;
import com.ruviapps.nephsynd.HelperClasses.Patient;
import com.ruviapps.nephsynd.HelperClasses.Utility;
import com.ruviapps.nephsynd.HelperClasses.myCustomAdapter;
import com.ruviapps.nephsynd.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class FragmentDiseases extends Fragment implements myCustomAdapter.ListItemClickListener {
  private static final String ARG_FRAG = "Patientobject";
    myCustomAdapter adapter;
    RecyclerView recyclerView;


    ArrayAdapter<String> list_of_diseasesAdapter;
        EditText disease_value1;
    Patient patientObject;
    Button saveBtn,cancelBtn;
    Spinner spinnerDiseaes;
    Diseases diseasesObj;
    ArrayList<Diseases> all_disease_object;
    TextView headerText;
    Calendar myCalender;
    private final String updateHeader = "Update";
    private static String addHeader = "Add Diseases";
    DiseasesToPatient currentdiseasesToPatientObject;
    CheckBox recoveredCheckBox;

    public static FragmentDiseases newInstance(Patient p) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_FRAG,p);
        FragmentDiseases fragment = new FragmentDiseases();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        patientObject = (Patient) getArguments().getSerializable(ARG_FRAG);
        myCalender =Calendar.getInstance();
        TimeZone timeZone = TimeZone.getTimeZone("GMT+0530");
        myCalender.setTimeZone(timeZone);
        View v =  inflater.inflate(R.layout.fragmentlayout_diseases,container,false);
        recyclerView = v.findViewById(R.id.recyclerview_for_currentDiseases);
        saveBtn = v.findViewById(R.id.saveBtn_disease);
        cancelBtn = v.findViewById(R.id.cancel_disease_update);
        spinnerDiseaes = v.findViewById(R.id.sp_choose_disease);
        spinnerDiseaes.setPrompt("Pick Problem");
        recoveredCheckBox = v.findViewById(R.id.checkbox1);
        recoveredCheckBox.setChecked(false);
        recoveredCheckBox.setVisibility(View.INVISIBLE);
       disease_value1 = v.findViewById(R.id.et_diseasevalue);
       headerText = v.findViewById(R.id.tv_header_in_Diseaes);

        return  v;
    }



    @Override
    public void onViewCreated(@NonNull @NotNull View v, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        loadDisease();

        spinnerDiseaes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
               all_disease_object = databaseHelper.getAllDiseases();
                if(all_disease_object == null || all_disease_object.size()<=0)
                    return;
                diseasesObj = all_disease_object.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        saveBtn.setOnClickListener(v1 -> {
        if(headerText.getText().toString().matches(addHeader)) {
            AddRecord();
        }
        else if(headerText.getText().toString().matches(updateHeader))
        {
            UpdateRecord();
        }
        });

        cancelBtn.setOnClickListener(v1 -> {
            if(headerText.getText().toString().matches(updateHeader))
            {
               AddDiseaseHeader();
            }
            ResetEntries();
        });

        if(patientObject!=null) {
            adapter = new myCustomAdapter(getAllDiseasesToPatient(), this::onListItemClick);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
                                }

        recoveredCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if(recoveredCheckBox.isChecked()) {
                disease_value1.setEnabled(false);
            }
            else {
                disease_value1.setEnabled(true);
            }
        });


    }



    void ResetEntries()
    {
        if(spinnerDiseaes!=null)
            spinnerDiseaes.setSelection(0);
        disease_value1.setText("");
        recoveredCheckBox.setChecked(false);
    }


    private void UpdateRecord() {

        if(patientObject==null)
        return;

            String newValue;
        DatabaseHelper obj = new DatabaseHelper(getContext());
        if(recoveredCheckBox.isChecked()==true)
        {
            long diseaseid = diseasesObj.getDisease_id();
            long dtpid = currentdiseasesToPatientObject.getDTP_id();
            long pid = patientObject.getPatient_id();
            int startDate = currentdiseasesToPatientObject.getStart_date();
            int endDate = Utility.DateToDaysInInteger(myCalender.getTime());
         int row=   obj.updateDiseaseToPatient(dtpid,diseaseid,0,startDate,endDate,pid);

         String diseaseName = obj.getDiseaseName(diseaseid);
         if(row==1)
         {
             Toast.makeText(getContext(),"Patient Recovered from Disease : " + diseaseName,Toast.LENGTH_SHORT).show();
             adapter.updateList(getAllDiseasesToPatient());

         }


        }else
                {

            if(!disease_value1.getText().toString().matches(""))
            {
                newValue = disease_value1.getText().toString();
            }
            else {
                Toast.makeText(getContext(),"Enter new Value first",Toast.LENGTH_SHORT).show();
                return;
            }

            long rowid = obj.addDiseaseValueToPatient(currentdiseasesToPatientObject.getDTP_id(),
                    newValue,Utility.DateToDaysInInteger(myCalender.getTime()));

            if(rowid>0)
            {
                Toast.makeText(getContext(),"Updated!!",Toast.LENGTH_SHORT).show();
            }
                    }
        AddDiseaseHeader();
        ResetEntries();
    }

    void AddDiseaseHeader()
    {
        headerText.setText(addHeader);
        cancelBtn.setText("Reset");
        saveBtn.setText("Save");
        recoveredCheckBox.setVisibility(View.INVISIBLE);

    }

    void AddRecord() {
        if (patientObject == null)
            return;


        long patient_id = patientObject.getPatient_id();
        long disease_id = diseasesObj.getDisease_id();
        if (!check_ifAddisValid(patient_id,disease_id))
        {
            Toast.makeText(getContext(),"Patient is already infected with selected Disease, Please use update Button",Toast.LENGTH_LONG).show();
            return;
        }
       int startDate_In_Days = Utility.DateToDaysInInteger(myCalender.getTime());
        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        long rowid = databaseHelper.addDiseaseToPatient(disease_id,1,
                    startDate_In_Days,
                    startDate_In_Days,
                    patient_id);



        if (rowid > 0) {
            Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();

            databaseHelper.addDiseaseValueToPatient(rowid,disease_value1.getText().toString(),startDate_In_Days);
            adapter.updateList(getAllDiseasesToPatient());
            ResetEntries();
        }
    }

    boolean check_ifAddisValid(long pid,long did)
    {

        long diseaseid = did;
        long patientid = pid;
        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());

        String selection = DiseasesToPatient.COLUMN_PATIENT_ID + " = ? " + " AND " + DiseasesToPatient.COLUMN_STATUS + " = 1" + " AND " +
               DiseasesToPatient.COLUMN_DISEASE_ID  + " =? ";
        String[] selectionArgs = new String[] {
                String.valueOf(patientid),
                String.valueOf(diseaseid)
        };


        ArrayList<DiseasesToPatient> DtoP = databaseHelper.getAllDiseasesToPatient(null,selection,selectionArgs,null,null,null,null);

        if(DtoP.size()>0)
            return false;
            else
        return true;

    }

    void loadDisease()
    {
        DatabaseHelper obj = new DatabaseHelper(getContext());
        all_disease_object = obj.getAllDiseases();
        DiseasesViewModel model = new ViewModelProvider(requireActivity()).get(DiseasesViewModel.class);
        model.getDiseaseShareAbleList().observe(getViewLifecycleOwner(), diseaseShareAbleList ->{

            list_of_diseasesAdapter = new ArrayAdapter<>(getContext(),R.layout.support_simple_spinner_dropdown_item,diseaseShareAbleList);
            list_of_diseasesAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            spinnerDiseaes.setAdapter(list_of_diseasesAdapter);

        });



    }

    ArrayList<DiseasesToPatient> DtoP = new ArrayList<>();

    ArrayList<DiseasesToPatient> getAllDiseasesToPatient()
    {
        if(patientObject==null)
            return null;
        DatabaseHelper o = new DatabaseHelper(getContext());
        //Select all those disease which has status set to running (i.e  1) and related to patient only
        String selection = DiseasesToPatient.COLUMN_PATIENT_ID + " = ? " + " AND " + DiseasesToPatient.COLUMN_STATUS + " = 1";
        String[] selectionArgs = new String[] {
                String.valueOf(patientObject.getPatient_id())
        };

        DtoP = o.getAllDiseasesToPatient(null,selection,selectionArgs,null,null,null,null);
        return DtoP;
    }

    @Override
    public void onListItemClick(int position) {
        if(DtoP.size()>0)
        currentdiseasesToPatientObject = DtoP.get(position);
        recoveredCheckBox.setVisibility(View.VISIBLE);
        headerText.setText(updateHeader);
        cancelBtn.setText(getString(R.string.cancel_disease_update));
        saveBtn.setText("Update");
        spinnerDiseaes.setSelection(position);
    }


}


