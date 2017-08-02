package com.pstryju.domoticz.devices;

import android.content.Context;
import android.widget.ListView;

import com.pstryju.domoticz.DevicesAdapter;

public class DeviceMotionSensor extends Device {
    private boolean state;

    public DeviceMotionSensor(String idx, String name, String state, Context context, DevicesAdapter devicesAdapter) {
        super(idx, name);
        mContext = context;
        mDevicesAdapter = devicesAdapter;

        if(state.equals("On"))
            this.state = true;
        else
            this.state = false;
    }

    public String GetData() {
        if(state)
            return "On";
        else
            return "Off";
    }
}
