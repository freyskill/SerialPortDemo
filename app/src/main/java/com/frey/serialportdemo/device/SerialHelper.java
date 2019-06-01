package com.frey.serialportdemo.device;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;


/**
 * 串口辅助工具类
 * @author：frey
 */
public class SerialHelper {

    private DeviceSerialPort mDeviceManger;

    /**
     * 打开串口
     *
     * @throws SecurityException
     * @throws IOException
     * @throws InvalidParameterException
     */
    public boolean open(String path,int baudRate) throws SecurityException, IOException, InvalidParameterException {
        mDeviceManger = new DeviceSerialPort(new File(path));
        return mDeviceManger.open(baudRate,0);
    }

    public DeviceSerialPort getDeviceManger(){
        return mDeviceManger;
    }

    /**
     * 关闭串口
     */
    public void close() {
        if (mDeviceManger != null) {
            mDeviceManger.close();
            mDeviceManger = null;
        }
    }
}