package org.lkpnotice.turnning.mynetty.example.comm.v2;

import java.io.Serializable;

/**
 * Created by liujinpeng on 2019/3/7.
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    private byte type;
    private PushMsg msg;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public PushMsg getMsg() {
        return msg;
    }

    public void setMsg(PushMsg msg) {
        this.msg = msg;
    }
}
