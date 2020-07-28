package com.example.androidpermission;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView tvDisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDisplay = findViewById(R.id.tvDisplay);
    }

    public void buGetLocation(View view) {
        CheckUserPermissions();
    }

    void CheckUserPermissions(){
        if ( Build.VERSION.SDK_INT >= 23){
            if ((ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED)){
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) !=
                        PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{
                                    android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_CONTACTS},
                            REQUEST_CODE_ASK_PERMISSIONS);
                }
                return ;
            }
        }

        GetLocation();// init the contact list

    }
    //get access to location permission
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    GetLocation();// GPS
                }
                /*else if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    GetLocation();// read contact list
                }*/
                else {
                    // Permission Denied
                    Toast.makeText(this, "you denied the location access", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    void GetLocation(){
        LocationManager lm = (LocationManager)getSystemService(LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        tvDisplay.setText("long:"+ location.getLongitude()+"lat:"+ location.getLongitude());
    }
}
