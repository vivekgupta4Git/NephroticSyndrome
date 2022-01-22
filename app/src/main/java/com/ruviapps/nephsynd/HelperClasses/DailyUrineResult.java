package com.ruviapps.nephsynd.HelperClasses;

import java.util.Date;

public class DailyUrineResult {

    public static final String COLUMN_DUR_ID = "DUR_id";
    public static final String COLUMN_PATIENT_ID= "patient_id";
    public static final String COLUMN_RESULT="result";
    public static final String COLUMN_DAILY_DATE="daily_date";
    public static final String COLUMN_WEIGHT="weight";
    public static final String COLUMN_REMARKS="remarks";

    long DUR_id;
    long patient_id;
    int result;
    int daily_date;
    float weight;
    String Remarks;

    public long getDUR_id() {
        return DUR_id;
    }

    public long getPatient_id() {
        return patient_id;
    }

    public int getResult() {
        return result;
    }

    public int getDaily_date() {
        return daily_date;
    }

    public float getWeight() {
        return weight;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setDUR_id(long DUR_id) {
        this.DUR_id = DUR_id;
    }

    public void setPatient_id(long patient_id) {
        this.patient_id = patient_id;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public void setDaily_date(int daily_date) {
        this.daily_date = daily_date;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }
}
