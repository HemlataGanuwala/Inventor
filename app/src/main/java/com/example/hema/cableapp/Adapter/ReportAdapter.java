package com.example.hema.cableapp.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.hema.cableapp.Model.DailyCollectionPlanet;
import com.example.hema.cableapp.Model.ReportPlanet;
import com.example.hema.cableapp.R;

import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ListHolder> {
    private List<ReportPlanet> mPlanetList1;
    private OnItemClickListner mlistner;

    public interface OnItemClickListner
    {
        void onItemClick(int position);
    }
    public void setOnItemClickListner(OnItemClickListner listner){
        mlistner = listner;
    }

    public ReportAdapter(List<ReportPlanet> mPlanetList1) {
        this.mPlanetList1 = mPlanetList1;
    }

    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.report_item,parent,false);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(ListHolder holder, int position) {
        holder.textViewdailycust.setText(mPlanetList1.get(position).getReportCustomerNm());
        holder.textViewdailymobile.setText(mPlanetList1.get(position).getReportMobileNo());
        holder.textViewdailypaid.setText(mPlanetList1.get(position).getReportPaidAmt());
    }

    @Override
    public int getItemCount() {
        return mPlanetList1.size();
    }

    public class ListHolder extends RecyclerView.ViewHolder {
        TextView textViewdailycust,textViewdailymobile,textViewdailypaid;

        public ListHolder(View itemView) {
            super(itemView);
            textViewdailycust = (TextView) itemView.findViewById(R.id.tvreportcustnm);
            textViewdailymobile = (TextView) itemView.findViewById(R.id.tvreportmobile);
            textViewdailypaid = (TextView) itemView.findViewById(R.id.tvreportpaid);


        }
    }
}
