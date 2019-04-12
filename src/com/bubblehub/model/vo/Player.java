package com.bubblehub.model.vo;


import com.bubblehub.model.loader.ElementLoader;
import com.bubblehub.model.manager.ElementManager;
import utils.CutImg;
import utils.MoveEnum;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Fisher
 * @Date 2019/4/9 15:31
 *
 * package vo
 **/

public class Player extends SuperElement{

    // 血量
    private int hp;
    // 键盘类型
    private int playType;
    // 能扔炸弹数量
    private int bombNum;
    // 攻击力
    private int power;
    // 分数
    private int score;
    // 攻击状态
    private boolean pk;

    // 移动方向，分为上下、左右两组
    private MoveEnum UDMove;
    private MoveEnum LRMove;

    // 记录图片左上角像素坐标
    private CutImg cutImg;

    public Player(int x, int y, int mapX, int mapY, String url) {
        super(x, y, mapX, mapY, "Player", url);
        setHp(Integer.parseInt(ElementLoader.getElementLoader().getElementConfig("PlayerLife")));
        setPlayType(Integer.parseInt(ElementLoader.getElementLoader().getElementConfig("PlayerPlayType")));
        setBombNum(Integer.parseInt(ElementLoader.getElementLoader().getElementConfig("PlayerBombNum")));
        setPower(Integer.parseInt(ElementLoader.getElementLoader().getElementConfig("PlayerPower")));
        if (Boolean.parseBoolean(ElementLoader.getElementLoader().getElementConfig("PlayerCut"))) {
            this.cutImg = new CutImg(getImg(),
                    Integer.parseInt(ElementLoader.getElementLoader().getElementConfig("PlayerCutX")),
                    Integer.parseInt(ElementLoader.getElementLoader().getElementConfig("PlayerCutY")));
        }

        // 初始为静止状态
        setScore(0);
        setPk(false);
        setUDMove(MoveEnum.stop);
        setLRMove(MoveEnum.stop);
    }

    // 自定义player创建方法
    public static Player createPlayer(int x,int y,int mapX,int mapY,String url) {
        return new Player(x,y,mapX,mapY,url);
    }

    // player移动方法
    public void move() {
        // 上下边界判定
        // 上下移动
        switch (UDMove) {
            case top:
                if (getY()<0) {
                    break;
                } else {
                    setY(getY()-1);
                }
                break;
            case down:
                if (getY()+getH()>HEIGHT) {
                    break;
                } else {
                    setY(getY()+1);
                }
                break;
            case stop:
                break;
            default:
                break;
        }

        // 左右边界判定
        // 左右移动
        switch (LRMove) {
            case left:
                if (getX()<0) {
                    break;
                } else {
                    setX(getX()-1);
                }
                break;
            case right:
                if (getX()+getW()>WIDTH) {
                    break;
                } else {
                    setX(getX()+1);
                }
                break;
            case stop:
                break;
            default:
                break;
        }
    }

    // player开火方法
    public void plantBomb() {
        // 如果处于攻击状态且可放置的炸弹数量不为0，才允许设置炸弹
        if (isPk() && getBombNum()>0) {


        }
    }

    @Override
    public void update() {
        // 如果有super，就是在父类update方法上追加
        super.update();
        this.plantBomb();
        this.destroy();
    }

    @Override
    public void showElement(Graphics g) {
//        move();
        // 画一整张完整的图
//        g.drawImage(imageIcon.getImage(), getX(), getY(), getW(), getH(), null);
        // 截取图片一部分
        g.drawImage(getImg().getImage(),
                getX(),getY(),                          //图片输出左上角坐标
                getX()+getW(),getY()+getH(),  //图片输出右下角坐标
                cutImg.getTopX(),cutImg.getTopY(),//截取的图片的左上角坐标
                cutImg.getBottomX(),cutImg.getBottomY(),//截取的图片的右下角坐标
                null);
        cutImg.setNoY(1-cutImg.getNoY());
        cutImg.changeImg(0, cutImg.getNoY());
    }

    @Override
    public void destroy() {
        // 如果没血了就判定为死亡状态
        if (getHp()<=0) {
            setVisible(false);
        }
    }

    // Auto Generate
    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public MoveEnum getUDMove() {
        return UDMove;
    }

    public void setUDMove(MoveEnum UDMove) {
        this.UDMove = UDMove;
    }

    public MoveEnum getLRMove() {
        return LRMove;
    }

    public void setLRMove(MoveEnum LRMove) {
        this.LRMove = LRMove;
    }

    public boolean isPk() {
        return pk;
    }

    public void setPk(boolean pk) {
        this.pk = pk;
    }

    public int getPlayType() {
        return playType;
    }

    public void setPlayType(int playType) {
        this.playType = playType;
    }

    public int getBombNum() {
        return bombNum;
    }

    public void setBombNum(int bombNum) {
        this.bombNum = bombNum;
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
