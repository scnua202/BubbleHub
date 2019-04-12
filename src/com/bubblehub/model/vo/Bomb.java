package com.bubblehub.model.vo;

import com.bubblehub.model.loader.ElementLoader;
import utils.CutImg;

import java.awt.*;

/**
 * @Author Fisher
 * @Date 2019/4/11 21:58
 **/


public class Bomb extends SuperElement {

    // 爆炸倒计时
    private int explodeTime;

    // 攻击力范围
    private int power;

    // 记录图片左上角像素坐标
    private CutImg cutImg;

    public Bomb() {
        super();
    }

    public Bomb(int x, int y, int mapX, int mapY, String url) {
        super(x, y, mapX, mapY, "Bomb", url);
        setExplodeTime(Integer.parseInt(ElementLoader.getElementLoader().getElementConfig("BombExplodeTime")));
        if (Boolean.parseBoolean(ElementLoader.getElementLoader().getElementConfig("BombCut"))) {
            this.cutImg = new CutImg(getImg(),
                    Integer.parseInt(ElementLoader.getElementLoader().getElementConfig("BombCutX")),
                    Integer.parseInt(ElementLoader.getElementLoader().getElementConfig("BombCutY")));
        }
        setPower(2);
    }

    public static Bomb createBomb(int x,int y,int mapX,int mapY,String url) {
        return new Bomb(x,y,mapX,mapY,url);
    }

    @Override
    public void showElement(Graphics g) {

    }

    @Override
    public void move() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void update() {
        super.update();
    }

    public int getExplodeTime() {
        return explodeTime;
    }

    public void setExplodeTime(int explodeTime) {
        this.explodeTime = explodeTime;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public CutImg getCutImg() {
        return cutImg;
    }

    public void setCutImg(CutImg cutImg) {
        this.cutImg = cutImg;
    }
}
