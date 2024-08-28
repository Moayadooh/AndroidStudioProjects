package com.moayad.project;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ManageAccount extends AppCompatActivity {

    String UserID;
    String password;
    DatabaseManager dbManager;
    TextView tvVisaAccount;
    ImageView imgBackArrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_account);

        UserID = getIntent().getStringExtra("ID");
        dbManager = new DatabaseManager(this);
        imgBackArrow = findViewById(R.id.imgBackArrow);
        imgBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Home.class);
                intent.putExtra("ID",UserID);
                startActivity(intent);
                finish();
            }
        });
        SetVisaAccount();
    }
    void SetVisaAccount(){
        String[] SelectionArgs = {UserID};
        Cursor cursor = dbManager.GetData(null,"ID like ?",SelectionArgs,null,dbManager.TableName1);
        if (cursor.moveToFirst()){
            String visaAccount = cursor.getString(cursor.getColumnIndex(dbManager.VisaAccount_Col));
            tvVisaAccount = findViewById(R.id.tvVisaAccount);
            tvVisaAccount.setText("Your current visa account is "+visaAccount+", if you wish to change this account click change visa account button");
        }
    }
    public void ChanageVisaAccount(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Enter the new visa account").setTitle("Change Visa Account");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        alert.setView(input);
        alert.setPositiveButton("Change", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ContentValues values = new ContentValues();
                values.put(dbManager.VisaAccount_Col,input.getText().toString());
                String[] SelectionArgs = {UserID};
                int count = dbManager.UpdateData(values,"ID like ?",SelectionArgs,dbManager.TableName1);
                if(count>0){
                    SetVisaAccount();
                    Toast.makeText(getApplicationContext(),"Your visa account has been changed successfully",Toast.LENGTH_LONG).show();
                }
            }
        })
        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        })
        .show();
    }

    public void ChanageOoredooAccountPassword(View view) {
        Intent intent = new Intent(getApplicationContext(),ChangePassword.class);
        intent.putExtra("ID",UserID);
        startActivity(intent);
    }

    public void DeleteCurrentAccount(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Enter account password").setTitle("Delete Account");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        alert.setView(input);
        alert.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String[] SelectionArgs = {UserID};
                Cursor cursor = dbManager.GetData(null,"ID like ?",SelectionArgs,null,dbManager.TableName1);
                if(cursor.moveToFirst())
                    password = cursor.getString(cursor.getColumnIndex(dbManager.Password_Col));
                if (input.getText().toString().equals(password)){
                    int ID = cursor.getInt(cursor.getColumnIndex(dbManager.ID_Col));

                    String[] SelectionArgs2 = {String.valueOf(ID)};
                    Cursor cursor1 = dbManager.GetData(null,"UserID like ?",SelectionArgs2,null,dbManager.TableName2);

                    if(cursor1.moveToFirst()) {
                        do {
                            int Mobile = cursor1.getInt(cursor1.getColumnIndex(dbManager.MobileNo_Col));
                            String[] SelectionArgs3 = {String.valueOf(Mobile)};
                            dbManager.DeleteData("MobileNoID like ?", SelectionArgs3, dbManager.TableName3);
                        } while (cursor1.moveToNext());
                    }
                    dbManager.DeleteData("UserID like ?", SelectionArgs2, dbManager.TableName2);
                    int count = dbManager.DeleteData("ID like ?",SelectionArgs,dbManager.TableName1);
                    if(count>0){
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
                else
                    Toast.makeText(getApplicationContext(),"Wrong Password!!",Toast.LENGTH_SHORT).show();
            }
        })
        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        })
        .show();
    }
}
