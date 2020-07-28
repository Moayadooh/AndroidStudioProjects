package com.example.onlinetransfermoneyapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Main5Activity extends AppCompatActivity {

    String userName, password;
    TextView tvCancel;
    EditText etFeedbackComments;
    boolean isPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        etFeedbackComments = findViewById(R.id.feedbackComments);
        userName = getIntent().getStringExtra("userName");
        password = getIntent().getStringExtra("password");
        tvCancel = findViewById(R.id.tvCancel);
        tvCancel.setPaintFlags(tvCancel.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Main3Activity.class);
                intent.putExtra("userName",userName);
                intent.putExtra("password",password);
                startActivity(intent);
                finish();
            }
        });
    }

    public void Send(View view) {
        isPass = true;
        String feedbackComments =  etFeedbackComments.getText().toString();
        if (feedbackComments.equals("")){
            Toast.makeText(this,"Please type a comment!!",Toast.LENGTH_LONG).show();
            isPass = false;
        }
        else if (!Character.isUpperCase(feedbackComments.charAt(0)))
        {
            Toast.makeText(this,"The first letter must be a capital letter!!",Toast.LENGTH_LONG).show();
            isPass = false;
        }
        else{
            String[] textSp = feedbackComments.split(" ");
            for(String word : textSp){
                word = word.trim();
                if(word.length()>12){
                    Toast.makeText(this,"Please enter a valid comment!!",Toast.LENGTH_LONG).show();
                    isPass = false;
                    break;
                }
            }
        }
        if (isPass){
            Toast.makeText(this,"Thank you for your comment",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(),Main3Activity.class);
            intent.putExtra("userName",userName);
            intent.putExtra("password",password);
            startActivity(intent);
        }
    }
}

