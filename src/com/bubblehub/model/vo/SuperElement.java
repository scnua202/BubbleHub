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

    // 游戏屏幕分辨率
    public int WIDTH;
    public int HEIGHT;

    // 位置，宽高
    private int x;
    private int y;
    private int w;
    private int h;

    // 地图格子坐标
    private int mapX;
    private int mapY;

    // 默认为true，代表生命周期
    private boolean visible;

    // 贴图
    private ImageIcon img;

    // 能否穿过
    private boolean ThroughAble;

    // 能否被摧毁
    private boolean DestroyAble;

    // 能否移动
    private boolean MoveAble;

    /*
     * jvm给每个类都会默认添加一个无参构造方法
     * 但如果手写一个构造方法之后（无论是有参或者无参） jvm都不会再添加默认构造方法
     * 一般作为父类，若果有其他构造方法，最好加一个无参构造方法，防止继承报错
     */
    public SuperElement() { }

    public SuperElement(int x, int y, int mapX, int mapY, String name, String url) {
        this.x = x;
        this.y = y;
        this.mapX = mapX;
        this.mapY = mapY;
        this.w = Integer.parseInt(ElementLoader.getElementLoader().getElementConfig(name+"Height"));
        this.h = Integer.parseInt(ElementLoader.getElementLoader().getElementConfig(name+"Width"));
        this.WIDTH = Integer.parseInt(ElementLoader.getElementLoader().getGlobalConfig("Width"));
        this.HEIGHT = Integer.parseInt(ElementLoader.getElementLoader().getGlobalConfig("Height"));
        this.MoveAble = Boolean.parseBoolean(ElementLoader.getElementLoader().getElementConfig(name+"MoveAble"));
        this.img = new ImageIcon(url);
        this.ThroughAble = Boolean.parseBoolean(ElementLoader.getElementLoader().getElementConfig(name+"ThroughAble"));
        this.DestroyAble = Boolean.parseBoolean(ElementLoader.getElementLoader().getElementConfig(name+"DestroyAble"));
        setVisible(true);
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

    public boolean isDestroyAble() {
        return DestroyAble;
    }

    public void setDestroyAble(boolean destroyAble) {
        DestroyAble = destroyAble;
    }

    public boolean isMoveAble() {
        return MoveAble;
    }

    public void setMoveAble(boolean moveAble) {
        MoveAble = moveAble;
    }

    public int getMapX() {
        return mapX;
    }

    public void setMapX(int mapX) {
        this.mapX = mapX;
    }

    public int getMapY() {
        return mapY;
    }

    public void setMapY(int mapY) {
        this.mapY = mapY;
    }
}
