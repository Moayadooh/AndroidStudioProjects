package com.example.watercontrolsystemcbr;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FragmentPlant extends Fragment {

    TextView textViewPlantAge;
    EditText editTextPlantAge;
    Button btnSavePlantAge;
    Spinner spinner;
    DatabaseReference connStatusRef, plantIDRef;
    ServerData serverData;
    boolean isConnected;
    int plantID;
    int plantAge;
    String strPlantAge;
    String nextYearDate;
    int i;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_plant, container, false);

        textViewPlantAge = view.findViewById(R.id.textViewPlantAge);
        editTextPlantAge = view.findViewById(R.id.editTextPlantAge);
        btnSavePlantAge = view.findViewById(R.id.btnSavePlantAge);
        spinner = view.findViewById(R.id.spinner);

        connStatusRef = FirebaseDatabase.getInstance().getReference("Connection Status");
        plantIDRef = FirebaseDatabase.getInstance().getReference("Plant ID");
        serverData = new ServerData();

        isConnected = false;
        plantID = 0;
        plantAge = -1;

        //Check connection status
        connStatusRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    if (dataSnapshot.getValue().equals("Connected"))
                    {
                        isConnected = true;
                        RetrievePlantsNames();
                    }
                    else
                        isConnected = false;
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Connection Issue: " + databaseError.getCode(), Toast.LENGTH_LONG).show();
            }
        });

        //Remove hint text when edit text is touched
        editTextPlantAge.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    editTextPlantAge.setHint("");
                else
                    editTextPlantAge.setHint("Plant Age");
            }
        });

        plantIDRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    plantID = Integer.parseInt(String.valueOf(snapshot.getValue()));
                    RetrievePlantAge(plantID);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Connection Issue: " + error.getCode(), Toast.LENGTH_LONG).show();
            }
        });

        //Store plant age in the database
        btnSavePlantAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected)
                {
                    strPlantAge =  editTextPlantAge.getText().toString();
                    if (strPlantAge.equals(""))
                        Toast.makeText(getContext(), "Please Enter Plant Age!!", Toast.LENGTH_LONG).show();
                    else if (strPlantAge.equals("0"))
                        Toast.makeText(getContext(), "Please Enter a valid Plant Age!!", Toast.LENGTH_LONG).show();
                    else
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Conformation");
                        builder.setMessage("Are you sure you want to update the plant age?");
                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch(which){
                                    case DialogInterface.BUTTON_POSITIVE:
                                        plantAge = Integer.parseInt(editTextPlantAge.getText().toString());
                                        editTextPlantAge.setText("");
                                        Date date = new Date();
                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                        String currentDate = simpleDateFormat.format(date);
                                        Calendar calendar = Calendar.getInstance();
                                        try {
                                            calendar.setTime(simpleDateFormat.parse(currentDate));
                                        }catch (Exception e){
                                            Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
                                        }
                                        calendar.add(Calendar.DATE, 360);  // number of days to add
                                        nextYearDate = simpleDateFormat.format(calendar.getTime());  // dt is now the new date
                                        plantIDRef.setValue(plantID);
                                        StorePlantAge();
                                        RetrievePlantAge(plantID);
                                        if (textViewPlantAge.getText().toString().equals("Plant Age: Not Set"))
                                        {
                                            textViewPlantAge.setTextColor(Color.parseColor("#000000"));
                                            textViewPlantAge.setText("Plant Age: " + plantAge + " years old" +"\n(Auto update on " + nextYearDate + ")");
                                        }
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
                    Toast.makeText(getActivity(), "Please Connect to The Water System!!", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    private void StorePlantAge()
    {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, serverData.getRootUrl1(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Toast.makeText(getActivity(),response,Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("hostname", serverData.getHostname());
                params.put("username", serverData.getUsername());
                params.put("password", serverData.getPassword());
                params.put("dbname", serverData.getDbname());
                params.put("plantID", String.valueOf(plantID));
                params.put("plantAge", String.valueOf(plantAge));
                params.put("nextYearDate", nextYearDate);
                return params;
            }
        };
        RequestHandler.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    private void RetrievePlantAge(final int plantID)
    {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, serverData.getRootUrl2(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonarray = new JSONArray(response);
                    for (i = 0; i < jsonarray.length(); i++)
                    {
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        plantAge = jsonobject.getInt("plant_age");
                        nextYearDate = jsonobject.getString("next_update_date");
                    }
                    textViewPlantAge.setTextColor(Color.parseColor("#000000"));
                    textViewPlantAge.setText("Plant Age: " + plantAge + " years old" +"\n(Auto update on " + nextYearDate + ")");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (getActivity()!=null)
                            Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("hostname", serverData.getHostname());
                params.put("username", serverData.getUsername());
                params.put("password", serverData.getPassword());
                params.put("dbname", serverData.getDbname());
                params.put("plantID", String.valueOf(plantID));
                return params;
            }
        };
        RequestHandler.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

    private void RetrievePlantsNames()
    {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, serverData.getRootUrl3(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonarray = new JSONArray(response);
                    ArrayList<String> items = new ArrayList<>();
                    ArrayAdapter<String> adapter;
                    for (i = 0; i < jsonarray.length(); i++)
                    {
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        items.add(jsonobject.getString("plant_name"));
                    }
                    if (getActivity()!=null)
                    {
                        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
                        spinner.setAdapter(adapter);
                        plantID = (items.indexOf(spinner.getSelectedItem().toString())) + 1;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (getActivity()!=null)
                            Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("hostname", serverData.getHostname());
                params.put("username", serverData.getUsername());
                params.put("password", serverData.getPassword());
                params.put("dbname", serverData.getDbname());
                return params;
            }
        };
        RequestHandler.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

}
