package com.example.hema.cableapp;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
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
    String regname, regaddress, regcity, regemail, regmobileno, reguserid, regpassword, regpin, regnoofagent, path, imeino, deviceno;
    int Status = 1;
    ProgressDialog progress;
    EditText editTextname, editTextaddress, editTextcity, editTextemail, editTextmobile, editTextuserid, editTextpassword, editTextpin, editTextagent;
    private AwesomeValidation awesomeValidation;
    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 0;


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


        editTextname = (EditText) view.findViewById(R.id.txtname);
        editTextaddress = (EditText) view.findViewById(R.id.txtaddress);
        editTextcity = (EditText) view.findViewById(R.id.txtcity);
        editTextemail = (EditText) view.findViewById(R.id.txtemail);
        editTextmobile = (EditText) view.findViewById(R.id.txtmobno);
        editTextuserid = (EditText) view.findViewById(R.id.txtuserid);
        editTextpassword = (EditText) view.findViewById(R.id.txtpassword);
        editTextpin = (EditText) view.findViewById(R.id.txtpin);
        editTextagent = (EditText) view.findViewById(R.id.txtagentno);
        buttonregister = (Button) view.findViewById(R.id.btnadminregister);

//        getPhoneNumber();
        loadIMEI();

        buttonregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validdata();
                InsertData();
            }
        });
        return view;
    }

    public void loadIMEI() {
        // Check if the READ_PHONE_STATE permission is already available.
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            // READ_PHONE_STATE permission has not been granted.
            requestReadPhoneStatePermission();
        } else {
            // READ_PHONE_STATE permission is already been granted.
            doPermissionGrantedStuffs();
        }
    }

    private void requestReadPhoneStatePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.READ_PHONE_STATE)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.
            new AlertDialog.Builder(getActivity())
                    .setTitle("Permission Request")
//                    .setMessage(getString(R.string.permission_read_phone_state_rationale))
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //re-request
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{Manifest.permission.READ_PHONE_STATE},
                                    MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
                        }
                    })
//                    .setIcon(R.drawable.onlinlinew_warning_sign)
                    .show();
        } else {
            // READ_PHONE_STATE permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_PHONE_STATE},
                    MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_READ_PHONE_STATE) {
            // Received permission result for READ_PHONE_STATE permission.est.");
            // Check if the only required permission has been granted
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // READ_PHONE_STATE permission has been granted, proceed with displaying IMEI Number
                //alertAlert(getString(R.string.permision_available_read_phone_state));
                doPermissionGrantedStuffs();
            } else {
//                alertAlert(getString(R.string.permissions_not_granted_read_phone_state));
            }
        }
    }

    private void alertAlert(String msg) {
        new AlertDialog.Builder(getActivity())
                .setTitle("Permission Request")
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do somthing here
                    }
                })
//                .setIcon(R.drawable.onlinlinew_warning_sign)
                .show();
    }


    public void doPermissionGrantedStuffs() {
        //Have an  object of TelephonyManager
        TelephonyManager tm = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        //Get IMEI Number of Phone  //////////////// for this example i only need the IMEI
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        imeino = tm.getDeviceId();

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
        awesomeValidation.addValidation(getActivity(), R.id.txtagentno, "^[0-9]{1}$", R.string.Dataerror);

//        awesomeValidation.addValidation(getActivity(), R.id.txtaddress, "[a-zA-Z0-9_-]+", R.string.Dataerror);
        awesomeValidation.addValidation(getActivity(), R.id.txtuserid, "[a-zA-Z0-9_-]+", R.string.Dataerror);
        awesomeValidation.addValidation(getActivity(), R.id.txtcity, "[a-zA-Z0-9_-]+", R.string.Dataerror);
        awesomeValidation.addValidation(getActivity(), R.id.txtpassword, "[a-zA-Z0-9_-]+", R.string.Dataerror);

        if(editTextaddress.equals(""))
        {
            Toast.makeText(getActivity(), "Fill Address", Toast.LENGTH_LONG).show();
        }




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
                para.add(new BasicNameValuePair("IMEINo", imeino));


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
            else if (Status == 2)
            {
                Toast.makeText(getActivity(), "Pin Already Exist", Toast.LENGTH_LONG).show();
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
