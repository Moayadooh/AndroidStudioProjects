package com.example.onlinetransfermoneyapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Main3Activity extends AppCompatActivity {

    String userName, password;
    TextView tvLogout, tvFeedback;
    EditText etAccount, etBalance;
    boolean isDigit;
    boolean isPass;
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        etAccount = findViewById(R.id.editText111);
        etBalance = findViewById(R.id.editText112);

        userName = getIntent().getStringExtra("userName");
        password = getIntent().getStringExtra("password");
        tvFeedback = findViewById(R.id.tvFeedback);
        tvFeedback.setPaintFlags(tvFeedback.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        tvFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Main5Activity.class);
                intent.putExtra("userName",userName);
                intent.putExtra("password",password);
                startActivity(intent);
                finish();
            }
        });
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

    public void Transfer(View view) {
        //isDigit = true;
        isPass = true;
        String account = etAccount.getText().toString();
        /*for (i=0;i<account.length();i++)
        {
            if (!Character.isDigit(account.charAt(i)))
            {
                isDigit = false;
                break;
            }
        }*/
        if (account.length() > 0 && account.length() != 16){
            Toast.makeText(this,"Account No Must Contain 16 Digits!!",Toast.LENGTH_LONG).show();
            //isPass = false;
        }
        /*else if (!isDigit){
            Toast.makeText(this,"Account No Must be Digits!!",Toast.LENGTH_LONG).show();
            isPass = false;
        }*/

        if (etAccount.getText().toString().equals("") || etBalance.getText().toString().equals("")){
            Toast.makeText(this,"Please fill all the fields!!",Toast.LENGTH_LONG).show();
            //isPass = false;
        }
        //if (isPass){
            if (Integer.parseInt(etBalance.getText().toString()) >= 5 && Integer.parseInt(etBalance.getText().toString()) <= 2000)
                Toast.makeText(this,"Balance Transfer Successful",Toast.LENGTH_LONG).show();
            else
                Toast.makeText(this,"Invalid Amount!!",Toast.LENGTH_LONG).show();
        //}
    }
}
