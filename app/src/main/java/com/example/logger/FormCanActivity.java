package com.example.logger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class FormCanActivity extends AppCompatActivity {

    private TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_can);

        email = (TextView)findViewById(R.id.caninos_email);

        try {
            Intent intent = getIntent();
            String login_email = intent.getExtras().getString("email");
            email.setText(login_email);
        } catch (NullPointerException e) {
            Log.e("ERROR", e.toString());
        }

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        String[] letra = {"RUTA1","RUTA2","RUTA3","RUTA4","RUTA5"};
        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, letra));

        /*spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id)
            {
                Toast.makeText(adapterView.getContext(),
                        (String) adapterView.getItemAtPosition(pos), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {    }
        });
*/
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        String[] institucion = {"COLEGIO AMERICANO","COSTA RICA","GIMNASIO MODERNO","REFUS","SAN BARTOLOME"};
        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, institucion));

        Spinner spinner3 = (Spinner) findViewById(R.id.spinnerSede);
        String[] sede = {"a","b"};
        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sede));
    }
}