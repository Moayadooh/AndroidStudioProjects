package com.example.onlinetransfermoneyapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    EditText etUserName,etPassword;
    String userName, password;
    TextView tvRegister;
    int numOfTry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        etUserName = findViewById(R.id.editText8);
        etPassword = findViewById(R.id.editText9);
        numOfTry = 0;
        userName = getIntent().getStringExtra("userName");
        password = getIntent().getStringExtra("password");
        tvRegister = findViewById(R.id.tvRegister);
        tvRegister.setPaintFlags(tvRegister.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("userName",userName);
                intent.putExtra("password",password);
                startActivity(intent);
                finish();
            }
        });
    }

    public void Login(View view) {
        if(numOfTry<3)
        {
            if (etUserName.getText().toString().equals(userName) && etPassword.getText().toString().equals(password)){
                Intent intent = new Intent(this,Main4Activity.class);
                intent.putExtra("userName",userName);
                intent.putExtra("password",password);
                startActivity(intent);
            }
            else if (etUserName.getText().toString().equals("") || etPassword.getText().toString().equals("")){
                Toast.makeText(this,"Please fill all the fields!!",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this,"Wrong UserName or Password!!",Toast.LENGTH_LONG).show();
                numOfTry++;
            }
        }
        else if (numOfTry==3){
            Toast.makeText(this,"You have entered Wrong UserName or Password more than 3 times!!",Toast.LENGTH_LONG).show();
            Toast.makeText(this,"Please try again after 30 minutes!!",Toast.LENGTH_LONG).show();
        }

    }
}
