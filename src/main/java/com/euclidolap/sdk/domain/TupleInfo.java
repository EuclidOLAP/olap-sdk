package com.euclidolap.sdk.domain;

import java.util.ArrayList;
import java.util.List;

public class TupleInfo {

    private List<MemberInfo> ms = new ArrayList<>();

    public void add(MemberInfo mi) {
        ms.add(mi);
    }

    public int length() {
        return ms.size();
    }

    public MemberInfo getMemberInfo(int i) {
        return ms.get(i);
    }
}
