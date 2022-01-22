package com.ruviapps.nephsynd.ui;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;

import com.google.android.material.navigation.NavigationView;
import com.ruviapps.nephsynd.DbHelper.DatabaseHelper;
import com.ruviapps.nephsynd.HelperClasses.Patient;
import com.ruviapps.nephsynd.HelperClasses.PatientViewModel;
import com.ruviapps.nephsynd.R;
import java.util.ArrayList;

public class MainScreen extends AppCompatActivity  {

    int selectedPatientPosition = 0;

    DrawerLayout drawerLayout;
    Spinner spinner;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    ArrayList<String> patient_name_array;
    ArrayList<Patient> all_patient_object;
    NavigationView navigationView;
    Patient patientObject;
    ArrayAdapter<String> list_of_patientadapter;

    public static final String RESULT_REGISTERTAG= "result_register_tag";
    public static final String TREATMENT_TAG = "treatment_tag";
    private static final String CREATENEW_TAG = "createnew_tag";

/*
    public class patientViewModel extends ViewModel {



        private MutableLiveData<List<String>> patientList;

        public patientViewModel(MutableLiveData<List<String>> patientList1)
        {
            this.patientList = patientList1;
        }
        public LiveData<List<String>> getPatientList() {
            if (patientList == null)
                patientList = new MutableLiveData<>();
            loadList();
            return patientList;
        }


        private void loadList() {
            List<String> patientString = new ArrayList<>();
            DatabaseHelper obj = new DatabaseHelper(getApplicationContext());
            all_patient_object = obj.getAllPatients();
            for (Patient pdata : all_patient_object) {
                patientString.add(pdata.getPatient_first_name());
            }
            patientList.setValue(patientString);

        }
    }
*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawerLayout  = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);

        navigationView.inflateMenu(R.menu.neph_menu_drawer);
        View headerView = navigationView.inflateHeaderView(R.layout.neph_head_drawer);
        spinner = headerView.findViewById(R.id.sp_patientname);

         toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.open_drawer,R.string.close_drawer);



        drawerLayout.addDrawerListener(toggle);

        toggle.setDrawerIndicatorEnabled(true);
       /* toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        toggle.setHomeAsUpIndicator(R.drawable.ic_home_black_24dp);*/
        toggle.syncState();






           loadPatientName();

        FrameLayout  resultRegisterFrameLayout = findViewById(R.id.resultRegisterfragment_container);
        FrameLayout  treatmentFrameLayout = findViewById(R.id.treatmentFragment_container);
        FrameLayout createNewFrameLayout = findViewById(R.id.createNewFragment_container);

