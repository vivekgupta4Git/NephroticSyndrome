package com.ruviapps.nephsynd.HelperClasses;

public class SideEffect {
    int sideEffect_id;
    String sideEffect_name;

    public static final String COLUMN_SIDEEFFECT_ID="sideEffect_id";
    public static final String COLUMN_SIDEEFFECT_NAME="sideEffect_name";

    public int getSideEffect_id() {
        return sideEffect_id;
    }

    public String getSideEffect_name() {
        return sideEffect_name;
    }

    public void setSideEffect_id(int sideEffect_id) {
        this.sideEffect_id = sideEffect_id;
    }

    public void setSideEffect_name(String sideEffect_name) {
        this.sideEffect_name = sideEffect_name;
    }
}
