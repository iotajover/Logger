package com.example.logger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
        String[] ruta = {"RUTA1","RUTA2","RUTA3","RUTA4","RUTA5"};
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ruta));
        if (spinner.getSelectedItem()!=""){
            Toast.makeText(getApplicationContext(), "RUTA1", Toast.LENGTH_LONG).show();

//Si el spinner no tiene nada seleccionado
        }else{
            Toast.makeText(getApplicationContext(), "Seleccione", Toast.LENGTH_LONG).show();
        }


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
        if (spinner.getSelectedItem()!=""){
            Toast.makeText(getApplicationContext(), "COLEGIO AMERICANO", Toast.LENGTH_LONG).show();

//Si el spinner no tiene nada seleccionado
        }else{
            Toast.makeText(getApplicationContext(), "Seleccione", Toast.LENGTH_LONG).show();
        }



        Spinner spinner3 = (Spinner) findViewById(R.id.spinner3);
        String[] sede = {"a","b"};
        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sede));
        if (spinner.getSelectedItem()!=""){
            Toast.makeText(getApplicationContext(), "A", Toast.LENGTH_LONG).show();

//Si el spinner no tiene nada seleccionado
        }else{
            Toast.makeText(getApplicationContext(), "Seleccione", Toast.LENGTH_LONG).show();
        }


        Spinner spinner5 = (Spinner) findViewById(R.id.spinner5);
        String[] orden = {"a","b"};
        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, orden));

        Spinner spinner4 = (Spinner) findViewById(R.id.spinner4);
        String[] Grupo = {"1","2","3","4","5"};
        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Grupo));

        Spinner spinner6 = (Spinner) findViewById(R.id.spinner6);
        String[] Uniforme = {"si","no", "otro"};
        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Uniforme));
    }
}