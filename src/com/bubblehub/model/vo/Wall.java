package com.bubblehub.model.vo;

import com.bubblehub.model.loader.ElementLoader;
//import com.sun.org.apache.xpath.internal.operations.Bool;
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

    public Wall(int mapX, int mapY, String url) {
        super(mapX, mapY, "Wall", url);
        if (Boolean.parseBoolean(ElementLoader.getElementLoader().getElementConfig("WallCut"))) {
            this.cutImg = new CutImg(getImg(),
                    Integer.parseInt(ElementLoader.getElementLoader().getElementConfig("WallCutX")),
                    Integer.parseInt(ElementLoader.getElementLoader().getElementConfig("WallCutY")));
        }
        cutImg.setNoY(2);
        setName("Wall");
    }

    public static Wall createWall(int mapX, int mapY, String url) {
        return new Wall(mapX,mapY,url);
    }

    @Override
    public void showElement(Graphics g) {
        // 截取图片一部分
        g.drawImage(getImg().getImage(),
                getX(),getY(),                          //图片输出左上角坐标
                getX()+getW(),getY()+getH(),  //图片输出右下角坐标
                cutImg.getTopX(),cutImg.getTopY(),//截取的图片的左上角坐标
                cutImg.getBottomX(),cutImg.getBottomY(),//截取的图片的右下角坐标
                null);
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
