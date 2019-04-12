package com.bubblehub.model.vo;


import com.bubblehub.model.loader.ElementLoader;
import utils.CutImg;

import java.awt.*;

/**
 * @Author Fisher
 * @Date 2019/4/11 22:11
 **/


public class BombTrack extends SuperElement {

    // 存在时间
    private int keepTime;

    // 记录图片左上角像素坐标
    private CutImg cutImg;

    public BombTrack() {
        super();
    }

    public BombTrack(int x, int y, int mapX, int mapY, String url) {
        super(x, y, mapX, mapY, "BombTrack", url);
        setKeepTime(Integer.parseInt(ElementLoader.getElementLoader().getElementConfig("TrackKeepTime")));
        if (Boolean.parseBoolean(ElementLoader.getElementLoader().getElementConfig("BombTrackCut"))) {
            this.cutImg = new CutImg(getImg(),
                    Integer.parseInt(ElementLoader.getElementLoader().getElementConfig("BombTrackCutX")),
                    Integer.parseInt(ElementLoader.getElementLoader().getElementConfig("BombTrackCutY")));
        }
    }

    public static BombTrack createBombTrack(int x,int y,int mapX,int mapY,String url) {
        return new BombTrack(x,y,mapX,mapY,url);
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

    public int getKeepTime() {
        return keepTime;
    }

    public void setKeepTime(int keepTime) {
        this.keepTime = keepTime;
    }

    public CutImg getCutImg() {
        return cutImg;
    }

    public void setCutImg(CutImg cutImg) {
        this.cutImg = cutImg;
    }
}
