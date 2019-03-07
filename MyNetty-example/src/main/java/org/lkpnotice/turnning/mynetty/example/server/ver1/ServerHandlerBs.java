package org.lkpnotice.turnning.mynetty.example.server.ver1;

import java.io.IOException;
import java.nio.channels.SelectionKey;

/**
 * Created by liujinpeng on 2019/3/7.
 */
public interface ServerHandlerBs {
    void handleAccept(SelectionKey selectionKey) throws IOException;
    String handleRead(SelectionKey selectionKey) throws IOException;
}
