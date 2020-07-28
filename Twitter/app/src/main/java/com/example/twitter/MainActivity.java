package com.example.twitter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class MainActivity extends AppCompatActivity {

    EditText etUsername, etEmail, etPassword;
    Button btnRegister;
    ImageView imageView;
    DatabaseReference myRef;
    FirebaseRemoteConfig mFirebaseRemoteConfig;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myRef = FirebaseDatabase.getInstance().getReference("users");
        imageView = findViewById(R.id.imageView);

        //Remote Config
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        mFirebaseRemoteConfig.setDefaults(R.xml.price_tool);
        //long exp = 3600; //Every 1 hour
        long exp = 0; //Development mode
        mFirebaseRemoteConfig.fetch(exp).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    String price = mFirebaseRemoteConfig.getString("price");
                    Toast.makeText(getApplicationContext(),price,Toast.LENGTH_LONG).show();
                    mFirebaseRemoteConfig.activateFetched();
                }
            }
        });

        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = myRef.push().getKey();
                myRef.child(id).child("username").setValue(etUsername.getText().toString());
                myRef.child(id).child("email").setValue(etEmail.getText().toString());
                myRef.child(id).child("password").setValue(etPassword.getText().toString());
            }
        });

    }

}
