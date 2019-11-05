package com.example.logger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class dashboardActivity extends AppCompatActivity {
    private TextView username;
    private TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        username = (TextView)findViewById(R.id.dashboard_username);
        name = (TextView)findViewById(R.id.dashboard_name);

        try {
            Intent intent = getIntent();
            String login_username = intent.getExtras().getString("username");
            String login_name = intent.getExtras().getString("name");
            username.setText(login_username);
            name.setText(login_name);
        } catch (NullPointerException e) {
            Log.e("ERROR", e.toString());
        }
    }
}
