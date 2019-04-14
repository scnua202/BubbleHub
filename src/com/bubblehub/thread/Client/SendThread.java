package com.bubblehub.thread.Client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class SendThread extends Thread {

    private String netAddress="localhost";  // 服务器地址
    private int serverPort = 9876;          // 服务器运行的端口
    private MsgProvider msgProvider;        // 数据源
    private RefreshPlayer refreshPlayer;    // 收到其他人位置信息后回调
    private Socket socket;

    public SendThread(String netAddress,int serverPort,MsgProvider msgProvider,RefreshPlayer refreshPlayer){
        this.netAddress = netAddress;
        this.serverPort = serverPort;
        this.msgProvider = msgProvider;
        this.refreshPlayer = refreshPlayer;
    }

    @Override
    public void run() {
        try {
            socket = new Socket(netAddress, serverPort); // 设置目标服务器端IP,并设置端口号;

            new ReceiveThread(socket,refreshPlayer).start();                   // 开启一个线程监听服务器返回的数据

            OutputStream outStream = socket.getOutputStream();   // 获取输出流，用于发送数据

                while(true){
                    // 取得数据
                    String sendMsg = msgProvider.getDataFromObject();

                    // 发送数据
                    outStream.write(sendMsg.getBytes());

                    // 线程休眠，不要让数据发送的太快
                    Thread.sleep(15);
                }


        } catch (Exception ex) {
            try {
                if(socket!=null) socket.close();            // 告诉服务端通信已经结束，让服务端不会出现connect reset错误。
            } catch (IOException e) {
                e.printStackTrace();
            }
            ex.printStackTrace();
        }
    }
}
