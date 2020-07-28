package com.moayad.assignment2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ViewFile extends AppCompatActivity {

    TextView textView;
    Button btnClose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_file);

        textView = (TextView)findViewById(R.id.Text);

        Bundle bundle = getIntent().getExtras();
        String fileName = bundle.getString("fileName");

        try {
            InputStream IS = openFileInput(fileName);
            if (IS != null) {
                InputStreamReader ISR = new InputStreamReader(IS);
                BufferedReader reader = new BufferedReader(ISR);
                String str;
                StringBuilder SB = new StringBuilder();
                while ((str = reader.readLine()) != null) {
                    SB.append(str+"\n");
                }
                IS.close();
                textView.setText(SB.toString());
            }
        }
        catch (Throwable e) {
            Toast.makeText(this, "Exception: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        btnClose = (Button)findViewById(R.id.Close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
