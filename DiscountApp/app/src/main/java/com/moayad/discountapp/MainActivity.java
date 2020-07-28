package com.moayad.discountapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    double currentBillTotal;
    int currentCustomPercentage;
    EditText billEditText,discount10EditText,discount15EditText,discount20EditText,total10EditText,total15EditText,total20EditText,
            discountCustomEditText,totalCustomEditText;
    TextView customDiscountTextView;
    SeekBar customSeekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        billEditText = findViewById(R.id.billEditText);
        discount10EditText = findViewById(R.id.discount10EditText);
        discount15EditText = findViewById(R.id.discount15EditText);
        discount20EditText = findViewById(R.id.discount20EditText);
        total10EditText = findViewById(R.id.total10EditText);
        total15EditText = findViewById(R.id.total15EditText);
        total20EditText = findViewById(R.id.total20EditText);
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
                currentCustomPercentage = seekBar.getProgress();
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
        Double tenPercentDiscount = currentBillTotal * .1;
        Double tenPercentTotal = currentBillTotal - tenPercentDiscount;

        discount10EditText.setText(String.format("%.02f",tenPercentDiscount));

        total10EditText.setText(String.format("%.02f",tenPercentTotal));
        total10EditText.setText(String.format("%.02f",tenPercentTotal));

        Double fifteenPercentDiscount = currentBillTotal * .15;
        Double fifteenPercentTotal = currentBillTotal - fifteenPercentDiscount;

        discount15EditText.setText(String.format("%.02f",fifteenPercentDiscount));

        total15EditText.setText(String.format("%.02f",fifteenPercentTotal));

        Double twentyPercentDiscount = currentBillTotal * .2;
        Double twentyPercentTotal = currentBillTotal - twentyPercentDiscount;

        discount20EditText.setText(String.format("%.02f",twentyPercentDiscount));

        total20EditText.setText(String.format("%.02f",twentyPercentTotal));
    }

    private void updateCustom() {
        customDiscountTextView.setText(currentCustomPercentage + "%");

        Double customDiscountAmount = currentBillTotal * currentCustomPercentage * .01;

        Double customTotalAmount = currentBillTotal - customDiscountAmount;

        discountCustomEditText.setText(String.format("%.02f", customDiscountAmount));

        totalCustomEditText.setText(String.format("%.02f", customTotalAmount));
    }

}

