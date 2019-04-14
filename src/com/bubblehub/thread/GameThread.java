package com.bubblehub.thread;

/**
 * @Author Fisher
 * @Date 2019/4/10 8:46
 *
 *
 * package thread
 **/

import com.bubblehub.model.loader.ElementLoader;
import com.bubblehub.model.manager.ElementManager;
import com.bubblehub.model.vo.DataPackage;
import com.bubblehub.model.vo.SuperElement;
import com.bubblehub.thread.Client.*;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 在Java中只能是单继承，多实现。
 * 我们通过内部类的方式，弥补单继承的缺陷
 *
 *
 * 游戏线程 -> 死循环，但会有状态变量控制
 */
public class GameThread extends Thread {

    // 定时器
    private int time;

    private int REFRESHRATE = Integer.parseInt(ElementLoader.getElementLoader().getGlobalConfig("SleepTime"));

    public int FPS = Integer.parseInt(ElementLoader.getElementLoader().getGlobalConfig("FPS"));

    private NetworkThread networkThread;

    private static GameThread gameThread;

    static {
        if (gameThread==null) {
            gameThread = new GameThread();
        }
    }

    public static GameThread getGameThread() {
        return gameThread;
    }

    @Override
    public void run() {
        // 游戏整体进度
        while (true) {
            // 1. 加载地图，人物
            loadElement();
            // 2. 显示人物、地图
            runGame();
            // 3. 结束本地图
            overGame();

            // 线程休眠
            try {
                sleep(REFRESHRATE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadElement() {
        // 如果启动了联机对战，则新建网络线程
        if (Boolean.parseBoolean(ElementLoader.getElementLoader().getGlobalConfig("Online"))) {
            networkThread = new NetworkThread();
            networkThread.start();
        }
    }

    private void runGame() {
        // 游戏进行中的死循环
        while (true) {
            // 游戏中所有元素的更新

            Map<String, List<SuperElement>> map = ElementManager.getElementManager().getMap();
            Set<String> set = map.keySet();
            for (String key:set) {
                List<SuperElement> elements = map.get(key);
                // 循环倒着写，防止删除元素时出现画面卡顿
                for (int i=elements.size()-1; i>=0; i--) {
                    elements.get(i).update();
                    // 如果对象不可见，则销毁对象
                    if (!elements.get(i).isVisible()) {
                        elements.remove(i);
                    }
                }
            }

            // 游戏流程控制
//            this.MapControl();

            // player处于死亡状态时，结束游戏

            try {
                sleep(REFRESHRATE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    // 游戏流程控制
    public void MapControl(DataPackage dataPackage) {
        switch (dataPackage.getType()) {
            // 墙体
            case 1:
                break;
            // 玩家
            case 3:
                if (dataPackage.getIndex()==Integer.parseInt(ElementLoader.getElementLoader().getGlobalConfig("PlayerIndex"))) {
                    List<SuperElement> list = ElementManager.getElementManager().getElementList("Player");
                    SuperElement superElement = list.get(1);
                    superElement.getCalcGrid().setRow(dataPackage.getRow());
                    superElement.getCalcGrid().setCol(dataPackage.getCol());
                    list.set(1,superElement);
                }
                break;
        }
    }

    // 游戏结束控制
    private void overGame() {
    }

}
