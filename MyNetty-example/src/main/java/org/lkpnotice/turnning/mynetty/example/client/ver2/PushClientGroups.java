package org.lkpnotice.turnning.mynetty.example.client.ver2;

import org.lkpnotice.turnning.mynetty.example.comm.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by liujinpeng on 2019/3/8.
 */
public class PushClientGroups {
    ExecutorService service = Executors.newFixedThreadPool(10000);

    public void execute(Runnable runnable){
        service.submit(runnable);
    }

    public static void main(String[] args){
        PushClientGroups pushClientGroups = new PushClientGroups();

        for (int i=0; i< 10;i++){
            pushClientGroups.execute(new Runnable() {
                @Override
                public void run() {
                    PushClient pushClient = new PushClient("localhost",8000);
                    try {
                        pushClient.connect();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Logger.log("error + " + e);
                    }
                }
            });

        }
    }
}
