package com.example.hema.cableapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hema.cableapp.Adapter.RegListAdapter;
import com.example.hema.cableapp.Model.RegListPlanet;
import com.example.hema.cableapp.Model.SpinnerAgentPlanet;
import com.example.hema.cableapp.Model.SpinnerPlanet;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RegEditActivity extends AppCompatActivity {

    Button buttonedit,buttondelete;
    EditText editTextcname,editTextaddress,editTextmobno,editTextarea,editTextnobox,editTextsetupdetails,editTextmcharges,editTextegdate;
    String custname,pathIp,custid,customerid,address, mobno,area,nobox, setupdetails, monthlycharge, regdate,path, agentnm, packagenm,imeino,operatorno,actdact;
    EditText spinneragentname,packagename;
    TextView textViewactiveDeactive;
    ServiceHandler shh;
    ProgressDialog progress;
    int Status = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_edit);

        editTextcname = (EditText)findViewById(R.id.eteditcustname);
        editTextaddress = (EditText)findViewById(R.id.eteditaddress);
        editTextmobno = (EditText)findViewById(R.id.eteditmobile);
        editTextarea = (EditText)findViewById(R.id.eteditarea);
        editTextnobox = (EditText)findViewById(R.id.eteditnobox);
        editTextsetupdetails = (EditText)findViewById(R.id.eteditsetboxdetail);
        packagename = (EditText) findViewById(R.id.spineditpackage);
        editTextmcharges=(EditText)findViewById(R.id.eteditrate);
        buttonedit = (Button) findViewById(R.id.btnedit);
        buttondelete=(Button) findViewById(R.id.btndelete);
        textViewactiveDeactive=(TextView)findViewById(R.id.tveditregad);
        spinneragentname=(EditText)findViewById(R.id.spineditagentname);

        Display();

        new FetchRegistrationList().execute();

        buttondelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetDeleteData().execute();
            }
        });

        buttonedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditData();
            }
        });
    }

    public void EditData()
    {
        custname=editTextcname.getText().toString();
        address=editTextaddress.getText().toString();
        mobno=editTextmobno.getText().toString();
        area=editTextarea.getText().toString();
        nobox=editTextnobox.getText().toString();
        setupdetails=editTextsetupdetails.getText().toString();
        packagenm=packagename.getText().toString();
        monthlycharge=editTextmcharges.getText().toString();
        agentnm=spinneragentname.getText().toString();
        actdact=textViewactiveDeactive.getText().toString();

        new GetEditData().execute();
    }


    public void Display()
    {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null)
        {
            custid = (String)bundle.get("a1");
            imeino = (String)bundle.get("a2");
            operatorno = (String)bundle.get("a3");
            pathIp = (String)bundle.get("a4");
        }
    }

    class FetchRegistrationList extends AsyncTask<String, String, String>
    {

        protected void onPreExecute()
        {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... params)
        {
            shh = new ServiceHandler();
            String url =  pathIp + "Registration/CustomerRegistrationDatacustid";

            Log.d("Url: ", "> " + url);

            try {
                List<NameValuePair> params2 = new ArrayList<>();
                params2.add(new BasicNameValuePair("CustId", custid));
                params2.add(new BasicNameValuePair("IMEINo", imeino));
                params2.add(new BasicNameValuePair("OperatorCode", operatorno));

                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST, params2);

                if (jsonStr != null)
                {
                    JSONObject c1 = new JSONObject(jsonStr);
                    JSONArray classArray = c1.getJSONArray("Response");
                    for (int i = 0; i < classArray.length(); i++) {
                        JSONObject a1 = classArray.getJSONObject(i);
                        customerid = a1.getString("CustId");
                        custname = a1.getString("CustName");
                        address = a1.getString("Address");
                        mobno = a1.getString("MobileNo");
                        area = a1.getString("Area");
                        setupdetails = a1.getString("SetupBox_Details");
                        nobox = a1.getString("NoOfBox");
                        packagenm = a1.getString("Package");
                        monthlycharge = a1.getString("PackageRate");
                        agentnm = a1.getString("AgentName");
                        actdact = a1.getString("Status");


                    }


                } else {
                    Toast.makeText(RegEditActivity.this, "Data Not Available", Toast.LENGTH_LONG).show();
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
            editTextcname.setText(custname);
            editTextaddress.setText(address);
            editTextmobno.setText(mobno);
            editTextarea.setText(area);
            editTextnobox.setText(nobox);
            editTextsetupdetails.setText(setupdetails);
            editTextmcharges.setText(monthlycharge);
            spinneragentname.setText(agentnm);
            packagename.setText(packagenm);
            textViewactiveDeactive.setText(actdact);
        }
    }

    public class GetEditData extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progress = new ProgressDialog(RegEditActivity.this);
            progress.setMessage("Loading...");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.setProgress(0);
            progress.show();
        }

        @Override
        protected String doInBackground(String... params) {


            shh = new ServiceHandler();

            String url = pathIp + "Registration/CustomerRegistrationEdit";

            Log.d("Url: ", "> " + url);

            try {
                // Making a request to url and getting response

                List<NameValuePair> para = new ArrayList<>();
                // para.add(new BasicNameValuePair("CustBal", balance));
                para.add(new BasicNameValuePair("CustId", custid));
                para.add(new BasicNameValuePair("CustName", custname));
                para.add(new BasicNameValuePair("Address", address));
                para.add(new BasicNameValuePair("MobileNo", mobno));
                para.add(new BasicNameValuePair("Area", area));
                para.add(new BasicNameValuePair("NoOfBox", nobox));
                para.add(new BasicNameValuePair("SetupBox_Details", setupdetails));
                para.add(new BasicNameValuePair("Package", packagenm));
                para.add(new BasicNameValuePair("PackageRate", monthlycharge));
                para.add(new BasicNameValuePair("AgentName", agentnm));
                para.add(new BasicNameValuePair("Status", actdact));
                para.add(new BasicNameValuePair("OperatorCode", operatorno));
                para.add(new BasicNameValuePair("IMEINo", imeino));


                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST, para);
                if (jsonStr != null) {
                    JSONObject jObj = new JSONObject(jsonStr);
                    String msg = jObj.getString("Message");
                    Status = Integer.parseInt(jObj.getString("Status"));


                } else {
                    Toast.makeText(RegEditActivity.this, "Data not Found", Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("ServiceHandler", "Json Error ");
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            super.onPostExecute(result);

            progress.dismiss();

            if (Status == 1) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RegEditActivity.this, "Edit succesfully", Toast.LENGTH_LONG).show();
                    }
                });

            } else {
                Toast.makeText(RegEditActivity.this, "Edit Failed", Toast.LENGTH_LONG).show();
            }

            editTextcname.setText("");
            editTextaddress.setText("");
            editTextmobno.setText("");
            editTextarea.setText("");
            editTextnobox.setText("");
            editTextsetupdetails.setText("");
            editTextmcharges.setText("");
            spinneragentname.setText("");
            packagename.setText("");

        }
    }


    public class GetDeleteData extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            progress = new ProgressDialog(RegEditActivity.this);
            progress.setMessage("Loading...");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.setProgress(0);
            progress.show();
        }

        @Override
        protected String doInBackground(String... params) {


            shh = new ServiceHandler();

            String url = pathIp + "Registration/CustomerRegistrationDelete";

            Log.d("Url: ", "> " + url);

            try {
                // Making a request to url and getting response

                List<NameValuePair> para = new ArrayList<>();
                // para.add(new BasicNameValuePair("CustBal", balance));
//                para.add(new BasicNameValuePair("CustName", custname));
                para.add(new BasicNameValuePair("CustId", custid));
                para.add(new BasicNameValuePair("OperatorCode", operatorno));
                para.add(new BasicNameValuePair("IMEINo", imeino));


                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST, para);
                if (jsonStr != null) {
                    JSONObject jObj = new JSONObject(jsonStr);
                    String msg = jObj.getString("Message");
                    Status = Integer.parseInt(jObj.getString("Status"));


                } else {
                    Toast.makeText(RegEditActivity.this, "Data not Found", Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("ServiceHandler", "Json Error ");
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            super.onPostExecute(result);

            progress.dismiss();

            if (Status == 1) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RegEditActivity.this, "Deleted succesfully", Toast.LENGTH_LONG).show();
                    }
                });

            } else {
                Toast.makeText(RegEditActivity.this, "Deleted Failed", Toast.LENGTH_LONG).show();
            }

            editTextcname.setText("");
            editTextaddress.setText("");
            editTextmobno.setText("");
            editTextarea.setText("");
            editTextnobox.setText("");
            editTextsetupdetails.setText("");
            editTextmcharges.setText("");

        }
    }


}
