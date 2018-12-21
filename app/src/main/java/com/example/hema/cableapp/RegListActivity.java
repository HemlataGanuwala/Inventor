package com.example.hema.cableapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.hema.cableapp.Adapter.DailyCollectionAdapter;
import com.example.hema.cableapp.Adapter.RegListAdapter;
import com.example.hema.cableapp.Model.DailyCollectionPlanet;
import com.example.hema.cableapp.Model.RegListPlanet;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RegListActivity extends AppCompatActivity implements RegListAdapter.OnItemClickListner,SwipeRefreshLayout.OnRefreshListener{

    RecyclerView recyclerView;
    RegListAdapter adapter;
    List<RegListPlanet>mPlanetlist = new ArrayList<RegListPlanet>();
    ServiceHandler shh;
    String pathIp,custnm,mobile,area,setupbox,statusad,imeino,operatorno,custid;
    SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_list);


        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.regswipelist);
        refreshLayout.setOnRefreshListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.rcreglist);
        recyclerView.setLayoutManager(new LinearLayoutManager(RegListActivity.this));

        Display();
        new FetchList1().execute();

    }

    public void Display()
    {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null)
        {
            imeino = (String)bundle.get("a1");
            operatorno = (String)bundle.get("a2");
            pathIp = (String)bundle.get("a3");
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(RegListActivity.this,RegEditActivity.class);
        RegListPlanet planet = mPlanetlist.get(position);
        intent.putExtra("a1",planet.getCustomerId());
        intent.putExtra("a2",imeino);
        intent.putExtra("a3",operatorno);
        intent.putExtra("a4",pathIp);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        mPlanetlist.clear();
        refreshLayout.setRefreshing(false);
        new FetchList1().execute();

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
            String url =  pathIp + "Registration/CustomerRegistrationData";

            Log.d("Url: ", "> " + url);

            try {
                List<NameValuePair> params2 = new ArrayList<>();
                params2.add(new BasicNameValuePair("IMEINo", imeino));
                params2.add(new BasicNameValuePair("OperatorCode", operatorno));

                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST, params2);

                if (jsonStr != null)
                {
                    JSONObject c1 = new JSONObject(jsonStr);
                    JSONArray classArray = c1.getJSONArray("Response");
                    for (int i = 0; i < classArray.length(); i++) {
                        JSONObject a1 = classArray.getJSONObject(i);
                        custid = a1.getString("CustId");
                        custnm = a1.getString("CustName");
                        mobile = a1.getString("MobileNo");
                        area = a1.getString("Area");
                        setupbox = a1.getString("SetupBox_Details");
                        statusad = a1.getString("Status");


                        RegListPlanet planet1 = new RegListPlanet(custid,custnm,mobile,area,setupbox,statusad);
                        mPlanetlist.add(planet1);
                    }
                    try  { Thread.sleep(500);}
                    catch (InterruptedException e){e.printStackTrace();}

                } else {
                    Toast.makeText(RegListActivity.this, "Data Not Available", Toast.LENGTH_LONG).show();
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
                    adapter = new RegListAdapter(mPlanetlist);
                    recyclerView.setAdapter(adapter);
                }
            });
            adapter.setOnItemClickListner(RegListActivity.this);
        }
    }
}