        FragmentManager fragmentManager = getSupportFragmentManager();




        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedPatientPosition = position;
                if(drawerLayout.isDrawerOpen(GravityCompat.START))
                {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                //set Global Patient Object
                DatabaseHelper ob = new DatabaseHelper(getApplicationContext());
                all_patient_object = ob.getAllPatients();
                if(all_patient_object==null || all_patient_object.size()<=0)
                    return;
                else
                patientObject = all_patient_object.get(selectedPatientPosition);
                //visible only main screen fragment i.e resultRegisterFragment rest Hide.
                resultRegisterFrameLayout.setVisibility(View.VISIBLE);
                treatmentFrameLayout.setVisibility(View.INVISIBLE);
                createNewFrameLayout.setVisibility(View.INVISIBLE);

                Fragment resultRegiserFragment = FragmentResultRegister.newInstance(patientObject);
                Fragment treatmentfragment  =  FragmentTreatment.newInstance(patientObject);

                Fragment createNewFragment = FragmentCreateNew.newInstance(patientObject);

                    fragmentManager.beginTransaction().replace(R.id.resultRegisterfragment_container, resultRegiserFragment,
                            RESULT_REGISTERTAG)
                            .commit();

                    fragmentManager.beginTransaction().replace(R.id.treatmentFragment_container,
                            treatmentfragment,TREATMENT_TAG).commit();

                    fragmentManager.beginTransaction().replace(R.id.createNewFragment_container,
                            createNewFragment,CREATENEW_TAG).commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });




       navigationView.setNavigationItemSelectedListener(item -> {


           switch (item.getItemId())
           {
               case R.id.result_register_item:
                resultRegisterFrameLayout.setVisibility(View.VISIBLE);
                treatmentFrameLayout.setVisibility(View.INVISIBLE);
                createNewFrameLayout.setVisibility(View.INVISIBLE);
                   Fragment resultRegiserFragment = FragmentResultRegister.newInstance(patientObject);
                    Log.v("RuviApps","Drawer has sent the patient Object");
                       if(fragmentManager.findFragmentByTag(RESULT_REGISTERTAG)==null)
                     fragmentManager.beginTransaction().replace(R.id.resultRegisterfragment_container, resultRegiserFragment,RESULT_REGISTERTAG)
                             .commit();

                                    break;

               case R.id.treatment_item:

                   resultRegisterFrameLayout.setVisibility(View.INVISIBLE);
                   treatmentFrameLayout.setVisibility(View.VISIBLE);
                    createNewFrameLayout.setVisibility(View.INVISIBLE);
                   Fragment treatmentFrag = FragmentTreatment.newInstance(patientObject);

                    if(fragmentManager.findFragmentByTag(TREATMENT_TAG)==null)
                   fragmentManager.beginTransaction().replace(R.id.treatmentFragment_container,treatmentFrag,TREATMENT_TAG)
                           .addToBackStack(null).commit();


                   break;
               case R.id.createnew_item:

                   resultRegisterFrameLayout.setVisibility(View.INVISIBLE);
                   treatmentFrameLayout.setVisibility(View.INVISIBLE);
                   createNewFrameLayout.setVisibility(View.VISIBLE);

                   Fragment createNewFrag = FragmentCreateNew.newInstance(patientObject);
                   if(fragmentManager.findFragmentByTag(CREATENEW_TAG)==null)
                   {
                       fragmentManager.beginTransaction().replace(R.id.createNewFragment_container,
                               createNewFrag,CREATENEW_TAG).commit();
                   }
                   break;
           }

           if(drawerLayout.isDrawerOpen(GravityCompat.START))
           {
               drawerLayout.closeDrawer(GravityCompat.START);
           }
       return  true;
       });


        resultRegisterFrameLayout.setVisibility(View.VISIBLE);
        treatmentFrameLayout.setVisibility(View.INVISIBLE);
        createNewFrameLayout.setVisibility(View.INVISIBLE);

        Fragment resultRegiserFragment = FragmentResultRegister.newInstance(patientObject);
        Fragment treatmentfragment  = FragmentTreatment.newInstance(patientObject);
        Fragment createNewFragment = FragmentCreateNew.newInstance(patientObject);
        if(fragmentManager.findFragmentByTag(RESULT_REGISTERTAG)==null)
        fragmentManager.beginTransaction().replace(R.id.resultRegisterfragment_container, resultRegiserFragment,
                RESULT_REGISTERTAG)
                .commit();
        if(fragmentManager.findFragmentByTag(TREATMENT_TAG)==null)
        fragmentManager.beginTransaction().replace(R.id.treatmentFragment_container,
                treatmentfragment,TREATMENT_TAG).commit();
        if(fragmentManager.findFragmentByTag(CREATENEW_TAG)==null)
        fragmentManager.beginTransaction().replace(R.id.createNewFragment_container,
                createNewFragment,CREATENEW_TAG).commit();


        getSupportFragmentManager().setFragmentResultListener("SaveAll", this, (requestKey, result) ->
                {
                    Patient obj =(Patient) result.getSerializable("PatientObject");
                 FragmentTreatment   fragment = FragmentTreatment.newInstance(obj);
                    fragmentManager.beginTransaction().replace(R.id.treatmentFragment_container, fragment, TREATMENT_TAG).commit();
                }
                );


    }

    void loadPatientName() {
       DatabaseHelper obj = new DatabaseHelper(this);
        all_patient_object = obj.getAllPatients();

     /*   patient_name_array = new ArrayList<>();
        for (Patient pdata : all_patient_object) {
            patient_name_array.add(pdata.getPatient_first_name());
        }

          list_of_patientadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,patient_name_array);
          list_of_patientadapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
         spinner.setAdapter(list_of_patientadapter);
*/

        PatientViewModel model = new ViewModelProvider(this).get(PatientViewModel.class);
        model.getShareAbleList().observe(this,shareAbleList ->{
            list_of_patientadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,shareAbleList);
            list_of_patientadapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            spinner.setAdapter(list_of_patientadapter);


        });

    }



}



