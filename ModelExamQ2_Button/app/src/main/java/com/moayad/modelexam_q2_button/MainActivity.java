package com.moayad.modelexam_q2_button;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText etBillAmount;
    RadioButton rbJuice, rbFastFood, rbNormalFood;
    CheckBox cbIceCream, cbCappuccino, cbDessert;
    Double billAmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etBillAmount = findViewById(R.id.etBillAmount);
        rbJuice = findViewById(R.id.rbJuice);
        rbFastFood = findViewById(R.id.rbFastFood);
        rbNormalFood = findViewById(R.id.rbNormalFood);
        cbIceCream = findViewById(R.id.cbIceCream);
        cbCappuccino = findViewById(R.id.cbCappuccino);
        cbDessert = findViewById(R.id.cbDessert);

    }
    public void Event(View v) {
        billAmount = Double.parseDouble(etBillAmount.getText().toString());
        if (rbJuice.isChecked()){
            billAmount += 500.0;
        }
        if (rbFastFood.isChecked()){
            billAmount += 1000.0;
        }
        if (rbNormalFood.isChecked()){
            billAmount += 1500.0;
        }
        if (cbIceCream.isChecked()){
            billAmount += 100.0;
        }
        if (cbCappuccino.isChecked()){
            billAmount += 200.0;
        }
        if (cbDessert.isChecked()){
            billAmount += 300.0;
        }
        Toast.makeText(this, "Total Bill is: "+billAmount+"RO", Toast.LENGTH_SHORT).show();
    }
}
