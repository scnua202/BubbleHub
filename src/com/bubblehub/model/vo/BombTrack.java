package com.bubblehub.model.vo;


import com.bubblehub.model.loader.ElementLoader;
import com.bubblehub.model.manager.ElementManager;
import utils.CutImg;
import utils.MoveEnum;

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

    public BombTrack(int mapX, int mapY, String url) {
        super(mapX, mapY, "BombTrack", url);
        setKeepTime(Integer.parseInt(ElementLoader.getElementLoader().getElementConfig("TrackKeepTime")));
        if (Boolean.parseBoolean(ElementLoader.getElementLoader().getElementConfig("BombTrackCut"))) {
            this.cutImg = new CutImg(getImg(),
                    Integer.parseInt(ElementLoader.getElementLoader().getElementConfig("BombTrackCutX")),
                    Integer.parseInt(ElementLoader.getElementLoader().getElementConfig("BombTrackCutY")));
        }
        setName("BombTrack");
    }

    public static BombTrack createBombTrack(int mapX,int mapY,String url) {
        return new BombTrack(mapX,mapY,url);
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

    public void setDirection(MoveEnum moveEnum, boolean isEnd, int i) {
        switch (moveEnum) {
            case top:
                setY(getY()-i*getH());
                cutImg.setNoX(0);
                break;
            case down:
                setY(getY()+i*getH());
                cutImg.setNoX(1);
                break;
            case left:
                setX(getX()-i*getW());
                cutImg.setNoX(2);
                break;
            case right:
                setX(getX()+i*getW());
                cutImg.setNoX(3);
            default:
                break;
        }
        if (isEnd) {
            cutImg.setNoY(0);
        } else {
            cutImg.setNoY(1);
        }
    }

    @Override
    public void move() {
        keepTime--;
    }

    @Override
    public void destroy() {
        if (keepTime < 0) {
            calcGrid.parseGrid(getX(),getY());
            int[][] gameMap = ElementManager.getElementManager().getPosition();
            if (getCalcGrid().getRow()>=0 && getCalcGrid().getRow()<12) {
                if (getCalcGrid().getCol()>=0 && getCalcGrid().getCol()<16) {
                    gameMap[getCalcGrid().getRow()][getCalcGrid().getCol()] = 0;
                }
            }
            setVisible(false);
        }
    }

    @Override
    public void update() {
        super.update();
        this.destroy();
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
