package com.pstryju.domoticz;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.ListView;

import com.pstryju.domoticz.devices.Device;
import com.pstryju.domoticz.devices.DeviceDimmer;
import com.pstryju.domoticz.devices.DeviceEnergyConsumption;
import com.pstryju.domoticz.devices.DeviceLightSwitch;
import com.pstryju.domoticz.devices.DeviceMotionSensor;
import com.pstryju.domoticz.devices.DevicePushButton;
import com.pstryju.domoticz.devices.DeviceTemperature;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class LoadDeviceListTask extends AsyncTask<Void,Void,Void> {

    private String TAG = LoadDeviceListTask.class.getSimpleName();
    private static String url = "http://pstryju.ddns.net/json.htm?type=devices&used=true&filter=all&favorite=1";
    ArrayList<Device> mDeviceList;
    DevicesAdapter mAdapter;
    private Context mContext;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public LoadDeviceListTask(Context context, DevicesAdapter adapter) {
        mContext = context;
        mDeviceList = new ArrayList<>();
        mAdapter = adapter;
    }

    public LoadDeviceListTask(Context context, DevicesAdapter adapter, SwipeRefreshLayout swipeRefreshLayout) {
        mContext = context;
        mDeviceList = new ArrayList<>();
        mAdapter = adapter;
        mSwipeRefreshLayout = swipeRefreshLayout;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Void doInBackground(Void... voids) {
        HttpHandler sh = new HttpHandler();
        String jsonStr = sh.makeServiceCall(url);
        Log.e(TAG, "Response from server: " + jsonStr);

        if(jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                JSONArray devices = jsonObj.getJSONArray("result");

                for(int i = 0; i < devices.length(); i++) {
                    JSONObject c = devices.getJSONObject(i);
                    String deviceName = c.getString("Name");
                    String deviceData = c.getString("Data");
                    String deviceIdx = c.getString("idx");
                    String deviceType = c.getString("Type");
                    Log.v(TAG, "Device " + i + deviceType);
                    Device device = new Device(deviceIdx, deviceName);

                    if(deviceType.equals("Lighting 2")) {
                        String deviceSubType = c.getString("SwitchType");
                        if(deviceSubType.equals("Motion Sensor"))
                            device = new DeviceMotionSensor(deviceIdx, deviceName, deviceData, mContext, mAdapter);
                        if(deviceSubType.equals("On/Off"))
                            device = new DeviceLightSwitch(deviceIdx, deviceName, deviceData, mContext, mAdapter);
                        if(deviceSubType.equals("Push On Button"))
                            device = new DevicePushButton(deviceIdx, deviceName, deviceData, mContext, mAdapter);
                        if(deviceSubType.equals("Dimmer")) {
                            String dimLevel = c.getString("LevelInt");
                            device = new DeviceDimmer(deviceIdx, deviceName, deviceData, dimLevel, mContext, mAdapter);
                        }
                    }

                    if(deviceType.equals("Temp + Humidity"))
                        device = new DeviceTemperature(deviceIdx, deviceName, deviceData, mContext);

                    if(deviceType.equals("General")) {
                        String deviceSubType = c.getString("TypeImg");
                        if(deviceSubType.equals("current")) {
                            String usage = c.getString("Usage");
                            String consumption = c.getString("CounterToday");
                            device = new DeviceEnergyConsumption(deviceIdx, deviceName, consumption, usage, mContext);
                        }
                    }
                    mDeviceList.add(device);
                }
            } catch (JSONException e) {
                Log.e(TAG, "JSONException: " + e.getMessage());
            }
        } else {
            Log.e(TAG, "JSON not received");
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Collections.sort(mDeviceList, new DeviceComparator());
        mAdapter.SetValues(mDeviceList);
        mAdapter.notifyDataSetChanged();
        if(mSwipeRefreshLayout != null)
            mSwipeRefreshLayout.setRefreshing(false);
    }

    private class DeviceComparator implements Comparator<Device> {
        @Override
        public int compare(Device device, Device t1) {
            return device.getClass().toString().compareTo(t1.getClass().toString());
        }
    }
}
