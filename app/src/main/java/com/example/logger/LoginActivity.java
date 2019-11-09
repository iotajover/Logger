package com.example.logger;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.logger.customfonts.manejoSQLiteHelper;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    public static Activity loginActivityInstance;
    private Button login;
    private Button register;
    private TextInputEditText email;
    private TextInputEditText password;
    private Properties properties;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginActivityInstance = this;

        login = (Button)findViewById(R.id.login_login);
        register = (Button)findViewById(R.id.login_register);
        email = (TextInputEditText)findViewById(R.id.login_email);
        password = (TextInputEditText)findViewById(R.id.login_password);

        PropertyReader propertyReader = new PropertyReader(this);
        properties = propertyReader.getProperties("logger.properties");

        try {
            Intent intent = getIntent();
            String login_email = intent.getExtras().getString("email");
            if(!login_email.isEmpty()) {
                email.setText(login_email);
            }
        } catch (NullPointerException e) {
            Log.e("ERROR", e.toString());
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                util u = new util();
                manejoSQLiteHelper manejoSQLiteHelper = new manejoSQLiteHelper();
                manejoSQLiteHelper.consultarFromularios(LoginActivity.this);
                if(u.verificarConexionInternet()) {
                    if (!email.getText().toString().isEmpty()) {
                        if (!password.getText().toString().isEmpty()) {
                            validateUser(properties.getProperty("validateUserURL"));
                        } else {
                            Toast.makeText(getApplicationContext(), "Por favor ingrese la contraseña.", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Por favor ingrese el correo.", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "No posee conexion a internet", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void registerUser() {
        util u = new util();
        if(u.verificarConexionInternet()){
            Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
            intent.putExtra("email", email.getText().toString());
            password.setText("");
            startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(), "Para registrarse debe poseer conexion a internet", Toast.LENGTH_LONG).show();
        }

    }

    private void validateUser(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()) {
                    User user;
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        user = new User(jsonObject.getString("nombre"), jsonObject.getString("usuario"), jsonObject.getString("correo"), jsonObject.getString("genero"), jsonObject.getString("contrasena"), jsonObject.getString("contrasena"));
                    }catch (JSONException e){
                        user = null;
                        Log.e("ERROR", e.toString());
                    }
                    if(user != null) {
                        Intent intent = new Intent(getApplicationContext(), dashboardActivity.class);
                        intent.putExtra("username", user.getUsername());
                        intent.putExtra("name", user.getName());
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Ha ocurrido un error serializando la respuesta del servidor.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrecta.", Toast.LENGTH_LONG).show();
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
                params.put("usuario", email.getText().toString());
                params.put("contrasena", password.getText().toString());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}