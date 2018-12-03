package com.example.hema.cableapp;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginPinFragment extends Fragment {

    View view;
    ProgressDialog progress;
    ServiceHandler shh;
    String path,pin1,pin2,pin3,pin4,tpin;
    TextView textViewpin1,textViewpin2,textViewpin3,textViewpin4;
    Button button1,button2,button3,button4,button5,button6,button7,button8,button9,button0,buttonloginpin;
    ImageButton buttonpasswordshow,buttoncross;
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
        buttonloginpin = (Button) view.findViewById(R.id.btnpinlogin);

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
                params2.add(new BasicNameValuePair("Pin",pin1));
                params2.add(new BasicNameValuePair("Password",null));

                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST , params2);

                if (jsonStr != null) {
                    JSONObject c1 = new JSONObject(jsonStr);
                    JSONArray classArray = c1.getJSONArray("Response");
                    //JSONArray jsonarry = new JSONArray(jsonStr);

                    for (int i = 0; i < classArray.length(); i++) {
                        JSONObject a1 = classArray.getJSONObject(i);

                        pin2 = a1.getString("Pin");

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

            if (flag == 1)
            {
                Toast.makeText(getActivity(), "Login Successfully", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
                    //finish();
            }
            else {
                Toast.makeText(getActivity(), "Login Failed", Toast.LENGTH_LONG).show(); }
        }


    }


}
