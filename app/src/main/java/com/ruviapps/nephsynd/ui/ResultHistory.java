package com.ruviapps.nephsynd.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;

import android.icu.util.UniversalTimeScale;
import android.os.Bundle;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnCalendarPageChangeListener;
import com.ruviapps.nephsynd.DbHelper.DatabaseHelper;
import com.ruviapps.nephsynd.HelperClasses.DailyUrineResult;
import com.ruviapps.nephsynd.HelperClasses.Utility;
import com.ruviapps.nephsynd.R;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class ResultHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_result_history);
        CalendarView calenderView = (CalendarView)findViewById(R.id.calendarView);

        List<EventDay> eventDayList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2021,6,19);
       // eventDayList.add(new EventDay(calendar,R.drawable.ic_trace,Color.yellow));
        EventDay day = new EventDay(calendar,R.drawable.ic_negative);
        eventDayList.add(day);
       Calendar calendar1 = Calendar.getInstance();
        calendar1.set(2021,6,18);
        eventDayList.add(new EventDay(calendar1,R.drawable.ic_trace));
        calenderView.setEvents(eventDayList);

//       myEventDay day1 = new myEventDay(calendar,R.drawable.ic_negative,"Negative");
  //      eventDayList.add(day1);
        Calendar date =calenderView.getCurrentPageDate();
        Date d= date.getTime();

        Log.v("RuviApps","Calendar Month is : " + d.getMonth());
//        Toast.makeText(ResultHistory.this,"Calendar Month is : "+ d.getMonth(),Toast.LENGTH_SHORT);

        calenderView.setOnPreviousPageChangeListener(new OnCalendarPageChangeListener() {
    @Override
    public void onChange() {

        Calendar date =calenderView.getCurrentPageDate();
        Date d= date.getTime();
        Log.v("RuviApps","Calendar Month after previousPage is : " + d.getMonth());
        //Toast.makeText(ResultHistory.this,"Calendar Month is : "+ d.getMonth(),Toast.LENGTH_SHORT);

    }
});


        calenderView.setOnForwardPageChangeListener(new OnCalendarPageChangeListener() {
            @Override
            public void onChange() {
                Calendar dd = calenderView.getCurrentPageDate();
                Date dd2 = dd.getTime();
                Log.v("RuviApps","Calender Month after Forwarding is : " + d.getMonth());
                //Toast.makeText(ResultHistory.this, "Calendar Month is : " + dd2.getMonth(), Toast.LENGTH_SHORT).show();
            }
        });
        List<Calendar> calendarsList = new ArrayList<>();
        calendarsList.add(calendar);
        calendarsList.add(calendar1);

        calenderView.setHighlightedDays(calendarsList);



    }


    void loadResultForMonth(int month, int year) throws ParseException {
        String startDateString = "1" + " - " + (month+1) + " - " + year;
       String endDateString;
        if(month==1) //feb month then check for leap year
        {
            if(isLeadYear(year))
            {
            endDateString = "28" + " - "  + (month + 1) + " - " + year;
            }else
            {
                endDateString = "29 " + "- " + (month +1) + " - " + year;
            }

        }
        else if(month%2==1 || month == 7) {

            endDateString = "31" + " - " + (month + 1) + " - " + year;
        }else
        {
            endDateString = "30" + " - " + (month +1) + " - " + year;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd - MM - yyyy");

       Date startingDate = dateFormat.parse(startDateString);
       Date endingDate = dateFormat.parse(endDateString);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        String selection = DailyUrineResult.COLUMN_PATIENT_ID + " =? " + " AND " + DailyUrineResult.COLUMN_DAILY_DATE + " >= ? "
                                                                        + "AND " + DailyUrineResult.COLUMN_DAILY_DATE + " <= ?";

        String[] selectionArgs = { String.valueOf(1)
                                    , String.valueOf(Utility.DateToDaysInInteger(startingDate))
                                    , String.valueOf(Utility.DateToDaysInInteger(endingDate))

                                    };

    }

    boolean isLeadYear(int year)
    {
        boolean isLeap = false;

        if(year % 4 == 0)
        {
            if( year % 100 == 0)
            {
                if ( year % 400 == 0)
                    isLeap = true;
                else
                    isLeap = false;
            }
            else
                isLeap = true;
        }
        else {
            isLeap = false;
        }

        return  isLeap;
    }

/*
    public class myEventDay extends EventDay
    //        implements Parcelable
    {

        private String mResult;
        private int drawable_int;

        public myEventDay(Calendar day, int drawable, String result) {
            super(day, drawable);
            //   drawable_int= drawable;
            mResult = result;
        }

        public String get_mResult() {
            return mResult;
        }

     /*   protected myEventDay(Parcel in) {
            super((Calendar)in.readSerializable(),in.readInt());
            mResult = in.readString();
        }
*/
     /*   public final Creator<myEventDay> CREATOR = new Creator<myEventDay>() {
            @Override
            public myEventDay createFromParcel(Parcel in) {
                return new myEventDay(in);
            }

            @Override
            public myEventDay[] newArray(int size) {
                return new myEventDay[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeSerializable(getCalendar());
        //    dest.writeInt(getImageResource());
            dest.writeInt(drawable_int);
            dest.writeString(mResult);
        }




    }

    */
}