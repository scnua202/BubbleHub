package com.bubblehub.thread;

import com.alibaba.fastjson.JSONObject;
import com.bubblehub.model.loader.ElementLoader;
import com.bubblehub.model.manager.ElementManager;
import com.bubblehub.model.vo.DataPackage;
import com.bubblehub.model.vo.SuperElement;
import com.bubblehub.thread.Client.MsgProvider;
import com.bubblehub.thread.Client.RefreshPlayer;
import com.bubblehub.thread.Client.SendThread;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Fisher
 * @Date 2019/4/16 22:42
 **/


public class BombNetworkThread extends Thread implements MsgProvider, RefreshPlayer {

    private SendThread sendThread;

    private String serverIp = ElementLoader.getElementLoader().getGlobalConfig("ServerIp");

    private int serverPort = Integer.parseInt(ElementLoader.getElementLoader().getGlobalConfig("ServerPort"));

    public BombNetworkThread() {
        try {
            sendThread = new SendThread(serverIp,serverPort,this,this);
            sendThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getDataFromObject() {
        List<SuperElement> bombList = ElementManager.getElementManager().getElementList("Bomb");
        if (bombList.size()>0) {
            List<String> list = new ArrayList<>();
            for (SuperElement x:bombList) {
                list.add(x.toString());
            }
            return JSONObject.toJSONString(list);
        } else {
            return null;
        }
    }

    @Override
    public void refreshPlayer(String strReceive) {
        try {
            List<String> list = JSONObject.parseObject(strReceive, List.class);
            for (String x:list) {
                GameThread.getGameThread().MapControl(JSONObject.parseObject(x, DataPackage.class));
            }
//            DataPackage dataPackage = JSONObject.parseObject(strReceive, DataPackage.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
