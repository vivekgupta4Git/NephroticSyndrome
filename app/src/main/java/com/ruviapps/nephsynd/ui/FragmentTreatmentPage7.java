package com.ruviapps.nephsynd.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ruviapps.nephsynd.DbHelper.DatabaseHelper;
import com.ruviapps.nephsynd.HelperClasses.Patient;
import com.ruviapps.nephsynd.HelperClasses.Prescribtion;
import com.ruviapps.nephsynd.HelperClasses.Utility;
import com.ruviapps.nephsynd.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FragmentTreatmentPage7 extends Fragment {

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.treatment_child_page7,container,false);
        TextView textView = view.findViewById(R.id.tv_No_more);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().setFragmentResult("sixthRequestKey", null);
            }
        });

        return view;
    }
}
