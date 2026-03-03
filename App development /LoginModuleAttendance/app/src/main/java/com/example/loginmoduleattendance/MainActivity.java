package com.example.loginmoduleattendance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
    String username = "Edgardian";
    String password = "Tavu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View v) {
        EditText edtTxtUser = findViewById(R.id.edtTxtUser);
        EditText edtTxtPass = findViewById(R.id.edtTxtPass);

        String user = edtTxtUser.getText().toString();
        String pass = edtTxtPass.getText().toString();

        if (user.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Fill in All Fields", Toast.LENGTH_LONG).show();
        }
        else if (!user.equals(username)) {
            Toast.makeText(this, "Incorrect Username", Toast.LENGTH_LONG).show();
        }
        else if (!pass.equals(password)) {
            Toast.makeText(this, "Incorrect Password and Username", Toast.LENGTH_LONG).show();
        }
        else {
            startActivity(new Intent(this, Dashboard.class)
                    .putExtra("username", user));
        }
    }
}