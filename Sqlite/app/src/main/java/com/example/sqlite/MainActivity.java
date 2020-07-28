package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText etUsername,etPassword,etSearch;
    DatabaseManager dbManager;
    int recordID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbManager = new DatabaseManager(this);
        etUsername = findViewById(R.id.etField);
        etPassword = findViewById(R.id.pwdField);
        etSearch = findViewById(R.id.etSearch);
    }

    public void Save(View view) {
        ContentValues values = new ContentValues();
        values.put(dbManager.Username_Col,etUsername.getText().toString());
        values.put(dbManager.Password_Col,etPassword.getText().toString());
        long ID = dbManager.InsertData(values);
        if(ID>0)
            Toast.makeText(this,"Data inserted successfully in record ID "+ID,Toast.LENGTH_SHORT).show();
    }

    ArrayList<AdapterItems> listItems = new ArrayList<>();
    MyCustomAdapter myAdapter;

    void ViewElements(Cursor cursor){
        listItems.clear();
        if(cursor.moveToFirst()){
            do{
                listItems.add(new AdapterItems(cursor.getInt(cursor.getColumnIndex(dbManager.ID_Col)),
                        cursor.getString(cursor.getColumnIndex(dbManager.Username_Col)),
                        cursor.getString(cursor.getColumnIndex(dbManager.Password_Col))));
            }while (cursor.moveToNext());
        }
        myAdapter = new MyCustomAdapter(listItems);
        ListView listView = findViewById(R.id.listviewID);
        listView.setAdapter(myAdapter);
    }

    public void View(View view) {
        Cursor cursor = dbManager.query(null,null,null,null);
        ViewElements(cursor);
    }

    public void Search(View view) {
        String[] SelectionArgs = {"%"+etSearch.getText().toString()+"%"};
        Cursor cursor = dbManager.query(null,"Username like ?",SelectionArgs,dbManager.Username_Col);
        ViewElements(cursor);
    }

    public void UpdateNow(View view) {
        ContentValues values = new ContentValues();
        values.put(dbManager.Username_Col,etUsername.getText().toString());
        values.put(dbManager.Password_Col,etPassword.getText().toString());

        String[] SelectionArgs = {String.valueOf(recordID)};
        int count = dbManager.UpdateData(values,"ID like ?",SelectionArgs);
        if(count>0){
            Toast.makeText(this,"Data updated successfully",Toast.LENGTH_SHORT).show();
            Cursor cursor = dbManager.query(null,null,null,null);
            ViewElements(cursor);
            recordID = 0;
        }
    }

    private class MyCustomAdapter extends BaseAdapter{
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

            TextView tvID,tvUname,tvPwd;
            tvID = myView.findViewById(R.id.tvID);
            tvID.setText(String.valueOf(s.ID));

            tvUname = myView.findViewById(R.id.tvUname);
            tvUname.setText(s.Username);

            tvPwd = myView.findViewById(R.id.tvPwd);
            tvPwd.setText(s.Password);

            Button deleteButton = myView.findViewById(R.id.btnDelete);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String[] SelectionArgs = {String.valueOf(s.ID)};
                    int count = dbManager.DeleteData("ID like ?",SelectionArgs);
                    if(count>0){
                        Toast.makeText(getApplicationContext(),"Data deleted successfully",Toast.LENGTH_SHORT).show();
                        Cursor cursor = dbManager.query(null,null,null,null);
                        ViewElements(cursor);
                    }
                }
            });

            Button updateButton = myView.findViewById(R.id.btnUpdate);
            updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    etUsername.setText(s.Username);
                    etPassword.setText(s.Password);
                    recordID = s.ID;
                }
            });

            return myView;
        }
    }
}
