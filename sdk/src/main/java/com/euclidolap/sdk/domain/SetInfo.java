package com.euclidolap.sdk.domain;

import java.util.ArrayList;
import java.util.List;

public class SetInfo {

    private List<TupleInfo> ts = new ArrayList<>();

    public void ad(TupleInfo ti) {
        ts.add(ti);
    }

    public int length() {
        return ts.size();
    }

    public TupleInfo getTupleInfo(int i) {
        return ts.get(i);
    }

    public int thickness() {
        int t = ts.get(0).length();
        for (int i = 1; i < ts.size(); i++) {
            if (ts.get(i).length() > t)
                t = ts.get(i).length();
        }
        return t;
    }
}
