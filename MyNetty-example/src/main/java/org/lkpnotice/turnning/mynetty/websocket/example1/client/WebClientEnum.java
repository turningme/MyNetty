package org.lkpnotice.turnning.mynetty.websocket.example1.client;

import java.util.concurrent.TimeUnit;

/**
 * Created by liujinpeng on 2019/4/24.
 */
public enum  WebClientEnum {
    CLIENT;

    private static MsgWebSocketClient socketClient = null;

    public static void initClient(MsgWebSocketClient client) throws InterruptedException {
        socketClient = client;
        if(null !=socketClient ) {
            socketClient.connect();
        }else{
            throw new RuntimeException("socketClient null");
        }

        TimeUnit.SECONDS.sleep(5L);
        boolean flag = true;
        int i=1000;
        while(flag) {
            socketClient.send("测试websocket。。。"+(i--));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if(i == 0) {
                flag = false;
            }
        }
    }
}
