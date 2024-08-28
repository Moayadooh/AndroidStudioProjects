package com.moayad.project;

import android.content.Intent;
import android.database.Cursor;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    EditText etUserName,etPassword;
    DatabaseManager dbManager;
    ImageView imgBackArrow;
    RadioButton radioButtonCustomer, radioButtonAdmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUserName = findViewById(R.id.UserName);
        etPassword = findViewById(R.id.Password);
        radioButtonCustomer = findViewById(R.id.radioButtonCustomer);
        radioButtonAdmin = findViewById(R.id.radioButtonAdmin);
        dbManager = new DatabaseManager(this);
        imgBackArrow = findViewById(R.id.imgBackArrow);
        imgBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void Login(View view) {
        if (radioButtonCustomer.isChecked()){
            Cursor data = dbManager.CheckAccount(etUserName.getText().toString(),etPassword.getText().toString());
            if (data.getCount()!=0){
                String[] SelectionArgs = {etUserName.getText().toString(),etPassword.getText().toString()};
                Cursor cursor = dbManager.GetData(null,"UserName like ? and Password like ?",SelectionArgs,null,dbManager.TableName1);
                if (cursor.moveToFirst()){
                    int ID = cursor.getInt(cursor.getColumnIndex(dbManager.ID_Col));
                    Intent intent = new Intent(this,Home.class);
                    intent.putExtra("ID",String.valueOf(ID));
                    startActivity(intent);
                }
            }
            else
                Toast.makeText(this,"Wrong UserName or Password!!",Toast.LENGTH_LONG).show();


        }
        if (radioButtonAdmin.isChecked()){
            if (etUserName.getText().toString().equals("admin") && etPassword.getText().toString().equals("admin")){
                Intent intent = new Intent(this,ManagePlan.class);
                startActivity(intent);
            }
            else
                Toast.makeText(this,"Wrong UserName or Password!!",Toast.LENGTH_LONG).show();
        }
    }
}
