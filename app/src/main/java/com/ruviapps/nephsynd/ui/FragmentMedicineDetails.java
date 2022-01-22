package com.ruviapps.nephsynd.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ruviapps.nephsynd.DbHelper.DatabaseHelper;
import com.ruviapps.nephsynd.HelperClasses.Diseases;
import com.ruviapps.nephsynd.HelperClasses.DiseasesViewModel;
import com.ruviapps.nephsynd.HelperClasses.Medicine;
import com.ruviapps.nephsynd.HelperClasses.MedicineViewModel;
import com.ruviapps.nephsynd.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FragmentMedicineDetails extends Fragment {
    EditText newMedicineName;
    Button saveNewMedicineBtn;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragmentlayout_medicine_details,container,false);
        newMedicineName = view.findViewById(R.id.et_medicine_details_editName);
        saveNewMedicineBtn = view.findViewById(R.id.bt_medicine_details_saveBtn);

        saveNewMedicineBtn.setOnClickListener(v->{
            saveNewSideEffect();
        });

        return view;
    }

    void saveNewSideEffect()
    {
        DatabaseHelper databaseHelper= new DatabaseHelper(getContext());
        if(newMedicineName.getText().toString().matches(""))
        {
            Toast.makeText(getContext(),"Please Enter a Name First!",Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            String newName = newMedicineName.getText().toString();
            databaseHelper.addMedicine(newName);
            newMedicineName.setText("");
            Toast.makeText(getContext(),"Entry Done",Toast.LENGTH_SHORT).show();
            updateList();
        }

    }

    private void updateList() {
        MedicineViewModel viewModel;

        viewModel = new ViewModelProvider(requireActivity()).get(MedicineViewModel.class);

        ArrayList<Medicine> all_medicine_objects;
        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        all_medicine_objects =databaseHelper.getAllMedicines();

        List<String> stringList = new ArrayList<>();

        for(Medicine d :all_medicine_objects)
        {
            stringList.add(d.getMedicine_name());
        }
        viewModel.setMedicineShareableList(stringList);


    }


}
