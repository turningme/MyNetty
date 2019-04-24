package org.lkpnotice.turnning.mynetty.websocket.example1.client;

import java.net.URISyntaxException;

/**
 * Created by liujinpeng on 2019/4/24.
 */
public class SocketClientEngine {
    public static final String  URI_ADDR = "ws://localhost:8899/ws";

    public static void main(String[] args)  {
        try {
            String addr = URI_ADDR;
            WebClientEnum.CLIENT.initClient(new MsgWebSocketClient(addr));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }
}
