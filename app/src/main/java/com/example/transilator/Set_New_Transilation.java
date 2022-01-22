package com.example.transilator;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Set_New_Transilation extends AppCompatActivity {
    String[] languages = { "Kinyarwanda", "English",
            "French", "Swahili",
    };
    String english,french,swahili,kinyarwanda,variable;
    EditText Englishtext,frenchtext,swahilitext,kinyarwandatext,variableText;
    Button submitbtn;
    ArrayList<String> variables=new ArrayList<>();
    Translation translation=new Translation();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_transilation);
        //  Spinner language_spiner=findViewById(R.id.Nlanguages);
        variableText = (EditText) findViewById(R.id.variablename);
        Englishtext = (EditText) findViewById(R.id.english);
        frenchtext = (EditText) findViewById(R.id.french);
        swahilitext = (EditText) findViewById(R.id.swahili);
        kinyarwandatext = (EditText) findViewById(R.id.kinyarwanda);

        submitbtn = (Button) findViewById(R.id.Submit);
        variable = variableText.getText().toString();
        translation.setVariable(variable);
        variables = (ArrayList<String>) getIntent().getSerializableExtra("Variables");

        Log.d("Variables passed: ", "" + variables);
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                variable = variableText.getText().toString();
                english=Englishtext.getText().toString();
                french=frenchtext.getText().toString();
                kinyarwanda=kinyarwandatext.getText().toString();
                swahili=swahilitext.getText().toString();
                translation=new Translation(variable,english,kinyarwanda,french,swahili);
                makeNewTranslation(translation);

            }
        });

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
                                   Toast.makeText(Set_New_Transilation.this, " Well posted",Toast.LENGTH_LONG).show();
                                   Set_New_Transilation.this.finish();
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
