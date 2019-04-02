package org.lkpnotice.turnning.mynetty.example.reentrant;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by liujinpeng on 2019/3/14.
 */
public class TestThread implements Runnable{
    private static ReentrantLock rLock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        TestThread imp = new TestThread();
        for(int i=0;i<2;i++){
            new Thread(imp).start();
            Thread.sleep(200);
        }
//        System.exit(0);
    }

    public void run() {
        try {
            if(rLock.tryLock()){
                //休眠5秒 用于第二次请求 抛出异常
                System.out.println("当前【持有锁】的线程编号："+Thread.currentThread().getId());
                Thread.sleep(1000);
            }else {
                System.out.println("当前资源已被锁定！【"+Thread.currentThread().getId()+"】线程号被踢出！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                System.out.println("线程：【"+Thread.currentThread().getId()+"】,开始解锁");
                rLock.unlock();
                System.out.println("线程：【"+Thread.currentThread().getId()+"】，解锁完成！");
            } catch (Exception e) {
                System.out.println(e.toString());
                e.printStackTrace();
            }
        }
    }
}