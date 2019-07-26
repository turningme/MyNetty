package org.lkpnotice.turnning.mynetty.websocket.example1.server;

import com.google.common.collect.Maps;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by jpliu on 2019/7/26.
 */
public class ChannelContainer {
    private static Map<ChannelHandlerContext,ChannelHandlerContext> wsMap = Maps.newConcurrentMap();
    private static BlockingQueue<String> messageQueue = new ArrayBlockingQueue<String>(20);
    static volatile  boolean flag = true;

    static {
        new Thread(new Runnable() {
            @Override
            public void run() {
                runTask();
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                String baseMsg = "my message";
                int counter = 0;
                while (true){

                    try {
                        addMessage(baseMsg + counter);
                        counter ++;
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        System.out.println(" " + e);
                    }


                }
            }
        }).start();
    }

    public static void  addChannel(ChannelHandlerContext cxt){
        wsMap.put(cxt,cxt);
    }

    public static void  removeChannel(ChannelHandlerContext cxt){
        wsMap.remove(cxt);
    }

    public static void addMessage(String msg) throws InterruptedException {
        messageQueue.offer(msg, 30,TimeUnit.SECONDS);
        System.out.println(" insert message " + msg);
    }


    public static void runTask(){
        while (flag){
            try {
                if (wsMap.size() <=0) {
                    TimeUnit.SECONDS.sleep(30);
                    continue;
                }
                String msg = messageQueue.poll(10, TimeUnit.SECONDS);
                Iterator<ChannelHandlerContext> cxtIter = wsMap.keySet().iterator();
                while (cxtIter.hasNext()){
                    ChannelHandlerContext cxt = cxtIter.next();
                    cxt.channel().writeAndFlush(new TextWebSocketFrame(msg));
                }
                System.out.println("send message  " + msg);
            } catch (InterruptedException e) {
                System.err.println("  " + e);
            }
        }

    }


}
