package com.ruviapps.nephsynd.ui;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.ruviapps.nephsynd.DbHelper.DatabaseHelper;
import com.ruviapps.nephsynd.HelperClasses.DailyUrineResult;
import com.ruviapps.nephsynd.HelperClasses.Patient;
import com.ruviapps.nephsynd.HelperClasses.Relapse;
import com.ruviapps.nephsynd.HelperClasses.UrineResultConstants;
import com.ruviapps.nephsynd.HelperClasses.Utility;
import com.ruviapps.nephsynd.R;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class FragmentResultRegister extends Fragment {
    private static final String ARG_FRAG = "Patientobject";
  //  ArrayList<Patient> all_patient_object;
    Patient patientObject;
    Button dosageBtn, sideEffectBtn, diseaseBtn;
    int Result;
    long current_patient_id;
    TextView changeDateTextView;
    Calendar myCalender;


    String[] resultArray = new String[]{
            UrineResultConstants.NEGATIVE_RESULT,
            UrineResultConstants.TRACE_RESULT,
            UrineResultConstants.ONE_PLUS_RESULT,
            UrineResultConstants.TWO_PLUS_RESULT,
            UrineResultConstants.THREE_PLUS_RESULT ,
            UrineResultConstants.FOUR_PLUS_RESULT
    };


    DatabaseHelper obj;

    ArrayList<Integer> previousTwoDayResult;
    int RelapseStartDate;
    private long RelapseId;
    TimeZone timeZone;


    public static FragmentResultRegister newInstance(Patient po)
    {
        FragmentResultRegister fragment = new FragmentResultRegister();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_FRAG, po);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
    obj  = new DatabaseHelper(getContext());
//        all_patient_object = obj.getAllPatients();
        patientObject = (Patient) getArguments().getSerializable(ARG_FRAG);
if(patientObject!=null)
        current_patient_id = patientObject.getPatient_id();

                   View view = inflater.inflate(R.layout.fragmentlayout_result_register, container, false);
        changeDateTextView = view.findViewById(R.id.tv_resultDatePick);

myCalender = Calendar.getInstance();
timeZone = TimeZone.getTimeZone("GMT+0530");
myCalender.setTimeZone(timeZone);

changeDateTextView.setText(Utility.FormattedDate(myCalender.getTime()));
        getLastTwoDaysResult(view);


        return view;
    }


    String getTextForResult(int result)
    {
        String res;

        switch (result)
        {
            case -1 :
                res = UrineResultConstants.NEGATIVE_RESULT;
                break;
            case 0 :
                res = UrineResultConstants.TRACE_RESULT;
                break;
            case 1 :
                res = UrineResultConstants.ONE_PLUS_RESULT;
                break;
            case 2 :
                res = UrineResultConstants.TWO_PLUS_RESULT;
                break;
            case 3 :
                res = UrineResultConstants.THREE_PLUS_RESULT;
                break;
            case 4 :
                res = UrineResultConstants.FOUR_PLUS_RESULT;
                break;
            default:
                res = " NOT AVAILABLE";

        }

        return  res;
    }


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Spinner result_spinner = view.findViewById(R.id.sp_result);
        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item,resultArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ImageButton changeDate = view.findViewById(R.id.ib_resultDatePickBtn);
        if(patientObject==null)
            changeDate.setEnabled(false);
        else
            changeDate.setEnabled(true);

            final DatePickerDialog.OnDateSetListener dateSetListener = (view1, year, month, dayOfMonth) -> {
            myCalender.set(Calendar.YEAR,year);
            myCalender.set(Calendar.MONTH,month);
            myCalender.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            changeDateTextView.setText(Utility.FormattedDate(myCalender.getTime()));

            getLastTwoDaysResult(view);
        };


        changeDate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getContext(),dateSetListener,  myCalender.get(Calendar.YEAR),
                    myCalender.get(Calendar.MONTH),
                    myCalender.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });



        result_spinner.setAdapter(adapter);

        result_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setResultWhenSelected(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        Button saveBtn = view.findViewById(R.id.bt_saveResult);

        if(patientObject==null)
                saveBtn.setEnabled(false);
        else
            saveBtn.setEnabled(true);

            saveBtn.setOnClickListener(v -> {

            if(Result==5)
                return;

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Relapse Occurred");
            builder.setMessage("Do you want to mark this as Relapse?");
            builder.setPositiveButton("Yes", (dialog, which) -> {
                makeEntryInRelapseTable();
                dialog.dismiss();
            });
            builder.setNegativeButton("No",(dialog, which) ->{
                dialog.dismiss();
            });

            AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
            builder1.setTitle("Relapse seems to go away!!");
            builder1.setMessage("Do you want to mark this end of Relapse?");

            builder1.setPositiveButton("Yes! please!", (dailog,which)->{
               updateEntryInRelapseTable();
               dailog.dismiss();
            });

            builder1.setNegativeButton("No,Wait for few more days",(dialog,which)->{
               dialog.dismiss();
            });


            if(previousTwoDayResult.size()>1){

                if(previousTwoDayResult.get(0)>=2 && previousTwoDayResult.get(1)>=2 && Result >=2)
                {
                       if ( ! checkForRelapseOccurence() && !checkForEntry())
                       {
                           AlertDialog createRelapseDialog =  builder.create();
                                createRelapseDialog.show();
                       }
                }
                else if(previousTwoDayResult.get(0)<=0 && previousTwoDayResult.get(1)<=0 && Result <=0)
                {

                    if(checkForRelapseOccurence() && !checkForEntry())
                    {
                        AlertDialog updateRelpaseDialog = builder1.create();
                        updateRelpaseDialog.show();
                    }
                }
            }


            Date selectedDate  =   myCalender.getTime();
            long timeInMili = selectedDate.getTime();
            long days = (timeInMili + timeZone.getRawOffset())/Utility.MAGIC;
            int i = (int)days;

            if(!checkForEntry()) {


             long    row_id = obj.addDailyResult(current_patient_id, Result, patientObject.getPatient_weight(), "Nothing", i);

                if(row_id>0 )
                {
                    Toast.makeText(getContext(),"Success",Toast.LENGTH_SHORT).show();
                    getLastTwoDaysResult(view);
                }
            }else
            {
                Toast.makeText(getContext(),"Only one entry per day is allowed.",Toast.LENGTH_SHORT).show();
            }

        });



        FrameLayout diseaselayout = view.findViewById(R.id.fragment_container_disease);
        diseaselayout.setVisibility(View.INVISIBLE);

        FrameLayout dosageLayout = view.findViewById(R.id.fragment_container_dosage);
        dosageLayout.setVisibility(View.INVISIBLE);

        FrameLayout sideEffectLayout = view.findViewById(R.id.fragment_container_sideEffect);
        sideEffectLayout.setVisibility(View.INVISIBLE);


        dosageBtn = view.findViewById(R.id.bt_dosage);
        dosageBtn.setOnClickListener(v -> {
            if(dosageLayout.getVisibility()==View.VISIBLE)
            {
                diseaselayout.setVisibility(View.INVISIBLE);
                sideEffectLayout.setVisibility(View.INVISIBLE);
                dosageLayout.setVisibility(View.INVISIBLE);
            }else {
                diseaselayout.setVisibility(View.INVISIBLE);
                sideEffectLayout.setVisibility(View.INVISIBLE);
                dosageLayout.setVisibility(View.VISIBLE);
            }
            Fragment DosageFrag = FragmentDosage.newInstance(patientObject);
                FragmentManager fragmentManager= getChildFragmentManager();
                if(fragmentManager.findFragmentById(R.id.fragment_container_dosage) == null)
                {
                    fragmentManager.beginTransaction().replace(R.id.fragment_container_dosage,DosageFrag).addToBackStack(null).commit();
                }

        });

        diseaseBtn = view.findViewById(R.id.bt_disease);
        diseaseBtn.setOnClickListener(v -> {
           if(diseaselayout.getVisibility()==View.VISIBLE)
           {
               diseaselayout.setVisibility(View.INVISIBLE);
               sideEffectLayout.setVisibility(View.INVISIBLE);
               dosageLayout.setVisibility(View.INVISIBLE);

           }
            else {
               diseaselayout.setVisibility(View.VISIBLE);
               sideEffectLayout.setVisibility(View.INVISIBLE);
               dosageLayout.setVisibility(View.INVISIBLE);
           }
            FragmentManager fm = getChildFragmentManager();

            Fragment diseaseFrag = FragmentDiseases.newInstance(patientObject);
            if(fm.findFragmentById(R.id.fragment_container_disease)== null)
            {
                fm.beginTransaction().replace(R.id.fragment_container_disease,diseaseFrag).addToBackStack(null).commit();
            }

        });

        sideEffectBtn = view.findViewById(R.id.bt_sideEffect);
        sideEffectBtn.setOnClickListener(v->{

            if(sideEffectLayout.getVisibility()==View.VISIBLE) {
                sideEffectLayout.setVisibility(View.INVISIBLE);
                diseaselayout.setVisibility(View.INVISIBLE);
                dosageLayout.setVisibility(View.INVISIBLE);
            }else{
                diseaselayout.setVisibility(View.INVISIBLE);
                sideEffectLayout.setVisibility(View.VISIBLE);
                dosageLayout.setVisibility(View.INVISIBLE);
            }
            FragmentManager fm = getChildFragmentManager();
            Fragment sideEffectFrag = FragmentSideEffects.newInstance(patientObject);
            if(fm.findFragmentById(R.id.fragment_container_sideEffect)== null)
            {
                fm.beginTransaction().replace(R.id.fragment_container_sideEffect,sideEffectFrag).addToBackStack(null).commit();
            }


        });


        Button moreBtn = view.findViewById(R.id.btn_more);
        moreBtn.setOnClickListener(v->{
            Intent intent = new Intent(getActivity(),ResultHistory.class);
            startActivity(intent);
        });
    }

    private void updateEntryInRelapseTable() {

//        Date selectedDate  =   myCalender.getTime();
//        long timeInMili = selectedDate.getTime();
//        long days = (timeInMili + timeZone.getRawOffset())/Utility.MAGIC;
        int currentDateAsIntegerDays;
      currentDateAsIntegerDays =  Utility.DateToDaysInInteger(myCalender.getTime());
        int rowupdated = obj.updateRelapse(RelapseId,current_patient_id, RelapseStartDate, currentDateAsIntegerDays, 0);
        if (rowupdated > 0) {
            Toast.makeText(getContext(), "Relapse Updated !! Thank God for Ending Relapse", Toast.LENGTH_SHORT).show();
        }

    }


    void makeEntryInRelapseTable() {

/*
        Date selectedDate  =   myCalender.getTime();
        long timeInMili = selectedDate.getTime();
        long days = (timeInMili + timeZone.getRawOffset())/Utility.MAGIC;
*/
        int currentDateAsIntegerDays;
        currentDateAsIntegerDays = Utility.DateToDaysInInteger(myCalender.getTime());

        long rowid = obj.addRelapse(current_patient_id, currentDateAsIntegerDays, currentDateAsIntegerDays, 1);
        if (rowid > 0) {
            Toast.makeText(getContext(), "Relapse Entry Done!!", Toast.LENGTH_SHORT).show();
        }
    }

    boolean checkForEntry()
    {
        boolean EntryDone = false;


       // int DateAsInteger = Utility.DateToDays(Utility.FormattedDateFromString(changeDateTextView.getText().toString(),"dd-MM-yyyy"));
       // Date selectedDate  =   myCalender.getTime();
      //  long timeInMili = selectedDate.getTime();
       // long days = (timeInMili + timeZone.getRawOffset())/Utility.MAGIC;
        int DateAsInteger = Utility.DateToDaysInInteger(myCalender.getTime());


    ArrayList<DailyUrineResult> dailyUrineResultArrayList=   obj.getAllDailyResult(null,DailyUrineResult.COLUMN_PATIENT_ID + " =? " + " AND " + DailyUrineResult.COLUMN_DAILY_DATE + " =?"

        ,new String[] { String.valueOf(current_patient_id), String.valueOf(DateAsInteger) },null,null,null,null);

        if(dailyUrineResultArrayList.size()>0 && dailyUrineResultArrayList!= null)
            EntryDone = true;

        return EntryDone;
    }

    boolean checkForRelapseOccurence()
    {
        boolean RelapseRunning = false;

   //     Date selectedDate  =   myCalender.getTime();
    //    long timeInMili = selectedDate.getTime();
     //   long days = (timeInMili + timeZone.getRawOffset())/Utility.MAGIC;
        int int_of_days = Utility.DateToDaysInInteger(myCalender.getTime());


        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
//String selection = Relapse.COLUMN_PATIENT_ID + " =?" + " AND " + Relapse.COLUMN_START_DATE + " >=?" ;
       ArrayList<Relapse> relapseArrayList = databaseHelper.getAllRelapse(null,Relapse.COLUMN_PATIENT_ID + " =? " +
                " AND " +  Relapse.COLUMN_START_DATE + " <? ",
               new String[]{ String.valueOf(current_patient_id),String.valueOf(int_of_days)},
               null,null, Relapse.COLUMN_RELAPSE_ID + " DESC ",null);


       for(Relapse r : relapseArrayList)
       {

           if(r.getDuration() == 1)
           {
               RelapseId = r.getRelapse_id();
               RelapseRunning = true;
               RelapseStartDate = r.getStart_date();
           }

       }
        return RelapseRunning;

    }



    void setResultWhenSelected(int position)
    {
        switch (resultArray[position])
        {
            case UrineResultConstants.NEGATIVE_RESULT:
                Result = -1;
                break;
            case UrineResultConstants.TRACE_RESULT:
                Result = 0;
                break;
            case UrineResultConstants.ONE_PLUS_RESULT:
                Result = 1;
                break;
            case UrineResultConstants.TWO_PLUS_RESULT:
                Result = 2;
                break;
            case UrineResultConstants.THREE_PLUS_RESULT:
                Result = 3;
                break;
            case UrineResultConstants.FOUR_PLUS_RESULT:
                Result = 4;
                break;
            default:
                Result = 5;
        }


    }


    public void getLastTwoDaysResult(View view) {

        //SELECT * FROM DailyUrineResults WHERE patient_id=1 ORDER BY date_date DESC LIMIT 2

       // current_patient_id = patientObject.getPatient_id();


    Log.v("RuviApps",   Utility.DatetoDaysAsString(myCalender.getTime()));
  //   Date selectedDate  =   myCalender.getTime();
   //  long timeInMili = selectedDate.getTime();
   //  long days = (timeInMili + timeZone.getRawOffset())/Utility.MAGIC;
     int currentDateSelected = Utility.DateToDaysInInteger(myCalender.getTime());

       /*String selection = DailyUrineResult.COLUMN_PATIENT_ID + " = ?" + " AND ( " + DailyUrineResult.COLUMN_DAILY_DATE + " = ? "
                + " OR " + DailyUrineResult.COLUMN_DAILY_DATE + " =? "
                + "OR " +  DailyUrineResult.COLUMN_DAILY_DATE + " =? )";
        */

        String selection = DailyUrineResult.COLUMN_PATIENT_ID + " =? " + " AND " + DailyUrineResult.COLUMN_DAILY_DATE + " <= ? ";
        String orderby = DailyUrineResult.COLUMN_DAILY_DATE + " DESC" ;
        String limit = "3";
        String[] selectionARgs = new String[]{
                String.valueOf(current_patient_id),
             //   String.valueOf(yesterdayDay), String.valueOf(aDayBeforeYesterday),
                String.valueOf(currentDateSelected)
        };


        TextView textView = view.findViewById(R.id.tv_lastTwoDayResult);
        ArrayList<DailyUrineResult> dailyUrineResultArrayList = obj.getAllDailyResult(null,selection,selectionARgs,null,null,orderby,limit);
    textView.setText(R.string.resultHistory);
      previousTwoDayResult = new ArrayList<>();

      textView.append("\n");
        StringBuilder stringBuilder = new StringBuilder();
        for(DailyUrineResult d : dailyUrineResultArrayList)
        {
            String date = Utility.DaysToDateString(d.getDaily_date());

            stringBuilder.append(getString(R.string.onDate) + date + getString(R.string.arrowSymbol) + getTextForResult(d.getResult()));
            stringBuilder.append("\n");
            previousTwoDayResult.add(d.getResult());
        }

String textToDisplay = stringBuilder.toString();
if(textToDisplay.matches(""))
    textToDisplay = getString(R.string.noRecordFound);


    textView.append(textToDisplay);


    }




}
