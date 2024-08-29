package com.moayad.sdw_project;

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

public class StudentEnrolment extends AppCompatActivity {

    DatabaseManager dbManager;
    TextView tvStatus, tvSeasonAndCourse;
    ImageView imgBackArrow;
    String seasonNum, courseName;
    Double price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_enrolment);

        dbManager = new DatabaseManager(this);
        tvStatus = findViewById(R.id.tvStatus);
        imgBackArrow = findViewById(R.id.imgBackArrow);
        imgBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        seasonNum = getIntent().getStringExtra("SeasonNum");
        courseName = getIntent().getStringExtra("CourseName");
        price = getIntent().getDoubleExtra("Price",0.0);
        tvSeasonAndCourse = findViewById(R.id.tvSeasonAndCourse);
        tvSeasonAndCourse.setText(seasonNum+": "+courseName);
        ViewElements();
    }

    public void AddNewStudent(View view) {    //Add new student
        Intent intent = new Intent(getApplicationContext(),AddNewStudent.class);
        intent.putExtra("SeasonNum",seasonNum);
        intent.putExtra("CourseName",courseName);
        intent.putExtra("Price",price);
        startActivity(intent);
        finish();
    }

    void ViewElements(){
        ArrayList<AdapterItems> listItems = new ArrayList<>();
        MyCustomAdapter myAdapter;
        Cursor cursor = dbManager.ViewData(null,null,null,null,dbManager.StudentsListTable);
        listItems.clear();
        if(cursor.moveToFirst()){
            tvStatus.setText("Students list :");
            do{
                listItems.add(new AdapterItems(cursor.getInt(cursor.getColumnIndex(dbManager.ID_Col)),
                        cursor.getString(cursor.getColumnIndex(dbManager.Name_Col))));
            }while (cursor.moveToNext());
        }
        else
            tvStatus.setText("No student registered");
        myAdapter = new MyCustomAdapter(listItems);
        ListView listView = findViewById(R.id.CoursesList);
        listView.setAdapter(myAdapter);
    }

    private class MyCustomAdapter extends BaseAdapter {
        ArrayList<AdapterItems> listItems;
        MyCustomAdapter(ArrayList<AdapterItems> listItems){
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
            View myView = LI.inflate(R.layout.layout,null);

            final AdapterItems s = listItems.get(position);

            TextView tvStudentName;
            tvStudentName = myView.findViewById(R.id.etStudentName);
            tvStudentName.setText(String.valueOf(s.name));

            Button removeButton = myView.findViewById(R.id.btnRemove);
            removeButton.setOnClickListener(new View.OnClickListener() {    //Remove student
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(StudentEnrolment.this);
                    builder.setTitle("Conformation");
                    builder.setMessage("Are you sure you want to remove all student data ?");
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch(which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    String[] SelectionArgs = {String.valueOf(s.ID)};
                                    int count = dbManager.DeleteData("ID like ?",SelectionArgs,dbManager.StudentsListTable);
                                    dbManager.DeleteData("StudentID like ?",SelectionArgs,dbManager.StudentsInformationTable);
                                    if(count>0){
                                        Toast.makeText(getApplicationContext(),"Student data has been removed successfully",Toast.LENGTH_SHORT).show();
                                        ViewElements();
                                    }
                                    else
                                        Toast.makeText(getApplicationContext(),"There is a problem in the database!!",Toast.LENGTH_LONG).show();
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

            Button btnCourseEnrolment = myView.findViewById(R.id.btnCourseEnrolment);
            btnCourseEnrolment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(),CourseEnrolment.class);
                    intent.putExtra("SeasonNum",seasonNum);
                    intent.putExtra("CourseName",courseName);
                    intent.putExtra("Price",price);
                    intent.putExtra("StudentID",s.ID);
                    intent.putExtra("StudentName",s.name);
                    startActivity(intent);
                    finish();
                }
            });
            return myView;
        }

    }
}
