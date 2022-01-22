package com.ruviapps.nephsynd.HelperClasses;



import java.util.Date;

public class Relapse {
    long relapse_id;
    long patient_id;
    int start_date;
    int end_date;
    int duration;

    public static final String COLUMN_RELAPSE_ID="relapse_id";
    public static final String COLUMN_PATIENT_ID="patient_id";
    public static final String COLUMN_START_DATE="start_date";
    public static final String COLUMN_END_DATE="end_date";
    public static final String COLUMN_DURATION="duration";

    public long getRelapse_id() {
        return relapse_id;
    }

    public long getPatient_id() {
        return patient_id;
    }

    public int getStart_date() {
        return start_date;
    }

    public int getEnd_date() {
        return end_date;
    }

    public int getDuration() {
        return duration;
    }

    public void setRelapse_id(long relapse_id) {
        this.relapse_id = relapse_id;
    }

    public void setPatient_id(long patient_id) {
        this.patient_id = patient_id;
    }

    public void setStart_date(int start_date) {
        this.start_date = start_date;
    }

    public void setEnd_date(int end_date) {
        this.end_date = end_date;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
