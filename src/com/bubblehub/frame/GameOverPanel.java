package com.bubblehub.frame;

import javax.swing.*;
import java.awt.*;

/**
 * @Author Fisher
 * @Date 2019/4/17 22:06
 **/


public class GameOverPanel extends JPanel {
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // 添加背景图片
        ImageIcon bgImg = new ImageIcon("resources/img/Gameover.png");
        JLabel bgLabel = new JLabel();
        bgLabel.setIcon(bgImg);
        bgLabel.setBounds(0,0,800,600);
        this.add(bgLabel);
//        this.repaint();
        this.updateUI();
    }
}
