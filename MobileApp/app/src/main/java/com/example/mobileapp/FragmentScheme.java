package com.example.mobileapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
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

public class FragmentScheme extends Fragment {

    TextView textViewPalmAge;
    EditText editTextPalmAge;
    Button btnSavePalmAge;
    DatabaseReference connStatusRef, palmAgeRef, palmAgeUpdateRef, palmAgeUpdateDateRef;
    int palmAge;
    boolean isConnected;
    String strPalmAge;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_scheme, container, false);

        textViewPalmAge = view.findViewById(R.id.textViewPalmAge);
        editTextPalmAge = view.findViewById(R.id.editTextPalmAge);
        btnSavePalmAge = view.findViewById(R.id.btnSavePalmAge);

        connStatusRef = FirebaseDatabase.getInstance().getReference("Connection Status");
        palmAgeRef = FirebaseDatabase.getInstance().getReference("Palm Age");
        palmAgeUpdateRef = FirebaseDatabase.getInstance().getReference("Palm Age Updated");
        palmAgeUpdateDateRef = FirebaseDatabase.getInstance().getReference("Palm Age Update Date");

        isConnected = false;
        palmAge = -1;

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

        //Retrieve the palm age from the database
        palmAgeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (isConnected)
                {
                    if (dataSnapshot.exists())
                        palmAge = Integer.parseInt(String.valueOf(dataSnapshot.getValue()));
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Connection Issue: " + databaseError.getCode(), Toast.LENGTH_LONG).show();
            }
        });

        //Retrieve the auto update date of palm age from the database
        palmAgeUpdateDateRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    if(palmAge > -1)
                    {
                        textViewPalmAge.setTextColor(Color.parseColor("#000000"));
                        textViewPalmAge.setText("Palm Age: " + palmAge + " years old" +"\n(Auto update on " + dataSnapshot.getValue() + ")");
                    }
                }
                else if (palmAge > -1)
                {
                    if (textViewPalmAge.getText().toString().equals("Palm Age: Not Set"))
                    {
                        textViewPalmAge.setTextColor(Color.parseColor("#000000"));
                        textViewPalmAge.setText("Palm Age: " + palmAge);
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Connection Issue: " + databaseError.getCode(), Toast.LENGTH_LONG).show();
            }
        });

        //Remove hint text when edit text is touched
        editTextPalmAge.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    editTextPalmAge.setHint("");
                else
                    editTextPalmAge.setHint("Palm Age");
            }
        });

        //Store palm age in the database
        btnSavePalmAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected)
                {
                    strPalmAge =  editTextPalmAge.getText().toString();
                    if (strPalmAge.equals(""))
                        Toast.makeText(getContext(), "Please Enter Palm Age!!", Toast.LENGTH_LONG).show();
                    else if (strPalmAge.equals("0"))
                        Toast.makeText(getContext(), "Please Enter a valid Palm Age!!", Toast.LENGTH_LONG).show();
                    else
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Conformation");
                        builder.setMessage("Are you sure you want to update the palm age?");
                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch(which){
                                    case DialogInterface.BUTTON_POSITIVE:
                                        palmAge = Integer.parseInt(editTextPalmAge.getText().toString());
                                        editTextPalmAge.setText("");
                                        palmAgeRef.setValue(palmAge);
                                        if (textViewPalmAge.getText().toString().equals("Palm Age: Not Set"))
                                        {
                                            textViewPalmAge.setTextColor(Color.parseColor("#000000"));
                                            textViewPalmAge.setText("Palm Age: " + palmAge);
                                        }
                                        palmAgeUpdateRef.setValue(true);
                                        Toast.makeText(getActivity(), "Palm Age Updated Successfully", Toast.LENGTH_LONG).show();
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
                }
                else
                    Toast.makeText(getActivity(), "Please Connect to The Watering System!!", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

}
