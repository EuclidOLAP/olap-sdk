package com.euclidolap.sdk;

import com.euclidolap.sdk.domain.AxisInfo;
import com.euclidolap.sdk.domain.MemberInfo;
import com.euclidolap.sdk.domain.SetInfo;
import com.euclidolap.sdk.domain.TupleInfo;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MultiDimResult implements Result {

    private List<SetInfo> sets = new ArrayList<>();

    private byte[] nullFlags;

    private double[] values;

    public void setNullFlags(byte[] nullFlags) {
        this.nullFlags = nullFlags;
    }

    public void setValues(double[] values) {
        this.values = values;
    }

    public void addSetInfo(SetInfo si) {
        sets.add(si);
    }

    public int axesCount() {
        return sets.size();
    }

    public AxisInfo getAxisInfo(int i) {
        return new AxisInfo(sets.get(i));
    }

    public Double getMeasureValue(int... pos) {

        if (pos.length != sets.size())
            return null;

        int[] offset = new int[pos.length];
        for (int i = pos.length - 1; i >= 0; i--) {
            if (i == pos.length - 1) {
                offset[i] = 1;
                continue;
            }
            offset[i] = offset[i + 1] * sets.get(i + 1).length();
        }

        int index = 0;
        for (int i = 0; i < pos.length; i++) {
            index += offset[i] * pos[i];
        }

        return nullFlags[index] != 0 ? null : values[index];
    }

    public void show(OutputStream out) {

        int axesCount = axesCount();

        if (axesCount == 2) {
            try {
                show2D(out);
            } catch (IOException e) {
                //e.printStackTrace();
                throw new RuntimeException(e);
            }
            return;
        }

        for (int i = 0; i < axesCount; i++) {
            AxisInfo axis = getAxisInfo(i);
            for (int j = 0; j < axis.length(); j++) {
                TupleInfo tupleInfo = axis.getTupleInfo(j);
                for (int k = 0; k < tupleInfo.length(); k++) {
                    MemberInfo memberInfo = tupleInfo.getMemberInfo(k);
                    try {
                        out.write(memberInfo.toString().getBytes(StandardCharsets.UTF_8));
                    } catch (IOException e) {
                        //e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                    //out.println(memberInfo);
                }
            }
        }

        if (axesCount == 2) {
            AxisInfo row = getAxisInfo(0);
            AxisInfo col = getAxisInfo(1);
            for (int i = 0; i < row.length(); i++) {
                for (int j = 0; j < col.length(); j++) {
                    Double measureValue = getMeasureValue(i, j);
                    try {
                        out.write(measureValue.toString().getBytes(StandardCharsets.UTF_8));
                    } catch (IOException e) {
                        //e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                    //out.println(measureValue);
                }
            }
        }
    }

    private void show2D(OutputStream out) throws IOException {

        SetInfo rowSet = sets.get(0);
        SetInfo colSet = sets.get(1);
        int rowLen = rowSet.length();
        int colLen = colSet.length();
        int rowThickness = rowSet.thickness();
        int colThickness = colSet.thickness();

        Object[][] objects = new Object[rowLen + colThickness][colLen + rowThickness];

        // column
        for (int i = 0; i < colSet.length(); i++) {
            TupleInfo tupleInfo = colSet.getTupleInfo(i);
            for (int j = 0; j < tupleInfo.length(); j++) {
                MemberInfo memberInfo = tupleInfo.getMemberInfo(j);
                objects[j + colThickness - tupleInfo.length()][rowThickness + i] = memberInfo;
            }
        }

        // row
        for (int i = 0; i < rowSet.length(); i++) {
            TupleInfo tupleInfo = rowSet.getTupleInfo(i);
            for (int j = 0; j < tupleInfo.length(); j++) {
                MemberInfo memberInfo = tupleInfo.getMemberInfo(j);
                objects[i + colThickness][j + rowThickness - tupleInfo.length()] = memberInfo;
            }
        }

        for (int i = 0; i < rowLen; i++) {
            for (int j = 0; j < colLen; j++) {
                objects[i + colThickness][j + rowThickness] = getMeasureValue(i, j);
            }
        }

        for (Object[] line : objects) {
            for (Object obj : line) {
                if (obj == null) {
                    out.write(String.format("%-20s", "").getBytes(StandardCharsets.UTF_8));
                    //out.print(String.format("%-20s", ""));
                } else if (obj instanceof MemberInfo) {
                    MemberInfo mi = (MemberInfo) obj;
                    if ("".equals(mi.getDisplay())) {
                        out.write(String.format("%-20s", "").getBytes(StandardCharsets.UTF_8));
                        //out.print(String.format("%-20s", ""));
                    } else {
                        out.write(String.format("%-20s", "[" + mi.getDisplay() + "]").getBytes(StandardCharsets.UTF_8));
                        //out.print(String.format("%-20s", "["+mi.getDisplay()+"]"));
                    }

                } else if (obj instanceof Double) {
                    double val = (double) obj;
                    out.write(String.format("%-20f", val).getBytes(StandardCharsets.UTF_8));
                    //out.print(String.format("%-20f", val));
                }
            }
            out.write("\n".getBytes(StandardCharsets.UTF_8));
            //out.println();
        }
    }
}
