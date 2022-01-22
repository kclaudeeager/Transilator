package com.example.transilator;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    // create array of Strings
    // and store name of variables
ArrayList<String> variables=new ArrayList<>();
    String[] languages = { "Kinyarwanda", "English",
            "French", "Swahili",
    };
    ArrayAdapter variables_adapter;
    String choosenVariable;
    String choosenLanguage=languages[0];
    String translated;
    TextView translatedTXV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Spinner variable_spiner = findViewById(R.id.variables);
variables= loadVariables();
variables.add("select.....");
translatedTXV=(TextView)findViewById(R.id.transilatedValue);
       // loadList();

        Spinner language_spiner=findViewById(R.id.languages);
        Button transilateBtn=(Button)findViewById(R.id.transilate);
        TextView gotoNewPage=(TextView)findViewById(R.id.goTonew) ;
        gotoNewPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newPageIntent=new Intent();
                newPageIntent.setClass(getApplicationContext(),Set_New_Transilation.class);
                newPageIntent.putExtra("Variables",variables);
                startActivity(newPageIntent);
            }
        });

      language_spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
              choosenLanguage=languages[position];
          }

          @Override
          public void onNothingSelected(AdapterView<?> adapterView) {

          }
      });
        transilateBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
              if(choosenVariable.equals("select....."))
                  Toast.makeText(MainActivity.this, "please select any variable", Toast.LENGTH_SHORT).show();
              else {
                  Translate(choosenVariable,choosenLanguage);

              }
            }
        });
        // Create the instance of ArrayAdapter
        // having the list of variables
        for(String variable:variables){
            Log.d("Var: ",variable);
        }
        if(variables.size()>0)
            choosenVariable=variables.get(0);
        Log.d("Local_variables: ",variables.toString());
        variables_adapter
                = new ArrayAdapter(
                MainActivity.this,
                android.R.layout.simple_spinner_item,
                variables);
        variable_spiner.setAdapter(variables_adapter);
        variables_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        // Set the ArrayAdapters data on the
        // Spinner which binds data to spinner


        // Create the instance of ArrayAdapter
        // having the list of languages
        ArrayAdapter languages_adapter
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                languages);
        languages_adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        // set simple layout resource file
        // for each item of spinner

        variable_spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                choosenVariable = parent.getItemAtPosition(pos).toString();
                Toast.makeText(MainActivity.this, "value"+choosenVariable, Toast.LENGTH_LONG).show();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        language_spiner.setAdapter(languages_adapter);

    }

    synchronized  public ArrayList<String> loadVariables(){
        // contacts=new ArrayList<Contact>();
        ArrayList<String> translations_variables=new ArrayList<>();
        Toast.makeText(MainActivity.this,"Loading valiables....",Toast.LENGTH_LONG).show();

        final ProgressDialog progressDialog = ProgressDialog.show(this, "", "Please wait for fetching...");
        new Thread() {
            public void run() {
                try{

                    RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
                    String url="https://ishyiga-transilation.herokuapp.com/demo/v1/translate/variables";

                    StringRequest request=new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        Log.d("RESPONSE", response);

                                        JSONArray variables= new JSONArray(response);
                                        for (int i = 0; i < variables.length(); i++) {
                                            Log.d("Variable "+(i+1),variables.get(i).toString());
                                            if(!translations_variables.contains(variables.get(i))){
                                                translations_variables.add(variables.get(i).toString());
                                            }
                                            //}
                                        }
                                        Log.i("array:",translations_variables.toString());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Error",""+error.getMessage());
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            return super.getParams();
                        }

                    };

                    requestQueue.add(request);
                }
                catch (Exception e) {
                    Log.e("tag", e.getMessage());
                }
                // dismiss the progress dialog
                progressDialog.dismiss();
            }
        }.start();


        return translations_variables;
    }

    synchronized  public void Translate(String choosenVariable,String choosenLanguage){
        // contacts=new ArrayList<Contact>();
        translatedTXV.setText("Loading......");
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        String url="https://ishyiga-transilation.herokuapp.com/demo/v1/translate/"+choosenVariable;

        StringRequest request=new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            Log.d("RESPONSE for language", response);
                            JSONObject jsonObject=null;
                            jsonObject = new JSONObject(response);
                            translated=jsonObject.getString(choosenLanguage.toLowerCase());

                            Toast.makeText(MainActivity.this,"Value is "+translated,Toast.LENGTH_LONG).show();
                            if((translated!="")&&(translated!=null)){
                                translatedTXV.setText(translated);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this,"Unable to translate "+choosenVariable+" in "+choosenLanguage,Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error",error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }

        };

        requestQueue.add(request);

    }
    public void loadList(){
        // contacts=new ArrayList<Contact>();
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        String url="https://ishyiga-transilation.herokuapp.com/demo/v1/translate/";
        ArrayList<Translation>translations=new ArrayList<>();
        StringRequest request=new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                             Log.d("RESPONSE", response);
                            JSONObject jsonObject = null;

                            jsonObject = new JSONObject(response);
                            JSONArray Translations= jsonObject.getJSONArray("Translations");
                            for (int i = 0; i < Translations.length(); i++) {
                                JSONObject res = null;

                                res = Translations.getJSONObject(i);

                                String variable = null;

                                Log.d("Transilation_Array",Translations.toString());
                                variable = res.getString("variable");

                                String english = null;

                                english = res.getString("english");

                                String french = null;

                                french = res.getString("french");

                                String kiswahil = null;

                                kiswahil = res.getString("swahili");
                                String kinyarwanda=res.getString("kinyarwanda");

                                Translation translation=new Translation(variable,english,kinyarwanda,french,kiswahil);

                                //Log.d("remote:", translation.toString());
                                 if (! translations.contains(translation)) {
                                     translations.add(translation);
                                 }
                               // Log.d("Translation: ",translation.toString());

                                //}
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error",""+error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }

        };

        requestQueue.add(request);
    }


    @Override
    protected void onStart() {
        super.onStart();
       // Log.d("Test: ","Onstart");

    }


}