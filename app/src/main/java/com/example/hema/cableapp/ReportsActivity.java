package com.example.hema.cableapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.hema.cableapp.Adapter.DailyCollectionAdapter;
import com.example.hema.cableapp.Adapter.ReportAdapter;
import com.example.hema.cableapp.Model.DailyCollectionPlanet;
import com.example.hema.cableapp.Model.ReportPlanet;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ReportsActivity extends AppCompatActivity {

    List<ReportPlanet> mPlanetlist1 = new ArrayList<ReportPlanet>();
    ReportAdapter adapter;
    String path,custnm,mobile,paidamt1,paidamt2;
    ServiceHandler shh;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
        path = globalVariable.getconstr();

        mPlanetlist1.clear();
        new FetchList1().execute();
        recyclerView = (RecyclerView) findViewById(R.id.rcdaliydate);
        recyclerView.setLayoutManager(new LinearLayoutManager(ReportsActivity.this));
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
            String url =  path + "Report/MonthWiseReport";

            Log.d("Url: ", "> " + url);

            try {
                List<NameValuePair> params2 = new ArrayList<>();

                String jsonStr = shh.makeServiceCall(url, ServiceHandler.GET, null);

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
