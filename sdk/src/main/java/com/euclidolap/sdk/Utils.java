package com.euclidolap.sdk;

import com.google.common.primitives.Ints;
import com.google.common.primitives.Shorts;

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

    public static short shortFromBytes(byte lowByte, byte highByte) {
        return Shorts.fromBytes(highByte, lowByte);
    }
}
