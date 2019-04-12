package com.bubblehub.frame;

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
//        String url = "resources/img/Titles.png";
//        ImageIcon img = new ImageIcon(url);
//        g.drawImage(img.getImage(),0,0,this.getWidth(),this.getHeight(),this);

        JButton startButton = new JButton();
        startButton.setText("开始游戏");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //点击按钮后跳转到 -->游戏mainPanel
                MainJPanel mainJPanel = new MainJPanel();
                setVisible(false);
                mainFrame.add(mainJPanel);
                mainFrame.setVisible(true);
            }
        });
        BorderLayout layout = new BorderLayout();
        this.setLayout(layout);
        this.add(startButton,BorderLayout.CENTER);

    }

    //背景图片
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

    }
}
