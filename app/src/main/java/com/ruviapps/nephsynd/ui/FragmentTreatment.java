package com.ruviapps.nephsynd.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ruviapps.nephsynd.HelperClasses.CustomViewPager;
import com.ruviapps.nephsynd.HelperClasses.Patient;
import com.ruviapps.nephsynd.HelperClasses.Prescribtion;
import com.ruviapps.nephsynd.R;
import com.ruviapps.nephsynd.ui.*;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;

public class FragmentTreatment extends Fragment  {
    private static final String ARG_FRAG = "Patientobject";
   Patient patientObject;
    CustomViewPager viewPager;
    SliderPagerAdapter pagerAdapter;
    int patientVisitedDoctor_onDate_In_Integer,nextFollowUpDate;
    int currentPageInViewPager;
    long selectedDoctorId;
    long[] selected_diseases_ids_byUser;
    ArrayList<Prescribtion> prescibedMedicines;

    public static FragmentTreatment newInstance(Patient obj)
    {
        FragmentTreatment fragment = new FragmentTreatment();
        Bundle Bundleargs = new Bundle();
       Bundleargs.putSerializable(ARG_FRAG,obj );
        fragment.setArguments(Bundleargs);
        return fragment;
    }



    public class SliderPagerAdapter extends FragmentStatePagerAdapter{


        public SliderPagerAdapter(@NonNull @NotNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @NotNull
        @Override
        public Fragment getItem(int position) {
            Fragment fragment=null;
            switch (position)
            {
                case 0:
                   fragment = new FragmentTreatmentPage1();
                    break;
                case 1:
                    fragment = new FragmentTreatmentPage2();
                    break;
                case 2:
                    fragment = new FragmentTreatmentPage3();
                    break;
                case 3:
                    fragment = new FragmentTreatmentPage4();
                    break;

                    case 4:
                    fragment = FragmentTreatmentPage5.newInstance(patientVisitedDoctor_onDate_In_Integer);
                    break;

                case 5:
                    fragment = new FragmentTreatmentPage7();
                    break;

                case 6:

                    fragment = FragmentTreatmentPage6.newInstance(patientObject,patientVisitedDoctor_onDate_In_Integer,
                                                                selectedDoctorId,selected_diseases_ids_byUser,prescibedMedicines,
                                                                nextFollowUpDate
                                                                    );
                    break;


            }
            return fragment;
        }


        @Override
        public int getCount() {

            return 7;
        }
    }


@Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        patientObject = (Patient)getArguments().getSerializable(ARG_FRAG);

    if(patientObject==null)
    {
      //  Toast.makeText(getContext(),"No Patient Exists",Toast.LENGTH_SHORT).show();
        return null;
    }
        View view = inflater.inflate(R.layout.fragment_treatment_2,container,false);

    viewPager = view.findViewById(R.id.viewPager);
    viewPager.setPagingEnabled(false);
    pagerAdapter = new SliderPagerAdapter(getChildFragmentManager());
    viewPager.setAdapter(pagerAdapter);


    getChildFragmentManager().setFragmentResultListener("requestKey", getViewLifecycleOwner(), (requestKey, result) -> {
        patientVisitedDoctor_onDate_In_Integer= result.getInt("DateInDays");
    //    currentPageInViewPager  = result.getInt("PagerCount");
       // viewPager.setCurrentItem(currentPageInViewPager,true);

        viewPager.setCurrentItem(viewPager.getCurrentItem()+1);


    });

    getChildFragmentManager().setFragmentResultListener("secondRequestKey", getViewLifecycleOwner(), (requestKey, result) -> {
        selectedDoctorId = result.getLong("SelectedDoctor");
        /*currentPageInViewPager = result.getInt("PagerCount");
        viewPager.setCurrentItem(currentPageInViewPager,true);*/
        viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
    });

    getChildFragmentManager().setFragmentResultListener("thirdRequestKey",
            getViewLifecycleOwner(),((requestKey, result) -> {

        selected_diseases_ids_byUser = result.getLongArray("Problems");
       /* currentPageInViewPager = result.getInt("PagerCount");
        viewPager.setCurrentItem(currentPageInViewPager,true);

        */
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
    }));


    getChildFragmentManager().setFragmentResultListener("fourthRequestKey",
            getViewLifecycleOwner(),(((requestKey, result) -> {

           prescibedMedicines= (ArrayList<Prescribtion>)  result.getSerializable("Prescribtion");
              /*  Toast.makeText(getContext(),"Length of Prescribtion List is : " +
                      obj.size()  ,Toast.LENGTH_SHORT).show();

                currentPageInViewPager = result.getInt("PagerCount");
                viewPager.setCurrentItem(currentPageInViewPager,true);


               */
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
            })));


    getChildFragmentManager().setFragmentResultListener("fifthRequestKey",
            getViewLifecycleOwner(),(((requestKey, result) -> {
                nextFollowUpDate = result.getInt("DateInDays");
/*                Log.v("RuviApps","Received date in parent : " + nextFollowUpDate);
                currentPageInViewPager = result.getInt("PagerCount");
                viewPager.setCurrentItem(currentPageInViewPager,true);

 */
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
            })));

    getChildFragmentManager().setFragmentResultListener("sixthRequestKey",getViewLifecycleOwner(),(((requestKey, result) -> {
        viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
    })));


    getChildFragmentManager().setFragmentResultListener("ReviewRequestKey",getViewLifecycleOwner(),(((requestKey, result) -> {
        int currentPageToDisplay=viewPager.getCurrentItem();
        if(result.containsKey("VisitDate"))
        currentPageToDisplay = result.getInt("VisitDate");
        else if(result.containsKey("Doctor"))
            currentPageToDisplay = result.getInt("Doctor");


        viewPager.setCurrentItem(currentPageToDisplay);
    })));


getChildFragmentManager().setFragmentResultListener("SaveAll",getViewLifecycleOwner(),(((requestKey, result) -> {
    Patient obj = (Patient)result.getSerializable("PatientObject");
    Bundle res = new Bundle();
    res.putSerializable("PatientObject",obj);
    getParentFragmentManager().setFragmentResult("SaveAll",res);
})));

        return view;
    }



 /*@Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onResultReceived(int d)
 {
     Toast.makeText(getContext(), "Received  " + d, Toast.LENGTH_SHORT).show();
 }
*/

}
