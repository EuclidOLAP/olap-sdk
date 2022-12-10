package com.euclidolap.sdkexample;

import com.euclidolap.sdk.MultiDimResult;
import com.euclidolap.sdk.Terminal;

public class Example {
    public static void main(String[] args) {

        Terminal terminal = new Terminal("192.168.66.235", 8760);

        terminal.connect();

        String mdx = "select " +
                "{ ([Calendar].[2020].[Q1]), ([Calendar].[2020].[Q2]), ([Calendar].[2020].[Q3]), ([Calendar].[2020].[Q4]) } on 0, " +
                "{ ([Payment Method].[Credit Card], measure.[sales amount]), ([Payment Method].[Debit Card], measure.[sales amount]), ([Payment Method].[Account Balance], measure.[sales amount]) } on 1 " +
                "from [Online Store];";

        MultiDimResult result = (MultiDimResult) terminal.exec(mdx);

        result.show(System.out);

        terminal.close();
    }
}
