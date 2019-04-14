package com.bubblehub.thread.Client;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ReceiveThread extends Thread{
    private Socket newSocket ;
    private RefreshPlayer refreshPlayer;
    private InputStream socketInStream;

    public ReceiveThread(Socket newSocket,RefreshPlayer refreshPlayer) throws IOException {
        this.newSocket = newSocket;
        this.socketInStream = newSocket.getInputStream();
        this.refreshPlayer = refreshPlayer;
    }
    @Override
    public void run() {
        try {
            //接受数据
            byte[] inStreamBytes = new byte[1024];                  // 数据缓存区
            int len ;                                               // 数据不一定写满缓存区，需要len来标志数据结束
            while ((len = socketInStream.read(inStreamBytes)) > 0) {         // 读取输入流
                String strReceive = new String(inStreamBytes, 0, len);
                System.out.println(strReceive);                     //接收到的字符串输出在控制台,方便测试
                refreshPlayer.refreshPlayer(strReceive);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
