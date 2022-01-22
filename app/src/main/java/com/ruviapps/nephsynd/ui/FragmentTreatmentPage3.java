package com.ruviapps.nephsynd.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ruviapps.nephsynd.DbHelper.DatabaseHelper;
import com.ruviapps.nephsynd.HelperClasses.Diseases;
import com.ruviapps.nephsynd.HelperClasses.DiseasesViewModel;
import com.ruviapps.nephsynd.HelperClasses.Doctor;
import com.ruviapps.nephsynd.HelperClasses.DoctorViewModel;
import com.ruviapps.nephsynd.HelperClasses.myArrayAdapter;
import com.ruviapps.nephsynd.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class FragmentTreatmentPage3 extends Fragment {
    Spinner diseaseSpinner;
    ArrayAdapter<String> adapter;
    Button addBtn, doneBtn;
    ArrayList<Diseases> diseasesArrayList;
    ListView listView;
    ArrayAdapter<Diseases> arrayAdapter;
    ArrayList<Diseases> selectedDiseasesArraylist = new ArrayList<>();
    Diseases curentDiseaseObject;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.treatment_child_page3,container,false);
        diseaseSpinner = view.findViewById(R.id.spinner_treatment_3_child);
        listView= view.findViewById(R.id.listview_treatment_childpage3);
        View headerView = getLayoutInflater().inflate(R.layout.header, listView, false);
        listView.addHeaderView(headerView);

        addBtn = view.findViewById(R.id.btn_addToList_treatment);
        doneBtn = view.findViewById(R.id.btn_done_treatmentPage3);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DiseasesViewModel model  = new ViewModelProvider(requireActivity()).get(DiseasesViewModel.class);
        model.getDiseaseShareAbleList().observe(getViewLifecycleOwner(),diseaseShareAbleList->{
            adapter = new ArrayAdapter<>(
                    getContext(),R.layout.support_simple_spinner_dropdown_item,diseaseShareAbleList);
            adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            diseaseSpinner.setAdapter(adapter);

        });

        diseaseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DatabaseHelper o = new DatabaseHelper(getContext());
              diseasesArrayList = o.getAllDiseases();;
              curentDiseaseObject = diseasesArrayList.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });
        arrayAdapter = new myArrayAdapter(requireContext(),0,selectedDiseasesArraylist);
        listView.setAdapter(arrayAdapter);

        addBtn.setOnClickListener(v -> {
            if(!selectedDiseasesArraylist.contains(curentDiseaseObject)) {
                selectedDiseasesArraylist.add(curentDiseaseObject);
                arrayAdapter.notifyDataSetChanged();
            }
        });

        doneBtn.setOnClickListener(v -> {
                long[] ids = new long[selectedDiseasesArraylist.size()];

            Bundle result = new Bundle();
                result.putInt("PagerCount",3);

           if(selectedDiseasesArraylist.size()>0)
           {
               for(int i = 0; i< selectedDiseasesArraylist.size();++i)
               {
                   ids[i]= selectedDiseasesArraylist.get(i).getDisease_id();
               }
               result.putLongArray("Problems",ids);

           }else {
               Toast.makeText(getContext(),"Please Select first ",Toast.LENGTH_SHORT).show();
               return;
           }
            // The child fragment needs to still set the result on its parent fragment manager
                getParentFragmentManager().setFragmentResult("thirdRequestKey", result);
        });

    }
}
