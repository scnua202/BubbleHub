package com.bubblehub.frame;

import com.bubblehub.main.GameStart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomePanel extends JPanel {
    MainFrame mainFrame = null;
    public WelcomePanel() {
    }

    public WelcomePanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        this.setLayout(null);
//        String url = "resources/img/Titles.png";
//        ImageIcon img = new ImageIcon(url);
//        g.drawImage(img.getImage(),0,0,this.getWidth(),this.getHeight(),this);

        // 添加背景图片
        ImageIcon bgImg = new ImageIcon("resources/img/Titles2.png");
        JLabel bgLabel = new JLabel();
        bgLabel.setIcon(bgImg);
        bgLabel.setBounds(0, 0, 800, 600);


        JButton startButton = new JButton();
        startButton.setText("开始游戏");
        startButton.setBounds(250, 200, 300, 100);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //点击按钮后跳转到 -->游戏mainPanel
                setVisible(false);
                MainJPanel mainJPanel = new MainJPanel();
                GameStart.getMainFrame().getContentPane().removeAll();
                GameStart.getMainFrame().setjPanel(mainJPanel);
                GameStart.getMainFrame().addJPanel();
                GameStart.getMainFrame().start();
            }
        });
        startButton.setFocusable(false);
        this.add(startButton);
        this.add(bgLabel);
        this.updateUI();
//        BorderLayout layout = new BorderLayout();
//        this.setLayout(layout);
//        this.add(startButton,BorderLayout.CENTER);

    }
}
