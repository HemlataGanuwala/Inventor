package com.example.hema.cableapp;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hema.cableapp.Adapter.DailyCollectionAdapter;
import com.example.hema.cableapp.Model.DailyCollectionPlanet;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashBoardFragment extends Fragment {

    View view;
    CardView cardViewtodaycollection,cardViewreport,cardViewbillgenerate,cardViewbalancereport,cardViewactivedeactive,cardViewhelp;
    ServiceHandler shh;
    String path,cmonth,cyear,cdate,dailytotal,reportstotal,balancetotal,activetotal,deactivetotal;
    int month,year,Status = 1;
    TextView textViewdailycollection,textViewreport,textViewbalance,textViewactive,textViewdeactive;
    ProgressDialog progress;

    public DashBoardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_dash_board, container, false);
        final GlobalClass globalVariable = (GlobalClass) getActivity().getApplicationContext();
        path = globalVariable.getconstr();

        cardViewtodaycollection = (CardView)view.findViewById(R.id.cardtoday);
        cardViewreport = (CardView)view.findViewById(R.id.cardreport);
        cardViewbillgenerate = (CardView)view.findViewById(R.id.cardbillgenerate);
        cardViewbalancereport = (CardView)view.findViewById(R.id.cardbalance);
        cardViewactivedeactive = (CardView)view.findViewById(R.id.cardactivedeactive);
        cardViewhelp = (CardView)view.findViewById(R.id.cardhelp);
        textViewdailycollection = (TextView)view.findViewById(R.id.tvdashdailycoll);
        textViewreport = (TextView)view.findViewById(R.id.tvdashreport);
        textViewbalance = (TextView)view.findViewById(R.id.tvdashbalance);
        textViewactive = (TextView)view.findViewById(R.id.tvdashactive);
        textViewdeactive = (TextView)view.findViewById(R.id.tvdashdeactive);

        Submitdata();

        Date d = new Date();
        CharSequence g = DateFormat.format("dd/MM/yyyy", d.getTime());
        cdate = g.toString();

        new FetchList1().execute();
        new Fetchreport().execute();
        new Fetchbalance().execute();
        new FetchActive().execute();
        new FetchDeactive().execute();

        cardViewtodaycollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(),DaliyCollectionActivity.class);
                startActivity(intent);
            }
        });

        cardViewreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),DaliyCollectionActivity.class);
                startActivity(intent);
            }
        });

        cardViewbillgenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Billgenerate().execute();
            }
        });

        cardViewbalancereport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),DaliyCollectionActivity.class);
                startActivity(intent);
            }
        });

        cardViewtodaycollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),DaliyCollectionActivity.class);
                startActivity(intent);
            }
        });

        cardViewtodaycollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),DaliyCollectionActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    public void Submitdata() {

        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);

        if (month == 0) {
            cmonth = "DEC";
            cyear = String.valueOf(year);
        }
        if (month == 1) {
            cmonth = "JAN";
            cyear = String.valueOf(year);
        }
        if (month == 2) {
            cmonth = "FEB";
            cyear = String.valueOf(year);
        }
        if (month == 3) {
            cmonth = "MAR";
            cyear = String.valueOf(year);
        }
        if (month == 4) {
            cmonth = "APR";
            cyear = String.valueOf(year);
        }
        if (month == 5) {
            cmonth = "MAY";
            cyear = String.valueOf(year);
        }
        if (month == 6) {
            cmonth = "JUN";
            cyear = String.valueOf(year);
        }
        if (month == 7) {
            cmonth = "JUL";
            cyear = String.valueOf(year);
        }
        if (month == 8) {
            cmonth = "AUG";
            cyear = String.valueOf(year);
        }
        if (month == 9) {
            cmonth = "SEP";
            cyear = String.valueOf(year);
        }
        if (month == 10) {
            cmonth = "OCT";
            cyear = String.valueOf(year);
        }
        if (month == 11) {
            cmonth = "NOV";
            cyear = String.valueOf(year);
        }

    }

    class FetchList1 extends AsyncTask<String, String, String>
    {

        protected void onPreExecute()
        {
            super.onPreExecute();
//            progress=new ProgressDialog(getActivity());
//            progress.setMessage("Loading...");
//            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progress.setIndeterminate(true);
//            progress.setProgress(0);
//            progress.show();
        }
        @Override
        protected String doInBackground(String... params)
        {
            shh = new ServiceHandler();
            String url =  path + "Report/DailyCollectionSumReport";

            Log.d("Url: ", "> " + url);

            try {
                List<NameValuePair> params2 = new ArrayList<>();
                params2.add(new BasicNameValuePair("PaymentDate1", cdate));
                params2.add(new BasicNameValuePair("PaymentDate2", cdate));
                params2.add(new BasicNameValuePair("Bmonth", cmonth));
                params2.add(new BasicNameValuePair("Byear", cyear));

                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST, params2);

                if (jsonStr != null)
                {
                    JSONObject jObj = new JSONObject(jsonStr);
                    dailytotal = jObj.getString("Response");

                } else {
                    Toast.makeText(getActivity(), "Data Not Available", Toast.LENGTH_LONG).show();
                }

            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            progress.dismiss();
            textViewdailycollection.setText(dailytotal);
        }
    }

    class Fetchreport extends AsyncTask<String, String, String>
    {

        protected void onPreExecute()
        {
            super.onPreExecute();
//            progress=new ProgressDialog(getActivity());
//            progress.setMessage("Loading...");
//            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progress.setIndeterminate(true);
//            progress.setProgress(0);
//            progress.show();
        }
        @Override
        protected String doInBackground(String... params)
        {
            shh = new ServiceHandler();
            String url =  path + "Report/MonthWiseSumReport";

            Log.d("Url: ", "> " + url);

            try {
                List<NameValuePair> params2 = new ArrayList<>();
                params2.add(new BasicNameValuePair("Bmonth", cmonth));
                params2.add(new BasicNameValuePair("Byear", cyear));

                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST, params2);

                if (jsonStr != null)
                {
                    JSONObject jObj = new JSONObject(jsonStr);
                    reportstotal = jObj.getString("Response");

                } else {
                    Toast.makeText(getActivity(), "Data Not Available", Toast.LENGTH_LONG).show();
                }

            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            progress.dismiss();
            textViewreport.setText(reportstotal);
        }
    }

    class Fetchbalance extends AsyncTask<String, String, String>
    {

        protected void onPreExecute()
        {
            super.onPreExecute();
//            progress=new ProgressDialog(getActivity());
//            progress.setMessage("Loading...");
//            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progress.setIndeterminate(true);
//            progress.setProgress(0);
//            progress.show();
        }
        @Override
        protected String doInBackground(String... params)
        {
            shh = new ServiceHandler();
            String url =  path + "Report/BalanceSumReport";

            Log.d("Url: ", "> " + url);

            try {
                List<NameValuePair> params2 = new ArrayList<>();
                params2.add(new BasicNameValuePair("Bmonth", cmonth));
                params2.add(new BasicNameValuePair("Byear", cyear));

                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST, params2);

                if (jsonStr != null)
                {
                    JSONObject jObj = new JSONObject(jsonStr);
                    balancetotal = jObj.getString("Response");

                } else {
                    Toast.makeText(getActivity(), "Data Not Available", Toast.LENGTH_LONG).show();
                }

            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            progress.dismiss();
            textViewbalance.setText(balancetotal);
        }
    }

    class FetchActive extends AsyncTask<String, String, String>
    {

        protected void onPreExecute()
        {
            super.onPreExecute();
//            progress=new ProgressDialog(getActivity());
//            progress.setMessage("Loading...");
//            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progress.setIndeterminate(true);
//            progress.setProgress(0);
//            progress.show();
        }
        @Override
        protected String doInBackground(String... params)
        {
            shh = new ServiceHandler();
            String url =  path + "Report/ActiveCustomerSumReport";

            Log.d("Url: ", "> " + url);

            try {
                List<NameValuePair> params2 = new ArrayList<>();
                params2.add(new BasicNameValuePair("Bmonth", cmonth));
                params2.add(new BasicNameValuePair("Byear", cyear));

                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST, params2);

                if (jsonStr != null)
                {
                    JSONObject jObj = new JSONObject(jsonStr);
                    activetotal = jObj.getString("Response");

                } else {
                    Toast.makeText(getActivity(), "Data Not Available", Toast.LENGTH_LONG).show();
                }

            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            textViewactive.setText(activetotal);
        }
    }

    class FetchDeactive extends AsyncTask<String, String, String>
    {

        protected void onPreExecute()
        {
            super.onPreExecute();
//            progress=new ProgressDialog(getActivity());
//            progress.setMessage("Loading...");
//            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progress.setIndeterminate(true);
//            progress.setProgress(0);
//            progress.show();
        }
        @Override
        protected String doInBackground(String... params)
        {
            shh = new ServiceHandler();
            String url =  path + "Report/DeactiveCustomerSumReport";

            Log.d("Url: ", "> " + url);

            try {
                List<NameValuePair> params2 = new ArrayList<>();
                params2.add(new BasicNameValuePair("Bmonth", cmonth));
                params2.add(new BasicNameValuePair("Byear", cyear));

                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST, params2);

                if (jsonStr != null)
                {
                    JSONObject jObj = new JSONObject(jsonStr);
                    deactivetotal = jObj.getString("Response");

                } else {
                    Toast.makeText(getActivity(), "Data Not Available", Toast.LENGTH_LONG).show();
                }

            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            progress.dismiss();
            textViewdeactive.setText(deactivetotal);
        }
    }

    class Billgenerate extends AsyncTask<String, String, String>
    {

        protected void onPreExecute()
        {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... params)
        {
            shh = new ServiceHandler();
            String url =  path + "Registration/BillGeneration";

            Log.d("Url: ", "> " + url);

            try {
                List<NameValuePair> params2 = new ArrayList<>();
                params2.add(new BasicNameValuePair("Bmonth", cmonth));
                params2.add(new BasicNameValuePair("Byear", cyear));

                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST, params2);

                if (jsonStr != null)
                {
                    JSONObject jObj = new JSONObject(jsonStr);
                    Status = Integer.parseInt(jObj.getString("Status"));

                } else {
                    Toast.makeText(getActivity(), "Data Not Available", Toast.LENGTH_LONG).show();
                }

            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (Status == 1)
                    {
                        Toast.makeText(getActivity(), "Bill Generated", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Bill Generated Failed", Toast.LENGTH_LONG).show();
                    }
                }
            });
            //adapter.setOnItemClickListner(g);
        }
    }

}
