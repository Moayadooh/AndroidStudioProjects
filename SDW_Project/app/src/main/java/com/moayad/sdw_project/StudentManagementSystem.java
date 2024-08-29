package com.moayad.sdw_project;

import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class StudentManagementSystem extends AppCompatActivity {

    RadioButton season1, season2, season3, season4;
    RadioButton italianCooking, businessWriting, sewing, seafoodCooking, creativeWriting;
    String seasonNum="", courseName="";
    Double price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_management_system);

        season1 = findViewById(R.id.rbSeason1);
        season2 = findViewById(R.id.rbSeason2);
        season3 = findViewById(R.id.rbSeason3);
        season4 = findViewById(R.id.rbSeason4);
        italianCooking = findViewById(R.id.rbItalianCooking);
        businessWriting = findViewById(R.id.rbBusinessWriting);
        sewing = findViewById(R.id.rbSwing);
        seafoodCooking = findViewById(R.id.rbSeafoodCooking);
        creativeWriting = findViewById(R.id.rbCreativeWriting);
    }

    void SelectSeason(){   //Select season
        if (season1.isChecked())
            seasonNum = "Season 1";
        if (season2.isChecked())
            seasonNum = "Season 2";
        if (season3.isChecked())
            seasonNum = "Season 3";
        if (season4.isChecked())
            seasonNum = "Season 4";
    }

    void SelectCourse(){   //Select course
        if (italianCooking.isChecked()){
            courseName = "Italian Cooking";
            price = 200.0;
        }
        if (businessWriting.isChecked()){
            courseName = "Business Writing";
            price = 150.0;
        }
        if (sewing.isChecked()){
            courseName = "Sewing";
            price = 190.0;
        }
        if (seafoodCooking.isChecked()){
            courseName = "Seafood Cooking";
            price = 200.0;
        }
        if (creativeWriting.isChecked()){
            courseName = "Creative Writing";
            price = 180.0;
        }
    }

    public void Enrollment(View view) {
        SelectSeason();
        SelectCourse();
        Intent intent = new Intent(getApplicationContext(),StudentEnrolment.class);
        intent.putExtra("SeasonNum",seasonNum);
        intent.putExtra("CourseName",courseName);
        intent.putExtra("Price",price);
        startActivity(intent);
    }

    public void CourseInfo(View view) {
        SelectSeason();
        SelectCourse();
        Intent intent = new Intent(this,CourseInformation.class);
        intent.putExtra("SeasonNum",seasonNum);
        intent.putExtra("CourseName",courseName);
        intent.putExtra("Price",price);
        startActivity(intent);
    }

    public void GiveFeedback(View view) {
        Intent intent = new Intent(getApplicationContext(),Feedback.class);
        startActivity(intent);
    }

    public void About(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Course Management System\n26/5/2019\nCreated by:\nMoayad Salim\nHamed Khamis\nAnwaar Mohammed\nLubna Rashid")
        .setTitle("About")
        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        })
        .show();
    }

    public void Exit(View view) {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }
}
