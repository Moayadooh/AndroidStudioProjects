package com.moayad.project;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class ExistingMobileNumber extends AppCompatActivity {

    DatabaseManager dbManager;
    String UserID;
    ImageView imgBackArrow;
    TextView tvStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing_mobile_number);

        dbManager = new DatabaseManager(this);
        UserID = getIntent().getStringExtra("ID");
        tvStatus = findViewById(R.id.tvStatus);
        imgBackArrow = findViewById(R.id.imgBackArrow);
        imgBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ViewElements();
    }
    void ViewElements(){
        ArrayList<AdapterItems> listItems = new ArrayList<>();
        MyCustomAdapter myAdapter;
        String[] SelectionArgs = {UserID};
        Cursor cursor = dbManager.GetData(null,"UserID like ?",SelectionArgs,null,dbManager.TableName2);
        listItems.clear();
        if(cursor.moveToFirst()){
            tvStatus.setText("Your existing mobile numbers :");
            do{
                listItems.add(new AdapterItems(cursor.getString(cursor.getColumnIndex(dbManager.MobileNo_Col))));
            }while (cursor.moveToNext());
        }
        else
            tvStatus.setText("You did not add any mobile Number");
        myAdapter = new MyCustomAdapter(listItems);
        ListView listView = findViewById(R.id.listview);
        listView.setAdapter(myAdapter);
    }
    private class MyCustomAdapter extends BaseAdapter {
        ArrayList<AdapterItems> listItems;
        MyCustomAdapter(ArrayList<AdapterItems> listItems){
            this.listItems = listItems;
        }

        @Override
        public int getCount() {
            return listItems.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater LI = getLayoutInflater();
            View myView = LI.inflate(R.layout.layout_ticket,null);

            final AdapterItems s = listItems.get(position);

            TextView tvMobile;
            tvMobile = myView.findViewById(R.id.tvMobile);
            tvMobile.setText(String.valueOf(s.MobileNo));

            Button deleteButton = myView.findViewById(R.id.btnDelete);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String[] SelectionArgs = {s.MobileNo};
                    int count = dbManager.DeleteData("MobileNo like ?",SelectionArgs,dbManager.TableName2);
                    dbManager.DeleteData("MobileNoID like ?",SelectionArgs,dbManager.TableName3);
                    if(count>0){
                        Toast.makeText(getApplicationContext(),"Mobile number has been deleted",Toast.LENGTH_SHORT).show();
                        ViewElements();
                    }
                }
            });

            Button rechargeButton = myView.findViewById(R.id.btnRecharge);
            rechargeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(),RechargeMobileNumber.class);
                    intent.putExtra("MobileNo",s.MobileNo);
                    startActivity(intent);
                }
            });

            return myView;
        }
    }
}
