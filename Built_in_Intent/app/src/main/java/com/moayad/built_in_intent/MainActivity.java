package com.moayad.built_in_intent;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText etName;
    TextView tvAge;
    String age;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        tvAge = findViewById(R.id.tvAge);

        age = getIntent().getStringExtra("Age");
        if (age!=null)
            tvAge.setText(age);
    }

    public void Dial(View v) {
        Intent dial = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:12345678"));
        startActivity(dial);
    }
    public void Website(View v) {
        Intent website = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ooredoo.om/"));
        startActivity(website);
    }
    public void Map(View v) {
        Intent map = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:11110000"));
        startActivity(map);
    }

    public void Send(View view) {
        String name = etName.getText().toString();
        Intent intent = new Intent(this,TheSecondActivity.class);
        intent.putExtra("Name",name);
        startActivity(intent);
    }
}
