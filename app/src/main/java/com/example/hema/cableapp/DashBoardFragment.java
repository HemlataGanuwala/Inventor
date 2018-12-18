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
    String path,cmonth,cyear,cdate,dailytotal,reportstotal,balancetotal,activetotal,deactivetotal,imeino,operatorno,dtot,pathIp;
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
//        final GlobalClass globalVariable = (GlobalClass) getActivity().getApplicationContext();
//        path = globalVariable.getconstr();

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

        Display();

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
                Intent intent = new Intent(getActivity(),ReportsActivity.class);
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
                Intent intent = new Intent(getActivity(),BalanceReportActivity.class);
                intent.putExtra("a1",imeino);
                intent.putExtra("a2",operatorno);
                intent.putExtra("a3",cmonth);
                intent.putExtra("a4",cyear);
                intent.putExtra("a5",pathIp);
                startActivity(intent);
            }
        });

        cardViewactivedeactive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ActiveDeactiveActivity.class);
                intent.putExtra("a1",imeino);
                intent.putExtra("a2",operatorno);
                intent.putExtra("a3",cmonth);
                intent.putExtra("a4",cyear);
                intent.putExtra("a5",pathIp);
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

    public void Display()
    {
        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null)
        {
            imeino = (String)bundle.get("a1");
            operatorno = (String)bundle.get("a2");
            cmonth = (String)bundle.get("a3");
            cyear = (String)bundle.get("a4");
            pathIp = (String)bundle.get("a5");
        }
    }

    class FetchList1 extends AsyncTask<String, String, String>
    {

        protected void onPreExecute()
        {
            super.onPreExecute();
            progress=new ProgressDialog(getActivity());
            progress.setMessage("Loading...");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.setProgress(0);
            progress.show();
        }
        @Override
        protected String doInBackground(String... params)
        {
            shh = new ServiceHandler();
            String url =  pathIp + "Report/DailyCollectionSumReport";

            Log.d("Url: ", "> " + url);

            try {
                List<NameValuePair> params2 = new ArrayList<>();
                params2.add(new BasicNameValuePair("PaymentDate1", cdate));
                params2.add(new BasicNameValuePair("PaymentDate2", cdate));
                params2.add(new BasicNameValuePair("Bmonth", cmonth));
                params2.add(new BasicNameValuePair("Byear", cyear));
                params2.add(new BasicNameValuePair("IMEINo", imeino));
                params2.add(new BasicNameValuePair("OperatorCode", operatorno));

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
            progress.dismiss();
            if (dailytotal.equals("null"))
            {
                dailytotal = "0";
            }
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
            String url =  pathIp + "Report/MonthWiseSumReport";

            Log.d("Url: ", "> " + url);

            try {
                List<NameValuePair> params2 = new ArrayList<>();
                params2.add(new BasicNameValuePair("Bmonth", cmonth));
                params2.add(new BasicNameValuePair("Byear", cyear));
                params2.add(new BasicNameValuePair("IMEINo", imeino));
                params2.add(new BasicNameValuePair("OperatorCode", operatorno));

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
            if (reportstotal.equals("null"))
            {
                reportstotal = "0";
            }
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
            String url =  pathIp + "Report/BalanceSumReport";

            Log.d("Url: ", "> " + url);

            try {
                List<NameValuePair> params2 = new ArrayList<>();
                params2.add(new BasicNameValuePair("Bmonth", cmonth));
                params2.add(new BasicNameValuePair("Byear", cyear));
                params2.add(new BasicNameValuePair("IMEINo", imeino));
                params2.add(new BasicNameValuePair("OperatorCode", operatorno));

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

            if (balancetotal.equals("null"))
            {
                balancetotal = "0";
            }
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
            String url =  pathIp + "Report/ActiveCustomerSumReport";

            Log.d("Url: ", "> " + url);

            try {
                List<NameValuePair> params2 = new ArrayList<>();
                params2.add(new BasicNameValuePair("Bmonth", cmonth));
                params2.add(new BasicNameValuePair("Byear", cyear));
                params2.add(new BasicNameValuePair("IMEINo", imeino));
                params2.add(new BasicNameValuePair("OperatorCode", operatorno));
                params2.add(new BasicNameValuePair("Status", "Active"));

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

            if (activetotal.equals("null"))
            {
                activetotal = "0";
            }
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
            String url =  pathIp + "Report/DeactiveCustomerSumReport";

            Log.d("Url: ", "> " + url);

            try {
                List<NameValuePair> params2 = new ArrayList<>();
                params2.add(new BasicNameValuePair("Bmonth", cmonth));
                params2.add(new BasicNameValuePair("Byear", cyear));
                params2.add(new BasicNameValuePair("IMEINo", imeino));
                params2.add(new BasicNameValuePair("OperatorCode", operatorno));
                params2.add(new BasicNameValuePair("Status", "Deactive"));

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
            if (deactivetotal.equals("null"))
            {
                deactivetotal = "0";
            }
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
            String url =  pathIp + "Registration/BillGeneration";

            Log.d("Url: ", "> " + url);

            try {
                List<NameValuePair> params2 = new ArrayList<>();
                params2.add(new BasicNameValuePair("Bmonth", cmonth));
                params2.add(new BasicNameValuePair("Byear", cyear));
                params2.add(new BasicNameValuePair("IMEINo", imeino));
                params2.add(new BasicNameValuePair("OperatorCode", operatorno));

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
                    else if(Status == 2)
                    {
                        Toast.makeText(getActivity(), "Bill Already Generated", Toast.LENGTH_LONG).show();
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
