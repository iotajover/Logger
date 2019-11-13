package com.example.logger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Properties;

public class FormCanActivity extends AppCompatActivity {

    private TextView email;
    private TextInputEditText name;
    private TextInputEditText document;
    private TextInputEditText password;
    private TextInputEditText confirmPassword;
    private RadioButton genderMale;
    private RadioButton genderFemale;
    private Button register;
    private User user;
    private Properties properties;


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



        Spinner spinner6 = (Spinner) findViewById(R.id.spGrupo);
        String[] Uniforme = {"si","no", "otro"};
        spinner6.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Uniforme));
        if (spinner6.getSelectedItem()!=""){
            Toast.makeText(getApplicationContext(), "si", Toast.LENGTH_LONG).show();

//Si el spinner no tiene nada seleccionado
        }else{
            Toast.makeText(getApplicationContext(), "Seleccione", Toast.LENGTH_LONG).show();
        }


    }
}