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
import android.widget.Toast;



import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PackageFragment extends Fragment {

    View view;
    ServiceHandler shh;
    Button buttoninsert;
    String packagenm,path,rate,imeino,operatorno,pathIp;

    int Status = 1;
    ProgressDialog progress;
    EditText editTextpackagenm,editTextrate;



    public PackageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_package, container, false);
//        final GlobalClass globalVariable = (GlobalClass) getActivity().getApplicationContext();
//        path = globalVariable.getconstr();


        editTextpackagenm = (EditText)view.findViewById(R.id.etpackagename);
        editTextrate=(EditText)view.findViewById(R.id.etpackagerate);
        buttoninsert = (Button) view.findViewById(R.id.btnpackageinsert);

        Display();

        buttoninsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InsertData();

            }
        });
        return view;

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_package, container, false);
    }

    public void InsertData()
    {
        packagenm = editTextpackagenm.getText().toString();
        rate = editTextrate.getText().toString();

    }


    public void Display()
    {
        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null)
        {
            imeino = (String)bundle.get("a1");
            operatorno = (String)bundle.get("a2");
            pathIp = (String)bundle.get("a5");
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

            String url = pathIp + "Registration/PackageInsert";

            Log.d("Url: ", "> " + url);

            try {
                // Making a request to url and getting response

                List<NameValuePair> para = new ArrayList<>();
                // para.add(new BasicNameValuePair("CustBal", balance));
                para.add(new BasicNameValuePair("PackageName", packagenm));
                para.add(new BasicNameValuePair("Rate", rate));
                para.add(new BasicNameValuePair("IMEINo", imeino));
                para.add(new BasicNameValuePair("OperatorCode", operatorno));

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

            editTextpackagenm.setText("");
            editTextrate.setText("");
        }
    }

}
