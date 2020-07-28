package com.moayad.discountapp_midterm;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    double currentBillTotal;
    int currentCustomPercent;
    EditText billEditText, discountEditText, totalEditText, discountCustomEditText, totalCustomEditText;
    TextView customDiscountTextView;
    SeekBar customSeekBar;
    RadioButton radioButton1, radioButton2, radioButton3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        billEditText = findViewById(R.id.billEditText);
        radioButton1 = findViewById(R.id.radioButton1);
        radioButton2 = findViewById(R.id.radioButton2);
        radioButton3 = findViewById(R.id.radioButton3);
        discountEditText = findViewById(R.id.discountEditText);
        totalEditText = findViewById(R.id.totalEditText);
        discountCustomEditText = findViewById(R.id.discountCustomEditText);
        totalCustomEditText = findViewById(R.id.totalCustomEditText);
        customDiscountTextView = findViewById(R.id.customDiscountTextView);
        customSeekBar = findViewById(R.id.customSeekBar);

        billEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                currentBillTotal = Double.parseDouble(s.toString());
                updateStandard();
                updateCustom();
            }

        });

        customSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentCustomPercent = seekBar.getProgress();
                updateCustom();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void updateStandard() {
        if (radioButton1.isChecked()){
            Double tenPercentDiscount = currentBillTotal * .1;
            Double tenPercentTotal = currentBillTotal - tenPercentDiscount;
            discountEditText.setText(String.format("%.2f",tenPercentDiscount));
            totalEditText.setText(String.format("%.2f",tenPercentTotal));
        }

        if (radioButton2.isChecked()){
            Double fifteenPercentDiscount = currentBillTotal * .15;
            Double fifteenPercentTotal = currentBillTotal - fifteenPercentDiscount;
            discountEditText.setText(String.format("%.2f",fifteenPercentDiscount));
            totalEditText.setText(String.format("%.2f",fifteenPercentTotal));
        }

        if (radioButton3.isChecked()){
            Double twentyPercentDiscount = currentBillTotal * .2;
            Double twentyPercentTotal = currentBillTotal - twentyPercentDiscount;
            discountEditText.setText(String.format("%.2f",twentyPercentDiscount));
            totalEditText.setText(String.format("%.2f",twentyPercentTotal));
        }

        Toast.makeText(getApplicationContext(),totalEditText.getText().toString(),Toast.LENGTH_SHORT).show();
        Snackbar.make(getCurrentFocus(), totalEditText.getText().toString(), Snackbar.LENGTH_LONG).show();
    }
    private void updateCustom() {
        customDiscountTextView.setText(currentCustomPercent + "%");
        Double customDiscountAmount = currentBillTotal * currentCustomPercent * .01;
        Double customTotalAmount = currentBillTotal - customDiscountAmount;
        discountCustomEditText.setText(String.format("%.2f", customDiscountAmount));
        totalCustomEditText.setText(String.format("%.2f", customTotalAmount));
    }

}
