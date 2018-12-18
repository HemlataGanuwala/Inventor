package com.example.hema.cableapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
import java.util.List;

public class DaliyCollectionActivity extends AppCompatActivity {

    List<DailyCollectionPlanet> mPlanetlist1 = new ArrayList<DailyCollectionPlanet>();
    DailyCollectionAdapter adapter;
    String path,custnm,mobile,paidamt1,paidamt2,imeino,operatorno,pathIp;
    ServiceHandler shh;
    RecyclerView recyclerView;
    int month,year,day;
    String CDay,CMonth,cmonth,cyear,paydate;
    TextView textViewdate;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    Button buttonsearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daliy_collection);

//        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
//        path = globalVariable.getconstr();

        textViewdate = (TextView)findViewById(R.id.tvdcdate);
        buttonsearch = (Button)findViewById(R.id.btnsearch);

        Display();
//        Submitdata();

        mPlanetlist1.clear();

        recyclerView = (RecyclerView) findViewById(R.id.rcdaliydate);
        recyclerView.setLayoutManager(new LinearLayoutManager(DaliyCollectionActivity.this));

        buttonsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FetchList1().execute();
            }
        });

        textViewdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(DaliyCollectionActivity.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,dateSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                if(Integer.toString(day).length() == 1)
                {
                    CDay = "0" + day;
                }
                else
                {
                    CDay =  String.valueOf(day);
                }
                if(Integer.toString(month).length() == 1)
                {
                    CMonth = "0" + month;
                }
                else
                {
                    CMonth =  String.valueOf(month);
                }
//                currdate = CDay + "/" + CMonth + "/" + year;
//                textViewcdate.setText(currdate);
                paydate = CDay + "/" + CMonth + "/" + year;
                textViewdate.setText(paydate);
            }
        };
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

//    public void Submitdata() {
//
//        Calendar c = Calendar.getInstance();
//        year = c.get(Calendar.YEAR);
//        month = c.get(Calendar.MONTH);
//
//        if (month == 0) {
//            cmonth = "DEC";
//            cyear = String.valueOf(year);
//        }
//        if (month == 1) {
//            cmonth = "JAN";
//            cyear = String.valueOf(year);
//        }
//        if (month == 2) {
//            cmonth = "FEB";
//            cyear = String.valueOf(year);
//        }
//        if (month == 3) {
//            cmonth = "MAR";
//            cyear = String.valueOf(year);
//        }
//        if (month == 4) {
//            cmonth = "APR";
//            cyear = String.valueOf(year);
//        }
//        if (month == 5) {
//            cmonth = "MAY";
//            cyear = String.valueOf(year);
//        }
//        if (month == 6) {
//            cmonth = "JUN";
//            cyear = String.valueOf(year);
//        }
//        if (month == 7) {
//            cmonth = "JUL";
//            cyear = String.valueOf(year);
//        }
//        if (month == 8) {
//            cmonth = "AUG";
//            cyear = String.valueOf(year);
//        }
//        if (month == 9) {
//            cmonth = "SEP";
//            cyear = String.valueOf(year);
//        }
//        if (month == 10) {
//            cmonth = "OCT";
//            cyear = String.valueOf(year);
//        }
//        if (month == 11) {
//            cmonth = "NOV";
//            cyear = String.valueOf(year);
//        }
//
//    }

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


                        DailyCollectionPlanet planet1 = new DailyCollectionPlanet(custnm,mobile,paidamt1,paidamt2);
                        mPlanetlist1.add(planet1);
                    }
                    try  { Thread.sleep(500);}
                    catch (InterruptedException e){e.printStackTrace();}

                } else {
                    Toast.makeText(DaliyCollectionActivity.this, "Data Not Available", Toast.LENGTH_LONG).show();
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
                    adapter = new DailyCollectionAdapter(mPlanetlist1);
                    recyclerView.setAdapter(adapter);
                }
            });
            //adapter.setOnItemClickListner(g);
        }
    }
}
