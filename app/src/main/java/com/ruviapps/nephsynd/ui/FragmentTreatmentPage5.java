package com.ruviapps.nephsynd.ui;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.ruviapps.nephsynd.HelperClasses.Utility;
import com.ruviapps.nephsynd.R;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class FragmentTreatmentPage5 extends Fragment {
    TextView textView;
    DatePicker datePicker;
    TimeZone timeZone;

    int minDate;

    public static FragmentTreatmentPage5 newInstance(int startingDate){
        FragmentTreatmentPage5 fragment =new FragmentTreatmentPage5();
        Bundle bundle = new Bundle();
        bundle.putInt("startDate",startingDate);
        fragment.setArguments(bundle);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        minDate = getArguments().getInt("startDate");
        Date date = Utility.daysToDate(minDate+1);
        Log.v("RuviApps","Date Received is : " + Utility.DaysToDateString(minDate));

        View view =inflater.inflate(R.layout.treatment_child_page5,container,false);
        Calendar mycalender = Calendar.getInstance();
        timeZone = TimeZone.getTimeZone("GMT+0530");
        mycalender.setTimeZone(timeZone);
        mycalender.setTime(date);



        textView  = view.findViewById(R.id.tv_selectedDateTreatment);
        datePicker = view.findViewById(R.id.datePicker_treatment);
        datePicker.setMinDate(mycalender.getTimeInMillis());
        mycalender.add(Calendar.DAY_OF_MONTH,7);
datePicker.init(mycalender.get(Calendar.YEAR), mycalender.get(Calendar.MONTH), mycalender.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        textView.setText(getCurrentDate());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = dateFormat.parse(textView.getText().toString());
            Log.v("RuviApps",date.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //listener.onFragmentCreated(Utility.DateToDaysInInteger(mycalender.getTime()));
        Bundle result = new Bundle();
        result.putInt("PagerCount",5);
        result.putInt("DateInDays",Utility.DateToDaysInInteger(date));
        // The child fragment needs to still set the result on its parent fragment manager
        getParentFragmentManager().setFragmentResult("fifthRequestKey", result);

    }

});
        textView.setText(getCurrentDate());
       return view;
    }

    String getCurrentDate()
    {
        StringBuilder builder= new StringBuilder();
        builder.append(datePicker.getDayOfMonth());
        builder.append("/"+ (datePicker.getMonth()+1));
        builder.append("/"+ datePicker.getYear());
        return builder.toString();
    }

}
