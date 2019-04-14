package com.bubblehub.model.manager;

import com.bubblehub.model.factory.ElementFactory;
import com.bubblehub.model.loader.ElementLoader;
import com.bubblehub.model.vo.Player;
import com.bubblehub.model.vo.SuperElement;
import com.bubblehub.model.vo.Tool;

import java.util.*;

/**
 * @Author Fisher
 * @Date 2019/4/9 9:15
 **/

/*
 * package manager
 * 元素管理器
 *
 * 使用Java设计模式 -> 单例模式 -> 全局只有一个实例
 *
 */
public class ElementManager {

    // 集合 NPC元素、场景元素......
    private Map<String, List<SuperElement>> map;

    // 地图坐标，1是墙，2是箱子，3是炸弹，4是炸弹轨迹，5是道具类
    private int[][] position;

    // 单例：需要一个全局唯一的引用
    private static ElementManager elementManager;

    // 构造方法私有化，只有在本类中可以实例化对象
    private ElementManager() {
        init();
    }

    // 受保护的init函数
    protected void init() {
        this.map = new HashMap<>();
        this.position=new int[12][16];

        /* 把List放进map中
         * hashCode()中，Object根据集合散列进行
         */
        List<SuperElement> player = new ArrayList<>();
        List<SuperElement> bomb = new ArrayList<>();
        List<SuperElement> bombTrack = new ArrayList<>();
        List<SuperElement> wall = new ArrayList<>();
        List<SuperElement> tool = new ArrayList<>();

        for (int i=0; i<Integer.parseInt(ElementLoader.getElementLoader().getGlobalConfig("Row")); i++) {
            String x = ElementLoader.getElementLoader().getElementConfig("row"+i);
            String[] item = x.split(",");
            for (int j=0; j<Integer.parseInt(ElementLoader.getElementLoader().getGlobalConfig("Col")); j++) {
                switch (item[j]) {
                    case "1":
                        wall.add(ElementFactory.eFactory("Wall",i,j));
                        break;
                    case "2":
                        wall.add(ElementFactory.eFactory("Wall",i,j));
                        break;
                    case "3":
                        SuperElement p = ElementFactory.eFactory("Player",i,j);
                        p.setIndex(player.size()+1);
                        player.add(p);
                        break;
                }
                if (!item[j].equals("3")) {
                    position[i][j] = Integer.parseInt(item[j]);
                }
            }
        }

        map.put("Player", player);
        map.put("Bomb", bomb);
        map.put("BombTrack", bombTrack);
        map.put("Wall", wall);
        map.put("Tool", tool);

    }

    // static语句块，类加载的时候执行，只执行一次
    static {
        if (elementManager == null) {
            elementManager = new ElementManager();
        }
    }

    // 根据key获取List，得到一个List集合
    public List<SuperElement> getElementList(String key) {
        return map.get(key);
    }

    // Auto Generate
    public Map<String, List<SuperElement>> getMap() {
        return map;
    }

    public int[][] getPosition() {
        return position;
    }

    public void setPosition(int[][] position) {
        this.position = position;
    }

    // 为防止出现线程安全问题，对该方法加锁
    public static ElementManager getElementManager() {
        // 用static语句块代替
        return elementManager;
    }
}
