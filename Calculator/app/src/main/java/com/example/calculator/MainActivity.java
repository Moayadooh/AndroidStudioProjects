package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText editText1,editText2;
    TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        resultTextView = findViewById(R.id.resultTextView);
    }

    public void buClick(View view) {
        int x = Integer.parseInt(editText1.getText().toString());
        int y = Integer.parseInt(editText2.getText().toString());
        int total = x + y;
        resultTextView.setText(String.valueOf(total));
    }
}
