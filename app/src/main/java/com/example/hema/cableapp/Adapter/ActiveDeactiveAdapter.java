package com.example.hema.cableapp.Adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.hema.cableapp.Model.ActiveDeactivePlanet;
import com.example.hema.cableapp.Model.DailyCollectionPlanet;
import com.example.hema.cableapp.R;

import java.util.List;

public class ActiveDeactiveAdapter extends RecyclerView.Adapter<ActiveDeactiveAdapter.ListHolder> {
    private List<ActiveDeactivePlanet> mPlanetList1;
    private OnItemClickListner mlistner;

    public interface OnItemClickListner
    {
        void onItemClick(int position);
    }
    public void setOnItemClickListner(OnItemClickListner listner){
        mlistner = listner;
    }

    public ActiveDeactiveAdapter(List<ActiveDeactivePlanet> mPlanetList1) {
        this.mPlanetList1 = mPlanetList1;
    }

    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.active_deactive_item,parent,false);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(ListHolder holder, int position) {
        holder.textViewadcust.setText(mPlanetList1.get(position).getADCustomerNm());
        holder.textViewadsetup.setText(mPlanetList1.get(position).getADSetupboxNo());
        holder.textViewad.setText(mPlanetList1.get(position).getADActiveDeactiveAmt());

        if (mPlanetList1.get(position).getADActiveDeactiveAmt().equals("Deactive")) {
            //holder.textViewad.setText("Deactive");
            holder.textViewad.setTextColor(Color.RED);
        } else {
            //holder.textViewad.setText("Active");
            holder.textViewad.setTextColor(Color.parseColor("#ff669900"));
        }
    }

    @Override
    public int getItemCount() {
        return mPlanetList1.size();
    }

    public class ListHolder extends RecyclerView.ViewHolder {
        TextView textViewadcust,textViewadsetup,textViewad;

        public ListHolder(View itemView) {
            super(itemView);
            textViewadcust = (TextView) itemView.findViewById(R.id.tvactivecustomername);
            textViewadsetup = (TextView) itemView.findViewById(R.id.tvactivesetupbox);
            textViewad = (TextView) itemView.findViewById(R.id.tvactivedeactive);



        }
    }
}
