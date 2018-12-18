package com.example.hema.cableapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hema.cableapp.Adapter.BalanceReportAdapter;
import com.example.hema.cableapp.Adapter.DailyCollectionAdapter;
import com.example.hema.cableapp.Model.BalancePlanet;
import com.example.hema.cableapp.Model.DailyCollectionPlanet;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BalanceReportActivity extends AppCompatActivity {

    List<BalancePlanet> mPlanetlist1 = new ArrayList<BalancePlanet>();
    BalanceReportAdapter adapter;
    String path,custnm,mobile,balance,pathIp;
    ServiceHandler shh;
    RecyclerView recyclerView;
    int month,year,day;
    String CDay,CMonth,cmonth,cyear,imeino,operatorno;
    TextView textViewdate;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_report);


        Display();

        mPlanetlist1.clear();
        new FetchList1().execute();
        recyclerView = (RecyclerView) findViewById(R.id.rcbalance);
        recyclerView.setLayoutManager(new LinearLayoutManager(BalanceReportActivity.this));
    }

    public void Display()
    {
        Intent intent = getIntent();
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
        }
        @Override
        protected String doInBackground(String... params)
        {
            shh = new ServiceHandler();
            String url =  pathIp + "Report/BalanceReport";

            Log.d("Url: ", "> " + url);

            try {
                List<NameValuePair> params2 = new ArrayList<>();
//                params2.add(new BasicNameValuePair("PaymentDate1", paydate));
//                params2.add(new BasicNameValuePair("PaymentDate2", paydate));
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
                        balance = a1.getString("Balance");



                        BalancePlanet planet1 = new BalancePlanet(custnm,mobile,balance);
                        mPlanetlist1.add(planet1);
                    }

                } else {
                    Toast.makeText(BalanceReportActivity.this, "Data Not Available", Toast.LENGTH_LONG).show();
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
                    adapter = new BalanceReportAdapter(mPlanetlist1);
                    recyclerView.setAdapter(adapter);
                }
            });
            //adapter.setOnItemClickListner(g);
        }
    }
}
