package com.moayad.intent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    TextView nameTextView;
    EditText ageEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        nameTextView = (TextView)findViewById(R.id.tvName);
        ageEditText = (EditText)findViewById(R.id.etAge);

        nameTextView.setText("Welcome "+getIntent().getStringExtra("name")+" to your second activity");

        /*Bundle extra = getIntent().getExtras();
        String name = extra.getString("name");

        nameTextView.setText("Welcome "+name+" to your second activity");*/
    }
    public void Go(View view){
        Intent intent2 = new Intent();
        intent2.putExtra("age",ageEditText.getText().toString());
        setResult(RESULT_OK,intent2);
        finish();
    }
}
