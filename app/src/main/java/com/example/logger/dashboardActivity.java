package com.example.logger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.logger.customfonts.manejoArchivos;

import java.util.List;

public class dashboardActivity extends AppCompatActivity {
    private TextView username;
    private TextView name;
    private TextView email;
    private TextView textSincronizacion;

    Button botonFormulario;
    Button buttonLogout;
    Button buttonSincronizacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        /*Spinner spinner = (Spinner) findViewById(R.id.spinner3);
        String[] letra = {"A","B","C","D","E"};
        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, letra));*/

        username = (TextView)findViewById(R.id.dashboard_username);
        name = (TextView)findViewById(R.id.dashboard_name);
        email = (TextView)findViewById(R.id.dashboard_email);
        botonFormulario = (Button)findViewById(R.id.buttonFormulario);
        buttonSincronizacion = (Button)findViewById(R.id.botonSincronizacion);
        buttonLogout = (Button)findViewById(R.id.buttonLogout);
        textSincronizacion=(TextView)findViewById(R.id.textSincronizacion);
        manejoArchivos archivos = new manejoArchivos();
        List<String >archivosS = archivos.leerRutasArchivos();
        textSincronizacion.setText("Sincronización ("+archivosS.size()+") pendientes");

        try {
            Intent intent = getIntent();
            String login_username = intent.getExtras().getString("username");
            String login_name = intent.getExtras().getString("name");
            String login_email = intent.getExtras().getString("email");
            username.setText(login_username);
            name.setText(login_name);
            email.setText(login_email);
        } catch (NullPointerException e) {
            Log.e("ERROR", e.toString());
        }

        botonFormulario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(),FormCanActivity.class);
                intent.putExtra("email", email.getText().toString());
                startActivity(intent);
            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearPreferences();
                Intent intent = new Intent (view.getContext(),MainActivity.class);
                intent.putExtra("document", username.getText().toString());
                startActivity(intent);
                finish();
            }
        });

        buttonSincronizacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util u = new util(getApplicationContext());
                if(u.verificarConexionInternet()) {

                    manejoArchivos archivos = new manejoArchivos();
                    List<String >archivosS = archivos.leerRutasArchivos();
                    int numero = 1;
                    for(String ruta:archivosS){
                        formulario obj =archivos.ReadFile(v.getContext(),ruta);
                        //chiti ACA COLOCA EL CODIGO QUE ACTUALIZA EN MYSQL
                        archivos.eliminarArchivo(ruta);
                        numero++;
                        Toast.makeText(getApplicationContext(), "Sincronizando "+numero+" de "+archivosS.size(), Toast.LENGTH_LONG).show();
                        textSincronizacion.setText("Sincronización ("+numero+") pendientes");
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Para sincronizar debe tener conexion a internet.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }



    private void clearPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("session_preferences", Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
    }

}
