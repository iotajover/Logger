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
import android.widget.Toast;

public class dashboardActivity extends AppCompatActivity {
    private TextView username;
    private TextView name;

    Button botonFormulario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
/*
        Spinner spinner = (Spinner) findViewById(R.id.spinner3);
        String[] letra = {"A","B","C","D","E"};
        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, letra));*/

        username = (TextView)findViewById(R.id.dashboard_username);
        name = (TextView)findViewById(R.id.dashboard_name);
        botonFormulario = (Button)findViewById(R.id.buttonFormulario);

        try {
            Intent intent = getIntent();
            String login_username = intent.getExtras().getString("username");
            String login_name = intent.getExtras().getString("name");
            username.setText(login_username);
            name.setText(login_name);
        } catch (NullPointerException e) {
            Log.e("ERROR", e.toString());
        }

        botonFormulario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(),FormCanActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }
}
