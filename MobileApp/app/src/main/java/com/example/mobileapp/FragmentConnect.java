package com.example.mobileapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
//import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class FragmentConnect extends Fragment {

    TextView textViewConnectStatus, textViewVerificationKey;
    EditText editTextNum1, editTextNum2, editTextNum3, editTextNum4, editTextNum5;
    Button buttonConnection;
    DatabaseReference connRequestRef, connStatusRef, disConnRequestRef, verificationKeyRef, fcmTokenRef, invalidNumRef;
    String verificationKey, fcmDeviceToken;
    boolean isConnected;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_connect, container, false);

        textViewConnectStatus = view.findViewById(R.id.textViewConnectStatus);
        textViewVerificationKey = view.findViewById(R.id.textViewVerificationKey);
        editTextNum1 = view.findViewById(R.id.editTextNum1);
        editTextNum2 = view.findViewById(R.id.editTextNum2);
        editTextNum3 = view.findViewById(R.id.editTextNum3);
        editTextNum4 = view.findViewById(R.id.editTextNum4);
        editTextNum5 = view.findViewById(R.id.editTextNum5);
        buttonConnection = view.findViewById(R.id.buttonConnection);

        connStatusRef = FirebaseDatabase.getInstance().getReference("Connection Status");
        connRequestRef = FirebaseDatabase.getInstance().getReference("Connection Request");
        disConnRequestRef = FirebaseDatabase.getInstance().getReference("Disconnection Request");
        verificationKeyRef = FirebaseDatabase.getInstance().getReference("Verification Key");
        invalidNumRef = FirebaseDatabase.getInstance().getReference("Invalid Key");
        fcmTokenRef = FirebaseDatabase.getInstance().getReference("FCM Device Token");

        //fcmDeviceToken = FirebaseInstanceId.getInstance().getToken();
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        //Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                        return;
                    }

                    // Get new FCM registration token
                    fcmDeviceToken = task.getResult();

                    // Log and/or send the token to your server
                    //Log.d(TAG, "FCM Token: " + token);
                });

        isConnected = false;

        //Navigate to next edit text
        editTextNum1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextNum1.getText().toString().length()==1)
                    editTextNum2.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editTextNum2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextNum2.getText().toString().length()==1)
                    editTextNum3.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editTextNum3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextNum3.getText().toString().length()==1)
                    editTextNum4.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editTextNum4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextNum4.getText().toString().length()==1)
                    editTextNum5.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editTextNum5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextNum5.getText().toString().length()==1)
                    buttonConnection.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Check and display connection status
        connStatusRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists())
                {
                    if (dataSnapshot.getValue().equals("Connected"))
                    {
                        isConnected = true;
                        textViewConnectStatus.setText("CONNECTED");
                        textViewConnectStatus.setTextColor(Color.parseColor("#2FFF00"));
                        //textViewConnect.setTextColor(getActivity().getResources().getColor(R.color.green));
                        textViewVerificationKey.setText("");
                        editTextNum1.setVisibility(View.INVISIBLE);
                        editTextNum2.setVisibility(View.INVISIBLE);
                        editTextNum3.setVisibility(View.INVISIBLE);
                        editTextNum4.setVisibility(View.INVISIBLE);
                        editTextNum5.setVisibility(View.INVISIBLE);
                        buttonConnection.setText("Disconnect");
                        fcmTokenRef.setValue(fcmDeviceToken);
                    }
                    else
                    {
                        isConnected = false;
                        textViewConnectStatus.setText("NOT CONNECTED");
                        textViewConnectStatus.setTextColor(Color.parseColor("#FF0000"));
                        //textViewConnect.setTextColor(getActivity().getResources().getColor(R.color.red));
                        textViewVerificationKey.setText("Enter Verification Key Here");
                        editTextNum1.setVisibility(View.VISIBLE);
                        editTextNum2.setVisibility(View.VISIBLE);
                        editTextNum3.setVisibility(View.VISIBLE);
                        editTextNum4.setVisibility(View.VISIBLE);
                        editTextNum5.setVisibility(View.VISIBLE);
                        buttonConnection.setText("Connect");
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Connection Issue: " + databaseError.getCode(), Toast.LENGTH_LONG).show();
            }
        });

        //Send connection or disconnection request to the watering system
        buttonConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected)
                {
                    //Send disconnection request
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Conformation");
                    builder.setMessage("You will not be able to control and monitor the watering system. Are you sure you want to disconnect?");
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch(which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    disConnRequestRef.setValue(true);
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
                    //Send connection request
                    verificationKey = " " + editTextNum1.getText().toString() + " " + editTextNum2.getText().toString() + " " +
                            editTextNum3.getText().toString() + " " + editTextNum4.getText().toString() + " " + editTextNum5.getText().toString();
                    verificationKeyRef.setValue(verificationKey);
                    connRequestRef.setValue(true);
                    editTextNum1.setText("");
                    editTextNum2.setText("");
                    editTextNum3.setText("");
                    editTextNum4.setText("");
                    editTextNum5.setText("");
                }
            }
        });

        //Display error message if verification key is not match
        invalidNumRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    if (dataSnapshot.getValue().equals(true))
                    {
                        if (getActivity()!=null)
                        {
                            Toast.makeText(getActivity(), "The Entered Key is Invalid!!", Toast.LENGTH_LONG).show();
                            invalidNumRef.setValue(false);
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
