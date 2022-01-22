package com.ruviapps.nephsynd.HelperClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ruviapps.nephsynd.DbHelper.DatabaseHelper;
import com.ruviapps.nephsynd.R;

import java.util.ArrayList;

public class myPrescribtionArrayAdapter extends ArrayAdapter<Medicine> {

    private Context mContext;
    private ArrayList<Prescribtion> prescribtionArrayList;
    private ImageButton deleteRowButton;
    private TextView medicineName;
    private TextView unitText, freqText,dosageText;
    public myPrescribtionArrayAdapter(@NonNull Context mContext, int resource, ArrayList<Prescribtion> list) {
        super(mContext, resource);
        this.mContext = mContext;
        prescribtionArrayList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentView = convertView;


            if(currentView==null)
            {
                currentView = LayoutInflater.from(mContext)
                        .inflate(R.layout.adapter_prescribtion_list,parent,false);

            }
        final Prescribtion current = prescribtionArrayList.get(position);
            medicineName = currentView.findViewById(R.id.textview);
            dosageText = currentView.findViewById(R.id.dosageValue_adapter_medicine);
            freqText = currentView.findViewById(R.id.freq_text);
            unitText = currentView.findViewById(R.id.unittext);

            deleteRowButton = currentView.findViewById(R.id.imageButton);

            medicineName.setText(new DatabaseHelper(getContext()).getMedicineName(current.getMedicine_id()));
            dosageText.setText(current.getPrescribed_dosage());
            freqText.setText(current.getFrequency());
            unitText.setText(current.getUnit());


        deleteRowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prescribtionArrayList.remove(position);
                notifyDataSetChanged();
            }
        });

        return currentView;
    }


    @Override
    public int getCount() {
        return  prescribtionArrayList.size();
    }
}
