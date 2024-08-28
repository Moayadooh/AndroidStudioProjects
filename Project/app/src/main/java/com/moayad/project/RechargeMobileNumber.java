package com.moayad.project;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class RechargeMobileNumber extends AppCompatActivity {

    String amountPlan1, amountPlan2, amountPlan3;
    DatabaseManager dbManager;
    String MobileNo;
    TextView tvMobileNumber,tvStatus,tvAmount,tvDate,tvTime;
    Button btnPlan1, btnPlan2, btnPlan3;
    ImageView imgBackArrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_mobile_number);

        dbManager = new DatabaseManager(this);
        MobileNo = getIntent().getStringExtra("MobileNo");
        tvStatus = findViewById(R.id.tvStatus);
        tvAmount = findViewById(R.id.tvAmount);
        tvDate = findViewById(R.id.tvDate);
        tvTime = findViewById(R.id.tvTime);
        btnPlan1 = findViewById(R.id.btnPlan1);
        btnPlan2 = findViewById(R.id.btnPlan2);
        btnPlan3 = findViewById(R.id.btnPlan3);
        tvMobileNumber = findViewById(R.id.tvMobileNumber);
        tvMobileNumber.setText("Mobile Number : "+MobileNo);
        imgBackArrow = findViewById(R.id.imgBackArrow);
        imgBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ViewElements();
        UpdatePlan();
    }
    void UpdatePlan() {
        Cursor cursor1 = dbManager.GetData(null, "Plan1", null, null, dbManager.TableName4);
        if (cursor1.moveToFirst()){
            btnPlan1.setText(cursor1.getString(cursor1.getColumnIndex(dbManager.Plan1_Col))+" Balance");
            amountPlan1 = cursor1.getString(cursor1.getColumnIndex(dbManager.Plan1_Col));
        }

        Cursor cursor2 = dbManager.GetData(null, "Plan2", null, null, dbManager.TableName4);
        if (cursor2.moveToFirst()){
            btnPlan2.setText(cursor2.getString(cursor2.getColumnIndex(dbManager.Plan2_Col))+" Balance");
            amountPlan2 = cursor2.getString(cursor1.getColumnIndex(dbManager.Plan2_Col));
        }

        Cursor cursor3 = dbManager.GetData(null, "Plan3", null, null, dbManager.TableName4);
        if (cursor3.moveToFirst()){
            btnPlan3.setText(cursor3.getString(cursor3.getColumnIndex(dbManager.Plan3_Col))+" Balance");
            amountPlan3 = cursor3.getString(cursor3.getColumnIndex(dbManager.Plan3_Col));
        }
    }
    void ViewElements(){
        ArrayList<AdapterItems2> listItems = new ArrayList<>();
        MyCustomAdapter myAdapter;
        String[] SelectionArgs = {MobileNo};
        Cursor cursor = dbManager.GetData(null,"MobileNoID like ?",SelectionArgs,null,dbManager.TableName3);
        listItems.clear();
        if(cursor.moveToFirst()){
            tvStatus.setText("Your previous recharge payments :");
            tvAmount.setText("Amount");
            tvDate.setText("Date");
            tvTime.setText("Time");
            do{
                listItems.add(new AdapterItems2(cursor.getString(cursor.getColumnIndex(dbManager.Balance_Col)),
                        cursor.getString(cursor.getColumnIndex(dbManager.Date_Col)),
                        cursor.getString(cursor.getColumnIndex(dbManager.Time_Col))));
            }while (cursor.moveToNext());
        }
        else
            tvStatus.setText("You did not perform any recharge payment");
        myAdapter = new MyCustomAdapter(listItems);
        ListView listView = findViewById(R.id.listView2);
        listView.setAdapter(myAdapter);
    }
    void Charge(String amount){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DATE);
        Date date = new Date();
        int hour = date.getHours();
        int minutes = date.getMinutes();
        int seconds = date.getSeconds();
        ContentValues values = new ContentValues();
        values.put(dbManager.MobileNoID_Col,MobileNo);
        values.put(dbManager.Balance_Col,amount);
        values.put(dbManager.Date_Col,day+"/"+month+"/"+year);
        values.put(dbManager.Time_Col,hour+":"+minutes+" "+seconds+"s");
        long ID = dbManager.InsertData(values,dbManager.TableName3);
        if(ID>0){
            Toast.makeText(this,"Payment Successful",Toast.LENGTH_SHORT).show();
            ViewElements();
        }
    }
    void Dialog(final String amount){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Enter your PIN number:").setTitle("Payment Process");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        alert.setView(input);
        alert.setPositiveButton("Pay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Charge(amount);
            }
        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"Payment has been canceled",Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }
    public void OMR1(View view) {
        Dialog(amountPlan1);
    }
    public void OMR3(View view) {
        Dialog(amountPlan2);
    }
    public void OMR5(View view) {
        Dialog(amountPlan3);
    }
    private class MyCustomAdapter extends BaseAdapter {
        ArrayList<AdapterItems2> listItems;
        MyCustomAdapter(ArrayList<AdapterItems2> listItems){
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
            View myView = LI.inflate(R.layout.layout_ticket2,null);

            final AdapterItems2 s = listItems.get(position);

            TextView tvBalance,tvDate,tvTime;
            tvBalance = myView.findViewById(R.id.tvBalance);
            tvBalance.setText(String.valueOf(s.Balance));

            tvDate = myView.findViewById(R.id.tvDate);
            tvDate.setText(String.valueOf(s.Date));

            tvTime = myView.findViewById(R.id.tvTime);
            tvTime.setText(String.valueOf(s.Time));

            return myView;
        }
    }
}
