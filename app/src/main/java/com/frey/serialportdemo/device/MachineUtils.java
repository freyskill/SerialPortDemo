package com.frey.serialportdemo.device;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * 串口操作
 * @author：frey
 */
public class MachineUtils implements Runnable {

    private static final String TAG = MachineUtils.class.getSimpleName();
    public static final byte[] READID = {0x02, (byte) 0xC2, 0x00, 0x00, 0x00, (byte) 0xC0, 0x03};
    public static final byte[] INIT = {0x02, (byte) 0xC0, 0x00, 0x00, 0x00, (byte) 0xC2, 0x03};

    private static MachineUtils instance;

    private Thread serialThread;

    private SerialHelper serialHelper;
    private InputStream inputStream;
    private OutputStream outputStream;

    private ArrayList<ComBean> mEntryList = new ArrayList<ComBean>();
    private ComBean currentComBean;

    public long mTimeStamp =0;
    private boolean isOpen = false;

    private String path;
    private int baudRate;

    public static MachineUtils getInstance() {
        if (instance == null) {
            instance = new MachineUtils();
        }
        return instance;
    }

    public void openSerialPort(String path,int baudRate){
        this.path = path;
        this.baudRate = baudRate;
        serialHelper = new SerialHelper();
        try {
            isOpen = serialHelper.open(path, baudRate);
            if(isOpen){
                inputStream = serialHelper.getDeviceManger().getInputStream();
                outputStream = serialHelper.getDeviceManger().getOutputStream();
                //outputStream.write(INIT);
                serialThread = new Thread(this);
                serialThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addCommond(byte[] command, OnResultCallback callback){
        ComBean comBean = new ComBean();
        comBean.bRec = command;
        comBean.onResultCallback = callback;
        mEntryList.add(comBean);
    }

    @Override
    public void run() {
        while (!serialThread.isInterrupted()) {
            if (isOpen) {
                Log.d(TAG,"读取中....");
                int size;
                try {
                    if(currentComBean == null){
                        if(mEntryList.size()>0){
                            ComBean entry = mEntryList.remove(0);
                            currentComBean = entry;
                            Thread.sleep(1200);
                            outputStream.write(currentComBean.bRec);
                            Thread.sleep(50);
                            mTimeStamp = System.currentTimeMillis();
                            Log.v(TAG,"write command");
                        }
                    }else{
                        // 重复写入
                        if((System.currentTimeMillis() - mTimeStamp) >= 2000){
                            outputStream.write(currentComBean.bRec);
                            mTimeStamp = System.currentTimeMillis();
                            Log.v(TAG,"write command again");
                        }
                    }
                    Thread.sleep(1200);
                    outputStream.write(READID);
                    Thread.sleep(50);
                    byte[] buffer = new byte[32];
                    if (inputStream == null){ return; }
                    Log.d(TAG,"开始读取信息");
                    size = inputStream.read(buffer);
                    Log.d(TAG,"读取的数据长度："+size);
                    if (size > 0) {
                        Log.d(TAG,"读到数据信息（hex）："+DataConversion.bytes2HexString(buffer,size));
                        if(buffer[0]==0x02 && buffer[1]==0x00){
                            String cardNo = DataConversion.bytes2HexString(buffer,size);
                            if(cardNo.length() == 22){
                                Log.d(TAG,"格式化卡号为："+setCardNum(cardNo.substring(4, 12)));
                            }
                            if(currentComBean!=null){
                                currentComBean.onResultCallback.onResult(buffer);
                                currentComBean = null;
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 十六进制转十进制，并对卡号补位
     */
    public static String setCardNum(String cardNun){
        String cardNo1= cardNun;
        String cardNo=null;
        if(cardNo1!=null){
            Long cardNo2=Long.parseLong(cardNo1,16);
            //cardNo=String.format("%015d", cardNo2);
            cardNo = String.valueOf(cardNo2);
        }
        return cardNo;
    }
}
