package com.euclidolap.sdk;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import static com.euclidolap.sdk.command.Intent.*;

public class Terminal {

    private String host;
    private int port;

    private Socket socket;

    private DataOutputStream output;
    private DataInputStream input;

    public Terminal(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connect() {
        try {
            socket = new Socket(host, port);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        try {
            output = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        try {
            output.write(Utils.intToBytes(6));
            output.write(Utils.shortToBytes((short) INTENT__TERMINAL_CONTROL.ordinal()));

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        try {
            input = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        byte[] capacityBytes = new byte[4];
        try {
            input.readFully(capacityBytes);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        int capacity = Utils.intFromBytes(capacityBytes);

        byte[] payload = new byte[capacity - 4];
        try {
            input.readFully(payload);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public Result exec(String mdx) {

        int capacity;
        try {
            capacity = 4 + 2 + 4 + mdx.getBytes("UTF-8").length + 1;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        try {
            output.write(Utils.intToBytes(capacity));
            output.write(Utils.shortToBytes((short) INTENT__MDX.ordinal()));
            output.write(Utils.intToBytes(mdx.getBytes("UTF-8").length));
            output.write(mdx.getBytes("UTF-8"));
            output.writeByte(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] capacityBytes = new byte[4];
        try {
            input.readFully(capacityBytes);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        capacity = Utils.intFromBytes(capacityBytes);

        byte[] payload = new byte[capacity - 4];
        try {
            input.readFully(payload);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        short intent = Utils.shortFromBytes(payload[0], payload[1]);

        if (INTENT__SUCCESSFUL.ordinal() == intent || INTENT__FAILURE.ordinal() == intent || INTENT__EXE_RESULT_DESC.ordinal() == intent) {
            for (int i = 2; i < payload.length; i++) {
                if (payload[i] == 0) {
                    System.out.println(new String(payload, 2, i - 2));
                    break;
                }
            }
        } else if (INTENT__MULTIDIM_RESULT_BIN.ordinal() == intent) {
            return Result.resolve(capacity, payload);
        } else {
            System.out.println("Unknown Information!");
        }

        return null;
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
