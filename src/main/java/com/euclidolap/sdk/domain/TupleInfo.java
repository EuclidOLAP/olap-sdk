package com.euclidolap.sdk.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TupleInfo {

    private List<MemberInfo> ms = new ArrayList<>();
    private String display;

    public void add(MemberInfo mi) {
        ms.add(mi);
        display = String.join(", ", ms.stream().map(MemberInfo::getDisplay).collect(Collectors.toList()));
    }

    public int length() {
        return ms.size();
    }

    public MemberInfo getMemberInfo(int i) {
        return ms.get(i);
    }

}