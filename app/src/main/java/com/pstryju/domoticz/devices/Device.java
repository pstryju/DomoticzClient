package com.pstryju.domoticz.devices;


import android.content.Context;
import android.widget.ListView;

import com.pstryju.domoticz.DevicesAdapter;

public class Device {
    protected Context mContext;
    public String idx;
    public String name;
    protected DevicesAdapter mDevicesAdapter;
    public int sortingValue;

    public Device(String idx, String name) {
        this.idx = idx;
        this.name = name;
    }
}
