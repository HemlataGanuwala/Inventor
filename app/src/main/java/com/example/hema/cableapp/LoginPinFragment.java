package com.example.hema.cableapp;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginPinFragment extends Fragment {

    View view;
    ProgressDialog progress;
    ServiceHandler shh;
    String path,username,password,user1,pass1,regstatus,regstatus1;
    TextView textViewpin1,textViewpin2,textViewpin3,textViewpin4;
    Button button1,button2,button3,button4,button5,button6,button7,button8,button9,button0,buttonpasswordshow,buttoncross;
    int flag=1;

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
        textViewpin1 = (TextView)view.findViewById(R.id.tvpin2);
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

//        buttonlogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LoginData();
//            }
//        });
        return view;
    }

    public void LoginData(){

//        username = editTextuser.getText().toString().trim();
//        password = editTextpassword.getText().toString().trim();
        regstatus = "1";

        new getloginData().execute();

    }

    class getloginData extends AsyncTask<Void, Void, String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress=new ProgressDialog(getActivity());
            progress.setMessage("Loading...");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.setProgress(0);
            progress.show();
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

                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST , params2);

                if (jsonStr != null) {
                    JSONObject c1 = new JSONObject(jsonStr);
                    JSONArray classArray = c1.getJSONArray("Response");
                    //JSONArray jsonarry = new JSONArray(jsonStr);

                    for (int i = 0; i < classArray.length(); i++) {
                        JSONObject a1 = classArray.getJSONObject(i);

                        user1 = a1.getString("UserId");
                        pass1 = a1.getString("Password");
                        regstatus1 = a1.getString("SkyStatus");
                    }


                    if(classArray.length() == 0)
                    {
                        flag = 0;
                    }
                    else
                    {
                        flag = 1;

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

            progress.dismiss();

            if (regstatus1 == "1")
            {
                if (flag == 1)
                {
                    Toast.makeText(getActivity(), "Login Successfully", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity(),MainActivity.class);
                    startActivity(intent);
                    //finish();
                }
                else {
                    Toast.makeText(getActivity(), "Login Failed", Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                Toast.makeText(getActivity(), "App is not Activated", Toast.LENGTH_LONG).show();
            }


        }
    }

}
