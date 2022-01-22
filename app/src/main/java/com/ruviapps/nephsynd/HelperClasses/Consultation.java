package com.ruviapps.nephsynd.HelperClasses;

import java.util.Date;

public class Consultation {
    long consultation_id;
    long doctor_id;
    long patient_id;
    int date_of_consultation;
    int next_followup;

    public static final String COLUMN_CONSULTATION_ID="consultation_id";
    public static final String COLUMN_DOCTOR_ID ="doctor_id";
    public static final String COLUMN_PATIENT_ID="patient_id";
    public static final String COLUMN_DATE="date_of_consultation";
    public static final String COLUMN_NEXT_FOLLOWUP="next_followup";

    public long getConsultation_id() {
        return consultation_id;
    }

    public void setConsultation_id(long consultation_id) {
        this.consultation_id = consultation_id;
    }

    public long getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(long doctor_id) {
        this.doctor_id = doctor_id;
    }

    public long getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(long patient_id) {
        this.patient_id = patient_id;
    }

    public int getDate_of_consultation() {
        return date_of_consultation;
    }

    public void setDate_of_consultation(int date_of_consultation) {
        this.date_of_consultation = date_of_consultation;
    }

    public int getNext_followup() {
        return next_followup;
    }

    public void setNext_followup(int next_followup) {
        this.next_followup = next_followup;
    }
}
