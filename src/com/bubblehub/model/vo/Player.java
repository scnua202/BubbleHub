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
    // 已经扔出的炸弹数量
    private List bombPlanted = new ArrayList();
    // 攻击力
    private int power;
    // 分数
    private int score;
    // 攻击状态
    private boolean pk;

    // 移动方向，分为上下、左右两组
    private MoveEnum Move;

    // 记录图片左上角像素坐标
    private CutImg cutImg;
    // 切换图片的间隔变量
    private int time = 0;
    // 图片轮播的序号
    private int no = 0;

    public Player(int mapX, int mapY, String url) {
        super(mapX, mapY, "Player", url);
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
        setMove(MoveEnum.stop);
    }

    // 自定义player创建方法
    public static Player createPlayer(int mapX,int mapY,String url) {
        return new Player(mapX,mapY,url);
    }

    // player移动方法
    public void move() {

        // 图片切换
        switchImg();

        // 角色格子坐标变化
        calcGrid.parseGrid(getX(),getY());
        setMapCol(calcGrid.getCol());
        setMapRow(calcGrid.getRow());

        // 上下边界判定
        // 上下移动
        switch (Move) {
            case top:
                if (getY()<0) {
                    break;
                } else {
                    setY(getY()-1);
                }
                return;
            case down:
                if (getY()+getH()>getHEIGHT()) {
                    break;
                } else {
                    setY(getY()+1);
                }
                return;
            case left:
                if (getX()<0) {
                    break;
                } else {
                    setX(getX()-1);
                }
                return;
            case right:
                if (getX()+getW()>getWIDTH()) {
                    break;
                } else {
                    setX(getX()+1);
                }
                return;
            case stop:
                break;
            default:
                break;
        }


    }

    private void switchImg() {
        if (time >= getFPS()/8) {
            time = 0;
            no = (no+1)%cutImg.getMaxY();
            switch (getMove()) {
                case top:
                    this.cutImg.setNoX(3);
                    this.cutImg.setNoY(no);
                    break;
                case down:
                    this.cutImg.setNoX(0);
                    this.cutImg.setNoY(no);
                    break;
                case left:
                    this.cutImg.setNoX(1);
                    this.cutImg.setNoY(no);
                    break;
                case right:
                    this.cutImg.setNoX(2);
                    this.cutImg.setNoY(no);
                    break;
                case stop:
                    if (getMove()==MoveEnum.stop) {
                        this.cutImg.setNoY(0);
                    }
                    break;
                default:
                    break;
            }
        } else {
            time++;
        }
    }

    // player开火方法
    public void plantBomb() {
        // 如果处于攻击状态且可放置的炸弹数量不为0，才允许设置炸弹
        if (isPk() && bombPlanted.size()<bombNum) {
            bombPlanted.add(Integer.parseInt(ElementLoader.getElementLoader().getElementConfig("BombExplodeTime")));
            setPk(false);
            List<SuperElement> list = ElementManager.getElementManager().getElementList("Bomb");
            list.add(Bomb.createBomb(getMapCol(),getMapRow(),ElementLoader.getElementLoader().getElementConfig("BombImgSrc1")));
        }
    }

    // 炸弹数量恢复
    public void recoverBomb() {
        if (bombPlanted.size()!=0) {
            for (int i=0;i<bombPlanted.size();i++) {
                int x = (int)bombPlanted.get(i);
                if (x>0) {
                    bombPlanted.set(i,--x);
                } else {
                    bombPlanted.remove(i);
                }
            }
        }
    }

    @Override
    public void update() {
        // 如果有super，就是在父类update方法上追加
        super.update();
        this.plantBomb();
        this.recoverBomb();
        this.destroy();
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

    public MoveEnum getMove() {
        return Move;
    }

    public void setMove(MoveEnum move) {
        Move = move;
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
