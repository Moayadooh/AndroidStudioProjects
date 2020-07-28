package com.moayad.project;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    EditText etFirstName,etLastName,etVisaAccount,etUserName,etPassword,etConfirmPassword;
    DatabaseManager dbManager;
    ImageView imgBackArrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etFirstName = findViewById(R.id.editTextFirstName);
        etLastName = findViewById(R.id.editTextLastName);
        etVisaAccount = findViewById(R.id.editTextVisaAccount);
        etUserName = findViewById(R.id.UserName);
        etPassword = findViewById(R.id.Password);
        etConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        dbManager = new DatabaseManager(this);
        imgBackArrow = findViewById(R.id.imgBackArrow);
        imgBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void SignUp(View view) {
        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();
        String visaAccount = etVisaAccount.getText().toString();
        String userName = etUserName.getText().toString();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();
        if (!firstName.equals("") && !lastName.equals("") && !visaAccount.equals("") && !userName.equals("") && !password.equals("") && !confirmPassword.equals("")){
            if (password.equals(confirmPassword)){
                ContentValues values = new ContentValues();
                values.put(dbManager.FirstName_Col,etFirstName.getText().toString());
                values.put(dbManager.LastName_Col,etLastName.getText().toString());
                values.put(dbManager.VisaAccount_Col,etVisaAccount.getText().toString());
                values.put(dbManager.UserName_Col,etUserName.getText().toString());
                values.put(dbManager.Password_Col,etPassword.getText().toString());
                long ID = dbManager.InsertData(values,dbManager.TableName1);
                if(ID>0){
                    Intent intent = new Intent(getApplicationContext(),Login.class);
                    startActivity(intent);
                }
            }
            else
                Toast.makeText(this,"The two passwords does not match!!",Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this,"Please fill all the fields!!",Toast.LENGTH_LONG).show();
    }
}
