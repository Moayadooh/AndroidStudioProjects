package com.example.sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPref = new SharedPref(this);
    }

    SharedPref sharedPref;
    public void Register(View view) {
        EditText etUsername = findViewById(R.id.etUsername);
        EditText etPassword = findViewById(R.id.etPassword);
        sharedPref.SaveData(etUsername.getText().toString(),etPassword.getText().toString());
    }

    public void Load(View view) {
        String data = sharedPref.LoadData();
        Toast.makeText(getApplicationContext(),data,Toast.LENGTH_LONG).show();
    }
}
