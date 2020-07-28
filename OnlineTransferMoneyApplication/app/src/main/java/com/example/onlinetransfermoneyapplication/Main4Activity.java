package com.example.onlinetransfermoneyapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Main4Activity extends AppCompatActivity {

    String userName, password;
    TextView tvLogout;
    EditText etVisaAccount, etPIN;
    boolean isDigit;
    boolean isPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        etVisaAccount = findViewById(R.id.editText111);
        etPIN = findViewById(R.id.editText112);
        userName = getIntent().getStringExtra("userName");
        password = getIntent().getStringExtra("password");
        tvLogout = findViewById(R.id.tvLogout);
        tvLogout.setPaintFlags(tvLogout.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        tvLogout.setOnClickListener(new View.OnClickListener() {
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

    public void VisaAccount(View view) {
        isDigit = true;
        isPass = true;
        String account = etVisaAccount.getText().toString();
        for (int i=0;i<account.length();i++)
        {
            if (!Character.isDigit(account.charAt(i)))
            {
                isDigit = false;
                break;
            }
        }
        if (account.length() > 0 && account.length() != 16){
            Toast.makeText(this,"Account No Must Contain 16 Digits!!",Toast.LENGTH_LONG).show();
            isPass = false;
        }
        else if (!isDigit){
            Toast.makeText(this,"Account No Must be Digits!!",Toast.LENGTH_LONG).show();
            isPass = false;
        }

        if (etVisaAccount.getText().toString().equals("") || etPIN.getText().toString().equals("")){
            Toast.makeText(this,"Please fill all the fields!!",Toast.LENGTH_LONG).show();
            isPass = false;
        }
        else if (etPIN.getText().toString().length() != 4){
            Toast.makeText(this,"PIN Must contain 4 Digits!!",Toast.LENGTH_LONG).show();
            isPass = false;
        }
        if (isPass){
            Toast.makeText(this,"Your Visa Account No has been Accepted",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(),Main3Activity.class);
            intent.putExtra("userName",userName);
            intent.putExtra("password",password);
            startActivity(intent);
        }

    }
}
