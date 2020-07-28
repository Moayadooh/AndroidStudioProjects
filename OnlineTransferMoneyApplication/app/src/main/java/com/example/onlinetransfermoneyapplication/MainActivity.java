package com.example.onlinetransfermoneyapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

        EditText etFirstName,etLastName,etMobileNo,etUserName,etPassword,etConfirmPassword;
        TextView tvLogin;
        String userName, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            etFirstName = findViewById(R.id.editText);
            etLastName = findViewById(R.id.editText2);
            etMobileNo = findViewById(R.id.editText4);
            etUserName = findViewById(R.id.editText5);
            etPassword = findViewById(R.id.editText6);
            etConfirmPassword = findViewById(R.id.editText7);
            userName = getIntent().getStringExtra("userName");
            password = getIntent().getStringExtra("password");
            tvLogin = findViewById(R.id.tvLogin);
            tvLogin.setPaintFlags(tvLogin.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
            tvLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(),Main2Activity.class);
                    intent.putExtra("userName",userName);
                    intent.putExtra("password",password);
                    startActivity(intent);
                    finish();
                }
            });
    }

    public void Register(View view) {
        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();
        String mobileNo = etMobileNo.getText().toString();
        String userName = etUserName.getText().toString();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();
        boolean pass = true;
        if (firstName.equals("") || lastName.equals("") || mobileNo.equals("") || userName.equals("") || password.equals("") || confirmPassword.equals(""))
        {
            Toast.makeText(this, "Please fill all the fields!!", Toast.LENGTH_SHORT).show();
            pass = false;
        }
        if (!password.equals(confirmPassword) && !password.equals("") && !confirmPassword.equals(""))
        {
            Toast.makeText(this, "The two passwords does not match!!", Toast.LENGTH_SHORT).show();
            pass = false;
        }
        if (mobileNo.length() != 8 && !mobileNo.equals(""))
        {
            Toast.makeText(this, "Mobile number must be 8 digits!!", Toast.LENGTH_SHORT).show();
            pass = false;
        }
        if (pass) {
            Intent intent = new Intent(this, Main2Activity.class);
            intent.putExtra("userName", userName);
            intent.putExtra("password", password);
            startActivity(intent);
            Toast.makeText(this, "Successfully Registered", Toast.LENGTH_LONG).show();
        }

    }
}
