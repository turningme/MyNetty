package org.lkpnotice.turnning.mynetty.example.comm.v3;

/**
 * Created by liujinpeng on 2019/3/7.
 */
public enum MessageType {
    TYPE_AUTH((byte)0),TYPE_LOGOUT((byte)1),TYPE_TEXT((byte)2),TYPE_EMPTY((byte)3), ;
    private byte value;
    MessageType(byte value){
        this.value = value;
    }
    public byte value(){
        return this.value;
    }
}
