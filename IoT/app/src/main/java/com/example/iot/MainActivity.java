package com.example.iot;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity {

    Button btnOn, btnOff, btnInsert, btnDisplay;
    EditText etName, etAge;
    DatabaseReference infoRef, statusRef, flagRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //String a = FirebaseInstanceId.getInstance().getToken();
        //Toast.makeText(this,a,Toast.LENGTH_LONG).show();
        btnOn = findViewById(R.id.btnOn);
        btnOff = findViewById(R.id.btnOff);
        btnInsert = findViewById(R.id.btnInsert);
        btnDisplay = findViewById(R.id.btnDisplay);
        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        infoRef = FirebaseDatabase.getInstance().getReference("INFO");
        statusRef = FirebaseDatabase.getInstance().getReference("STATUS");
        flagRef = FirebaseDatabase.getInstance().getReference("FLAG");

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoRef.child("Name").setValue(etName.getText().toString());
                infoRef.child("Age").setValue(Integer.parseInt(etAge.getText().toString()));
            }
        });

        btnDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagRef.setValue(true);
            }
        });

        btnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusRef.setValue(1);
            }
        });

        btnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusRef.setValue(0);
            }
        });

        LocalBroadcastManager.getInstance(this).registerReceiver(mHandler, new IntentFilter("com.example.iot"));

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    void makeNotificationChannel(String id, String name, int importance)
    {
        NotificationChannel channel = new NotificationChannel(id, name, importance);
        channel.setShowBadge(true);
        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        assert notificationManager != null;
        notificationManager.createNotificationChannel(channel);
    }

    private BroadcastReceiver mHandler = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String title = intent.getStringExtra("title");
            String message = intent.getStringExtra("message");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                makeNotificationChannel("CHANNEL_1", "Example channel", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationCompat.Builder notification = new NotificationCompat.Builder(MainActivity.this, "CHANNEL_1");
            notification
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setNumber(3);
            NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            assert notificationManager != null;
            notificationManager.notify(1, notification.build());
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mHandler);
    }

}
