package com.example.transilator;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Set_New_Transilation extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String[] languages = { "Kinyarwanda", "English",
            "French", "Swahili",
    };
    String previousLanguage;
    String choosenLanguage=languages[0];
    EditText variableText,valueText;
    String  variable,value,previousValue;
    Button submitbtn;
    ArrayList<String> variables=new ArrayList<>();
    Translation translation=new Translation();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_transilation);
        Spinner language_spiner=findViewById(R.id.Nlanguages);
         variableText=(EditText)findViewById(R.id.variablename);
         valueText=(EditText)findViewById(R.id.languageValue);
         submitbtn=(Button)findViewById(R.id.Submit);
        variable=variableText.getText().toString();
        translation.setVariable(variable);
        variables = (ArrayList<String>) getIntent().getSerializableExtra("Variables");

        Log.d("Variables passed: ",""+variables);
         submitbtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 variable=variableText.getText().toString();
                 value=valueText.getText().toString();
                 translation.setVariable(variable);
                 if (choosenLanguage.toLowerCase().equals(("Kinyarwanda").toLowerCase())) {
                     translation.setKinyarwanda(value);

                 } else if (choosenLanguage.toLowerCase().equals(("French").toLowerCase())) {
                     translation.setFrench(value);

                 } else if (choosenLanguage.toLowerCase().equals(("Swahili").toLowerCase())) {
                     translation.setKiswahil(value);

                 } else {
                     translation.setEnglish(previousValue);

                 }
                 makeNewTranslation(translation);

             }
         });
        language_spiner.setOnItemSelectedListener(this);
        ArrayAdapter languages_adapter
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                languages);
        languages_adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        language_spiner.setAdapter(languages_adapter);
    }
//public void SendEmail(String email,String subject,String body){
//    Intent i=new Intent(Intent.ACTION_SEND);
//    i.setType("message/rfc822");
//    i.putExtra(Intent.EXTRA_EMAIL,new String[]{email});
//    i.putExtra(Intent.EXTRA_SUBJECT,subject);
//    i.putExtra(Intent.EXTRA_TEXT,body);
//    try{
//        startActivity(Intent.createChooser(i,"Send mail....."));
//
//    } catch (android.content.ActivityNotFoundException e) {
//        Toast.makeText(Set_New_Transilation.this,"There are no email clients installed.",Toast.LENGTH_SHORT);
//        e.printStackTrace();
//    }
//}
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int postion, long id) {

        previousLanguage=choosenLanguage;
      //  Toast.makeText(Set_New_Transilation.this,"Previous: "+previousLanguage,Toast.LENGTH_SHORT).show();
        choosenLanguage=languages[postion];

        value=valueText.getText().toString();


        if (previousLanguage.toLowerCase().equals(("Kinyarwanda").toLowerCase())) {
            translation.setKinyarwanda(value);

        } else if (previousLanguage.toLowerCase().equals(("French").toLowerCase())) {
            translation.setFrench(value);

        } else if (previousLanguage.toLowerCase().equals(("Swahili").toLowerCase())) {
            translation.setKiswahil(value);

        } else {
            translation.setEnglish(value);

        }
        valueText.setText("");
       // Toast.makeText(Set_New_Transilation.this,"Current value of  "+choosenLanguage+" is "+value,Toast.LENGTH_SHORT).show();



    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
   synchronized public void makeNewTranslation(Translation translation){
       final ProgressDialog progressDialog = ProgressDialog.show(this, "", "Please wait for uploading....");
       new Thread() {
           public void run() {
               try {
                   RequestQueue requestQueue = Volley.newRequestQueue(Set_New_Transilation.this);
                   int method=1;
                   String url = "https://ishyiga-transilation.herokuapp.com/demo/v1/translate/";
                     if(variables.contains(translation.getVariable())){
                         url=url+translation.getVariable();
                         Toast.makeText(Set_New_Transilation.this,"Updating variable "+translation.getVariable(),Toast.LENGTH_LONG).show();
                         method=2;
                     }
                   JSONObject js = new JSONObject();
                   try {
                       js.put("variable", translation.getVariable());
                       js.put("kinyarwanda", translation.getKinyarwanda());
                       js.put("english", translation.getEnglish());
                       js.put("french", translation.getFrench());
                       js.put("swahili", translation.getKiswahil());
                   } catch (JSONException e) {
                       e.printStackTrace();
                   }

                   // Make request for JSONObject
                   JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                           method, url, js,
                           new Response.Listener<JSONObject>() {
                               @Override
                               public void onResponse(JSONObject response) {
                                   Log.d("Response for post", response.toString() + " Well posted");
                               }
                           }, new Response.ErrorListener() {
                       @Override
                       public void onErrorResponse(VolleyError error) {
                           VolleyLog.d("Error: Tag", "Error: " + error.getMessage());
                       }
                   }) {

                       /**
                        * Passing some request headers
                        */
                       @Override
                       public Map<String, String> getHeaders() throws AuthFailureError {
                           HashMap<String, String> headers = new HashMap<String, String>();
                           headers.put("Content-Type", "application/json; charset=utf-8");
                           return headers;
                       }

                   };

                   // Adding request to request queue
                   Volley.newRequestQueue(Set_New_Transilation.this).add(jsonObjReq);

               }

               catch (Exception e) {
                   Log.e("tag", ""+e.getMessage());
               }
               // dismiss the progress dialog
               progressDialog.dismiss();
              // finish();
           }
       }.start();

       }
}
