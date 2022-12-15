package com.euclidolap.sdk.domain;

public class MemberInfo {

    private long id;
    private String display;

    public MemberInfo(long id, String display) {
        this.id = id;
        this.display = display;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    @Override
    public String toString() {
        return "MemberInfo{" +
                "id=" + id +
                ", display='" + display + '\'' +
                '}';
    }
}
