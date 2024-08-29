package com.moayad.sdw_project;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    TextView tvSignUp;
    DatabaseManager dbManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsername = findViewById(R.id.UserName);
        etPassword = findViewById(R.id.Password);
        dbManager = new DatabaseManager(this);
        tvSignUp = findViewById(R.id.tvSignUp);
        tvSignUp.setPaintFlags(tvSignUp.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SignUp.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void Login(View view) {
        Cursor data = dbManager.CheckAccount(etUsername.getText().toString(),etPassword.getText().toString());
        if (data.getCount()!=0){   //Check if username and password id exist or not
            Intent intent = new Intent(this,StudentManagementSystem.class);
            startActivity(intent);
            finish();
        }
        else
            Toast.makeText(this,"Wrong UserName or Password!!",Toast.LENGTH_LONG).show();
    }
}
