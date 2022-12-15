package com.euclidolap.sdk;

import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import com.google.common.primitives.Shorts;

import java.io.UnsupportedEncodingException;

public class Utils {

    public static <E> void arrayReverse(E[] arr) {
        E tmp;
        for (int i = 0; i <= arr.length / 2; i++) {
            tmp = arr[i];
            arr[i] = arr[arr.length - i];
            arr[arr.length - i] = tmp;
        }
    }

    public static byte[] intToBytes(int value) {

        return new byte[]
                {
                        (byte) value,
                        (byte) (value >> 8),
                        (byte) (value >> 16),
                        (byte) (value >> 24),
                };
    }

    public static byte[] shortToBytes(short value) {

        return new byte[]{(byte) value, (byte) (value >> 8)};

    }

    public static int intFromBytes(byte[] bytes) {
        return Ints.fromBytes(bytes[3], bytes[2], bytes[1], bytes[0]);
    }

    public static int intFromBytes(byte[] bytes, int start) {
        return Ints.fromBytes(bytes[start + 3], bytes[start + 2], bytes[start + 1], bytes[start]);
    }

    public static long longFromBytes(byte[] bytes, int start) {
        return Longs.fromBytes(bytes[start + 7], bytes[start + 6], bytes[start + 5], bytes[start + 4], bytes[start + 3], bytes[start + 2], bytes[start + 1], bytes[start]);
    }

    public static double doubleFromBytes(byte[] bytes, int start) {
        return Double.longBitsToDouble(longFromBytes(bytes, start));
    }

    public static short shortFromBytes(byte lowByte, byte highByte) {
        return Shorts.fromBytes(highByte, lowByte);
    }

    public static String fetchOutString(byte[] bytes, int start) {
        for (int i = start; i < bytes.length; i++) {
            if (bytes[i] != 0)
                continue;

            byte[] strBytes = new byte[i - start];
            for (int j = 0; j < strBytes.length; j++) {
                strBytes[j] = bytes[start + j];
            }
            try {
                return new String(strBytes, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
