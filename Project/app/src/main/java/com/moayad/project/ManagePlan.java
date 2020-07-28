package com.moayad.project;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ManagePlan extends AppCompatActivity {

    TextView tvLogout, tvPlan1, tvPlan2, tvPlan3;
    DatabaseManager dbManager;
    EditText editTextPlan1, editTextPlan2, editTextPlan3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_plan);

        editTextPlan1 = findViewById(R.id.editTextPlan1);
        editTextPlan2 = findViewById(R.id.editTextPlan2);
        editTextPlan3 = findViewById(R.id.editTextPlan3);
        tvPlan1 = findViewById(R.id.tvPlan1);
        tvPlan2 = findViewById(R.id.tvPlan2);
        tvPlan3 = findViewById(R.id.tvPlan3);
        dbManager = new DatabaseManager(this);
        tvLogout = findViewById(R.id.tvLogout);
        tvLogout.setPaintFlags(tvLogout.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        UpdatePlan();
    }
    void UpdatePlan() {
        Cursor cursor1 = dbManager.GetData(null, "Plan1", null, null, dbManager.TableName4);
        if (cursor1.moveToFirst())
            tvPlan1.setText("Plan A: "+cursor1.getString(cursor1.getColumnIndex(dbManager.Plan1_Col))+" Balance");

        Cursor cursor2 = dbManager.GetData(null, "Plan2", null, null, dbManager.TableName4);
        if (cursor2.moveToFirst())
            tvPlan2.setText("Plan B: "+cursor2.getString(cursor2.getColumnIndex(dbManager.Plan2_Col))+" Balance");

        Cursor cursor3 = dbManager.GetData(null, "Plan3", null, null, dbManager.TableName4);
        if (cursor3.moveToFirst())
            tvPlan3.setText("Plan C: "+cursor3.getString(cursor3.getColumnIndex(dbManager.Plan3_Col))+" Balance");
    }
    void ChangePlan(String columnName, String planNumber, String amount){
        ContentValues values = new ContentValues();
        values.put(columnName,amount+" OMR");
        int count = dbManager.UpdateData(values,columnName,null,dbManager.TableName4);
        if(count>0){
            Toast.makeText(getApplicationContext(),"Plan "+planNumber+" has been changed successfully",Toast.LENGTH_LONG).show();
            UpdatePlan();
        }
    }
    public void Plan1(View view) {
        ChangePlan(dbManager.Plan1_Col,"A",editTextPlan1.getText().toString());
    }
    public void Plan2(View view) {
        ChangePlan(dbManager.Plan2_Col,"B",editTextPlan2.getText().toString());
    }
    public void Plan3(View view) {
        ChangePlan(dbManager.Plan3_Col,"C",editTextPlan3.getText().toString());
    }
}
