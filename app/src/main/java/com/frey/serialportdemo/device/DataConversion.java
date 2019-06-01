package com.frey.serialportdemo.device;


/**
 * 数据转换工具
 * @author：frey
 */
public class DataConversion {
    /**
     * 判断奇数或偶数，位运算，最后一位是1则为奇数，为0是偶数
     * @param num
     * @return
     */
    public static int isOdd(int num) {
        return num & 0x1;
    }

    /**
     * 将int转成byte
     * @param number
     * @return
     */
    public static byte intToByte(int number){
        return Byte.parseByte(intToHex(number));
    }

    /**
     * 将int转成hex字符串
     * @param number
     * @return
     */
    public static String intToHex(int number){
        String st = Integer.toHexString(number).toUpperCase();
        return String.format("%2s",st).replaceAll(" ","0");
    }

    /**
     * 字节转十进制
     * @param b
     * @return
     */
    public static int byteToDec(byte b){
        String s = byte2Hex(b);
        return (int) hexToDec(s);
    }

    /**
     * 字节数组转十进制
     * @param bytes
     * @return
     */
    public static int bytesToDec(byte[] bytes){
        String s = bytes2HexString(bytes,bytes.length);
        return (int)  hexToDec(s);
    }

    /**
     * Hex字符串转int
     *
     * @param inHex
     * @return
     */
    public static int hexToInt(String inHex) {
        return Integer.parseInt(inHex, 16);
    }

    /**
     * Hex字符串转byte
     *
     * @param inHex
     * @return
     */
    public static byte hexToByte(String inHex) {
        return (byte) Integer.parseInt(inHex, 16);
    }

    /**
     * 1字节转2个Hex字符
     *
     * @param inByte
     * @return
     */
    public static String byte2Hex(Byte inByte) {
        return String.format("%02x", inByte).toUpperCase();
    }

    /**
     * 字节数组转转hex字符串
     *
     * @param inBytArr
     * @return
     */
    public static String byteArrToHex(byte[] inBytArr) {
        StringBuilder strBuilder = new StringBuilder();
        int j = inBytArr.length;
        for (int i = 0; i < j; i++) {
            strBuilder.append(byte2Hex(inBytArr[i]));
            strBuilder.append(" ");
        }
        return strBuilder.toString();
    }

    /**
     * 字节数组转转hex字符串，可选长度
     *
     * @param inBytArr
     * @param offset
     * @param byteCount
     * @return
     */
    public static String byteArrToHex(byte[] inBytArr, int offset, int byteCount) {
        StringBuilder strBuilder = new StringBuilder();
        int j = byteCount;
        for (int i = offset; i < j; i++) {
            strBuilder.append(byte2Hex(inBytArr[i]));
        }
        return strBuilder.toString();
    }

    /**
     * 字节数组转十六进制字符串
     * @param b
     * @param size
     * @return
     */
    public static String bytes2HexString(byte[] b,int size) {
        String ret = "";
        for (int i = 0; i < size; i++) {
            String hex = Integer.toHexString(b[ i ] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            ret += hex.toUpperCase();
        }
        return ret;
    }

    /**
     * 转hex字符串转字节数组
     *
     * @param inHex
     * @return
     */
    public static byte[] hexToByteArr(String inHex) {
        int hexlen = inHex.length();
        byte[] result;
        //奇数
        if (isOdd(hexlen) == 1) {
            hexlen++;
            result = new byte[(hexlen / 2)];
            inHex = "0" + inHex;
        } else {//偶数
            result = new byte[(hexlen / 2)];
        }
        int j = 0;
        for (int i = 0; i < hexlen; i += 2) {
            result[j] = hexToByte(inHex.substring(i, i + 2));
            j++;
        }
        return result;
    }

    /**
     * 十进制转十六进制
     * @param dec
     * @return
     */
    public static String decToHex(int dec){
        String hex = Integer.toHexString(dec);
        if (hex.length() == 1) {
            hex = '0' + hex;
        }
        return hex.toLowerCase();
    }

    /**
     * 十六进制转十进制
     * @param hex
     * @return
     */
    public static long hexToDec(String hex){
        return Long.parseLong(hex, 16);
    }

}