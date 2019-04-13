package com.bubblehub.model.vo;

import com.bubblehub.model.loader.ElementLoader;
import com.bubblehub.model.manager.ElementManager;
import utils.CutImg;
import utils.MoveEnum;

import java.awt.*;
import java.util.List;

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
    // 切换图片的间隔变量
    private int time = 0;
    // 图片轮播的序号
    private int no = 0;

    public Bomb() {
        super();
    }

    public Bomb(int mapX, int mapY, String url) {
        super(mapX, mapY, "Bomb", url);
        setExplodeTime(Integer.parseInt(ElementLoader.getElementLoader().getElementConfig("BombExplodeTime")));
        if (Boolean.parseBoolean(ElementLoader.getElementLoader().getElementConfig("BombCut"))) {
            this.cutImg = new CutImg(getImg(),
                    Integer.parseInt(ElementLoader.getElementLoader().getElementConfig("BombCutX")),
                    Integer.parseInt(ElementLoader.getElementLoader().getElementConfig("BombCutY")));
        }
        setPower(5);
        setName("Bomb");
    }

    public static Bomb createBomb(int mapX,int mapY,String url) {
        return new Bomb(mapX,mapY,url);
    }

    @Override
    public void showElement(Graphics g) {
//        System.out.println("x:"+getX()+"y:"+getY());
//        System.out.println("w:"+getW()+"h:"+getH());
//        System.out.println("topx:"+cutImg.getTopX()+"topY:"+cutImg.getTopY());
//        System.out.println("Bottomx:"+cutImg.getBottomX()+"BottomY:"+cutImg.getBottomY());
        // 截取图片一部分
        g.drawImage(getImg().getImage(),
                getX(),getY(),                          //图片输出左上角坐标
                getX()+getW(),getY()+getH(),  //图片输出右下角坐标
                cutImg.getTopX(),cutImg.getTopY(),//截取的图片的左上角坐标
                cutImg.getBottomX(),cutImg.getBottomY(),//截取的图片的右下角坐标
                null);
    }

    // Bomb的移动方法就是图片的轮播，爆炸时间的缩短
    @Override
    public void move() {
        explodeTime--;
        // 图片轮播
        if (time >= getFPS()/8) {
            time = 0;
            no = (no+1)%cutImg.getMaxY();
            cutImg.setNoY(no);
        } else {
            time++;
        }
    }

    @Override
    public void destroy() {
        if (explodeTime<0) {
            MoveEnum[] direction = {MoveEnum.top, MoveEnum.down, MoveEnum.left, MoveEnum.right};
            boolean[] needShow = {true,true,true,true};
            List<SuperElement> list = ElementManager.getElementManager().getElementList("BombTrack");
            // 炸弹中心爆炸效果
            list.add(BombTrack.createBombTrack(getMapCol(),getMapRow(),ElementLoader.getElementLoader().getElementConfig("TrackImgSrc2")));
            // 炸弹轨迹
            for (int i=0; i<power; i++) {
                for (int j=0; j<4; j++) {
                    Boolean isEnd = false;
                    if (i == power-1) {
                        isEnd = true;
                    }
                    if (!needShow[j]) {
                        continue;
                    } else {
                        if (canIDisplay(i,direction[j])) {
                            BombTrack track = BombTrack.createBombTrack(getMapCol(),getMapRow(),ElementLoader.getElementLoader().getElementConfig("TrackImgSrc1"));
                            track.setDirection(direction[j], isEnd, i);
                            list.add(track);
                        } else {
                            needShow[j] = false;
                        }
                    }

                }
            }
            setVisible(false);
        }
    }

    // 判断轨迹是否需要显示（是否撞到墙了）
    private boolean canIDisplay(int i, MoveEnum direction) {
        List<SuperElement> list = ElementManager.getElementManager().getElementList("Wall");
        for (SuperElement x:list) {
            if (x.getMapCol()==this.getMapCol()) {
                switch (direction) {
                    case top:
                        if (x.getMapRow()+i==this.getMapRow()) {
                            // 如果墙可以炸坏
                            if (x.isThroughAble()) {
                                x.setVisible(false);
                            }
                            return false;
                        } else {
                            return true;
                        }
                    case down:
                        if (x.getMapRow()-i==this.getMapRow()) {
                            if (x.isThroughAble()) {
                                x.setVisible(false);
                            }
                            return false;
                        } else {
                            return true;
                        }
                }
            }
            if (x.getMapRow()==this.getMapRow()) {
                switch (direction) {
                    case left:
                        if (x.getMapCol()+i==this.getMapCol()) {
                            if (x.isThroughAble()) {
                                x.setVisible(false);
                            }
                            return false;
                        } else {
                            return true;
                        }
                    case right:
                        if (x.getMapCol()-i==this.getMapCol()) {
                            if (x.isThroughAble()) {
                                x.setVisible(false);
                            }
                            return false;
                        } else {
                            return true;
                        }
                }
            }
        }
        return true;
    }

    @Override
    public void update() {
        super.update();
        this.destroy();
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
