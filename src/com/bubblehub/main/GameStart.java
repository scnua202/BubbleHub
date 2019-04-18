package com.bubblehub.main;

import com.bubblehub.frame.MainFrame;
import com.bubblehub.frame.MainJPanel;
import com.bubblehub.frame.WelcomePanel;
import com.bubblehub.model.loader.ElementLoader;
import com.bubblehub.thread.GameListener;
//import sun.applet.Main;

/**
 * @Author Fisher
 * @Date 2019/4/10 20:21
 **/


public class GameStart {

    private static MainFrame mainFrame;

    // 游戏启动唯一入口
    public static void main(String[] args) {

        try {
            // 游戏资源加载
            ElementLoader.getElementLoader().readGlobalConfig();

            // 窗体加载
            mainFrame = new MainFrame();

            // 画板加载
            WelcomePanel welcomePanel = new WelcomePanel();
            MainJPanel mainJPanel = new MainJPanel();
            mainFrame.setjPanel(welcomePanel);
            mainFrame.addJPanel();

            // 监听加载
            GameListener listener = new GameListener();
            mainFrame.setKeyListener(listener);
            mainFrame.addListener();

            // 游戏启动
//            mainFrame.start();
            mainFrame.runStart();
            mainFrame.playGameBgm();

        } catch (RuntimeException e) {
            e.printStackTrace();
        }

    }

    public static MainFrame getMainFrame() {
        return mainFrame;
    }
}
