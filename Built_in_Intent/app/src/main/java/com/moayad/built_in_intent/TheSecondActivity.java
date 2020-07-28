package com.moayad.built_in_intent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class TheSecondActivity extends AppCompatActivity {

    EditText etAge;
    TextView tvName;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_second);

        etAge = findViewById(R.id.etAge);
        tvName = findViewById(R.id.tvName);

        name = getIntent().getStringExtra("Name");
        if (name!=null)
            tvName.setText(name);
    }

    public void Send(View view) {
        String name = etAge.getText().toString();
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("Age",name);
        startActivity(intent);
    }
}
