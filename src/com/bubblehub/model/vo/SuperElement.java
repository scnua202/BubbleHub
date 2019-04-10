package com.bubblehub.model.vo;

import com.bubblehub.model.loader.ElementLoader;

import javax.swing.*;
import java.awt.*;

/**
 * @Author Fisher
 * @Date 2019/4/9 9:51
 *
 * package vo
 * 所有model的父类
 *
 **/


public abstract class SuperElement {

    public int WIDTH;
    public int HEIGHT;

    private int x;
    private int y;
    private int w;
    private int h;

    // 默认为true，代表生命周期
    private boolean visible;

    private ImageIcon img;
    private boolean ThroughAble;

    /*
     * jvm给每个类都会默认添加一个无参构造方法
     * 但如果手写一个构造方法之后（无论是有参或者无参） jvm都不会再添加默认构造方法
     * 一般作为父类，若果有其他构造方法，最好加一个无参构造方法，防止继承报错
     */
    public SuperElement() { }

    public SuperElement(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.WIDTH = Integer.parseInt(ElementLoader.getElementLoader().getConfig("Width"));
        this.HEIGHT = Integer.parseInt(ElementLoader.getElementLoader().getConfig("Height"));
        setVisible(true);
        setImg(null);
        setThroughAble(false);
    }

    public abstract void showElement(Graphics g);

    public abstract void move();

    public abstract void destroy();

    public void update(){
        this.move();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public ImageIcon getImg() {
        return img;
    }

    public void setImg(ImageIcon img) {
        this.img = img;
    }

    public boolean isThroughAble() {
        return ThroughAble;
    }

    public void setThroughAble(boolean throughAble) {
        ThroughAble = throughAble;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public void setWIDTH(int WIDTH) {
        this.WIDTH = WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public void setHEIGHT(int HEIGHT) {
        this.HEIGHT = HEIGHT;
    }
}
