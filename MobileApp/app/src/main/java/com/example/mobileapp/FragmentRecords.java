package com.example.mobileapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentRecords extends Fragment {

    DatabaseReference connStatusRef, recordsUpdatedRef;
    boolean isConnected;
    ListView listViewRecords;
    List<Records> recordsList;
    private static final String ROOT_URL = "http://moayad.ueuo.com/Application_Server.php";
    private static final String hostname = "";
    private static final String username  = "";
    private static final String password = "";
    private static final String dbname  = "";
    Double temperature, soilMoisture, waterAmount;
    String dateTime;
    int i;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_records, container, false);

        connStatusRef = FirebaseDatabase.getInstance().getReference("Connection Status");
        recordsUpdatedRef = FirebaseDatabase.getInstance().getReference("Records Updated");

        isConnected = false;

        listViewRecords = view.findViewById(R.id.litView);
        recordsList = new ArrayList<>();

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

        //Retrieve watering operations records from database server
        recordsUpdatedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (isConnected)
                {
                    RetrieveRecords();
                    recordsUpdatedRef.setValue(false);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Connection Issue: " + databaseError.getCode(), Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    //Retrieve watering records function
    private void RetrieveRecords()
    {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, ROOT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    recordsList.clear();
                    JSONArray jsonarray = new JSONArray(response);
                    for (i = 0; i < jsonarray.length(); i++)
                    {
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        temperature = jsonobject.getDouble("temp_level");
                        soilMoisture = jsonobject.getDouble("soil_mois_level");
                        waterAmount = jsonobject.getDouble("water_amount");
                        dateTime = jsonobject.getString("date_time");
                        recordsList.add(new Records(temperature,soilMoisture,waterAmount,dateTime));
                    }
                    if (getActivity()!=null)
                    {
                        RecordsList adapter = new RecordsList(getActivity(), recordsList);
                        listViewRecords.setAdapter(adapter);
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
                params.put("hostname", hostname);
                params.put("username", username);
                params.put("password", password);
                params.put("dbname", dbname);
                return params;
            }
        };
        RequestHandler.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

}
