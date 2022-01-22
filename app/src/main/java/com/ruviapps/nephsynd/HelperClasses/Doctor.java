package com.ruviapps.nephsynd.HelperClasses;

public class Doctor {
    int doctor_id;
    String doctor_first_name;
    String doctor_last_name;

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public void setDoctor_first_name(String doctor_first_name) {
        this.doctor_first_name = doctor_first_name;
    }

    public void setDoctor_last_name(String doctor_last_name) {
        this.doctor_last_name = doctor_last_name;
    }

    public static final String COLUMN_DOCTOR_ID="doctor_id";
    public static final String COLUMN_DOCTOR_FIRST_NAME="doctor_first_name";
    public static final String COLUMN_DOCTOR_LAST_NAME="doctor_last_name";
    public int getDoctor_id() {
        return doctor_id;
    }

    public String getDoctor_first_name() {
        return doctor_first_name;
    }

    public String getDoctor_last_name() {
        return doctor_last_name;
    }
}
