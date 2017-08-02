package com.pstryju.domoticz;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;


public class ExecuteCommandTask extends AsyncTask<String, Void, String> {
    private Context mContext;
    private DevicesAdapter mDevicesAdapter;

    public ExecuteCommandTask(Context context, DevicesAdapter devicesAdapter) {
        mContext = context;
        mDevicesAdapter = devicesAdapter;
    }
    private String TAG = ExecuteCommandTask.class.getSimpleName();
    @Override
    protected String doInBackground(String... strings) {
        HttpHandler sh = new HttpHandler();
        String jsonStr = sh.makeServiceCall(strings[0]);
        Log.e(TAG, "Response from server: " + jsonStr);
        return jsonStr;
    }

    @Override
    protected void onPostExecute(String jsonStr) {
        super.onPostExecute(jsonStr);
        if(jsonStr == null) {
            Toast toast = Toast.makeText(mContext, "Error sending command", Toast.LENGTH_SHORT);
            toast.show();
        }
        else {
            new LoadDeviceListTask(mContext, mDevicesAdapter).execute();
        }

    }
}
