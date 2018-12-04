package com.example.hema.cableapp;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.hema.cableapp.Model.SpinnerAgentPlanet;
import com.example.hema.cableapp.Model.SpinnerPlanet;
import com.google.common.collect.Range;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;



/**
 * A simple {@link Fragment} subclass.
 */

public class RegFragment extends Fragment {

    View view;
    ServiceHandler shh;
    Button buttonreg,buttondate;
    String custname;
    String address;
    String mobno;
    String area;
    String nobox;
    String setupdetails;
    String monthlycharge;
    String regdate;
    String path;
    String agentnm;
    String packagenm;
    Spinner spinneragentname,packagename;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    int year,month,day;
    int Status = 1;
    ProgressDialog progress;
    EditText editTextcname,editTextaddress,editTextmobno,editTextarea,editTextnobox,editTextsetupdetails,editTextmcharges,editTextegdate;
    TextView textViewdate;
    ArrayList<SpinnerPlanet> packlist;
    ArrayList<SpinnerAgentPlanet> agentlist;
    SpinnerPlanet packnm;
    SpinnerAgentPlanet agentspinname;
    String getpack,getrate;
    String packrate;
    ArrayAdapter<String> spinnerpackageAdapter;

    //defining AwesomeValidation object
    private AwesomeValidation awesomeValidation;


