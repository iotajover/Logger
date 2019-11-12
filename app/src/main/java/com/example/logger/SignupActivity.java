package com.example.logger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class SignupActivity extends AppCompatActivity {
    private TextInputEditText name;
    private TextInputEditText document;
    private TextInputEditText email;
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
        setContentView(R.layout.activity_signup);

        name = (TextInputEditText)findViewById(R.id.register_name);
        document = (TextInputEditText)findViewById(R.id.register_document);
        email = (TextInputEditText)findViewById(R.id.register_email);
        password = (TextInputEditText)findViewById(R.id.register_password);
        confirmPassword = (TextInputEditText)findViewById(R.id.register_confirm_password);
        genderMale = (RadioButton)findViewById(R.id.register_gender_male);
        genderFemale = (RadioButton)findViewById(R.id.register_gender_female);
        register = (Button)findViewById(R.id.register_register);

        PropertyReader propertyReader = new PropertyReader(this);
        properties = propertyReader.getProperties("logger.properties");

        try {
            Intent intent = getIntent();
            String login_document = intent.getExtras().getString("document");
            if (!login_document.isEmpty()) {
                document.setText(login_document);
            }
        } catch (NullPointerException e) {
            Log.e("ERROR", e.toString());
        }

        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String gender;
                if(genderMale.isChecked()) {
                    gender = "M";
                } else if(genderFemale.isChecked()) {
                    gender = "F";
                } else {
                    gender = "";
                }
                user = new User(name.getText().toString(), document.getText().toString(), email.getText().toString(), gender, "Técnica", password.getText().toString(), confirmPassword.getText().toString());

                String errorCompleteInformation = user.validateCompleteInformation();
                if(errorCompleteInformation.isEmpty()) {
                    if(user.validatePassword()) {
                        util u = new util(getApplicationContext());
                        if(u.verificarConexionInternet()) {
                            validateUserCreated(properties.getProperty("validateUserCreatedURL"));
                        } else {
                            Toast.makeText(getApplicationContext(), "Para registrarse debe tener conexion a internet.", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), errorCompleteInformation, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void createUser(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Usuario registrado exitosamente.", Toast.LENGTH_LONG).show();
                LoginActivity.loginActivityInstance.finish();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.putExtra("document", user.getDocument());
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("nombre", user.getName());
                params.put("usuario", user.getDocument());
                params.put("correo", user.getEmail());
                params.put("genero", user.getGender());
                params.put("contrasena", user.getPassword());
                params.put("perfil", user.getProfile());

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void validateUserCreated(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Ya existe un usuario creado con ese número de documento de identidad.", Toast.LENGTH_LONG).show();
                    LoginActivity.loginActivityInstance.finish();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    intent.putExtra("document", user.getDocument());
                    startActivity(intent);
                    finish();
                } else {
                    createUser(properties.getProperty("createUserURL"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("usuario", document.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
