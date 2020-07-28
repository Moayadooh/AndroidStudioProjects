package com.example.mobileapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
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

public class FragmentRemote extends Fragment {

    TextView textViewSystemStatus;
    Button customButton;
    Switch switchEnableButton;
    DatabaseReference connStatusRef, palmAgeRef, systemStatusRef, systemStoppedRef;
    boolean isConnected, isPalmAgeExist;
    String systemStatus;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_remote, container, false);

        textViewSystemStatus = view.findViewById(R.id.textViewSystemStatus);
        customButton = view.findViewById(R.id.custom_button);

        connStatusRef = FirebaseDatabase.getInstance().getReference("Connection Status");
        palmAgeRef = FirebaseDatabase.getInstance().getReference("Palm Age");
        systemStatusRef = FirebaseDatabase.getInstance().getReference("System Status");
        systemStoppedRef = FirebaseDatabase.getInstance().getReference("System Stopped");

        isConnected = false;
        isPalmAgeExist = false;
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
                        isConnected = false;
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Connection Issue: " + databaseError.getCode(), Toast.LENGTH_LONG).show();
            }
        });

        //Check if palm age is set
        palmAgeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                    isPalmAgeExist = true;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Connection Issue: " + databaseError.getCode(), Toast.LENGTH_LONG).show();
            }
        });

        //Check and display system status
        systemStatusRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (isConnected)
                {
                    systemStatus = String.valueOf(dataSnapshot.getValue());
                    if (systemStatus.equals("On"))
                    {
                        textViewSystemStatus.setText("SYSTEM IS ON");
                        textViewSystemStatus.setTextColor(Color.parseColor("#2FFF00"));
                        //textViewSystemStatus.setTextColor(getActivity().getResources().getColor(R.color.green));
                    }
                    else
                    {
                        textViewSystemStatus.setText("SYSTEM IS OFF");
                        textViewSystemStatus.setTextColor(Color.parseColor("#FF0000"));
                        //textViewSystemStatus.setTextColor(getActivity().getResources().getColor(R.color.red));
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Connection Issue: " + databaseError.getCode(), Toast.LENGTH_LONG).show();
            }
        });

        //Turn on/off the watering system by the custom button
        customButton.setEnabled(false);
        customButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected)
                {
                    if (isPalmAgeExist)
                    {
                        if (systemStatus.equals("On"))
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("Conformation");
                            builder.setMessage("The watering operations will stop. Are you sure you want to stop the system?");
                            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch(which){
                                        case DialogInterface.BUTTON_POSITIVE:
                                            systemStatusRef.setValue("Off");
                                            textViewSystemStatus.setText("SYSTEM IS OFF");
                                            textViewSystemStatus.setTextColor(Color.parseColor("#FF0000"));
                                            customButton.setActivated(true);
                                            //textViewSystemStatus.setTextColor(getActivity().getResources().getColor(R.color.red));
                                            systemStoppedRef.setValue(true);
                                        case DialogInterface.BUTTON_NEGATIVE:
                                            break;
                                    }
                                }
                            };
                            builder.setPositiveButton("Yes", dialogClickListener);
                            builder.setNegativeButton("No",dialogClickListener);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                        else
                        {
                            systemStatusRef.setValue("On");
                            textViewSystemStatus.setText("SYSTEM IS ON");
                            textViewSystemStatus.setTextColor(Color.parseColor("#2FFF00"));
                            customButton.setActivated(false);
                            //textViewSystemStatus.setTextColor(getActivity().getResources().getColor(R.color.green));
                        }
                    }
                    else
                        Toast.makeText(getActivity(), "Please Fill The Scheme Data!!", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getActivity(), "Please Connect to The Watering System!!", Toast.LENGTH_LONG).show();
            }
        });

        //Enable or Disable the custom button
        switchEnableButton = view.findViewById(R.id.switch_enable_button);
        switchEnableButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    customButton.setEnabled(true);
                    switchEnableButton.setText("UNLOCKED");
                    if (systemStatus.equals("On"))
                        customButton.setActivated(false);
                    else
                        customButton.setActivated(true);
                } else {
                    customButton.setEnabled(false);
                    customButton.setActivated(false);
                    switchEnableButton.setText("LOCKED");
                }
            }
        });

        return view;
    }

}
