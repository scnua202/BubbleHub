package com.bubblehub.thread.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ReceiveThread implements Runnable{
    @Override
    public void run() {
        int serverPort = 9876;  //服务器运行端口
        try {
            ServerSocket serverSocket = new ServerSocket(serverPort);  // 从该端口接收数据
            while (true) {
                System.out.println(this.getClass()+"这是一个全新的服务端接受循环----------");
                Socket sSocket = serverSocket.accept();                 // 堵塞程序，等待直到接受到连接才执行下面的代码
                ReceiveSocket newConnectedSocket = new ReceiveSocket(sSocket);
                MsgManager.addConnectedSocket(newConnectedSocket);
                newConnectedSocket.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

