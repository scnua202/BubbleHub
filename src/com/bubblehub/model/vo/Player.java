package com.bubblehub.model.vo;

import com.bubblehub.CutImg;
import com.bubblehub.model.manager.ElementManager;
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
    // 蓝量
    private int mp;
    // 技能点
    private int sp;
    // 分数
    private int score;
    // 贴图
    private ImageIcon imageIcon;

    // 攻击状态
    private boolean pk;

    // 移动方向，分为上下、左右两组
    private MoveEnum UDMove;
    private MoveEnum LRMove;

    // 记录图片左上角像素坐标
    private CutImg cutImg;

    public Player(int x, int y, int w, int h, ImageIcon imageIcon) {
        super(x, y, w, h);
        this.imageIcon = imageIcon;
        setHp(0);
        setMp(0);
        setSp(0);
        setScore(0);
        setPk(false);
        this.cutImg = new CutImg(60,60,0,1);
        cutImg.changeImg(0,0);

        // 初始为静止状态
        UDMove = MoveEnum.stop;
        LRMove = MoveEnum.stop;
    }

    // 自定义player创建方法
    public static Player createPlayer(String s) {
        int x = 400;
        int y = 500;
        int w = 50;
        int h = 50;
        String url = "resources/img/play/4.png";
        return new Player(x,y,w,h,new ImageIcon(url));
    }

    // player移动方法
    public void move() {
        // 上下边界判定
        // 上下移动
        switch (UDMove) {
            case top:
                if (getY()<0) {
                    setY(0);
                } else {
                    setY(getY()-1);
                }
                break;
            case down:
                if (getY()+getH()>HEIGHT) {
                    setY(HEIGHT-getH());
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
                    setX(0);
                } else {
                    setX(getX()-1);
                }
                break;
            case right:
                if (getX()+getW()>WIDTH) {
                    setX(WIDTH-getW());
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
    public void addFire() {
        // 如果player不在开火状态，则直接退出
        if (!isPk()) {
            return;
        }
        List<SuperElement> list = ElementManager.getElementManager().getElementList("PlayerFire");
        // 如果ElementManager中没有PlayerFire，则新建一个
        if (list == null) {
            list = new ArrayList<>();
        }
        // 添加子弹
//        list.add(PlayerFire.createPlayerFire(getX(),getY(),null));
//        ElementManager.getElementManager().getMap().put("PlayerFire",list);

        // 每一次按键只能放一颗子弹
        setPk(false);
    }

    @Override
    public void update() {
        // 如果有super，就是在父类update方法上追加
        super.update();
        addFire();
    }

    @Override
    public void showElement(Graphics g) {
//        move();
        // 画一整张完整的图
//        g.drawImage(imageIcon.getImage(), getX(), getY(), getW(), getH(), null);
        // 截取图片一部分
        g.drawImage(imageIcon.getImage(),
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

    }

    // Auto Generate
    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    public int getSp() {
        return sp;
    }

    public void setSp(int sp) {
        this.sp = sp;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    public void setImageIcon(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
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
}
