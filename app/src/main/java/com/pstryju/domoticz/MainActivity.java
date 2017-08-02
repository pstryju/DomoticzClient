package com.pstryju.domoticz;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.pstryju.domoticz.devices.Device;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    private ListView mDeviceList;
    private DevicesAdapter adapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDeviceList = (ListView) findViewById(R.id.device_list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        adapter = new DevicesAdapter(MainActivity.this, new ArrayList<Device>());
        mDeviceList.setAdapter(adapter);
        new LoadDeviceListTask(MainActivity.this, adapter).execute();
        mSwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.i(TAG, "Refreshing from swipe");
                        new LoadDeviceListTask(MainActivity.this, adapter, mSwipeRefreshLayout).execute();
                    }
                }
        );
    }


}
