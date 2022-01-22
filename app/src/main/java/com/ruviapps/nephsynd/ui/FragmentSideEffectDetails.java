package com.ruviapps.nephsynd.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ruviapps.nephsynd.DbHelper.DatabaseHelper;
import com.ruviapps.nephsynd.HelperClasses.Diseases;
import com.ruviapps.nephsynd.HelperClasses.DiseasesViewModel;
import com.ruviapps.nephsynd.HelperClasses.Patient;
import com.ruviapps.nephsynd.HelperClasses.SideEffect;
import com.ruviapps.nephsynd.HelperClasses.SideEffectsViewModel;
import com.ruviapps.nephsynd.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FragmentSideEffectDetails  extends Fragment {
    EditText newSideEffectName;
    Button saveNewSideEffectButton;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragmentlayout_side_effects_details,container,false);
        newSideEffectName = view.findViewById(R.id.et_side_effect_details_editName);
        saveNewSideEffectButton = view.findViewById(R.id.bt_side_effect_details_saveBtn);

        saveNewSideEffectButton.setOnClickListener(v->{
            saveNewSideEffect();
        });

        return view;
    }

    void saveNewSideEffect()
    {
        DatabaseHelper databaseHelper= new DatabaseHelper(getContext());
        if(newSideEffectName.getText().toString().matches(""))
        {
            Toast.makeText(getContext(),"Please Enter a Name First!",Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            String newName = newSideEffectName.getText().toString();
            databaseHelper.addSideEffect(newName);
            newSideEffectName.setText("");
            Toast.makeText(getContext(),"Entry Done",Toast.LENGTH_SHORT).show();
            updateList();
        }

    }


    private void updateList() {
        SideEffectsViewModel viewModel;

        viewModel = new ViewModelProvider(requireActivity()).get(SideEffectsViewModel.class);

        ArrayList<SideEffect> all_sideEffect_objects;
        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        all_sideEffect_objects =databaseHelper.getAllSideEffects();

        List<String> stringList = new ArrayList<>();

        for(SideEffect s :all_sideEffect_objects)
        {
            stringList.add(s.getSideEffect_name());
        }
        viewModel.setSideEffectsShareableList(stringList);


    }



}
