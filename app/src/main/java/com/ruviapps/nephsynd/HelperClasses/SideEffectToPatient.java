package com.ruviapps.nephsynd.HelperClasses;

public class SideEffectToPatient {
    long SETP_id;
    long side_effect_id;
    long patient_id;
    String explain;

    public static final String COLUMN_SEPT_ID="SETP_id";
    public static final String COLUMN_SIDE_EFFECT_ID="sideEffect_id";
    public static final String COLUMN_PATIENT_ID="patient_id";
    public static final String COLUMN_EXPLAIN = "explain_sideEffect";


    public long getSETP_id() {
        return SETP_id;
    }

    public void setSETP_id(long SETP_id) {
        this.SETP_id = SETP_id;
    }

    public long getSide_effect_id() {
        return side_effect_id;
    }

    public void setSide_effect_id(long side_effect_id) {
        this.side_effect_id = side_effect_id;
    }

    public long getPatient_id() {
        return patient_id;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public void setPatient_id(long patient_id) {
        this.patient_id = patient_id;
    }
}
