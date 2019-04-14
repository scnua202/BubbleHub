package com.bubblehub.frame;

import com.bubblehub.model.loader.ElementLoader;
import com.bubblehub.thread.GameThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * @Author: Fisher
 * @Date: 2019-04-08 16:15
 */

/*
 * package frame
 * 游戏的窗体类
 * 功能：窗体初始化，大小，关闭方式，监听绑定，画板注入
 * 继承自JFrame
 */
public class MainFrame extends JFrame {

    // 窗口尺寸
    public int WIDTH;

    public int HEIGHT;

    // 键盘监听器
    private KeyListener keyListener;
    // 鼠标监听
    private MouseListener mouseListener;
    // 鼠标移动监听
    private MouseMotionListener mouseMotionListener;
    // 画板
    private JPanel jPanel;

    // 窗体构造方法，为了提高可复用程度，使用init函数
    public MainFrame() throws HeadlessException {
        super();
        init();
    }

    // 初始化方法，在构造函数中进行调用
    // 使用原因：构造方法不能被override，而init函数可以
    public void init() {
        this.WIDTH = Integer.parseInt(ElementLoader.getElementLoader().getGlobalConfig("Width"));
        this.HEIGHT = Integer.parseInt(ElementLoader.getElementLoader().getGlobalConfig("Height"));
        this.setTitle("BubbleHub");
        this.setSize(WIDTH, HEIGHT);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        // 添加各种监听器
        this.addListener();
        // 添加画板，这里使用手动添加
//        this.addJPanel();
        // 背景图片
        JLabel bgImg = new JLabel(new ImageIcon("resources/img/backgroundMap.png"));
        bgImg.setBounds(0,0,800,600);
        this.add(bgImg);
    }

    // 游戏启动
    public void start() {
        GameThread gameThread = new GameThread();
        gameThread.start();

        // 使用线程启动窗口
        // 判断jPanel是否支持多线程
        if (this.jPanel instanceof Runnable) {
            new Thread((Runnable)jPanel).start();
        }
        this.setVisible(true);
    }

    // 添加画板
    public void addJPanel() {
        if (jPanel != null) {
            this.add(jPanel);
        } else {
            throw new RuntimeException("游戏初始化失败");
        }
    }

    // 添加监听
    public void addListener() {
        if (keyListener != null) {
            // 添加键盘监听
            this.addKeyListener(keyListener);
        }
        if (mouseListener != null) {
            // 添加鼠标监听
            this.addMouseListener(mouseListener);
        }
        if (mouseMotionListener != null) {
            // 添加鼠标移动监听
            this.addMouseMotionListener(mouseMotionListener);
        }

    }

    public void setKeyListener(KeyListener keyListener) {
        this.keyListener = keyListener;
    }

    public void setMouseListener(MouseListener mouseListener) {
        this.mouseListener = mouseListener;
    }

    public void setMouseMotionListener(MouseMotionListener mouseMotionListener) {
        this.mouseMotionListener = mouseMotionListener;
    }

    public void setjPanel(JPanel jPanel) {
        this.jPanel = jPanel;
    }
}
