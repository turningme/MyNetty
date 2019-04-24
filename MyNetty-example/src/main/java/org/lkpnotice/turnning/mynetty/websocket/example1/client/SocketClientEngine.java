package org.lkpnotice.turnning.mynetty.websocket.example1.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;

/**
 * Created by liujinpeng on 2019/4/24.
 */
public class SocketClientEngine {
    static final Logger LOG = LoggerFactory.getLogger(SocketClientEngine.class);
    public static final String  URI_ADDR = "ws://localhost:8899/ws/ww";

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