    public RegFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_reg, container, false);




        final GlobalClass globalVariable = (GlobalClass) getActivity().getApplicationContext();
        path = globalVariable.getconstr();

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        editTextcname = (EditText)view.findViewById(R.id.etcustname);
        editTextaddress = (EditText)view.findViewById(R.id.etaddress);
        editTextmobno = (EditText)view.findViewById(R.id.etmobile);
        editTextarea = (EditText)view.findViewById(R.id.etarea);
        editTextnobox = (EditText)view.findViewById(R.id.etnobox);
        editTextsetupdetails = (EditText)view.findViewById(R.id.etsetboxdetail);
        packagename = (Spinner) view.findViewById(R.id.spinpackage);
        packlist = new ArrayList<SpinnerPlanet>();
        agentlist = new ArrayList<SpinnerAgentPlanet>();
        editTextmcharges=(EditText)view.findViewById(R.id.etrate);
        buttonreg = (Button) view.findViewById(R.id.btncustregistration);
        buttondate=(Button) view.findViewById(R.id.btnsetdt);
        textViewdate=(TextView)view.findViewById(R.id.txtdate);
        spinneragentname=(Spinner)view.findViewById(R.id.spinagentname);



        // Validation







        new GetPackageData().execute();
        new GetAgentData().execute();

       buttondate.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Calendar calendar = Calendar.getInstance();
               year = calendar.get(Calendar.YEAR);
               month = calendar.get(Calendar.MONTH);
               day = calendar.get(Calendar.DAY_OF_MONTH);

               DatePickerDialog dialog = new DatePickerDialog(getActivity(),android.R.style.Theme_Holo_Light_Dialog_MinWidth,dateSetListener,year,month,day);
               dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
               dialog.show();
           }
       });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;
                textViewdate.setText(date);
            }
        };

        buttonreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Valid();
                Insert();
            }
        });

       packagename.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

               getrate = packlist.get(position).getPackageRate();
               editTextmcharges.setText(getrate);
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });

       return view;
    }
    public void Valid()
    {

        awesomeValidation.addValidation(getActivity(), R.id.etcustname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(getActivity(), R.id.etmobile, "^[2-9]{2}[0-9]{8}$", R.string.mobileerror);
        awesomeValidation.addValidation(getActivity(), R.id.etarea, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.Areaerror);
        awesomeValidation.addValidation(getActivity(), R.id.etnobox, "^[1-9]", R.string.Setupboxerror);

        awesomeValidation.addValidation(getActivity(), R.id.etaddress, "", R.string.Dataerror);

        awesomeValidation.addValidation(getActivity(), R.id.etsetboxdetail, "", R.string.Dataerror);



    }
        public  void Insert()
        {
            custname=editTextcname.getText().toString();
            address=editTextaddress.getText().toString();
            mobno=editTextmobno.getText().toString();
            area=editTextarea.getText().toString();
            nobox=editTextnobox.getText().toString();
            setupdetails=editTextsetupdetails.getText().toString();
            packagenm=packagename.getSelectedItem().toString();
            monthlycharge=editTextmcharges.getText().toString();
            regdate=textViewdate.getText().toString();
            agentnm=spinneragentname.getSelectedItem().toString();

            if (awesomeValidation.validate()) {

                new GetInsertData().execute();
            }
            else
            {
                Toast.makeText(getActivity(), "Validation failed", Toast.LENGTH_LONG).show();
            }

        }

    public class GetInsertData extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            progress = new ProgressDialog(getActivity());
            progress.setMessage("Loading...");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.setProgress(0);
            progress.show();
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub

            shh = new ServiceHandler();

            String url = path + "Registration/CustomerRegistration";

            Log.d("Url: ", "> " + url);

            try {
                // Making a request to url and getting response

                List<NameValuePair> para = new ArrayList<>();
                // para.add(new BasicNameValuePair("CustBal", balance));
//                para.add(new BasicNameValuePair("CustName", custname));
                para.add(new BasicNameValuePair("CustName", custname));
                para.add(new BasicNameValuePair("Address", address));
                para.add(new BasicNameValuePair("MobileNo", mobno));
                para.add(new BasicNameValuePair("Area", area));
                para.add(new BasicNameValuePair("NoOfBox", nobox));
                para.add(new BasicNameValuePair("SetupBox_Details", setupdetails));
                para.add(new BasicNameValuePair("Package", packagenm));
                para.add(new BasicNameValuePair("PackageRate", monthlycharge));
                para.add(new BasicNameValuePair("RegistrationDate", regdate));
                para.add(new BasicNameValuePair("AgentName", agentnm));


                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST, para);
                if (jsonStr != null) {
                    JSONObject jObj = new JSONObject(jsonStr);
                    String msg = jObj.getString("Message");
                    Status = Integer.parseInt(jObj.getString("Status"));


                } else {
                    Toast.makeText(getActivity(), "Data not Found", Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("ServiceHandler", "Json Error ");
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            progress.dismiss();

            if (Status == 1) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "Register succesfully", Toast.LENGTH_LONG).show();
                    }
                });

            } else {
                Toast.makeText(getActivity(), "Register Failed", Toast.LENGTH_LONG).show();
            }

            editTextcname.setText("");
            editTextaddress.setText("");
            editTextmobno.setText("");
            editTextarea.setText("");
            editTextnobox.setText("");
            editTextsetupdetails.setText("");
            textViewdate.setText("");
            editTextmcharges.setText("");

        }
    }


    public class GetPackageData extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub

            shh = new ServiceHandler();

            String url = path + "Registration/PackageData";

            Log.d("Url: ", "> " + url);

            try {
                // Making a request to url and getting response

                List<NameValuePair> para = new ArrayList<>();
                // para.add(new BasicNameValuePair("CustBal", balance));



                String jsonStr = shh.makeServiceCall(url, ServiceHandler.GET, null);
                if (jsonStr != null) {
                    JSONObject jObj = new JSONObject(jsonStr);
                    JSONArray jsonArray=jObj.getJSONArray("Response");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject a1 = jsonArray.getJSONObject(i);

                        packnm = new SpinnerPlanet(a1.getString("PackageName"),a1.getString("Rate"));
                        packlist.add(packnm);

                    }

                } else {
                    Toast.makeText(getActivity(), "Data not Found", Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("ServiceHandler", "Json Error ");
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            List<String> lables = new ArrayList<String>();

            for (int i = 0; i < packlist.size(); i++) {
                lables.add(packlist.get(i).getPackagename());
            }

            // Creating adapter for spinner
            spinnerpackageAdapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_spinner_item, lables);

            // Drop down layout style - list view with radio button
            spinnerpackageAdapter
                    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            packagename.setAdapter(spinnerpackageAdapter);
        }
    }


    public class GetAgentData extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub

            shh = new ServiceHandler();

            String url = path + "Registration/AgentData";

            Log.d("Url: ", "> " + url);

            try {
                // Making a request to url and getting response

                List<NameValuePair> para = new ArrayList<>();
                // para.add(new BasicNameValuePair("CustBal", balance));



                String jsonStr = shh.makeServiceCall(url, ServiceHandler.GET, null);
                if (jsonStr != null) {
                    JSONObject jObj = new JSONObject(jsonStr);
                    JSONArray jsonArray=jObj.getJSONArray("Response");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject a1 = jsonArray.getJSONObject(i);

                        SpinnerAgentPlanet packnm = new SpinnerAgentPlanet(a1.getString("AgentName"));
                        agentlist.add(packnm);

                    }


                } else {
                    Toast.makeText(getActivity(), "Data not Found", Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("ServiceHandler", "Json Error ");
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            List<String> agentlables = new ArrayList<String>();

            for (int i = 0; i < agentlist.size(); i++) {
                agentlables.add(agentlist.get(i).getAgentname());
            }

            // Creating adapter for spinner
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_spinner_item, agentlables);

            // Drop down layout style - list view with radio button
            spinnerAdapter
                    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            spinneragentname.setAdapter(spinnerAdapter);

        }
    }


}
