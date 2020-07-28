package com.example.radiobuttoncheckboxswitch;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    CheckBox checkBox;
    RadioButton rMale,rFemale;
    Switch switchGrad;
    TextView tvDisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkBox = findViewById(R.id.checkBox);
        rMale = findViewById(R.id.radMale);
        rFemale = findViewById(R.id.radFemale);
        switchGrad = findViewById(R.id.switchGrad);
        tvDisplay = findViewById(R.id.tvResult);
    }

    public void buCheck(View view) {
        String Result = "";
        if(checkBox.isChecked())
            Result = "this student is married,";
        else
            Result = "this student is not married,";

        if(rMale.isChecked())
            Result += " also he is male,";
        else
            Result += " also she is female,";

        if(switchGrad.isChecked())
            Result += " and is graduate.";
        else
            Result += " and is not graduate.";

        tvDisplay.setText(Result);
    }
}
