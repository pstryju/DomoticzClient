package com.pstryju.domoticz.devices;


import android.content.Context;

import com.pstryju.domoticz.DevicesAdapter;
import com.pstryju.domoticz.ExecuteCommandTask;

public class DeviceDimmer extends Device {
    private boolean state;
    private int dimLevel;
    private final String baseSwitchURL = "http://pstryju.ddns.net/json.htm?type=command&param=switchlight&idx=" + idx + "&";
    private final String baseDimUrl = "http://pstryju.ddns.net/json.htm?type=command&param=switchlight&idx=" + idx + "&";
    private String URL;

    public DeviceDimmer(String idx, String name, String state, String dimLevelString, Context context, DevicesAdapter devicesAdapter) {
        super(idx, name);
        mContext = context;
        mDevicesAdapter = devicesAdapter;

        if(state.equals("On"))
            this.state = true;
        else
            this.state = false;

        dimLevel = Integer.parseInt(dimLevelString);
    }

    public void ChangeState() {
        if(!state)
            URL = baseSwitchURL + "switchcmd=On";
        else
            URL = baseSwitchURL + "switchcmd=Off";

        new ExecuteCommandTask(mContext, mDevicesAdapter).execute(URL);
    }

    public void ChangeDimLevel(int level) {
        URL = baseDimUrl + "switchcmd=Set%20Level&level=" + level;
        new ExecuteCommandTask(mContext, mDevicesAdapter).execute(URL);
    }

    public boolean GetState() {
        return state;
    }

    public int GetDimLevel() { return dimLevel; }
}
