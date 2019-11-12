package com.example.logger;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    private TextInputEditText document;
    private TextInputEditText password;
    private Properties properties;

    private String sessionUsername;
    private String sessionPassword;
    private String sessionName;
    private String sessionEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginActivityInstance = this;

        login = (Button)findViewById(R.id.login_login);
        register = (Button)findViewById(R.id.login_register);
        document = (TextInputEditText)findViewById(R.id.login_document);
        password = (TextInputEditText)findViewById(R.id.login_password);

        getPreferences();

        PropertyReader propertyReader = new PropertyReader(this);
        properties = propertyReader.getProperties("logger.properties");

        try {
            Intent intent = getIntent();
            String login_document = intent.getExtras().getString("document");
            if(!login_document.isEmpty()) {
                document.setText(login_document);
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
                sessionUsername = document.getText().toString();
                sessionPassword = password.getText().toString();

                util u = new util(getApplicationContext());
                manejoSQLiteHelper manejoSQLiteHelper = new manejoSQLiteHelper();
                manejoSQLiteHelper.consultarFromularios(LoginActivity.this);

                if (u.verificarConexionInternet()) {
                    if (!sessionUsername.isEmpty()) {
                        if (!sessionPassword.isEmpty()) {
                            validateUser(properties.getProperty("validateUserURL"));
                        } else {
                            Toast.makeText(getApplicationContext(), "Por favor ingrese la contraseña.", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Por favor ingrese su número de identificación.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No está conectado a internet.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void registerUser() {
        sessionUsername = document.getText().toString();

        util u = new util(getApplicationContext());
        if(u.verificarConexionInternet()) {
            Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
            intent.putExtra("document", sessionUsername);
            password.setText("");
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Para registrarse debe tener conexion a internet.", Toast.LENGTH_LONG).show();
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
                        user = new User(jsonObject.getString("UserName"), jsonObject.getString("UserLogin"), jsonObject.getString("UserEmail"), jsonObject.getString("UserGender"), jsonObject.getString("UserPerfil"), jsonObject.getString("UserPassword"), jsonObject.getString("UserPassword"));
                    }catch (JSONException e){
                        user = null;
                        Log.e("ERROR", e.toString());
                    }
                    if(user != null) {
                        sessionName = user.getName();
                        sessionEmail = user.getEmail();
                        savePreferences();
                        Intent intent = new Intent(getApplicationContext(), dashboardActivity.class);
                        intent.putExtra("username", sessionUsername);
                        intent.putExtra("name", sessionName);
                        intent.putExtra("email", sessionEmail);
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
                params.put("usuario", sessionUsername);
                params.put("contrasena", sessionPassword);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void savePreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("session_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", sessionUsername);
        editor.putString("password", sessionPassword);
        editor.putString("name", sessionPassword);
        editor.putString("email", sessionPassword);
        editor.putBoolean("session", true);
        editor.commit();
    }

    private void getPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("session_preferences", Context.MODE_PRIVATE);
        document.setText(sharedPreferences.getString("username", ""));
        password.setText(sharedPreferences.getString("password", ""));
    }

}