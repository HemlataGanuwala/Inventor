package com.example.hema.cableapp;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginPasswordFragment extends Fragment {

    View view;
    ProgressDialog progress;
    ServiceHandler shh;
    String path,username,password,user1,pass1,regstatus,regstatus1,imeino,Message,operatorno,cmonth,cyear,pathIp;
    EditText editTextuser,editTextpassword;
    int month,year,day;
    Button buttonlogin;
    int Status=1;
    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 0;


    public LoginPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login_password, container, false);

        final GlobalClass globalVariable = (GlobalClass) getActivity().getApplicationContext();
        path = globalVariable.getconstr();

        editTextuser = (EditText)view.findViewById(R.id.etuserid);
        editTextpassword = (EditText)view.findViewById(R.id.etloginpass);
        buttonlogin = (Button)view.findViewById(R.id.btnlogin);

        Submitdata();

        loadIMEI();

        new getOperaterNoData().execute();

        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginData();
//                Intent obj= new Intent(getActivity(),MainActivity.class);
//                startActivity(obj);
            }
        });

        return view;
    }

    public void LoginData(){

        username = editTextuser.getText().toString().trim();
        password = editTextpassword.getText().toString().trim();
        regstatus = "1";

        new getloginData().execute();

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

    public void Submitdata() {

        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);

        if (month == 0) {
            cmonth = "DEC";
            cyear = String.valueOf(year);
        }
        if (month == 1) {
            cmonth = "JAN";
            cyear = String.valueOf(year);
        }
        if (month == 2) {
            cmonth = "FEB";
            cyear = String.valueOf(year);
        }
        if (month == 3) {
            cmonth = "MAR";
            cyear = String.valueOf(year);
        }
        if (month == 4) {
            cmonth = "APR";
            cyear = String.valueOf(year);
        }
        if (month == 5) {
            cmonth = "MAY";
            cyear = String.valueOf(year);
        }
        if (month == 6) {
            cmonth = "JUN";
            cyear = String.valueOf(year);
        }
        if (month == 7) {
            cmonth = "JUL";
            cyear = String.valueOf(year);
        }
        if (month == 8) {
            cmonth = "AUG";
            cyear = String.valueOf(year);
        }
        if (month == 9) {
            cmonth = "SEP";
            cyear = String.valueOf(year);
        }
        if (month == 10) {
            cmonth = "OCT";
            cyear = String.valueOf(year);
        }
        if (month == 11) {
            cmonth = "NOV";
            cyear = String.valueOf(year);
        }

    }

    class getloginData extends AsyncTask<Void, Void, String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(getActivity());
            progress.getWindow().setBackgroundDrawable(new
                    ColorDrawable(android.graphics.Color.TRANSPARENT));
            progress.setIndeterminate(true);
            progress.setCancelable(false);
            progress.show();
            progress.setContentView(R.layout.progress_dialog);
        }

        @Override
        protected String doInBackground(Void... params) {
            shh = new ServiceHandler();
            String url = path + "Registration/AdminLogin";
            Log.d("Url: ", "> " + url);

            try{
                List<NameValuePair> params2 = new ArrayList<>();
                params2.add(new BasicNameValuePair("UserId",username));
                params2.add(new BasicNameValuePair("Password",password));
                params2.add(new BasicNameValuePair("IMEINo",imeino));
                params2.add(new BasicNameValuePair("SkyStatus","1"));

                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST , params2);

                if (jsonStr != null) {
                    JSONObject jObj = new JSONObject(jsonStr);
                    Status = Integer.parseInt(jObj.getString("Status"));
                    Message = (jObj.getString("Message"));

                }
                else
                {
                    //Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
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

            progress.dismiss();

            if (Status == 1)
            {
                    Toast.makeText(getActivity(), "Login Successfully", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity(),MainActivity.class);
                     intent.putExtra("a1",imeino);
                     intent.putExtra("a2",operatorno);
                     intent.putExtra("a3",cmonth);
                     intent.putExtra("a4",cyear);
                     intent.putExtra("a5",pathIp);
                    startActivity(intent);
                    //finish();

            }
            else
            {
                Toast.makeText(getActivity(), "" + Message, Toast.LENGTH_LONG).show();
            }


        }
    }

    class getOperaterNoData extends AsyncTask<Void, Void, String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            shh = new ServiceHandler();
            String url = path + "RegistrationApi/getOperatorno";
            Log.d("Url: ", "> " + url);

            try{
                List<NameValuePair> params2 = new ArrayList<>();
                params2.add(new BasicNameValuePair("IMEINo",imeino));

                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST , params2);

                if (jsonStr != null) {
                    JSONObject c1 = new JSONObject(jsonStr);
                    JSONArray classArray = c1.getJSONArray("Response");
                    for (int i = 0; i < classArray.length(); i++) {
                        JSONObject a1 = classArray.getJSONObject(i);
                        operatorno = a1.getString("OperatorCode");
                        pathIp = a1.getString("ApiLink");
                    }

                }
                else
                {
                    //Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
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

        }


    }



}
