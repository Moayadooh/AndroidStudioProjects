package com.moayad.project;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    String UserID;
    TextView tvLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        UserID = getIntent().getStringExtra("ID");
        tvLogout = findViewById(R.id.tvLogout);
        tvLogout.setPaintFlags(tvLogout.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void AddNewMobileNumber(View view) {
        Intent intent = new Intent(this,AddMobileNumber.class);
        intent.putExtra("ID",UserID);
        startActivity(intent);
    }

    public void ViewExistingMobileNumber(View view) {
        Intent intent = new Intent(this,ExistingMobileNumber.class);
        intent.putExtra("ID",UserID);
        startActivity(intent);
    }

    public void ManageCurrentAccount(View view) {
        Intent intent = new Intent(this,ManageAccount.class);
        intent.putExtra("ID",UserID);
        startActivity(intent);
        finish();
    }
}
