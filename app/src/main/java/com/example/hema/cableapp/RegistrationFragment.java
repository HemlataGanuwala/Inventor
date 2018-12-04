package com.example.hema.cableapp;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.hema.cableapp.R.string.nameerror;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationFragment extends Fragment {
    View view;
    ServiceHandler shh;
    Button buttonregister;
    String regname,regaddress,regcity,regemail,regmobileno,reguserid,regpassword,regpin,regnoofagent,path;
    int Status = 1;
    ProgressDialog progress;
    EditText editTextname,editTextaddress,editTextcity,editTextemail,editTextmobile,editTextuserid,editTextpassword,editTextpin,editTextagent;
    private AwesomeValidation awesomeValidation;


    public RegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_registration, container, false);
        final GlobalClass globalVariable = (GlobalClass) getActivity().getApplicationContext();
        path = globalVariable.getconstr();
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);


        editTextname = (EditText)view.findViewById(R.id.txtname);
        editTextaddress = (EditText)view.findViewById(R.id.txtaddress);
        editTextcity = (EditText)view.findViewById(R.id.txtcity);
        editTextemail = (EditText)view.findViewById(R.id.txtemail);
        editTextmobile = (EditText)view.findViewById(R.id.txtmobno);
        editTextuserid = (EditText)view.findViewById(R.id.txtuserid);
        editTextpassword = (EditText)view.findViewById(R.id.txtpassword);
        editTextpin = (EditText)view.findViewById(R.id.txtpin);
        editTextagent = (EditText)view.findViewById(R.id.txtagentno);
        buttonregister = (Button) view.findViewById(R.id.btnadminregister);

        buttonregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validdata();
                InsertData();
            }
        });
        return view;
    }

    public void InsertData()
    {
        regname = editTextname.getText().toString();
        regaddress = editTextaddress.getText().toString();
        regcity = editTextcity.getText().toString();
        regemail = editTextemail.getText().toString();
        regmobileno = editTextmobile.getText().toString();
        reguserid = editTextuserid.getText().toString();
        regpassword = editTextpassword.getText().toString();
        regpin = editTextpin.getText().toString();
        regnoofagent = editTextagent.getText().toString();

        if (awesomeValidation.validate()) {

            new GetInsertData().execute();
        }
        else
        {
            Toast.makeText(getActivity(), "Validation failed", Toast.LENGTH_LONG).show();
        }


    }

    public void validdata(){

        awesomeValidation.addValidation(getActivity(), R.id.txtname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", nameerror);
        awesomeValidation.addValidation(getActivity(), R.id.txtmobno, "^[2-9]{2}[0-9]{8}$", R.string.mobileerror);

        awesomeValidation.addValidation(getActivity(), R.id.txtemail, Patterns.EMAIL_ADDRESS, R.string.nameerror);

        awesomeValidation.addValidation(getActivity(), R.id.txtpin, "^[0-9]{4}$", R.string.Dataerror);
        awesomeValidation.addValidation(getActivity(), R.id.txtagentno, "^[0-9]{10}$", R.string.Dataerror);

        awesomeValidation.addValidation(getActivity(), R.id.txtaddress, "[a-zA-Z0-9_-]+", R.string.Dataerror);
        awesomeValidation.addValidation(getActivity(), R.id.txtuserid, "[a-zA-Z0-9_-]+", R.string.Dataerror);
        awesomeValidation.addValidation(getActivity(), R.id.txtcity, "[a-zA-Z0-9_-]+", R.string.Dataerror);
        awesomeValidation.addValidation(getActivity(), R.id.txtpassword, "[a-zA-Z0-9_-]+", R.string.Dataerror);


    }

    public class GetInsertData extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute()
        {
            // TODO Auto-generated method stub
            super.onPreExecute();

            progress=new ProgressDialog(getActivity());
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

            String url = path + "Registration/AdminRegistration";

            Log.d("Url: ", "> " + url);

            try {
                // Making a request to url and getting response

                List<NameValuePair> para = new ArrayList<>();
                // para.add(new BasicNameValuePair("CustBal", balance));
                para.add(new BasicNameValuePair("Name", regname));
                para.add(new BasicNameValuePair("Address", regaddress));
                para.add(new BasicNameValuePair("City", regcity));
                para.add(new BasicNameValuePair("Email", regemail));
                para.add(new BasicNameValuePair("MobileNo", regmobileno));
                para.add(new BasicNameValuePair("UserId", reguserid));
                para.add(new BasicNameValuePair("Password", regpassword));
                para.add(new BasicNameValuePair("Pin", regpin));
                para.add(new BasicNameValuePair("NoOfAgent", regnoofagent));


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

            if (Status == 1)
            {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "Register Successfully And Your App Will Activated After 8-10 hours", Toast.LENGTH_LONG).show();
                    }
                });

            }
            else { Toast.makeText(getActivity(), "Data not Registered", Toast.LENGTH_LONG).show();}

            editTextname.setText("");
            editTextaddress.setText("");
            editTextcity.setText("");
            editTextemail.setText("");
            editTextmobile.setText("");
            editTextuserid.setText("");
            editTextpassword.setText("");
            editTextpin.setText("");
            editTextagent.setText("");

        }
    }

}
