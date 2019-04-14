package com.bubblehub.thread;

import com.alibaba.fastjson.JSONObject;
import com.bubblehub.model.manager.ElementManager;
import com.bubblehub.model.vo.DataPackage;
import com.bubblehub.model.vo.Player;
import com.bubblehub.model.vo.SuperElement;
import com.bubblehub.thread.Client.MsgProvider;
import com.bubblehub.thread.Client.ReceiveThread;
import com.bubblehub.thread.Client.RefreshPlayer;
import com.bubblehub.thread.Client.SendThread;

import java.util.List;

/**
 * @Author Fisher
 * @Date 2019/4/14 14:51
 **/


public class NetworkThread extends Thread implements MsgProvider, RefreshPlayer {

    private SendThread sendThread;

    public NetworkThread() {
        try {
            sendThread = new SendThread("localhost",9876,this,this);
            sendThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getDataFromObject() {
        List<SuperElement> list = ElementManager.getElementManager().getElementList("Player");
        return list.get(0).toString();
    }

    @Override
    public void refreshPlayer(String strReceive) {
        try {
            DataPackage dataPackage = JSONObject.parseObject(strReceive, DataPackage.class);
            GameThread.getGameThread().MapControl(dataPackage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
