package com.example.hema.cableapp;

import android.app.DatePickerDialog;
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
    String path,custnm,mobile,paidamt1,paidamt2;
    ServiceHandler shh;
    RecyclerView recyclerView;
    int month,year,day;
    String CDay,CMonth,cmonth,cyear,paydate;
    TextView textViewdate;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_report);

        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
        path = globalVariable.getconstr();
//        Submitdata();

        mPlanetlist1.clear();
        new FetchList1().execute();
        recyclerView = (RecyclerView) findViewById(R.id.rcbalance);
        recyclerView.setLayoutManager(new LinearLayoutManager(BalanceReportActivity.this));
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
            String url =  path + "Report/DailyCollectionReport";

            Log.d("Url: ", "> " + url);

            try {
                List<NameValuePair> params2 = new ArrayList<>();
                params2.add(new BasicNameValuePair("PaymentDate1", paydate));
                params2.add(new BasicNameValuePair("PaymentDate2", paydate));
                params2.add(new BasicNameValuePair("Bmonth", cmonth));
                params2.add(new BasicNameValuePair("Byear", cyear));

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


                        BalancePlanet planet1 = new BalancePlanet(custnm,mobile,paidamt1);
                        mPlanetlist1.add(planet1);
                    }
                    try  { Thread.sleep(500);}
                    catch (InterruptedException e){e.printStackTrace();}

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
