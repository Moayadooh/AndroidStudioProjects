package com.moayad.sdw_project;

import android.database.Cursor;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class CourseInformation extends AppCompatActivity {

    DatabaseManager dbManager;
    String seasonNum, courseName;
    Double income, cost, profit, price;
    int numOfStudents;
    TextView tvIncome, tvCost, tvProfit, tvNumOfStudent, tvSeasonAndCourse;
    ImageView imgBackArrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_information);

        dbManager = new DatabaseManager(this);
        tvIncome = findViewById(R.id.tvIncome);
        tvCost = findViewById(R.id.tvCost);
        tvProfit = findViewById(R.id.tvProfit);
        tvNumOfStudent = findViewById(R.id.tvTotalStudent);
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
        CourseInfo();  //Display list of course information
    }

    void CourseInfo(){
        ArrayList<AdapterItems3> listItems = new ArrayList<>();
        MyCustomAdapter myAdapter;
        String[] SelectionArgs = {seasonNum,courseName};
        Cursor cursor = dbManager.ViewData(null,"Period = ? and CourseName = ?",SelectionArgs,null,dbManager.StudentsInformationTable);
        listItems.clear();
        if(cursor.moveToFirst()){
            income = 0.0;  //Initialize Income
            cost = 500.0;  //Initialize Cost
            numOfStudents = 0;  //Initialize number of students
            do{
                listItems.add(new AdapterItems3(cursor.getString(cursor.getColumnIndex(dbManager.StudentName_Col)),
                        cursor.getDouble(cursor.getColumnIndex(dbManager.Price_Col))));

                numOfStudents++;            //Calculate number of student in the course

            }while (cursor.moveToNext());

            income = numOfStudents * price;  //Calculate income
            profit = income - cost;          //Calculate profit
            tvIncome.setText("Income: $"+income);
            tvCost.setText("Cost: $"+cost);
            tvProfit.setText("Profit: $"+profit);
            tvNumOfStudent.setText("Total Students: "+numOfStudents);

        }
        myAdapter = new MyCustomAdapter(listItems);
        ListView listView = findViewById(R.id.StudentAndPricesList);
        listView.setAdapter(myAdapter);
    }

    private class MyCustomAdapter extends BaseAdapter {
        ArrayList<AdapterItems3> listItems;
        MyCustomAdapter(ArrayList<AdapterItems3> listItems){
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
            View myView = LI.inflate(R.layout.layout2,null);

            final AdapterItems3 s = listItems.get(position);

            TextView tvStudentName;
            tvStudentName = myView.findViewById(R.id.etStudentName);
            tvStudentName.setText(String.valueOf(s.studentName));

            TextView tvPrice;
            tvPrice = myView.findViewById(R.id.tvPrice);
            tvPrice.setText("$"+s.price);

            return myView;
        }

    }
}
