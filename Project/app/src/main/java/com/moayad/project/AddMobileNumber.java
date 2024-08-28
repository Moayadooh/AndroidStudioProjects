package com.moayad.project;

import android.content.ContentValues;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddMobileNumber extends AppCompatActivity {

    EditText MobileNo;
    DatabaseManager dbManager;
    ImageView imgBackArrow;
    String UserID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mobile_number);

        MobileNo = findViewById(R.id.editTextMobileNumber);
        dbManager = new DatabaseManager(this);
        UserID = getIntent().getStringExtra("ID");
        imgBackArrow = findViewById(R.id.imgBackArrow);
        imgBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void AddNumber(View view) {
        if (MobileNo.getText().toString().length()==8){
            ContentValues values = new ContentValues();
            values.put(dbManager.UserID_Col,UserID);
            values.put(dbManager.MobileNo_Col,MobileNo.getText().toString());
            long ID = dbManager.InsertData(values,dbManager.TableName2);
            if(ID>0){
                Intent intent = new Intent(this,ExistingMobileNumber.class);
                intent.putExtra("ID",UserID);
                startActivity(intent);
                finish();
            }
        }
        else
            Toast.makeText(this,"Mobile number must be 8 digits!!",Toast.LENGTH_LONG).show();
    }
}
