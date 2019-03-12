package org.lkpnotice.turnning.mynetty.example.server.v6;

import com.alibaba.fastjson.JSON;
import org.lkpnotice.turnning.mynetty.example.comm.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;

/**
 * Created by liujinpeng on 2019/3/11.
 */
public class IOServer {
    public static void main(String[] args) throws Exception {
        new IOServer().startUp();

    }


    public void startUp(){
        new Thread(() -> {
            try {
                ;
                ServerSocket serverSocket = new ServerSocket(8971);


                //绑定指定的ip地址
             /*   SocketAddress socketAddress = new InetSocketAddress("172.18.206.216",8971);
                serverSocket.bind(socketAddress);*/


                constructBlockingState();
          /*      while (true) try {
                    // (1) 阻塞方法获取新的连接
                    Socket socket = serverSocket.accept();
                    Logger.log("get new socket");

                    // (2) 每一个新的连接都创建一个线程，负责读取数据
                    new Thread(() -> {
                        try {
                            byte[] data = new byte[1024];
                            InputStream inputStream = socket.getInputStream();
                            while (true) {
                                int len;
                                // (3) 按字节流方式读取数据
                                while ((len = inputStream.read(data)) != -1) {
                                    System.out.println(new String(data, 0, len));
                                }
                            }
                        } catch (IOException e) {
                            Logger.log("exception is " + JSON.toJSONString(e));
                            e.printStackTrace();
                            throw new RuntimeException(e);
                        }
                    }).start();

                } catch (IOException e) {
                    Logger.log("exception = " + JSON.toJSONString(e));
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }*/

            } catch (Exception e) {
                Logger.log(JSON.toJSONString(e));
                throw  new RuntimeException(e);
            } finally {

            }
        }).start();
    }


    Integer mutex = new Integer(1);
    private void constructBlockingState(){
        synchronized (mutex){
            while(true){
                try {
                    mutex.wait();
                } catch (InterruptedException e) {
            /*        e.printStackTrace();*/
                    Logger.log("constructBlockingState mutex wait exception");
                }
            }
        }

    }


}
