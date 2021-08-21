package com.example.accidentsystem;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {


        private ArrayList<Accident> accidentList;
        private OnAccidentListener onAccidentListenerA;

    public HistoryAdapter(ArrayList<Accident> accidentList,OnAccidentListener onAccidentListenerA) {

        this.accidentList = accidentList;
        this.onAccidentListenerA = onAccidentListenerA;
    }

    @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
            return new ViewHolder(view,onAccidentListenerA);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {



        String id = accidentList.get(position).getId();
        holder.idAccident.setText(" Report Number : "+id);

        String date = " Date : "+accidentList.get(position).getDate();
        holder.dateAccident.setText(date);

        }

        @Override
        public int getItemCount() {
            return accidentList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        View mView;

        public TextView idAccident;
        public TextView dateAccident;
        OnAccidentListener onAccidentListener;

        public ViewHolder(View itemView,OnAccidentListener onAccidentListener){
            super(itemView);
            mView = itemView;


            idAccident = (TextView) mView.findViewById(R.id.idAccident);
            dateAccident = (TextView) mView.findViewById(R.id.dateAccident);

            this.onAccidentListener = onAccidentListener;
            itemView.setOnClickListener(this);
        }


            @Override
            public void onClick(View v) {

onAccidentListener.onAccidentClick(getAdapterPosition());
            }
        }

    public interface OnAccidentListener{
        void onAccidentClick(int position);
    }
}
