package com.example.loginmoduleattendance;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Dashboard extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        TextView welcome = findViewById(R.id.txtWelcome);
        String user = getIntent().getStringExtra("username");
        welcome.setText(user != null ? "Welcome " + user : "Welcome!");
    }
}