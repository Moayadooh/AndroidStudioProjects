package com.example.watercontrolsystemcbr;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
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
import java.util.Map;

public class FragmentCharts extends Fragment {

    DatabaseReference connStatusRef, recordsUpdatedRef;
    boolean isConnected;
    ServerData serverData;
    Double temperature, soilMoisture, waterAmount;
    String dateTime;
    private LineChart temperatureLineChart, soilMoistureLineChart, waterAmountLineChart;
    int i;
    Double[] temperatureDataSet, soilMoistureDataSet, waterAmountDataSet;
    String[] dates;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_charts, container, false);

        connStatusRef = FirebaseDatabase.getInstance().getReference("Connection Status");
        recordsUpdatedRef = FirebaseDatabase.getInstance().getReference("Records Updated");
        serverData = new ServerData();

        isConnected = false;

        temperatureLineChart = view.findViewById(R.id.temperaturelinechart);
        soilMoistureLineChart = view.findViewById(R.id.soilmoisturelinechart);
        waterAmountLineChart = view.findViewById(R.id.wateramountlinechart);

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

        //Retrieve watering operations records from database
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
                Request.Method.POST, serverData.getRootUrl(), new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonarray = new JSONArray(response);
                    temperatureDataSet = new Double[jsonarray.length()];
                    soilMoistureDataSet = new Double[jsonarray.length()];
                    waterAmountDataSet = new Double[jsonarray.length()];
                    dates = new String[jsonarray.length()];
                    for (i = 0; i < jsonarray.length(); i++)
                    {
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        temperature = jsonobject.getDouble("temp_level");
                        temperatureDataSet[(jsonarray.length()-1-i)] = temperature;
                        soilMoisture = jsonobject.getDouble("soil_mois_level");
                        soilMoistureDataSet[(jsonarray.length()-1-i)] = soilMoisture;
                        waterAmount = jsonobject.getDouble("water_amount");
                        waterAmountDataSet[(jsonarray.length()-1-i)] = waterAmount;
                        dateTime = jsonobject.getString("date_time");
                        dates[(jsonarray.length()-1-i)] = dateTime.substring(8,10) + "/" + dateTime.substring(5,7);
                    }
                    if (getActivity()!=null)
                    {
                        MPChart(temperatureLineChart,temperatureDataSet,dates,0,50,"Temperature (°C)","Temperature");
                        MPChart(soilMoistureLineChart,soilMoistureDataSet,dates,0,100,"Soil Moisture (%)","Soil Moisture");
                        MPChart(waterAmountLineChart,waterAmountDataSet,dates,0,250,"Water Amount (Liter)","Water Amount");
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
                params.put("RETRIEVE_RECORDS", "1");
                return params;
            }
        };
        RequestHandler.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

    //Create charts function
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void MPChart(LineChart mChart, Double[] dataSet, String[] dates, float yMin, float yMax, String label, String labelDescription)
    {
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);
        mChart.getDescription().setText(labelDescription);
        mChart.getDescription().setTextSize(11.5f);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines();
        leftAxis.setAxisMaximum(yMax);
        leftAxis.setAxisMinimum(yMin);
        leftAxis.enableGridDashedLine(10f,10f,0f);
        leftAxis.setDrawLimitLinesBehindData(true);

        mChart.getAxisRight().setEnabled(false);
        mChart.getAxisLeft().setXOffset(10f);
        ArrayList<Entry> yValues = new ArrayList<>();
        for (i=0;i<dataSet.length;i++)
        {
            yValues.add(new Entry(i, Float.parseFloat(String.valueOf(dataSet[i]))));
        }

        LineDataSet set1 = new LineDataSet(yValues, label);

        if (label=="Temperature (°C)")
            set1.setColor(Color.rgb(1f,0.75f,0.5f));
        else if (label=="Soil Moisture (%)")
            set1.setColor(Color.rgb(0.5f,1f,0.0f));
        else
            set1.setColor(Color.rgb(0.0f,1f,1f));

        set1.setFillAlpha(110);
        set1.setLineWidth(3f);
        set1.setValueTextSize(11.5f);
        set1.setValueTextColor(Color.BLACK);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        LineData data = new LineData(dataSets);
        mChart.setData(data);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setValueFormatter(new MyXAxisValueFormatter(dates));
        xAxis.setGranularity(1);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        mChart.invalidate();
    }

    public class MyXAxisValueFormatter extends ValueFormatter {
        private String[] mValues;
        public MyXAxisValueFormatter(String[] values){
            this.mValues = values;
        }
        @Override
        public String getFormattedValue(float value) {
            int index = (int) value;
            if (index < 0 || index >= mValues.length) {
                return "";
            } else {
                return mValues[index];
            }
        }
    }

}
