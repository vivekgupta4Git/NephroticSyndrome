package com.ruviapps.nephsynd.HelperClasses;

import android.content.Intent;
import android.provider.MediaStore;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Utility {

   /* public static int getCurrentDate()
    {
        int i = (int)(new Date().getTime()/1000);
            return i;

    }
*/
    // magic number=
// millisec * sec * min * hours
// 1000 * 60 * 60 * 24 = 86400000
    public static final long MAGIC=86400000L;
    private static final TimeZone timezone = TimeZone.getTimeZone("GMT+0530");

  /*  public static int DateToDays (Date date){
        //  convert a date to an integer and back again
        long currentTime=date.getTime();
        currentTime=(currentTime + timezone.getRawOffset())/MAGIC;
        return (int) currentTime;
    }
*/
    public static String DatetoDaysAsString(Date date) {


        long timeInMilliSeconds = date.getTime();
        Log.v("RuviApps","Time in MiliSeconds " + String.valueOf(timeInMilliSeconds));
        Log.v("RuviApps","Raw Offset : " + timezone.getRawOffset());
        timeInMilliSeconds = (timeInMilliSeconds + timezone.getRawOffset())/MAGIC;

        return   String.valueOf(timeInMilliSeconds);

    }

    public static int DateToDaysInInteger(Date date)
    {
        long timeInMilliSeconds = date.getTime();
        Log.v("RuviApps","Time in MiliSeconds " + String.valueOf(timeInMilliSeconds));
        Log.v("RuviApps","Raw Offset : " + timezone.getRawOffset());
        timeInMilliSeconds = (timeInMilliSeconds + timezone.getRawOffset())/MAGIC;

        return  (int) timeInMilliSeconds;
    }


   /* public static Date DaysToDate(int days) {
        //  convert integer back again to a date
        long currentTime=(long) ((days*MAGIC) + timezone.getRawOffset());

        return new Date(currentTime);


    }
*/
    public static String DaysToDateString(int days)
    {

        long currentTime=(long) ((days*MAGIC) + timezone.getRawOffset());

        Date d = new Date(currentTime);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
       return dateFormat.format(d);

    }

    public static String FormattedDate(Date d)
    {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String FormattedDate_string = dateFormat.format(d);
        return FormattedDate_string;
    }

    public static Date daysToDate(int days)
    {
        Date date = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            date =    dateFormat.parse( Utility.DaysToDateString(days));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }
   /* public static Date FormattedDateFromString(String date_value_as_String,String pattern_of_string_for_simpleDateFormatter)
    {
        Date date = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern_of_string_for_simpleDateFormatter); //"dd-MM-yyyy

        try {
            date= dateFormat.parse(date_value_as_String);
        } catch (ParseException e) {
            e.printStackTrace();
        }

return  date;
    }
*/

    public static Intent getCameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 0);
        intent.putExtra("aspectY", 0);
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 200);

        return  intent;
    }

    //startActivityForResult(getCameraIntent(), 1);
}



