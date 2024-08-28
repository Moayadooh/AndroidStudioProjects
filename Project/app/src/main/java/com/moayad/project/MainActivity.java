package com.moayad.project;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.text.LineBreaker;
import android.net.Uri;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tvDescription, tvLink, tvCall, tvMessage;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDescription = findViewById(R.id.tvDescription);
        tvDescription.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
        tvLink = findViewById(R.id.tvLink);
        tvLink.setPaintFlags(tvLink.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        tvLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://ooredoo.om/"));
                startActivity(intent);
            }
        });
        tvCall = findViewById(R.id.tvCall);
        tvCall.setPaintFlags(tvCall.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        tvCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:1500"));
                startActivity(intent);
            }
        });
        tvMessage = findViewById(R.id.tvMessage);
        tvMessage.setPaintFlags(tvLink.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        tvMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.fromParts("sms","1500",null)));
            }
        });
    }

    public void SignUpPage(View view) {
        Intent intent = new Intent(this,SignUp.class);
        startActivity(intent);
    }

    public void LoginPage(View view) {
        Intent intent = new Intent(this,Login.class);
        startActivity(intent);
    }
}
