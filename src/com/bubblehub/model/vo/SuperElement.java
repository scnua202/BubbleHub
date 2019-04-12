package com.bubblehub.model.vo;

import com.bubblehub.model.loader.ElementLoader;
import utils.CalcGrid;

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

    // 游戏运行参数(FPS,每帧渲染间隔时间)
    private final int FPS = Integer.parseInt(ElementLoader.getElementLoader().getGlobalConfig("FPS"));
    private final int SLEEPTIME =Integer.parseInt(ElementLoader.getElementLoader().getGlobalConfig("SleepTime"));

    // 游戏屏幕分辨率
    private int WIDTH;
    private int HEIGHT;

    // 位置，宽高
    private int x;
    private int y;
    private int w;
    private int h;

    // 地图格子坐标
    private int MapCol;
    private int MapRow;

    // 地图格子坐标和像素点互转工具
    public CalcGrid calcGrid;

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

    public SuperElement(int MapCol, int MapRow, String name, String url) {
        this.calcGrid = new CalcGrid(MapRow ,MapCol);
        this.x = calcGrid.getX();
        this.y = calcGrid.getY();
        this.MapCol = MapCol;
        this.MapRow = MapRow;
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

    public int getMapCol() {
        return MapCol;
    }

    public void setMapCol(int MapCol) {
        this.MapCol = MapCol;
    }

    public int getMapRow() {
        return MapRow;
    }

    public void setMapRow(int MapRow) {
        this.MapRow = MapRow;
    }

    public int getFPS() {
        return FPS;
    }

    public int getSLEEPTIME() {
        return SLEEPTIME;
    }

    public CalcGrid getCalcGrid() {
        return calcGrid;
    }

    public void setCalcGrid(CalcGrid calcGrid) {
        this.calcGrid = calcGrid;
    }
}
