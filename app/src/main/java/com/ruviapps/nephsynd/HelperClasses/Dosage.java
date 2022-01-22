package com.ruviapps.nephsynd.HelperClasses;


import java.util.Date;

public class Dosage {
    long dosage_id;
    long medicine_id;
    long patient_id;
    String dosage_value;
    int given_on_date;
    long doctor_id;
    long disease_id;

    public static final String COLUMN_DOSAGE_ID="dosage_id";
    public static final String COLUMN_MEDICINE_ID="medicine_id";
    public static final String COLUMN_PATIENT_ID="patient_id";
    public static final String COLUMN_DOSAGE_VALUE="dosage_value";
    public static final String COLUMN_GIVEN_ON_DATE="given_on_date";
    public static final String COLUMN_DOCTOR_ID="doctor_id";
    public static final String COLUMN_DISEASE_ID="disease_id";

    public long getDosage_id() {
        return dosage_id;
    }

    public void setDosage_id(long dosage_id) {
        this.dosage_id = dosage_id;
    }

    public long getMedicine_id() {
        return medicine_id;
    }

    public void setMedicine_id(long medicine_id) {
        this.medicine_id = medicine_id;
    }

    public long getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(long patient_id) {
        this.patient_id = patient_id;
    }

    public String getDosage_value() {
        return dosage_value;
    }

    public void setDosage_value(String dosage_value) {
        this.dosage_value = dosage_value;
    }

    public int getGiven_on_date() {
        return given_on_date;
    }

    public void setGiven_on_date(int given_on_date) {
        this.given_on_date = given_on_date;
    }

    public long getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(long doctor_id) {
        this.doctor_id = doctor_id;
    }

    public long getDisease_id() {
        return disease_id;
    }

    public void setDisease_id(long disease_id) {
        this.disease_id = disease_id;
    }
}
