package com.moayad.sdw_project;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddNewStudent extends AppCompatActivity {

    DatabaseManager dbManager;
    EditText etName, etAge, etAddress;
    ImageView imgBackArrow;
    String seasonNum, courseName;
    Double price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_student);

        dbManager = new DatabaseManager(this);
        etName = findViewById(R.id.etStudentName);
        etAge = findViewById(R.id.etStudentAge);
        etAddress = findViewById(R.id.etStudentAddress);
        imgBackArrow = findViewById(R.id.imgBackArrow);
        imgBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),StudentEnrolment.class);
                intent.putExtra("SeasonNum",seasonNum);
                intent.putExtra("CourseName",courseName);
                intent.putExtra("Price",price);
                startActivity(intent);
                finish();
            }
        });
        seasonNum = getIntent().getStringExtra("SeasonNum");
        courseName = getIntent().getStringExtra("CourseName");
        price = getIntent().getDoubleExtra("Price",0.0);
    }

    public void AddStudent(View view) {   //Add Student
        String name = etName.getText().toString();
        int age = Integer.parseInt(etAge.getText().toString());
        String address = etAddress.getText().toString();
        if (!name.equals("") && !etAge.getText().toString().equals("") && !address.equals("")){
            ContentValues values = new ContentValues();
            values.put(dbManager.Name_Col,name);
            values.put(dbManager.Age_Col,age);
            values.put(dbManager.Address_Col,address);
            long ID = dbManager.InsertData(values,dbManager.StudentsListTable);
            if(ID>0)
                Toast.makeText(this,"Student successfully registered",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(),StudentEnrolment.class);
            intent.putExtra("SeasonNum",seasonNum);
            intent.putExtra("CourseName",courseName);
            intent.putExtra("Price",price);
            startActivity(intent);
            finish();
        }
        else
            Toast.makeText(this, "Please fill all the fields!!", Toast.LENGTH_LONG).show();
    }

}
