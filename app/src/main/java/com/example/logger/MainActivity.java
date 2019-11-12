package com.example.logger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private String document;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        document = "";

        try {
            Intent intent = getIntent();
            String login_document = intent.getExtras().getString("document");
            if(!login_document.isEmpty()) {
                document = login_document;
            }
        } catch (NullPointerException e) {
            Log.e("ERROR", e.toString());
        }

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                SharedPreferences sharedPreferences = getSharedPreferences("session_preferences", Context.MODE_PRIVATE);
                boolean session = sharedPreferences.getBoolean("session", false);
                if(session) {
                    Intent intent = new Intent(getApplicationContext(), dashboardActivity.class);
                    intent.putExtra("username", sharedPreferences.getString("username", ""));
                    intent.putExtra("name", sharedPreferences.getString("name", ""));
                    intent.putExtra("email", sharedPreferences.getString("email", ""));
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    intent.putExtra("document", document);
                    startActivity(intent);
                    finish();
                }
            }
        }, 1000);
    }
}
