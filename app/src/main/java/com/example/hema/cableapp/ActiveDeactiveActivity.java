package com.example.hema.cableapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.hema.cableapp.Adapter.ActiveDeactiveAdapter;
import com.example.hema.cableapp.Adapter.DailyCollectionAdapter;
import com.example.hema.cableapp.Model.ActiveDeactivePlanet;
import com.example.hema.cableapp.Model.DailyCollectionPlanet;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ActiveDeactiveActivity extends AppCompatActivity {

    List<ActiveDeactivePlanet> mPlanetlist1 = new ArrayList<ActiveDeactivePlanet>();
    ActiveDeactiveAdapter adapter;
    String path,cmonth,cyear,pathIp,imeino,operatorno,custnm,setupbox,actdeact;
    ServiceHandler shh;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_deactive);

        Display();

        mPlanetlist1.clear();
        new FetchList1().execute();
        recyclerView = (RecyclerView) findViewById(R.id.rvactivedeactive);
        recyclerView.setLayoutManager(new LinearLayoutManager(ActiveDeactiveActivity.this));
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
            String url =  pathIp + "Report/ActiveCustomerReport";

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
                        setupbox = a1.getString("SetupBox_Details");
                        actdeact = a1.getString("Status");


                        ActiveDeactivePlanet planet1 = new ActiveDeactivePlanet(custnm,setupbox,actdeact);
                        mPlanetlist1.add(planet1);
                    }


                } else {
                    Toast.makeText(ActiveDeactiveActivity.this, "Data Not Available", Toast.LENGTH_LONG).show();
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
                    adapter = new ActiveDeactiveAdapter(mPlanetlist1);
                    recyclerView.setAdapter(adapter);
                }
            });
            //adapter.setOnItemClickListner(g);
        }
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
}
