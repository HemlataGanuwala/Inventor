package com.example.hema.cableapp.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.hema.cableapp.Model.DailyCollectionPlanet;
import com.example.hema.cableapp.R;

import java.util.List;

public class DailyCollectionAdapter extends RecyclerView.Adapter<DailyCollectionAdapter.ListHolder> {
    private List<DailyCollectionPlanet> mPlanetList1;
    private OnItemClickListner mlistner;

    public interface OnItemClickListner
    {
        void onItemClick(int position);
    }
    public void setOnItemClickListner(OnItemClickListner listner){
        mlistner = listner;
    }

    public DailyCollectionAdapter(List<DailyCollectionPlanet> mPlanetList1) {
        this.mPlanetList1 = mPlanetList1;
    }

    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.daily_collection_item,parent,false);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(ListHolder holder, int position) {
        holder.textViewdailycust.setText(mPlanetList1.get(position).getCustomerName());
        holder.textViewdailymobile.setText(mPlanetList1.get(position).getMobileNo());
        holder.textViewdailypaid.setText(mPlanetList1.get(position).getPaidAmt());
    }

    @Override
    public int getItemCount() {
        return mPlanetList1.size();
    }

    public class ListHolder extends RecyclerView.ViewHolder {
        TextView textViewdailycust,textViewdailymobile,textViewdailypaid;
        ImageButton imageButtoncall;
        public ListHolder(View itemView) {
            super(itemView);
            textViewdailycust = (TextView) itemView.findViewById(R.id.tvdailycustname);
            textViewdailymobile = (TextView) itemView.findViewById(R.id.tvdailymobileno);
            textViewdailypaid = (TextView) itemView.findViewById(R.id.tvdailypaidamt);


            imageButtoncall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mlistner != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mlistner.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
