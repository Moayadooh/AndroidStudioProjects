package com.moayad.findmyage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText et1;
    TextView text1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = findViewById(R.id.et1id);
        text1 = findViewById(R.id.text1id);
    }

    public void event(View view) {
        int birthYear = Integer.parseInt(et1.getText().toString());
        Calendar ca = Calendar.getInstance();
        int year = ca.get(Calendar.YEAR);
        int age = year - birthYear;
        text1.setText("You are: "+age+" years old, in months "+age*12+" and in days "+age*365);

    }
}
