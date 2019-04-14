package com.bubblehub.thread.Server;

import java.io.IOException;
import java.util.ArrayList;

public class MsgManager {
    public static ArrayList<ReceiveSocket> connectedSockets = new ArrayList<ReceiveSocket>();

    /**
     * 添加已经连接的socket
     * @param connectedSocket 已经连接的socket
     */
    public static void addConnectedSocket(ReceiveSocket connectedSocket){
        if(connectedSockets.contains(connectedSocket))
        connectedSockets.add(connectedSocket);
    }

    /**
     * 移除断开连接的socket
     * @param badSocket 断开连接的socket
     */
    public static void removeBadSocket(ReceiveSocket badSocket){
        if(connectedSockets.contains(badSocket))
        connectedSockets.remove(badSocket);
    }

    /**
     * 由其他线程调用，将socket收到的消息广播给其他的socket
     * @param msg 需要广播的消息
     * @param broadcasters 需要广播消息的socket，用来排除自身
     */
    public static void broadcast(String msg,ReceiveSocket broadcasters) throws IOException {
        msg = "收到其他客户端:"+msg;
        for(int i=0;i<connectedSockets.size();i++){
            if(connectedSockets.get(i)!=broadcasters){
                connectedSockets.get(i).sendMsgToClient(msg);
            }
        }
    }
}
