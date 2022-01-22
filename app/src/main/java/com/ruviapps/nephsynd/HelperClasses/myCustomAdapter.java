package com.ruviapps.nephsynd.HelperClasses;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ruviapps.nephsynd.DbHelper.DatabaseHelper;
import com.ruviapps.nephsynd.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class myCustomAdapter extends RecyclerView.Adapter<myCustomAdapter.myViewHolder> {

int count;
//List<String> diseaseList;
ArrayList<DiseasesToPatient> diseasesToPatient;
final private ListItemClickListener mOnClickListener;

   public interface ListItemClickListener{
        void onListItemClick(int position);

    }


public  myCustomAdapter(ArrayList<DiseasesToPatient> toPatients,ListItemClickListener listener){
    this.diseasesToPatient = toPatients;
    this.mOnClickListener = listener;

}



public void updateList(ArrayList<DiseasesToPatient> diseasesToPatientArrayList)
{

    diseasesToPatient.clear();
    diseasesToPatient.addAll(diseasesToPatientArrayList);
    this.notifyDataSetChanged();
}
    @NonNull
    @NotNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.diseases_list_for_adapter,parent,false);

        myViewHolder obj = new myViewHolder(v);
        return obj;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull myViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        //count = diseaseList.size();
        count = diseasesToPatient.size();
        return count;
    }

    public class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
Context context;
        TextView dName;
        Button btnUpdate;
        public myViewHolder( View itemView) {
            super(itemView);
context =itemView.getContext();
            dName = itemView.findViewById(R.id.tv_inside_list_for_disease);
            btnUpdate = itemView.findViewById(R.id.bt_value_inside_list_of_disease);

            btnUpdate.setOnClickListener(this);

        }
        public  void bindView(int pos)
        {
            if(dName !=null)
            Log.v("RuviApps","dname is not null");
            else
                Log.v("RuviApps","dname is null");

            if(diseasesToPatient.size()>0 && diseasesToPatient!=null)
            {
                DatabaseHelper databaseHelper = new DatabaseHelper(context);
                dName.setText(databaseHelper.getDiseaseName(diseasesToPatient.get(pos).getDisease_id()));
            }
          /*  if(diseaseList.size()>0 && diseaseList!=null)
                Log.v("RuviApps","Size of Disease List is : " + getItemCount());
                   dName.setText(diseaseList.get(pos));
*/
        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mOnClickListener.onListItemClick(position);
        }
    }

}
