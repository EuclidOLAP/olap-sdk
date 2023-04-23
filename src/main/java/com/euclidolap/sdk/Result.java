package com.euclidolap.sdk;

import com.euclidolap.sdk.domain.MemberInfo;
import com.euclidolap.sdk.domain.SetInfo;
import com.euclidolap.sdk.domain.TupleInfo;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public interface Result {

    static Result resolve(int capacity, byte[] payload) {

        int axesCount = Utils.intFromBytes(payload, 2);

        int idx = 6;

        MultiDimResult multiDimResult = new MultiDimResult();

        for (int i = 0; i < axesCount; i++) {
            int s_len = Utils.intFromBytes(payload, idx);
            idx += 4;
            int t_len = Utils.intFromBytes(payload, idx);
            idx += 4;

            SetInfo setInfo = new SetInfo();
            for (int s = 0; s < s_len; s++) {
                TupleInfo tupleInfo = new TupleInfo();
                for (int t = 0; t < t_len; t++) {
                    long memberId = Utils.longFromBytes(payload, idx);
                    idx += 8;

                    String memberDisplay = Utils.fetchOutString(payload, idx);
                    try {
                        idx += memberDisplay.getBytes("UTF-8").length + 1;
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }

                    MemberInfo memberInfo = new MemberInfo(memberId, memberDisplay);
                    tupleInfo.add(memberInfo);

                }
                setInfo.ad(tupleInfo);
            }

            multiDimResult.addSetInfo(setInfo);

        }

        long rs_len = Utils.longFromBytes(payload, idx);
        idx += 8;

        double[] values = new double[(int) rs_len];
        for (int i = 0; i < rs_len; i++) {
            double val = Utils.doubleFromBytes(payload, idx);
            idx += 8;
            values[i] = val;
        }
        multiDimResult.setValues(values);


        byte[] nullFlags = Arrays.copyOfRange(payload, idx, payload.length);
        multiDimResult.setNullFlags(nullFlags);

        for (int i = 0; i < rs_len; i++) {
            byte nullFlag = payload[idx++];
        }

        return multiDimResult;
    }
}
