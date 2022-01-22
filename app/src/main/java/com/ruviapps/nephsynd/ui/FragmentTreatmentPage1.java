package com.ruviapps.nephsynd.ui;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

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

public class FragmentTreatmentPage1 extends Fragment {
    TextView textView;
    DatePicker datePicker;
    TimeZone timeZone;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.treatment_child_page1,container,false);
        Calendar mycalender = Calendar.getInstance();
        timeZone = TimeZone.getTimeZone("GMT+0530");
        mycalender.setTimeZone(timeZone);

        textView  = view.findViewById(R.id.tv_selectedDateTreatment);

        datePicker = view.findViewById(R.id.datePicker_treatment);
datePicker.init(mycalender.get(Calendar.YEAR), mycalender.get(Calendar.MONTH), mycalender.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        textView.setText(getCurrentDate());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = dateFormat.parse(textView.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //listener.onFragmentCreated(Utility.DateToDaysInInteger(mycalender.getTime()));
        Bundle result = new Bundle();
        result.putInt("PagerCount",1);
        result.putInt("DateInDays",Utility.DateToDaysInInteger(date));
        // The child fragment needs to still set the result on its parent fragment manager
        getParentFragmentManager().setFragmentResult("requestKey", result);

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
