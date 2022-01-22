package com.ruviapps.nephsynd.HelperClasses;

public class Diseases {
    long disease_id;
    String disease_name;


    public static final String COLUMN_DISEASE_ID="disease_id";
    public static final String COLUMN_DISEASE_NAME="disease_name";

    public void setDisease_id(int disease_id) {
        this.disease_id = disease_id;
    }

    public void setDisease_name(String disease_name) {
        this.disease_name = disease_name;
    }

    public long getDisease_id() {
        return disease_id;
    }

    public String getDisease_name() {
        return disease_name;
    }
}
