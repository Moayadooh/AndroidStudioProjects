package com.example.watercontrolsystemcbr;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class RecordsList extends ArrayAdapter {
    private Activity context;
    private List<Records> records;

    public RecordsList(Activity context, List<Records> recordsList){
        super(context, R.layout.layout_records_list, recordsList);
        this.context = context;
        this.records = recordsList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.layout_records_list, null, true);

        TextView tvTemperature = listViewItem.findViewById(R.id.tvTemperature);
        TextView tvHumidity = listViewItem.findViewById(R.id.tvHumidity);
        TextView tvSoilMoisture = listViewItem.findViewById(R.id.tvSoilMoisture);
        TextView tvWaterAmount = listViewItem.findViewById(R.id.tvWaterAmount);
        TextView tvSensorsStatus = listViewItem.findViewById(R.id.tvSensors);
        TextView tvDateTime = listViewItem.findViewById(R.id.tvDateTime);

        Records record = records.get(position);

        tvTemperature.setText(String.valueOf(record.getTemperature()));
        tvHumidity.setText(String.valueOf(record.getHumidity()));
        tvSoilMoisture.setText(String.valueOf(record.getSoilMoisture()));
        tvWaterAmount.setText(String.valueOf(record.getWaterAmount()));
        tvSensorsStatus.setText(String.valueOf(record.getSensorsStatus()));
        tvDateTime.setText(record.getDateTime());

        return listViewItem;
    }

}
