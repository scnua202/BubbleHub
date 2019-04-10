package com.bubblehub.model.manager;

import com.bubblehub.model.vo.SuperElement;

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

    // 单例：需要一个全局唯一的引用
    private static ElementManager elementManager;

    // 构造方法私有化，只有在本类中可以实例化对象
    private ElementManager() {
        init();
    }

    // 受保护的init函数
    protected void init() {
        this.map = new HashMap<>();

        /* 把List放进map中
         * hashCode()中，Object根据集合散列进行
         */

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

    // 为防止出现线程安全问题，对该方法加锁
    public static ElementManager getElementManager() {
        // 用static语句块代替
        return elementManager;
    }
}
