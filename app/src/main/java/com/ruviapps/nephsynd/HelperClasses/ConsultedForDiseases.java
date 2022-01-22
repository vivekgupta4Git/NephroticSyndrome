package com.ruviapps.nephsynd.HelperClasses;

public class ConsultedForDiseases {
    long consulted_for_diseasedId;
    long diseases_id;
    long consultation_id;
    int onDate;


    public static final String COLUMN_CONSULTED_FOR_DISEASED_ID = "cfd_id";
    public static final String COLUMN_DISEASE_ID ="disease_id";
    public static final String COLUMN_CONSULTATION_ID = "consultation_id";
    public static final String COLUMN_ON_DATE = "onDate";

    public long getConsulted_for_diseasedId() {
        return consulted_for_diseasedId;
    }

    public void setConsulted_for_diseasedId(long consulted_for_diseasedId) {
        this.consulted_for_diseasedId = consulted_for_diseasedId;
    }

    public long getDiseases_id() {
        return diseases_id;
    }

    public void setDiseases_id(long diseases_id) {
        this.diseases_id = diseases_id;
    }

    public long getConsultation_id() {
        return consultation_id;
    }

    public void setConsultation_id(long consultation_id) {
        this.consultation_id = consultation_id;
    }

    public int getOnDate() {
        return onDate;
    }

    public void setOnDate(int onDate) {
        this.onDate = onDate;
    }


}
