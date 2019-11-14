package com.example.logger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.logger.customfonts.manejoArchivos;
import com.google.android.material.textfield.TextInputEditText;

public class FormCanActivity extends AppCompatActivity {

    private TextView email;
    private Button guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_can);

        email = (TextView)findViewById(R.id.caninos_email);
        guardar = (Button)findViewById(R.id.btnGuardarArchivo);
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

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarFormulario();
            }
        });
    }



    public void cargarFormulario(){
        formulario formulario = new formulario();
        TextInputEditText emial = (TextInputEditText)findViewById(R.id.caninos_email);
        formulario.setCorreo(email.getText().toString());

        TextInputEditText nombreSupervisor = (TextInputEditText)findViewById(R.id.nombreSupervisor);
        formulario.setNombreSupervisor(nombreSupervisor.getText().toString());

        TextInputEditText ruta = (TextInputEditText)findViewById(R.id.ruta);
        formulario.setRuta(ruta.getText().toString());

        TextInputEditText institucionEducativa = (TextInputEditText)findViewById(R.id.institucionEducativa);
        formulario.setInstitucionEducativa(institucionEducativa.getText().toString());

        TextInputEditText sede = (TextInputEditText)findViewById(R.id.sede);
        formulario.setSede(sede.getText().toString());

        TextInputEditText grupo = (TextInputEditText)findViewById(R.id.grupo);
        formulario.setGrupo(grupo.getText().toString());

        manejoArchivos manejoArchivos= new manejoArchivos();
        manejoArchivos.saveToFile(formulario);
    }
}