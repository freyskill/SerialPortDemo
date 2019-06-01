package com.frey.serialportdemo.device;

import java.io.Serializable;

/**
 * 串口数据
 * @author：frey
 */
public class ComBean implements Serializable {
    public byte[] bRec = null;
    public OnResultCallback onResultCallback;
}