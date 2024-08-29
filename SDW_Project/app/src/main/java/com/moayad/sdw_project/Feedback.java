package com.moayad.sdw_project;

import android.content.ContentValues;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Feedback extends AppCompatActivity {

    DatabaseManager dbManager;
    EditText etFeedbackComments;
    ImageView imgBackArrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        dbManager = new DatabaseManager(this);
        etFeedbackComments = findViewById(R.id.FeedbackComments);
        imgBackArrow = findViewById(R.id.imgBackArrow);
        imgBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void Send(View view) {    //give a feedback
        if (!etFeedbackComments.getText().toString().equals("")){
            ContentValues values = new ContentValues();
            values.put(dbManager.FeedbackComments_Col,etFeedbackComments.getText().toString());
            long ID = dbManager.InsertData(values,dbManager.FeedbackTable);
            if(ID>0)
                Toast.makeText(this,"Your feedback has been sent successfully",Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this,"There is a problem in the database!!",Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this,"Please type something!!",Toast.LENGTH_LONG).show();
    }
}
