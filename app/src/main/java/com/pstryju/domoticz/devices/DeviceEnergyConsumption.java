package com.pstryju.domoticz.devices;

import android.content.Context;


public class DeviceEnergyConsumption extends Device {

    private String mTodayConsumption;
    private String mCurrentUsage;

    public DeviceEnergyConsumption(String idx, String name, String today, String usage, Context context) {
        super(idx, name);
        mContext = context;
        mTodayConsumption = today;
        mCurrentUsage = usage;
    }

    public String GetUsage() {
        return mCurrentUsage;
    }

    public String GetConsumption() {
        return mTodayConsumption;
    }
}
