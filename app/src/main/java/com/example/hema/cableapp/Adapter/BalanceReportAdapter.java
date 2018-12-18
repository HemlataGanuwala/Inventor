package com.example.hema.cableapp.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.hema.cableapp.Model.BalancePlanet;
import com.example.hema.cableapp.R;

import java.util.List;

public class BalanceReportAdapter extends RecyclerView.Adapter<BalanceReportAdapter.ListHolder> {
    private List<BalancePlanet> mPlanetList1;
    private OnItemClickListner mlistner;

    public interface OnItemClickListner
    {
        void onItemClick(int position);
    }
    public void setOnItemClickListner(OnItemClickListner listner){
        mlistner = listner;
    }

    public BalanceReportAdapter(List<BalancePlanet> mPlanetList1) {
        this.mPlanetList1 = mPlanetList1;
    }

    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.balance_report_item,parent,false);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(ListHolder holder, int position) {
        holder.textViewbalcust.setText(mPlanetList1.get(position).getBalCustomerNm());
        holder.textViewbalmobile.setText(mPlanetList1.get(position).getBalMobileNo());
        holder.textViewbalpaid.setText(mPlanetList1.get(position).getBalBalanceAmt());
    }

    @Override
    public int getItemCount() {
        return mPlanetList1.size();
    }

    public class ListHolder extends RecyclerView.ViewHolder {
        TextView textViewbalcust,textViewbalmobile,textViewbalpaid;

        public ListHolder(View itemView) {
            super(itemView);
            textViewbalcust = (TextView) itemView.findViewById(R.id.tvblancecustnm);
            textViewbalmobile = (TextView) itemView.findViewById(R.id.tvbalancemobno);
            textViewbalpaid = (TextView) itemView.findViewById(R.id.tvbalancebalamt);



        }
    }
}
