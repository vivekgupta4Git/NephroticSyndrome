package com.ruviapps.nephsynd.HelperClasses;

import java.io.Serializable;

public class Patient implements Serializable {

    public static final String COLUMN_PATIENT_ID =  "patient_id";
    public static final String COLUMN_PATIENT_FIRST_NAME="patient_first_name";
    public static final String COLUMN_PATIENT_LAST_NAME="patient_last_name";
    public static final String COLUMN_PATIENT_AGE="patient_age";
    public static final String COLUMN_PATIENT_WEIGHT="patient_weight";
    public static final String COLUMN_PATIENT_SNAP="patient_snap";

    public void setPatient_id(long patient_id) {
        this.patient_id = patient_id;
    }

    public void setPatient_first_name(String patient_first_name) {
        this.patient_first_name = patient_first_name;
    }

    public void setPatient_last_name(String patient_last_name) {
        this.patient_last_name = patient_last_name;
    }

    public void setPatient_weight(Float patient_weight) {
        this.patient_weight = patient_weight;
    }

    public void setPatient_age(int patient_age) {
        this.patient_age = patient_age;
    }

    public void setPatient_snap(byte[] patient_snap) {
        this.patient_snap = patient_snap;
    }

    long patient_id;
    String patient_first_name;
    String patient_last_name;
    Float patient_weight;
    int patient_age;
    byte[] patient_snap;

    public long getPatient_id() {
        return patient_id;
    }

    public String getPatient_first_name() {
        return patient_first_name;
    }

    public String getPatient_last_name() {
        return patient_last_name;
    }

    public Float getPatient_weight() {
        return patient_weight;
    }

    public int getPatient_age() {
        return patient_age;
    }

    public byte[] getPatient_snap() {
        return patient_snap;
    }
}
