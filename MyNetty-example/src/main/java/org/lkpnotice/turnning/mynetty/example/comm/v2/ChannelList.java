package org.lkpnotice.turnning.mynetty.example.comm.v2;

import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by liujinpeng on 2019/3/7.
 */
public class ChannelList {
    public static List<Channel> channels = Collections.synchronizedList(new ArrayList<>());
}
