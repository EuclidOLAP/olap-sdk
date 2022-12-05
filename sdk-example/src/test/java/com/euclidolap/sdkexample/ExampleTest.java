package com.euclidolap.sdkexample;

import java.io.UnsupportedEncodingException;

public class ExampleTest {

    public static void main(String[] args) throws UnsupportedEncodingException {
        System.out.println(" a ".getBytes("UTF-8").length);
        System.out.println("".getBytes("UTF-8").length);
        System.out.println("\t\nX".getBytes("UTF-8").length);
        System.out.println("数据".getBytes("UTF-8").length);
        System.out.println("数据 a".getBytes("UTF-8").length);
    }
}
