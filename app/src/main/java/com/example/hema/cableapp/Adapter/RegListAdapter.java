package com.example.hema.cableapp.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.hema.cableapp.Model.DailyCollectionPlanet;
import com.example.hema.cableapp.Model.RegListPlanet;
import com.example.hema.cableapp.R;

import java.util.List;

public class RegListAdapter extends RecyclerView.Adapter<RegListAdapter.ListHolder> {
    private List<RegListPlanet> mPlanetList1;
    private OnItemClickListner mlistner;

    public interface OnItemClickListner
    {
        void onItemClick(int position);
    }
    public void setOnItemClickListner(OnItemClickListner listner){
        mlistner = listner;
    }

    public RegListAdapter(List<RegListPlanet> mPlanetList1) {
        this.mPlanetList1 = mPlanetList1;
    }

    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.reg_list_item,parent,false);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(ListHolder holder, int position) {
        holder.textViewregcustid.setText(mPlanetList1.get(position).getCustomerId());
        holder.textViewregcust.setText(mPlanetList1.get(position).getCustomerName());
        holder.textViewregmobile.setText(mPlanetList1.get(position).getMobileNo());
        holder.textViewregarea.setText(mPlanetList1.get(position).getArea());
        holder.textViewregsetupbox.setText(mPlanetList1.get(position).getSetupbox());
        holder.textViewstatus.setText(mPlanetList1.get(position).getStatus());


    }

    @Override
    public int getItemCount() {
        return mPlanetList1.size();
    }

    public class ListHolder extends RecyclerView.ViewHolder {
        TextView textViewregcust,textViewregmobile,textViewregarea,textViewregsetupbox,textViewstatus,textViewregcustid;

        public ListHolder(View itemView) {
            super(itemView);
            textViewregcustid = (TextView) itemView.findViewById(R.id.tvcustid);
            textViewregcust = (TextView) itemView.findViewById(R.id.tvreglistname);
            textViewregmobile = (TextView) itemView.findViewById(R.id.tvreglistmobileno);
            textViewregarea = (TextView) itemView.findViewById(R.id.tvreglistarea);
            textViewregsetupbox = (TextView) itemView.findViewById(R.id.tvreglistsetupbox);
            textViewstatus = (TextView) itemView.findViewById(R.id.tvregliststatus);

            itemView.setOnClickListener(new View.OnClickListener() {
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
