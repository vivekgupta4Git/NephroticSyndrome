package com.ruviapps.nephsynd.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ruviapps.nephsynd.DbHelper.DatabaseHelper;
import com.ruviapps.nephsynd.HelperClasses.Diseases;
import com.ruviapps.nephsynd.HelperClasses.Medicine;
import com.ruviapps.nephsynd.HelperClasses.MedicineViewModel;
import com.ruviapps.nephsynd.HelperClasses.Prescribtion;
import com.ruviapps.nephsynd.HelperClasses.myArrayAdapter;
import com.ruviapps.nephsynd.HelperClasses.myMedicineArrayAdapter;
import com.ruviapps.nephsynd.HelperClasses.myPrescribtionArrayAdapter;
import com.ruviapps.nephsynd.R;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;

public class FragmentTreatmentPage4 extends Fragment {
    Spinner medicineSpinner,frequencySpinner,unitSpinner;
    ArrayAdapter<String> adapter;
    Button addBtn, doneBtn, advanceBtn;
    ArrayList<Medicine> medicineArrayList;
    ListView listView;
    myPrescribtionArrayAdapter myPrescribtionArrayAdapter;
    LinearLayout linearLayout;
    ArrayList<Prescribtion>  prescribtionlist = new ArrayList<>();
    Medicine currentMedicineObject;
    Prescribtion currentPrescribtionObject;
    EditText editText;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.treatment_child_page4,container,false);
        medicineSpinner = view.findViewById(R.id.spinner_treatment_4_child);
        listView= view.findViewById(R.id.listview_treatment_childpage4);
        View headerView = getLayoutInflater().inflate(R.layout.header_medicine, listView, false);
        listView.addHeaderView(headerView);
        advanceBtn = view.findViewById(R.id.btn_advance_view);
        addBtn = view.findViewById(R.id.btn_addToList_treatment4);
        doneBtn = view.findViewById(R.id.btn_done_treatmentPage4);
        editText = view.findViewById(R.id.et_dosage_treatment_child_page4);
        frequencySpinner = view.findViewById(R.id.sp_frequency_of_medicine);
        unitSpinner = view.findViewById(R.id.sp_unit_spinner);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        MedicineViewModel model  = new ViewModelProvider(requireActivity()).get(MedicineViewModel.class);
        model.getMedicineShareableList().observe(getViewLifecycleOwner(),medicineShareableList->{
            adapter = new ArrayAdapter<>(
                    getContext(),R.layout.support_simple_spinner_dropdown_item,medicineShareableList);
            adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            medicineSpinner.setAdapter(adapter);

        });

        medicineSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DatabaseHelper o = new DatabaseHelper(getContext());
                medicineArrayList = o.getAllMedicines();;
                currentMedicineObject = medicineArrayList.get(position);
                currentPrescribtionObject = new Prescribtion();
                currentPrescribtionObject.setMedicine_id(currentMedicineObject.getMedicine_id());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });


        ArrayList<String> fre = new ArrayList<>();
        fre.add("Once a Day");
        fre.add("Twice a Day");
        fre.add("Thrice a Day");
        fre.add("Alternative Day");
        fre.add("as Needed");
        ArrayAdapter s = new ArrayAdapter(getContext(),R.layout.support_simple_spinner_dropdown_item,fre);
        s.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        frequencySpinner.setAdapter(s);

        frequencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
             // if(currentPrescribtionObject!=null)
              //  currentPrescribtionObject.setFrequency(fre.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayList<String> unitof  = new ArrayList<>();
        unitof.add("Unit");
        unitof.add("tablet");
        unitof.add("ml");
        ArrayAdapter u = new ArrayAdapter(getContext(),R.layout.support_simple_spinner_dropdown_item,unitof);
        u.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        unitSpinner.setAdapter(u);

        unitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              //  if(currentPrescribtionObject!=null)
                //    currentPrescribtionObject.setUnit(unitof.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


       // arrayAdapter = new myMedicineArrayAdapter(getContext(),0,selectedMedicineList);
       // listView.setAdapter(arrayAdapter);

        myPrescribtionArrayAdapter = new myPrescribtionArrayAdapter(getContext(),0,prescribtionlist);
        listView.setAdapter(myPrescribtionArrayAdapter);

        addBtn.setOnClickListener(v -> {
            if(currentPrescribtionObject==null)
                return;

            if(editText.getText().toString().matches(""))
                return;
            else {

                currentPrescribtionObject.setPrescribed_dosage(editText.getText().toString());
                if(linearLayout.getVisibility()==View.GONE)
                {
                    currentPrescribtionObject.setUnit(unitof.get(0));
                    currentPrescribtionObject.setFrequency(fre.get(0));
                }else {
                    currentPrescribtionObject.setUnit(unitSpinner.getSelectedItem().toString());
                    currentPrescribtionObject.setFrequency(frequencySpinner.getSelectedItem().toString());
                }

                boolean flag = true;
                for (Prescribtion p : prescribtionlist) {
                    if (p.getMedicine_id() == currentMedicineObject.getMedicine_id()) {
                            flag = false;
                            break;
                        }
                }

                if(flag) {
                    prescribtionlist.add(currentPrescribtionObject);
                    myPrescribtionArrayAdapter.notifyDataSetChanged();
                }
            }
        });

        doneBtn.setOnClickListener(v -> {



            Bundle result = new Bundle();
                result.putInt("PagerCount",4);

           if(prescribtionlist.size()>0)
           {
               result.putSerializable("Prescribtion", prescribtionlist);

           }else {
               Toast.makeText(getContext(),"Please Select first ",Toast.LENGTH_SHORT).show();
               return;
           }
            // The child fragment needs to still set the result on its parent fragment manager
                getParentFragmentManager().setFragmentResult("fourthRequestKey", result);
        });

        linearLayout = view.findViewById(R.id.linearlayout3);
        advanceBtn.setOnClickListener(v->{
            if(linearLayout.getVisibility() == View.VISIBLE)
            {
                linearLayout.setVisibility(View.GONE);
            }
            else
            linearLayout.setVisibility(View.VISIBLE);

        });

    }



}
