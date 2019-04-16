package com.bubblehub.model.vo;

import com.alibaba.fastjson.JSON;
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
        setPower(2);
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
        int[][] gameMap = ElementManager.getElementManager().getPosition();
        if (explodeTime<0) {
            MoveEnum[] direction = {MoveEnum.top, MoveEnum.down, MoveEnum.left, MoveEnum.right};
            boolean[] needShow = {true,true,true,true};
            List<SuperElement> list = ElementManager.getElementManager().getElementList("BombTrack");
            // 炸弹中心爆炸效果
            list.add(BombTrack.createBombTrack(getCalcGrid().getCol(),getCalcGrid().getRow(),ElementLoader.getElementLoader().getElementConfig("TrackImgSrc2")));
            // 炸弹轨迹
            for (int i=1; i<power; i++) {
                Boolean isEnd = false;
                for (int j=0; j<4; j++) {
                    if (i == power-1) {
                        isEnd = true;
                    }
                    if (needShow[j]){
                        if (canIDisplay(i,direction[j])) {
                            BombTrack track = BombTrack.createBombTrack(getCalcGrid().getCol(),getCalcGrid().getRow(),ElementLoader.getElementLoader().getElementConfig("TrackImgSrc1"));
                            track.setDirection(direction[j], isEnd, i);
                            list.add(track);
                            switch (direction[j]) {
                                case top:
                                    if (getCalcGrid().getRow()-i>=0) {
                                        gameMap[getCalcGrid().getRow()-i][getCalcGrid().getCol()] = 4;
                                    }
                                    break;
                                case down:
                                    if (getCalcGrid().getRow()+i<=11) {
                                        gameMap[getCalcGrid().getRow()+i][getCalcGrid().getCol()] = 4;
                                    }
                                    break;
                                case left:
                                    if (getCalcGrid().getCol()-i>=0) {
                                        gameMap[getCalcGrid().getRow()][getCalcGrid().getCol()-i] = 4;
                                    }
                                    break;
                                case right:
                                    if (getCalcGrid().getCol()+i<=15) {
                                        gameMap[getCalcGrid().getRow()][getCalcGrid().getCol()+i] = 4;
                                    }
                                    break;
                            }
                        } else {
                            needShow[j] = false;
                        }
                    }

                }
            }
            setVisible(false);
        }
        gameMap[getCalcGrid().getRow()][getCalcGrid().getCol()]=0;
    }

    // 判断轨迹是否需要显示（是否撞到墙了）
    private boolean canIDisplay(int i, MoveEnum direction) {
        int[][] gameMap = ElementManager.getElementManager().getPosition();
        int bombX = getCalcGrid().getRow();
        int bombY = getCalcGrid().getCol();
        switch (direction){
            case top:
                if (bombX-i >= 0) {
                    if (gameMap[bombX-i][bombY]==0) return true;
                    else if (gameMap[bombX-i][bombY]==2) {
                        gameMap[bombX-i][bombY]=0;
                        return false;
                    }
                    else return false;
                }
            case down:
                if (bombX+i <= 11) {
                    if (gameMap[bombX+i][bombY]==0) return true;
                    else if (gameMap[bombX+i][bombY]==2) {
                        gameMap[bombX+i][bombY]=0;
                        return false;
                    }
                    else return false;
                }
            case left:
                if (bombY-i >= 0) {
                    if (gameMap[bombX][bombY-i]==0) return true;
                    else if (gameMap[bombX][bombY-i]==2) {
                        gameMap[bombX][bombY-i]=0;
                        return false;
                    }
                    else return false;
                }
            case right:
                if (bombY+i <= 15) {
                    if (gameMap[bombX][bombY+i]==0) return true;
                    else if (gameMap[bombX][bombY+i]==2) {
                        gameMap[bombX][bombY+i]=0;
                        return false;
                    }
                    else return false;
                }
            default:
                return false;
        }
//        List<SuperElement> list = ElementManager.getElementManager().getElementList("Wall");
//        for (SuperElement x:list) {
//            if (x.getCalcGrid().getCol()==this.getCalcGrid().getCol()) {
//                switch (direction) {
//                    case top:
//                        if (x.getCalcGrid().getRow()+i==this.getCalcGrid().getRow()) {
//                            // 如果墙可以炸坏
//                            if (x.isThroughAble()) {
//                                x.setVisible(false);
//                            }
//                            return false;
//                        }
//                    case down:
//                        if (x.getCalcGrid().getRow()-i==this.getCalcGrid().getRow()) {
//                            if (x.isThroughAble()) {
//                                x.setVisible(false);
//                            }
//                            return false;
//                        }
//                }
//            }
//            if (x.getCalcGrid().getRow()==this.getCalcGrid().getRow()) {
//                switch (direction) {
//                    case left:
//                        if (x.getCalcGrid().getCol()+i==this.getCalcGrid().getCol()) {
//                            if (x.isThroughAble()) {
//                                x.setVisible(false);
//                            }
//                            return false;
//                        }
//                    case right:
//                        if (x.getCalcGrid().getCol()-i==this.getCalcGrid().getCol()) {
//                            if (x.isThroughAble()) {
//                                x.setVisible(false);
//                            }
//                            return false;
//                        }
//                }
//            }
//        }
//        return true;
    }

    @Override
    public void update() {
        super.update();
        this.destroy();
    }

    @Override
    public String toString() {
        DataPackage dataPackage = new DataPackage(2, getIndex(), calcGrid.getRow(), calcGrid.getCol());
        return JSON.toJSONString(dataPackage);
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
