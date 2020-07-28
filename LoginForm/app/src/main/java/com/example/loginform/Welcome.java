package com.example.loginform;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Welcome extends AppCompatActivity {

    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Bundle b;
        b = getIntent().getExtras();
        String username = b.getString("username");

        txt = findViewById(R.id.text1id);
        txt.setText("Welcome "+username);
    }

    public void event(View view) {
        finish();
    }
}
