package com.example.transilator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Set_New_Transilation extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String[] languages = { "Kinyarwanda", "English",
            "French", "Swahil",
    };
    String choosenLanguage=languages[0];
    EditText variableText,valueText;
    String  variable,value;
    Button submitbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_transilation);
        Spinner language_spiner=findViewById(R.id.Nlanguages);
         variableText=(EditText)findViewById(R.id.variablename);
         valueText=(EditText)findViewById(R.id.languageValue);
         submitbtn=(Button)findViewById(R.id.Submit);
         submitbtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 value=valueText.getText().toString();
                 variable=variableText.getText().toString();
                 Toast.makeText(getApplicationContext(),"Choosen: "+variable+", "+value+", "+choosenLanguage,Toast.LENGTH_LONG).show();
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int postion, long id) {
     choosenLanguage=languages[postion];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}