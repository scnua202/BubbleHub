package com.bubblehub.thread.Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ReceiveSocket  extends Thread  {

    private Socket sSocket;

    private OutputStream socketOutStream;
    public ReceiveSocket(Socket sSocket) throws IOException {
        this.sSocket = sSocket;
        this.socketOutStream=sSocket.getOutputStream();
    }

    public synchronized  void sendMsgToClient(String msg) throws IOException {
       this.socketOutStream.write(msg.getBytes());
        System.out.println(this.getName()+"线程收到并返回给客户端："+msg);

    }

    //每次接受到新的sScoket就开新线程执行通信
    @Override
    public void run() {
        try {
            System.out.println(this.getName()+"线程启动---------------------------");
            InputStream inStream = sSocket.getInputStream();        // 接受到连接后获取连接的输入流
            OutputStream outStream = sSocket.getOutputStream();     // 接受到连接后获取连接的输出流
            byte[] inStreamBytes = new byte[1024];                  // 数据缓存区
            int len ;                                               // 数据不一定写满缓存区，需要len来标志数据结束

            System.out.println(this.getName()+"线程开始读取流---------------------------");

                            /*
                                这里的inStream.read会同样会堵塞，如果客户端没有发送数据，服务端就会等客户端发送数据后再检验数据
                                是有效还是关闭标志。
                             */
            while ((len = inStream.read(inStreamBytes)) > 0) {      // 循环读取输入流直到连接关闭
                String strReceive = new String(inStreamBytes, 0, len);
//                MsgManager.broadcast(strReceive,this.sSocket);
//                outStream.write(strReceive.getBytes());
                sendMsgToClient(strReceive);
                MsgManager.broadcast(strReceive,this);
//                System.out.println(this.getName()+"线程收到并返回给客户端："+strReceive);                     //接收到的字符串输出在控制台,方便测试
            }
            MsgManager.removeBadSocket(this);


        } catch (IOException e) {
            MsgManager.removeBadSocket(this);
            e.printStackTrace();
        }
        System.out.println(this.getName()+"线程run方法结束");

    }


}
