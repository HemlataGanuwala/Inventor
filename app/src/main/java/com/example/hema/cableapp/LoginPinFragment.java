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
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
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
public class LoginPinFragment extends Fragment {

    View view;
    ProgressDialog progress;
    ServiceHandler shh;
    String path,pin1,pin2,pin3,pin4,tpin,Message,imeino,operatorno,cmonth,cyear,pathIp;
    TextView textViewpin1,textViewpin2,textViewpin3,textViewpin4;
    Button button1,button2,button3,button4,button5,button6,button7,button8,button9,button0,buttonloginpin;
    ImageButton buttonpasswordshow,buttoncross;
    int Status = 1;
    int month,year,day;
    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 0;

    public LoginPinFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login_pin, container, false);
        final GlobalClass globalVariable = (GlobalClass) getActivity().getApplicationContext();
        path = globalVariable.getconstr();

        textViewpin1 = (TextView) view.findViewById(R.id.tvpin1);
        textViewpin2 = (TextView)view.findViewById(R.id.tvpin2);
        textViewpin3 = (TextView) view.findViewById(R.id.tvpin3);
        textViewpin4 = (TextView)view.findViewById(R.id.tvpin4);
        button0 = (Button)view.findViewById(R.id.btn0);
        button1 = (Button)view.findViewById(R.id.btn1);
        button2 = (Button)view.findViewById(R.id.btn2);
        button3 = (Button)view.findViewById(R.id.btn3);
        button4 = (Button)view.findViewById(R.id.btn4);
        button5 = (Button)view.findViewById(R.id.btn5);
        button6 = (Button)view.findViewById(R.id.btn6);
        button7 = (Button)view.findViewById(R.id.btn7);
        button8 = (Button)view.findViewById(R.id.btn8);
        button9 = (Button)view.findViewById(R.id.btn9);
        buttonpasswordshow = (ImageButton) view.findViewById(R.id.btneye);
        buttoncross = (ImageButton) view.findViewById(R.id.btnclear);

        Submitdata();
        loadIMEI();

        new getOperaterNoData().execute();

//        buttonloginpin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LoginData();
//            }
//        });

        textViewpin4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                LoginData();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        buttoncross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cleartextdata();
            }
        });

        buttonpasswordshow.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch ( event.getAction() ) {

                    case MotionEvent.ACTION_UP:
                        textViewpin1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        textViewpin2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        textViewpin3.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        textViewpin4.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;

                    case MotionEvent.ACTION_DOWN:
                        textViewpin1.setInputType(InputType.TYPE_CLASS_TEXT);
                        textViewpin2.setInputType(InputType.TYPE_CLASS_TEXT);
                        textViewpin3.setInputType(InputType.TYPE_CLASS_TEXT);
                        textViewpin4.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;

                }
                return true;
            }

        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tpin = "1";
                Filltextdata();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tpin = "2";
                Filltextdata();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tpin = "3";
                Filltextdata();
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tpin = "4";
                Filltextdata();
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tpin = "5";
                Filltextdata();
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tpin = "6";
                Filltextdata();
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tpin = "7";
                Filltextdata();
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tpin = "8";
                Filltextdata();
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tpin = "9";
                Filltextdata();
            }
        });

        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tpin = "0";
                Filltextdata();
            }
        });
        return view;
    }

    private static int getPreviousYear() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, -1);
        return c.get(Calendar.YEAR);
    }

    public void Submitdata() {

        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);

        if (month == 0) {
            year = getPreviousYear();
            cyear = String.valueOf(year);
            cmonth = "DEC";
        }
        else
        {
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
    }

    public void Filltextdata()
    {
        if (textViewpin1.getText().toString().equals(""))
        {
            textViewpin1.setText(tpin);
        }
        else if (textViewpin2.getText().toString().equals(""))
        {
            textViewpin2.setText(tpin);
        }
        else if (textViewpin3.getText().toString().equals(""))
        {
            textViewpin3.setText(tpin);
        }
        else if (textViewpin4.getText().toString().equals(""))
        {
            textViewpin4.setText(tpin);
        }
    }

    public void Cleartextdata()
    {
        if (!textViewpin4.getText().toString().equals(""))
        {
            textViewpin4.setText("");
        }
        else if (!textViewpin3.getText().toString().equals(""))
        {
            textViewpin3.setText("");
        }
        else if (!textViewpin2.getText().toString().equals(""))
        {
            textViewpin2.setText("");
        }
        else if (!textViewpin1.getText().toString().equals(""))
        {
            textViewpin1.setText("");
        }
    }

    public void LoginData(){

        pin1 = textViewpin1.getText().toString() + textViewpin2.getText().toString() + textViewpin3.getText().toString() + textViewpin4.getText().toString();

        if(!textViewpin4.getText().toString().equals(""))
        {
            new getloginData().execute();
        }


    }



    class getloginData extends AsyncTask<Void, Void, String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progress=new ProgressDialog(getActivity());
//            progress.setMessage("Loading...");
//            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progress.setIndeterminate(true);
//            progress.setProgress(0);
//            progress.show();

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
            String url = path + "RegistrationApi/AdminLogin";
            Log.d("Url: ", "> " + url);

            try{
                List<NameValuePair> params2 = new ArrayList<>();
                params2.add(new BasicNameValuePair("Pin",pin1));
                //params2.add(new BasicNameValuePair("Password",null));
                params2.add(new BasicNameValuePair("IMEINo",imeino));
                params2.add(new BasicNameValuePair("SkyStatus","1"));

                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST , params2);

                if (jsonStr != null) {
                    JSONObject c1 = new JSONObject(jsonStr);
                    Status = Integer.parseInt(c1.getString("Status"));
                    Message = (c1.getString("Message"));

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
                textViewpin1.setText("");
                textViewpin2.setText("");
                textViewpin3.setText("");
                textViewpin4.setText("");
                    //finish();
            }
            else {
                Toast.makeText(getActivity(), "" + Message, Toast.LENGTH_LONG).show();
                textViewpin1.setText("");
                textViewpin2.setText("");
                textViewpin3.setText("");
                textViewpin4.setText("");
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




}
