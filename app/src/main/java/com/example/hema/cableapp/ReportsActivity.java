package com.example.hema.cableapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hema.cableapp.Adapter.DailyCollectionAdapter;
import com.example.hema.cableapp.Adapter.ReportAdapter;
import com.example.hema.cableapp.Model.DailyCollectionPlanet;
import com.example.hema.cableapp.Model.ReportPlanet;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ReportsActivity extends AppCompatActivity {

    List<ReportPlanet> mPlanetlist1 = new ArrayList<ReportPlanet>();
    ReportAdapter adapter;
    String path,custnm,mobile,paidamt1,paidamt2,imeino,operatorno,cmonth,cyear,pathIp,month;
    ServiceHandler shh;
    RecyclerView recyclerView;
    Button buttonsearch;
    Spinner textViewmonth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        Display();
        buttonsearch = (Button)findViewById(R.id.btnsearchmonth);
        textViewmonth = (Spinner)findViewById(R.id.tvselectmonth);
        recyclerView = (RecyclerView) findViewById(R.id.rcreportmonth);
        recyclerView.setLayoutManager(new LinearLayoutManager(ReportsActivity.this));

        List<String> list = new ArrayList<String>();
        list.add("JAN");
        list.add("FEB");
        list.add("MAR");
        list.add("APR");
        list.add("MAY");
        list.add("JUN");
        list.add("JUL");
        list.add("AUG");
        list.add("SEP");
        list.add("OCT");
        list.add("NOV");
        list.add("DEC");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        textViewmonth.setAdapter(dataAdapter);

        buttonsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPlanetlist1.clear();
                cmonth = textViewmonth.getSelectedItem().toString();
                new FetchList1().execute();
            }
        });
    }

    public void Display()
    {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null)
        {
            imeino = (String)bundle.get("a1");
            operatorno = (String)bundle.get("a2");
            //cmonth = (String)bundle.get("a3");
            cyear = (String)bundle.get("a4");
            pathIp = (String)bundle.get("a5");
        }
    }



    class FetchList1 extends AsyncTask<String, String, String>
    {

        protected void onPreExecute()
        {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... params)
        {
            shh = new ServiceHandler();
            String url =  pathIp + "Report/MonthWiseReport";

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
                    JSONObject c1 = new JSONObject(jsonStr);
                    JSONArray classArray = c1.getJSONArray("Response");
                    for (int i = 0; i < classArray.length(); i++) {
                        JSONObject a1 = classArray.getJSONObject(i);
                        custnm = a1.getString("CustName");
                        mobile = a1.getString("MobileNo");
                        paidamt1 = a1.getString("PaymentAmount1");
                        paidamt2 = a1.getString("PaymentAmount2");


                        ReportPlanet planet1 = new ReportPlanet(custnm,mobile,paidamt1,paidamt2);
                        mPlanetlist1.add(planet1);
                    }


                } else {
                    Toast.makeText(ReportsActivity.this, "Data Not Available", Toast.LENGTH_LONG).show();
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
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter = new ReportAdapter(mPlanetlist1);
                    recyclerView.setAdapter(adapter);
                }
            });
            //adapter.setOnItemClickListner(g);
        }
    }
}
