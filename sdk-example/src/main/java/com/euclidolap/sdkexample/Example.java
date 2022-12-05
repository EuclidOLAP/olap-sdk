package com.euclidolap.sdkexample;

import com.euclidolap.sdk.Terminal;

public class Example {
    public static void main(String[] args) {

        Terminal terminal = new Terminal("192.168.66.235", 8760);

        terminal.connect();

        terminal.exec("select members([Goods], NOT_LEAFS) on 10, members(Calendar, LEAFS) on 100 from [Online Store];");
        //terminal.exec("select children([Goods].[Kitchen & Dining]) on 0, children([Calendar].[ALL].[2021].[Q4]) on 1 from [Online Store] where (measure.[sales amount]);");

    }
}
