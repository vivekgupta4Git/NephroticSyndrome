package com.ruviapps.nephsynd.HelperClasses;

import java.io.Serializable;

public class Prescribtion implements Serializable {
    long prescribtion_id;
    long consult_id;
    long medicine_id;
    String prescribed_dosage;
    String frequency;
    String unit;

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public static final String COLUMN_PRESCRIBE_ID="prescribtion_id";
    public static final String COLUMN_CONSULT_ID   ="consult_id";
    public static final String COLUMN_MEDICINE_ID   ="medicine_id";
    public static final String COLUMN_PRESCRIBED_DOSAGE="prescribed_dosage";
    public static final String COLUMN_PRESCRIBED_FREQ = "frequency";
    public static final String COLUMN_PRESCRIBED_UNIT = "unit";
    public static String getColumnPrescribedDosage() {
        return COLUMN_PRESCRIBED_DOSAGE;
    }


    public long getPrescribtion_id() {
        return prescribtion_id;
    }

    public void setPrescribtion_id(long prescribtion_id) {
        this.prescribtion_id = prescribtion_id;
    }

    public long getConsult_id() {
        return consult_id;
    }

    public void setConsult_id(long consult_id) {
        this.consult_id = consult_id;
    }

    public long getMedicine_id() {
        return medicine_id;
    }

    public void setMedicine_id(long medicine_id) {
        this.medicine_id = medicine_id;
    }

    public String getPrescribed_dosage() {
        return prescribed_dosage;
    }


    public void setPrescribed_dosage(String prescribed_dosage) {
        this.prescribed_dosage = prescribed_dosage;
    }
}
