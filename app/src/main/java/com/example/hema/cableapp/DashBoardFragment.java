package com.example.hema.cableapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashBoardFragment extends Fragment {

    View view;
    CardView cardViewtodaycollection,cardViewreport,cardViewbillgenerate,cardViewbalancereport,cardViewactivedeactive,cardViewhelp;

    public DashBoardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_dash_board, container, false);
        cardViewtodaycollection = (CardView)view.findViewById(R.id.cardtoday);
        cardViewreport = (CardView)view.findViewById(R.id.cardreport);
        cardViewbillgenerate = (CardView)view.findViewById(R.id.cardbillgenerate);
        cardViewbalancereport = (CardView)view.findViewById(R.id.cardbalance);
        cardViewactivedeactive = (CardView)view.findViewById(R.id.cardactivedeactive);
        cardViewhelp = (CardView)view.findViewById(R.id.cardhelp);

        cardViewtodaycollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new
            }
        });
        return view;
    }

}
