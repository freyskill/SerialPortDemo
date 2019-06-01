package com.frey.serialportdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.frey.serialportdemo.device.MachineUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String path = "/dev/ttyS0";
        int baudRate = 115200;
        MachineUtils.getInstance().openSerialPort(path, baudRate);
    }
}
