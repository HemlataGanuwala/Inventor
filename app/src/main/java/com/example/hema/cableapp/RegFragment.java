package com.example.hema.cableapp;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

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
    String custname,address,mobno,area,nobox,setupdetails,monthlycharge,regdate,path,agentnm,packagenm,packnm,prate,agent;
    Spinner spinneragentname,packagename;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    int year,month,day;
    int Status = 1;
    ProgressDialog progress;
    EditText editTextcname,editTextaddress,editTextmobno,editTextarea,editTextnobox,editTextsetupdetails,editTextMCharges,editTextegdate;
    TextView textViewdate;
    ArrayList<String> packlist,agentlist;


    public RegFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_reg, container, false);

        final GlobalClass globalVariable = (GlobalClass) getActivity().getApplicationContext();
        path = globalVariable.getconstr();

        editTextcname = (EditText)view.findViewById(R.id.etcustname);
        editTextaddress = (EditText)view.findViewById(R.id.etaddress);
        editTextmobno = (EditText)view.findViewById(R.id.etmobile);
        editTextarea = (EditText)view.findViewById(R.id.etarea);
        editTextnobox = (EditText)view.findViewById(R.id.etnobox);
        editTextsetupdetails = (EditText)view.findViewById(R.id.etsetboxdetail);
        packagename = (Spinner) view.findViewById(R.id.spinpackage);
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item, packnm);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        packagename.setAdapter(dataAdapter);

        editTextMCharges=(EditText)view.findViewById(R.id.etrate);
        buttonreg = (Button) view.findViewById(R.id.btncustregistration);
        buttondate=(Button) view.findViewById(R.id.btnsetdt);
        textViewdate=(TextView)view.findViewById(R.id.txtdate);
        spinneragentname=(Spinner)view.findViewById(R.id.spinagentname);
//        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item, agent);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinneragentname.setAdapter(dataAdapter2);

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
                Insert();
            }
        });
       // return view;

        /* Inflate the layout for this fragment */
        return inflater.inflate(R.layout.fragment_reg, container, false);
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
            monthlycharge=editTextMCharges.getText().toString();
            regdate=textViewdate.getText().toString();
            agentnm=spinneragentname.getSelectedItem().toString();

            new GetInsertData().execute();

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
                para.add(new BasicNameValuePair("CustName  ", custname));
                para.add(new BasicNameValuePair("Address  ", address));
                para.add(new BasicNameValuePair("MobileNo  ", mobno));
                para.add(new BasicNameValuePair("Area  ", area));
                para.add(new BasicNameValuePair("NoOfBox  ", nobox));
                para.add(new BasicNameValuePair("SetupBox_Details  ", setupdetails));
                para.add(new BasicNameValuePair("Package  ", packagenm));
                para.add(new BasicNameValuePair("PackageRate   ",monthlycharge));
                para.add(new BasicNameValuePair("RegistrationDate  ", regdate));
                para.add(new BasicNameValuePair("AgentName  ", agentnm));


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
                        Toast.makeText(getActivity(), "Package save succesfully", Toast.LENGTH_LONG).show();
                    }
                });

            } else {
                Toast.makeText(getActivity(), "Package not registered", Toast.LENGTH_LONG).show();
            }

            editTextcname.setText("");
            editTextaddress.setText("");
            editTextmobno.setText("");
            editTextarea.setText("");
            editTextnobox.setText("");
            editTextsetupdetails.setText("");
            editTextegdate.setText("");
            editTextMCharges.setText("");

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

                        packnm = a1.getString("PackageName");
                        prate = a1.getString("Rate");
                        packlist.add(packnm);

                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            packagename.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, packlist));
                        }
                    });


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

                        agent = a1.getString("AgentName");
                        agentlist.add(agent);

                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            spinneragentname.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, agentlist));
                        }
                    });


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


        }
    }


}
