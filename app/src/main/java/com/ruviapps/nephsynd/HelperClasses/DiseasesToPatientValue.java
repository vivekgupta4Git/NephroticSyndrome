package com.ruviapps.nephsynd.HelperClasses;

public class DiseasesToPatientValue {
    long dtpvId;
    long dtpId;
    String value;
    int onDate;
    public static final String COLUMN_DTPVID = "dtpvId";
    public static final String COLUMN_DTPID = "dtpId";
    public static final String COLUMN_VALUE = "value";
    public static final String COLUMN_ONDATE ="onDate";


    public long getDtpvId() {
        return dtpvId;
    }

    public void setDtpvId(long dtpvId) {
        this.dtpvId = dtpvId;
    }

    public long getDtpId() {
        return dtpId;
    }

    public void setDtpId(long dtpId) {
        this.dtpId = dtpId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getOnDate() {
        return onDate;
    }

    public void setOnDate(int onDate) {
        this.onDate = onDate;
    }
}
