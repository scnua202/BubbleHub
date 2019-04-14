package com.bubblehub.frame;

import com.bubblehub.model.loader.ElementLoader;
import com.bubblehub.model.manager.ElementManager;
import com.bubblehub.model.vo.SuperElement;
import utils.CFPSMaker;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: Fisher
 * @Date: 2019-04-09 08:54
 */

// package frame
// 画板类 -> 多线程支持 -> 实现fps
public class MainJPanel extends JPanel implements Runnable{

    private CFPSMaker cfpsMaker = new CFPSMaker();

    // 画板刷新时间间隔，单位为ms
    public int REFRESHRATE = Integer.parseInt(ElementLoader.getElementLoader().getGlobalConfig("SleepTime"));

    // 理论最大fps
    public int FPS = Integer.parseInt(ElementLoader.getElementLoader().getGlobalConfig("FPS"));

    /**
     * 什么是重写：
     * 1. 是有继承关系的类与类之间的语法现象（多态的一种实现）
     * 2. 重写的方法必须和父类的方法签名一样（返回值，方法名称，参数序列）
     * 3. 重写的方法访问修饰符只可以比父类的更加开放，不可以比父类更加封闭
     */
    @Override
    public void run() {
        while (true) {
            try {
                // 线程休眠，控制fps，异常不能向上抛，因为子类异常比父类异常级别更高
                // x毫秒刷新一次画板
                Thread.sleep(REFRESHRATE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 面板刷新
            this.repaint();
        }
    }

    // 只做显示作用
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 设置画板背景颜色
        //this.setBackground(Color.GRAY);
        //this.paintComponent(g);
        // 添加背景图片
        ImageIcon bgImg = new ImageIcon("resources/img/backgroundMap2.png");
        JLabel bgLabel = new JLabel();
        bgLabel.setIcon(bgImg);
        bgLabel.setBounds(0,0,820,625);
        this.add(bgLabel);


        // 计算FPS
        cfpsMaker.makeFPS();
        // 给一个判定值，可以使用枚举
        gameRuntime(g);
    }

    private void gameRuntime(Graphics g) {

        // 从map中获取map keySet
        Map<String, List<SuperElement>> map = ElementManager.getElementManager().getMap();
        Set<String> set = map.keySet();

        // 图层顺序加载
//        List<String> list0 = new ArrayList<>();
//        list0.addAll(set);
//        Collections.sort(list0);    // 自然顺序排序
//        Collections.sort(list0, compare);  //自定义顺序


        // 根据key循环获取List，将List中的元素画在画板上
        for (String key:set) {
            List<SuperElement> list = ElementManager.getElementManager().getElementList(key);
            for (SuperElement x:list) {
                x.showElement(g);
            }
        }

        // 在左上角显示fps
        g.setColor(Color.WHITE);
        g.drawString("FPS: " + cfpsMaker.getFPS(), 5, 15);

    }

}
