package com.pstryju.domoticz.devices;

import android.content.Context;


public class DeviceTemperature extends Device {
    private String data;

    public DeviceTemperature(String idx, String name, String data, Context context) {
        super(idx, name);
        mContext = context;
        this.data = data;
    }

    public String GetData() {
        return data;
    }
}
