package com.pstryju.domoticz.devices;

import android.content.Context;

import com.pstryju.domoticz.DevicesAdapter;
import com.pstryju.domoticz.ExecuteCommandTask;

public class DevicePushButton extends Device {
    private boolean state;
    private final String baseURL = "http://pstryju.ddns.net/json.htm?type=command&param=switchlight&idx=" + idx + "&";
    private String URL;

    public DevicePushButton(String idx, String name, String state, Context context, DevicesAdapter devicesAdapter) {
        super(idx, name);
        mContext = context;
        mDevicesAdapter = devicesAdapter;

        if(state.equals("On"))
            this.state = true;
        else
            this.state = false;
    }

    public void ChangeState() {
        if(!state)
            URL = baseURL + "switchcmd=On";
        else
            URL = baseURL + "switchcmd=Off";

        new ExecuteCommandTask(mContext, mDevicesAdapter).execute(URL);
    }

    public boolean GetState() {
        return state;
    }
}
