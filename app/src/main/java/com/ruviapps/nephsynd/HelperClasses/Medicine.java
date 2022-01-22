package com.ruviapps.nephsynd.HelperClasses;

public class Medicine {
    int medicine_id;
    String medicine_name;

    public void setMedicine_id(int medicine_id) {
        this.medicine_id = medicine_id;
    }

    public void setMedicine_name(String medicine_name) {
        this.medicine_name = medicine_name;
    }

    public static final String COLUMN_MEDICINE_ID="medicine_id";
    public static final String COLUMN_MEDICINE_NAME="medicine_name";
    public int getMedicine_id() {
        return medicine_id;
    }

    public String getMedicine_name() {
        return medicine_name;
    }
}
