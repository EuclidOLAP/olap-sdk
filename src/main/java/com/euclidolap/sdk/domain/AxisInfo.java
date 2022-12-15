package com.euclidolap.sdk.domain;

public class AxisInfo {

    private SetInfo setInfo;

    public AxisInfo(SetInfo setInfo) {
        this.setInfo = setInfo;
    }

    public int length() {
        return setInfo.length();
    }

    public TupleInfo getTupleInfo(int i) {
        return setInfo.getTupleInfo(i);
    }
}
