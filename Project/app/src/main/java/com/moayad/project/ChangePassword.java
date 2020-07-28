package com.moayad.project;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ChangePassword extends AppCompatActivity {

    EditText etOldPassword, etNewPassword;
    String password,UserID;
    DatabaseManager dbManager;
    ImageView imgBackArrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        etOldPassword = findViewById(R.id.editTextOldPassword);
        etNewPassword = findViewById(R.id.editTextNewPassword);
        UserID = getIntent().getStringExtra("ID");
        dbManager = new DatabaseManager(this);
        imgBackArrow = findViewById(R.id.imgBackArrow);
        imgBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void Change(View view) {
        String[] SelectionArgs = {UserID};
        Cursor cursor = dbManager.GetData(null,"ID like ?",SelectionArgs,null,dbManager.TableName1);
        if(cursor.moveToFirst())
                password = cursor.getString(cursor.getColumnIndex(dbManager.Password_Col));
        if (etOldPassword.getText().toString().equals(password)){
            ContentValues values = new ContentValues();
            values.put(dbManager.Password_Col,etNewPassword.getText().toString());
            int count = dbManager.UpdateData(values,"ID like ?",SelectionArgs,dbManager.TableName1);
            if(count>0){
                Toast.makeText(getApplicationContext(),"Your password has been changed successfully",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this,ManageAccount.class);
                intent.putExtra("ID",UserID);
                startActivity(intent);
                finish();
            }
            else
                Toast.makeText(getApplicationContext(),"You have entered wrong old password!!",Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(getApplicationContext(),"You have entered wrong old password!!",Toast.LENGTH_LONG).show();
    }
}
