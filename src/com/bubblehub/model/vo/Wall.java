package com.bubblehub.model.vo;

import com.bubblehub.model.loader.ElementLoader;
import com.sun.org.apache.xpath.internal.operations.Bool;
import utils.CutImg;

import java.awt.*;

/**
 * @Author Fisher
 * @Date 2019/4/11 22:19
 **/


public class Wall extends SuperElement {

    // 记录图片左上角像素坐标
    private CutImg cutImg;

    public Wall() {
        super();
    }

    public Wall(int x, int y, int mapX, int mapY, String url) {
        super(x, y, mapX, mapY, "Wall", url);
        if (Boolean.parseBoolean(ElementLoader.getElementLoader().getElementConfig("WallCut"))) {
            this.cutImg = new CutImg(getImg(),
                    Integer.parseInt(ElementLoader.getElementLoader().getElementConfig("WallCutX")),
                    Integer.parseInt(ElementLoader.getElementLoader().getElementConfig("WallCutY")));
        }
    }

    public static Wall createWall(int x, int y, int mapX, int mapY, String url) {
        return new Wall(x,y,mapX,mapY,url);
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

    public CutImg getCutImg() {
        return cutImg;
    }

    public void setCutImg(CutImg cutImg) {
        this.cutImg = cutImg;
    }
}
