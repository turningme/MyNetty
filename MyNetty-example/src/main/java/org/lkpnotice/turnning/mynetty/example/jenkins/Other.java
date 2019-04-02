package org.lkpnotice.turnning.mynetty.example.jenkins;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Created by liujinpeng on 2019/3/21.
 */
public class Other {

    static String timeMillis2String(long milliseconds) {
        long temp = Math.round(milliseconds / 1000.0d);
        String[] unit = new String[]{"秒", "分", "时", "天"};
        long[] unitSize = new long[]{60L, 60L, 24L, 30L};

        Deque<String> stackQueue = new LinkedList<>();
        int pos = 0;
        do{//pos = 0 ,1 ,2 最大是天
            long unitVar = pos<2?temp % unitSize[pos]:temp;
            String localUnit = unit[pos];

            stackQueue.push(String.format("%s%s", unitVar, localUnit));
            temp = temp / unitSize[pos];
            pos++;
        }while (temp > 0 && pos < 3);

        StringBuffer sbuffer = new StringBuffer();
        while (!stackQueue.isEmpty()) {
            sbuffer.append(stackQueue.pop());
        }

        return sbuffer.toString();
    }



    public static void main(String[] args){
        System.out.println(timeMillis2String(24*3600*1000L + 1000));
    }
}

