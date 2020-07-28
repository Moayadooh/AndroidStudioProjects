package com.moayad.sdw_project;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    DatabaseManager dbManager;
    EditText etFirstName, etLastName, etMobileNo, etUserName, etPassword, etConfirmPassword;
    TextView tvLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        dbManager = new DatabaseManager(this);
        etFirstName = findViewById(R.id.editTextFirstName);
        etLastName = findViewById(R.id.editTextLastName);
        etMobileNo = findViewById(R.id.etMobileNo);
        etUserName = findViewById(R.id.UserName);
        etPassword = findViewById(R.id.Password);
        etConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        tvLogin = findViewById(R.id.tvLogin);
        tvLogin.setPaintFlags(tvLogin.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void SignUp(View view) {
        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();
        String mobile = etMobileNo.getText().toString();
        String userName = etUserName.getText().toString();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();
        if (!firstName.equals("") && !lastName.equals("") && !mobile.equals("") && !userName.equals("") && !password.equals("") && !confirmPassword.equals("")) {
            if (password.equals(confirmPassword)) {
                if (mobile.length()==8){
                    ContentValues values = new ContentValues();
                    values.put(dbManager.FirstName_Col, etFirstName.getText().toString());
                    values.put(dbManager.LastName_Col, etLastName.getText().toString());
                    values.put(dbManager.MobileNo_Col, etMobileNo.getText().toString());
                    values.put(dbManager.UserName_Col, etUserName.getText().toString());
                    values.put(dbManager.Password_Col, etPassword.getText().toString());
                    long ID = dbManager.InsertData(values, dbManager.AccountsTable);
                    if (ID > 0) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                        Toast.makeText(this,"There is a problem in the database!!",Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(this, "Mobile No must be 8 digits!!", Toast.LENGTH_LONG).show();
            } else
                Toast.makeText(this, "The two passwords does not match!!", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(this, "Please fill all the fields!!", Toast.LENGTH_LONG).show();
    }
}
