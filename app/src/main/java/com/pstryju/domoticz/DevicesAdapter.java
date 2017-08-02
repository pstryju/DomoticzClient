package com.pstryju.domoticz;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.pstryju.domoticz.devices.Device;
import com.pstryju.domoticz.devices.DeviceDimmer;
import com.pstryju.domoticz.devices.DeviceEnergyConsumption;
import com.pstryju.domoticz.devices.DeviceLightSwitch;
import com.pstryju.domoticz.devices.DeviceMotionSensor;
import com.pstryju.domoticz.devices.DevicePushButton;
import com.pstryju.domoticz.devices.DeviceTemperature;

import java.util.ArrayList;


public class DevicesAdapter extends ArrayAdapter<ArrayList> {
    private String TAG = DevicesAdapter.class.getSimpleName();

    private final Context context;
    private ArrayList<Device> values;
    public DevicesAdapter(Context context, ArrayList values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    public void SetValues(ArrayList<Device> values) {
        this.values.clear();
        this.values.addAll(values);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Log.e(TAG, values.get(0).name);
        final Device currentDevice = values.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = null;

        if(currentDevice instanceof DeviceLightSwitch) {
            rowView = inflater.inflate(R.layout.light_list_layout, parent, false);
            TextView deviceName = (TextView) rowView.findViewById(R.id.device_name);
            Button deviceButton = (Button) rowView.findViewById(R.id.state_button);

            if(((DeviceLightSwitch) currentDevice).GetState())
                deviceButton.setText("Off");
            else
                deviceButton.setText("On");
            deviceName.setText(currentDevice.name);
            deviceButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((DeviceLightSwitch) currentDevice).ChangeState();
                    notifyDataSetChanged();
                }
            });
            return rowView;
        }

        if(currentDevice instanceof DevicePushButton) {
            rowView = inflater.inflate(R.layout.push_button_list_item, parent, false);
            TextView deviceName = (TextView) rowView.findViewById(R.id.device_name);
            Button deviceButton = (Button) rowView.findViewById(R.id.state_button);

            if(((DevicePushButton) currentDevice).GetState())
                deviceButton.setText("Off");
            else
                deviceButton.setText("On");
            deviceName.setText(currentDevice.name);
            deviceButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((DevicePushButton) currentDevice).ChangeState();
                    notifyDataSetChanged();
                }
            });
            return rowView;
        }

        if(currentDevice instanceof DeviceDimmer) {
            rowView = inflater.inflate(R.layout.dimmer_list_item, parent, false);
            TextView deviceName = (TextView) rowView.findViewById(R.id.device_name);
            Button deviceButton = (Button) rowView.findViewById(R.id.state_button);
            SeekBar dimLevelSeekBar = (SeekBar) rowView.findViewById(R.id.dim_level_seek_bar);
            deviceButton.setText(((DeviceDimmer) currentDevice).GetState() ? "On" : "Off");
            dimLevelSeekBar.setProgress(((DeviceDimmer) currentDevice).GetDimLevel());
            deviceName.setText(currentDevice.name);
            deviceButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((DeviceDimmer) currentDevice).ChangeState();
                    notifyDataSetChanged();
                }
            });

            dimLevelSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    ((DeviceDimmer) currentDevice).ChangeDimLevel(i + 1);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            return rowView;
        }

        if(currentDevice instanceof DeviceTemperature) {
            rowView = inflater.inflate(R.layout.temperature_list_layout, parent, false);
            TextView deviceName = (TextView) rowView.findViewById(R.id.device_name);
            TextView deviceData = (TextView) rowView.findViewById(R.id.device_data);
            deviceName.setText(currentDevice.name);
            deviceData.setText(((DeviceTemperature) currentDevice).GetData());
            return rowView;
        }
        if(currentDevice instanceof DeviceMotionSensor) {
            rowView = inflater.inflate(R.layout.motion_list_layout, parent, false);
            TextView deviceName = (TextView) rowView.findViewById(R.id.device_name);
            TextView deviceData = (TextView) rowView.findViewById(R.id.device_data);
            deviceName.setText(currentDevice.name);
            deviceData.setText(((DeviceMotionSensor) currentDevice).GetData());
            return rowView;
        }

        if(currentDevice instanceof DeviceEnergyConsumption) {
            rowView = inflater.inflate(R.layout.power_list_layout, parent, false);
            TextView deviceName = (TextView) rowView.findViewById(R.id.device_name);
            TextView deviceConsumption = (TextView) rowView.findViewById(R.id.device_consumption);
            TextView deviceUsage = (TextView) rowView.findViewById(R.id.device_usage);
            deviceName.setText(currentDevice.name);
            deviceConsumption.setText(((DeviceEnergyConsumption) currentDevice).GetConsumption());
            deviceUsage.setText(((DeviceEnergyConsumption) currentDevice).GetUsage());
            return rowView;
        }
        rowView = inflater.inflate(R.layout.list_item, parent, false);
        return rowView;
    }
}
