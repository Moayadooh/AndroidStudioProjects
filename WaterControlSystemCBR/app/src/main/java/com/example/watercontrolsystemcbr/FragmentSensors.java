package com.example.watercontrolsystemcbr;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragmentSensors extends Fragment {

    TextView textViewTempSensorStatus, textViewSoilMoisSensorStatus;
    DatabaseReference connStatusRef, systemStatusRef, tempSensorRef, soilMoisSensorRef;
    boolean isConnected, tempSensorStatus, soilMoisSensorStatus;
    String systemStatus;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sensors, container, false);

        textViewTempSensorStatus = view.findViewById(R.id.textViewTemperatureSensorStatus);
        textViewSoilMoisSensorStatus = view.findViewById(R.id.textViewSoilMoistureSensorStatus);

        connStatusRef = FirebaseDatabase.getInstance().getReference("Connection Status");
        systemStatusRef = FirebaseDatabase.getInstance().getReference("System Status");
        tempSensorRef = FirebaseDatabase.getInstance().getReference("Temperature Sensor Status");
        soilMoisSensorRef = FirebaseDatabase.getInstance().getReference("Soil Moisture Sensor Status");

        isConnected = false;
        systemStatus = "";

        //Check connection status
        connStatusRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    if (dataSnapshot.getValue().equals("Connected"))
                        isConnected = true;
                    else
                    {
                        isConnected = false;
                        textViewTempSensorStatus.setText("Temperature Sensor Status: Not Available");
                        textViewTempSensorStatus.setTextColor(Color.parseColor("#8A8A8A"));
                        textViewSoilMoisSensorStatus.setText("Soil Moisture Sensor Status: Not Available");
                        textViewSoilMoisSensorStatus.setTextColor(Color.parseColor("#8A8A8A"));
                        tempSensorRef.removeValue();
                        soilMoisSensorRef.removeValue();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Connection Issue: " + databaseError.getCode(), Toast.LENGTH_LONG).show();
            }
        });

        //Check system status
        systemStatusRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (isConnected)
                    systemStatus = String.valueOf(dataSnapshot.getValue());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Connection Issue: " + databaseError.getCode(), Toast.LENGTH_LONG).show();
            }
        });

        //Check and display temperature sensor status
        tempSensorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (isConnected)
                {
                    if (systemStatus.equals("On"))
                    {
                        if (snapshot.exists())
                        {
                            tempSensorStatus = (boolean) snapshot.getValue();
                            if (tempSensorStatus)
                            {
                                textViewTempSensorStatus.setText("Temperature Sensor Status: Running");
                                textViewTempSensorStatus.setTextColor(Color.parseColor("#2FFF00"));
                            }
                            else
                            {
                                textViewTempSensorStatus.setText("Temperature Sensor Status: Not Running");
                                textViewTempSensorStatus.setTextColor(Color.parseColor("#FF0000"));
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Connection Issue: " + error.getCode(), Toast.LENGTH_LONG).show();
            }
        });

        //Check and display soil moisture sensor status
        soilMoisSensorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (isConnected)
                {
                    if (systemStatus.equals("On"))
                    {
                        if (dataSnapshot.exists())
                        {
                            soilMoisSensorStatus = (boolean) dataSnapshot.getValue();
                            if (soilMoisSensorStatus)
                            {
                                textViewSoilMoisSensorStatus.setText("Soil Moisture Sensor Status: Running");
                                textViewSoilMoisSensorStatus.setTextColor(Color.parseColor("#2FFF00"));
                            }
                            else
                            {
                                textViewSoilMoisSensorStatus.setText("Soil Moisture Sensor Status: Not Running");
                                textViewSoilMoisSensorStatus.setTextColor(Color.parseColor("#FF0000"));
                            }
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Connection Issue: " + databaseError.getCode(), Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }
}
