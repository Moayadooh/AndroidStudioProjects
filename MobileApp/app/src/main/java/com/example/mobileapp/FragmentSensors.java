package com.example.mobileapp;

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

    TextView textViewTemperatureSensorStatus, textViewSoilMoistureSensorStatus;
    DatabaseReference connStatusRef, systemStatusRef, soilMoistureSensorRef, tempSensorRef;
    boolean isConnected;
    String tempSensorStatus, soilMoistureSensorStatus, systemStatus;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sensors, container, false);

        textViewTemperatureSensorStatus = view.findViewById(R.id.textViewTemperatureSensorStatus);
        textViewSoilMoistureSensorStatus = view.findViewById(R.id.textViewSoilMoistureSensorStatus);

        connStatusRef = FirebaseDatabase.getInstance().getReference("Connection Status");
        systemStatusRef = FirebaseDatabase.getInstance().getReference("System Status");
        tempSensorRef = FirebaseDatabase.getInstance().getReference("Temperature Sensor Status");
        soilMoistureSensorRef = FirebaseDatabase.getInstance().getReference("Soil Moisture Sensor Status");

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
                        textViewTemperatureSensorStatus.setText("Temperature Sensor Status: Not Available");
                        textViewTemperatureSensorStatus.setTextColor(Color.parseColor("#8A8A8A"));
                        textViewSoilMoistureSensorStatus.setText("Soil Moisture Sensor Status: Not Available");
                        textViewSoilMoistureSensorStatus.setTextColor(Color.parseColor("#8A8A8A"));
                        tempSensorRef.removeValue();
                        soilMoistureSensorRef.removeValue();
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
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (isConnected)
                {
                    if (systemStatus.equals("On"))
                    {
                        if (dataSnapshot.exists())
                        {
                            tempSensorStatus = String.valueOf(dataSnapshot.getValue());
                            if (tempSensorStatus.equals("Running"))
                            {
                                textViewTemperatureSensorStatus.setText("Temperature Sensor Status: Running");
                                textViewTemperatureSensorStatus.setTextColor(Color.parseColor("#2FFF00"));
                                //textViewSystemStatus.setTextColor(getActivity().getResources().getColor(R.color.green));
                            }
                            else
                            {
                                textViewTemperatureSensorStatus.setText("Temperature Sensor Status: Not Running");
                                textViewTemperatureSensorStatus.setTextColor(Color.parseColor("#FF0000"));
                                //textViewSoilMoistureSensorStatus.setTextColor(getActivity().getResources().getColor(R.color.red));
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

        //Check and display soil moisture sensor status
        soilMoistureSensorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (isConnected)
                {
                    if (systemStatus.equals("On"))
                    {
                        if (dataSnapshot.exists())
                        {
                            soilMoistureSensorStatus = String.valueOf(dataSnapshot.getValue());
                            if (soilMoistureSensorStatus.equals("Running"))
                            {
                                textViewSoilMoistureSensorStatus.setText("Soil Moisture Sensor Status: Running");
                                textViewSoilMoistureSensorStatus.setTextColor(Color.parseColor("#2FFF00"));
                                //textViewSoilMoistureSensorStatus.setTextColor(getActivity().getResources().getColor(R.color.green));
                            }
                            else
                            {
                                textViewSoilMoistureSensorStatus.setText("Soil Moisture Sensor Status: Not Running");
                                textViewSoilMoistureSensorStatus.setTextColor(Color.parseColor("#FF0000"));
                                //textViewSoilMoistureSensorStatus.setTextColor(getActivity().getResources().getColor(R.color.red));
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
