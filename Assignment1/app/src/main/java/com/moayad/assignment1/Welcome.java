package com.moayad.assignment1;

import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Calendar;

public class Welcome extends AppCompatActivity {

    Button btnYears, btnMonths, btnDays;
    EditText yearText, monthText, dayText;

    Calendar calendar = Calendar.getInstance();
    int yearDate = calendar.get(Calendar.YEAR);
    int monthDate = calendar.get(Calendar.MONTH)+1;
    int dayDate = calendar.get(Calendar.DATE);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Snackbar.make(getWindow().getDecorView(), "You now logged in", Snackbar.LENGTH_LONG).show();

        yearText = findViewById(R.id.etYear);
        monthText = findViewById(R.id.etMonth);
        dayText = findViewById(R.id.etDay);

        btnYears = findViewById(R.id.yearButton);
        btnYears.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dayText.getText()!=null&&monthText.getText()!=null&&yearText.getText()!=null){
                    int ageYear = yearDate - Integer.parseInt(yearText.getText().toString());
                    Toast.makeText(getApplicationContext(),"You are now in "+ageYear+" year old",Toast.LENGTH_LONG).show();
                }
            }
        });
        btnMonths = findViewById(R.id.monthButton);
        btnMonths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dayText.getText()!=null&&monthText.getText()!=null&&yearText.getText()!=null){
                    int ageMonths = (yearDate - Integer.parseInt(yearText.getText().toString())) * 12;
                    ageMonths += monthDate;
                    ageMonths -= (Integer.parseInt(monthText.getText().toString()) - 1);
                    Toast.makeText(getApplicationContext(),"You are now in "+ageMonths+" month old",Toast.LENGTH_LONG).show();
                }
            }
        });
        btnDays = findViewById(R.id.dayButton);
        btnDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dayText.getText()!=null&&monthText.getText()!=null&&yearText.getText()!=null){
                    int days1 = ((Integer.parseInt(monthText.getText().toString()) - 1) * 30) + Integer.parseInt(dayText.getText().toString());
                    int days2 = (monthDate-1) * 30 + dayDate;
                    int agedays = (yearDate - Integer.parseInt(yearText.getText().toString())) * 365;
                    agedays -= days1;
                    agedays += days2;
                    Toast.makeText(getApplicationContext(),"You are now in "+agedays+" day old",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void Logout(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Proceed")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }
}
