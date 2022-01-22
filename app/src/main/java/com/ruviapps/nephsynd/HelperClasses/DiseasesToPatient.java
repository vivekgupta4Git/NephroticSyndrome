package com.ruviapps.nephsynd.HelperClasses;

public class DiseasesToPatient {
    long DTP_id;
    long disease_id;
    long patient_id;
    int start_date;
    int end_date;
    int status;

    public static final String COLUMN_DTP_ID="DTP_id";
    public static final String COLUMN_DISEASE_ID="disease_id";
    public static final String COLUMN_PATIENT_ID="patient_id";
    public static final String COLUMN_START_DATE="start_date";
    public static final String COLUMN_END_DATE="end_date";
    public static final String COLUMN_STATUS="status";

    public long getDTP_id() {
        return DTP_id;
    }

    public long getDisease_id() {
        return disease_id;
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

    public int getStatus() {
        return status;
    }

    public void setDTP_id(long DTP_id) {
        this.DTP_id = DTP_id;
    }

    public void setDisease_id(long disease_id) {
        this.disease_id = disease_id;
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

    public void setStatus(int status) {
        this.status = status;
    }


}
