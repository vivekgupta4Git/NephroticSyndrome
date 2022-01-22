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

import com.ruviapps.nephsynd.R;

import java.util.ArrayList;

public class myMedicineArrayAdapter extends ArrayAdapter<Medicine> {

    private Context mContext;
    private ArrayList<Medicine> medicineArrayList;
    private ImageButton deleteRowButton;
    private TextView textView;
    public myMedicineArrayAdapter(@NonNull Context mContext, int resource, ArrayList<Medicine> list) {
        super(mContext, resource);
        this.mContext = mContext;
        medicineArrayList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentView = convertView;


            if(currentView==null)
            {
                currentView = LayoutInflater.from(mContext)
                        .inflate(R.layout.adapter_medicine_list,parent,false);

            }
        final Medicine current = medicineArrayList.get(position);
            textView= currentView.findViewById(R.id.textview);
            deleteRowButton = currentView.findViewById(R.id.imageButton);
            textView.setText(current.getMedicine_name());

        deleteRowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                medicineArrayList.remove(position);
                notifyDataSetChanged();
            }
        });

        return currentView;
    }


    @Override
    public int getCount() {
        return  medicineArrayList.size();
    }
}
