package org.lkpnotice.turnning.mynetty.example.client.v6;

import com.alibaba.fastjson.JSON;
import org.lkpnotice.turnning.mynetty.example.comm.Logger;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

/**
 * Created by liujinpeng on 2019/3/11.
 */
public class IOClient {
    public static void main(String[] args) {
        new Thread(() -> {
            try {
                Socket socket = new Socket("172.18.206.216", 8971);
                Logger.log("client finished ");
                while (true) {
                    try {
                        socket.getOutputStream().write((new Date() + ": hello world").getBytes());
                        socket.getOutputStream().flush();
                        Thread.sleep(2000);
                    } catch (Exception e) {
                   /*     Logger.log(JSON.toJSONString(e));*/
                    }finally {

                    }
                }
            } catch (IOException e) {
                Logger.log(JSON.toJSONString(e));
                throw  new RuntimeException(e);
            }
        }).start();


    }


}
