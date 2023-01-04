package com.euclidolap.sdk.domain;

import java.util.ArrayList;
import java.util.List;

public class SetInfo {

    private List<TupleInfo> ts = new ArrayList<>();
    private int tupLenMax;

    public void ad(TupleInfo ti) {
        tupLenMax = Math.max(ti.length(), tupLenMax);
        ts.add(ti);
    }

    public int length() {
        return ts.size();
    }

    public TupleInfo getTupleInfo(int i) {
        return ts.get(i);
    }

    public int getTupLenMax() {
        return tupLenMax;
    }
}
