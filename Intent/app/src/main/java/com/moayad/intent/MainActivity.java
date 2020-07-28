package com.moayad.intent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText nameText;
    Button sendButton;
    TextView ageTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameText = (EditText)findViewById(R.id.etName);
        sendButton = (Button)findViewById(R.id.btnSend);
        ageTextView = (TextView)findViewById(R.id.tvAge);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                intent.putExtra("name",nameText.getText().toString());
                startActivityForResult(intent, 5);
                nameText.setText(null);
            }
        });
    }
    protected void onActivityResult(int requestCode,int ResultCode,Intent data){
        if(requestCode==5 && ResultCode==RESULT_OK){
            ageTextView.setText("You are now "+data.getStringExtra("age")+" years old");
        }
    }
}
