package com.moayad.sdw_project;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class CourseEnrolment extends AppCompatActivity {

    DatabaseManager dbManager;
    TextView tvStatus, tvStudentName, tvStudentAge, tvStudentAddress, tvSeasonAndCourse;
    String seasonNum, courseName, studentName;
    int studentID;
    Double price;
    ImageView imgBackArrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_enrolment);

        dbManager = new DatabaseManager(this);
        tvStatus = findViewById(R.id.tvStatus);
        tvStudentName = findViewById(R.id.etStudentName);
        tvStudentAge = findViewById(R.id.tvStudentAge);
        tvStudentAddress = findViewById(R.id.tvStudentAddress);
        seasonNum = getIntent().getStringExtra("SeasonNum");
        courseName = getIntent().getStringExtra("CourseName");
        price = getIntent().getDoubleExtra("Price",0.0);
        studentID = getIntent().getIntExtra("StudentID",0);
        studentName = getIntent().getStringExtra("StudentName");
        tvSeasonAndCourse = findViewById(R.id.tvSeasonAndCourse);
        tvSeasonAndCourse.setText(seasonNum+": "+courseName);
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
        String[] SelectionArgs = {studentName};
        Cursor cursor = dbManager.ViewData(null,"studentName = ?",SelectionArgs,null,dbManager.StudentsListTable);
        if(cursor.moveToFirst()){
            int studentAge = cursor.getInt(cursor.getColumnIndex(dbManager.Age_Col));
            String studentAddress = cursor.getString(cursor.getColumnIndex(dbManager.Address_Col));
            tvStudentName.setText("Name: "+studentName);
            tvStudentAge.setText("Age: "+studentAge);
            tvStudentAddress.setText("Address: "+studentAddress);
        }
        else
            Toast.makeText(this,"There is a problem in the database!!",Toast.LENGTH_LONG).show();
        ViewElements();
    }

    public void AllottingStudentToCourse(View view) {  //Allotting student to course
        String[] SelectionArgs = {String.valueOf(studentID),seasonNum,courseName};
        Cursor cursor = dbManager.ViewData(null,"StudentID = ? and Period = ? and CourseName = ?",SelectionArgs,null,dbManager.StudentsInformationTable);
        if(cursor.moveToFirst())
            Toast.makeText(this,"The student already enrolled to this course!!",Toast.LENGTH_LONG).show();
        else {
            ContentValues values = new ContentValues();
            values.put(dbManager.StudentID_Col,studentID);
            values.put(dbManager.StudentName_Col,studentName);
            values.put(dbManager.Period_Col,seasonNum);
            values.put(dbManager.CourseName_Col,courseName);
            values.put(dbManager.Price_Col,price);
            long ID = dbManager.InsertData(values,dbManager.StudentsInformationTable);
            if(ID>0)
                ViewElements();
            else
                Toast.makeText(this,"There is a problem in the database!!",Toast.LENGTH_LONG).show();
        }
    }

    void ViewElements(){
        ArrayList<AdapterItems2> listItems = new ArrayList<>();
        MyCustomAdapter myAdapter;
        String[] SelectionArgs = {String.valueOf(studentID),seasonNum};
        Cursor cursor = dbManager.ViewData(null,"StudentID = ? and Period = ?",SelectionArgs,null,dbManager.StudentsInformationTable);
        listItems.clear();
        if(cursor.moveToFirst()){
            tvStatus.setText("Enrolled courses :");
            do{
                listItems.add(new AdapterItems2(cursor.getString(cursor.getColumnIndex(dbManager.CourseName_Col))));
            }while (cursor.moveToNext());
        }
        else
            tvStatus.setText("The student is not enrolled to any course");
        myAdapter = new MyCustomAdapter(listItems);
        ListView listView = findViewById(R.id.CoursesList);
        listView.setAdapter(myAdapter);
    }

    void RemoveStudentFromCourse(String courseName){  //Remove student from course
        String[] SelectionArgs = {String.valueOf(studentID),seasonNum,courseName};
        int count = dbManager.DeleteData("StudentID like ? and Period like ? and CourseName like ?",SelectionArgs,dbManager.StudentsInformationTable);
        if(count>0){
            Toast.makeText(getApplicationContext(),"The student has been removed from this course",Toast.LENGTH_SHORT).show();
            ViewElements();
        }
        else
            Toast.makeText(getApplicationContext(),"There is a problem in the database!!",Toast.LENGTH_LONG).show();
    }

    private class MyCustomAdapter extends BaseAdapter {
        ArrayList<AdapterItems2> listItems;
        MyCustomAdapter(ArrayList<AdapterItems2> listItems){
            this.listItems = listItems;
        }

        @Override
        public int getCount() {
            return listItems.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater LI = getLayoutInflater();
            View myView = LI.inflate(R.layout.layout1,null);

            final AdapterItems2 s = listItems.get(position);

            TextView tvCourseName;
            tvCourseName = myView.findViewById(R.id.tvStatus);
            tvCourseName.setText(s.courseName);

            Button removeButton = myView.findViewById(R.id.btnRemoveFromCourse);
            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CourseEnrolment.this);
                    builder.setTitle("Conformation");
                    builder.setMessage("Are you sure you want to remove the student from this course ?");
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch(which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    RemoveStudentFromCourse(s.courseName);
                                case DialogInterface.BUTTON_NEGATIVE:
                                    break;
                            }
                        }
                    };
                    builder.setPositiveButton("Yes", dialogClickListener);
                    builder.setNegativeButton("No",dialogClickListener);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
            return myView;
        }

    }
}
