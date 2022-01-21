package com.example.transilator;

import androidx.appcompat.app.AppCompatActivity;

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

public class Set_New_Transilation extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String[] languages = { "Kinyarwanda", "English",
            "French", "Swahili",
    };
    String choosenLanguage=languages[0];
    EditText variableText,valueText;
    String  variable,value;
    Button submitbtn;
    Translation translation=new Translation();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_transilation);
        Spinner language_spiner=findViewById(R.id.Nlanguages);
         variableText=(EditText)findViewById(R.id.variablename);
         valueText=(EditText)findViewById(R.id.languageValue);
         submitbtn=(Button)findViewById(R.id.Submit);
         valueText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
             @Override
             public void onFocusChange(View view, boolean hasFocus) {
                 if (!hasFocus) {
                     if (choosenLanguage.toLowerCase().equals(("Kinyarwanda").toLowerCase())) {
                         translation.setKinyarwanda(choosenLanguage.toLowerCase());
                         translation.setVariable(variable);
                         translation.setVariable(value);
                     } else if (choosenLanguage.toLowerCase().equals(("French").toLowerCase())) {
                         translation.setFrench(choosenLanguage.toLowerCase());
                         translation.setVariable(variable);
                         translation.setVariable(value);
                     } else if (choosenLanguage.toLowerCase().equals(("Swahili").toLowerCase())) {
                         translation.setKiswahil(choosenLanguage.toLowerCase());
                         translation.setVariable(variable);
                         translation.setVariable(value);
                     } else {
                         translation.setEnglish(choosenLanguage.toLowerCase());
                         translation.setVariable(variable);
                         translation.setVariable(value);
                     }

                     Log.d("current object: ", translation.toString());
                 }
             }
         });
         submitbtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 value=valueText.getText().toString();
                 variable=variableText.getText().toString();
                 Toast.makeText(getApplicationContext(),"Choosen: "+variable+", "+value+", "+choosenLanguage,Toast.LENGTH_LONG).show();
                Log.d("Translation object: ",translation.toString());
                 finish();
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
public void SendEmail(String email,String subject,String body){
    Intent i=new Intent(Intent.ACTION_SEND);
    i.setType("message/rfc822");
    i.putExtra(Intent.EXTRA_EMAIL,new String[]{email});
    i.putExtra(Intent.EXTRA_SUBJECT,subject);
    i.putExtra(Intent.EXTRA_TEXT,body);
    try{
        startActivity(Intent.createChooser(i,"Send mail....."));

    } catch (android.content.ActivityNotFoundException e) {
        Toast.makeText(Set_New_Transilation.this,"There are no email clients installed.",Toast.LENGTH_SHORT);
        e.printStackTrace();
    }
}
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int postion, long id) {
     choosenLanguage=languages[postion];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}