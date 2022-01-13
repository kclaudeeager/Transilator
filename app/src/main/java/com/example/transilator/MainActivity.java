package com.example.transilator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    // create array of Strings
    // and store name of variables
    String[] variables = { "V_Gura", "V_Gurisha",
            "V_Barisha", "V_Sora",
            };
    String[] languages = { "Kinyarwanda", "English",
            "French", "Swahil",
    };
    String choosenVariable=variables[0];
    String choosenLanguage=languages[0];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner variable_spiner = findViewById(R.id.variables);
        Spinner language_spiner=findViewById(R.id.languages);
        Button transilateBtn=(Button)findViewById(R.id.transilate);
        TextView gotoNewPage=(TextView)findViewById(R.id.goTonew) ;
        gotoNewPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newPageIntent=new Intent();
                newPageIntent.setClass(getApplicationContext(),Set_New_Transilation.class);
                startActivity(newPageIntent);
            }
        });
        variable_spiner.setOnItemSelectedListener(this);
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
                Toast.makeText(getApplicationContext(),
                       "Choosen variable and language: "+ choosenVariable+" ,"+choosenLanguage,
                        Toast.LENGTH_LONG)
                        .show();
            }
        });
        // Create the instance of ArrayAdapter
        // having the list of variables
        ArrayAdapter variables_adapter
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                variables);
        // Create the instance of ArrayAdapter
        // having the list of languages
        ArrayAdapter languages_adapter
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                languages);
        // set simple layout resource file
        // for each item of spinner
        variables_adapter.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        languages_adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        // Set the ArrayAdapters data on the
        // Spinner which binds data to spinner
        variable_spiner.setAdapter(variables_adapter);
        language_spiner.setAdapter(languages_adapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
         choosenVariable=variables[position];

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}