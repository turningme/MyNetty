package org.lkpnotice.turnning.mynetty.example.client.ver2;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.lkpnotice.turnning.mynetty.example.comm.v2.Message;
import org.lkpnotice.turnning.mynetty.example.comm.v2.PushMsg;

/**
 * Created by liujinpeng on 2019/3/7.
 */
public class PushMsgHandler extends SimpleChannelInboundHandler<Message> {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message = (Message) msg;
        PushMsg pushMsg = message.getMsg();
        System.out.println(JSON.toJSONString(pushMsg));
     /*   if(message.getType() == MessageType.MSG_PUSH.getValue()){
            NotificationManager manager = (NotificationManager) MainApplication.getContext().getSystemService(MainApplication.getContext().NOTIFICATION_SERVICE);
            Notification.Builder builder = new Notification.Builder(MainApplication.getContext());

            //创建消息,设置点击效果
            Intent i = new Intent(MainApplication.getContext(), NewsDetailActivity.class);
            NewsDetailList newsDetail = new NewsDetailList();
            newsDetail.author_name = pushMsg.getAuthor_name();
            newsDetail.date = pushMsg.getDate();
            newsDetail.thumbnail_pic_s = pushMsg.getThumbnail_pic_s();
            newsDetail.title = pushMsg.getTitle();
            newsDetail.url = pushMsg.getUrl();
            i.putExtra(Config.NEWS_DATA, newsDetail);
            PendingIntent intent = PendingIntent.getActivity(MainApplication.getContext(), 0,
                    i, PendingIntent.FLAG_ONE_SHOT);

            builder.setContentTitle(pushMsg.getTitle())//设置通知栏标题
                    .setContentText(pushMsg.getAuthor_name()) //设置通知栏显示内容
                    .setTicker("ok社区") //通知首次出现在通知栏，带上升动画效果的
                    .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                    .setContentIntent(intent)
                    .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                    //.setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                    .setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                    //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
                    .setSmallIcon(R.mipmap.logo);//设置通知小ICON
            manager.notify(1, builder.build());
        }*/
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {

    }

}
