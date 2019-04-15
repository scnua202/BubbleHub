package com.bubblehub.model.vo;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bubblehub.model.factory.ElementFactory;
import com.bubblehub.model.loader.ElementLoader;
import com.bubblehub.model.manager.ElementManager;
import utils.CutImg;
import utils.MoveEnum;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    private int time1 = 0;

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
        setName("Player");
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
//        calcGrid.parseGrid(getX(),getY());
//        setMapCol(calcGrid.getCol());
//        setMapRow(calcGrid.getRow());
        calcGrid.parsePixel();
        setX(calcGrid.getX());
        setY(calcGrid.getY());

        if (!canIMove()) {
            return;
        }

        if (time1 >= getFPS()/4) {
            time1 = 0;
        } else {
            time1++;
            return;
        }

        // 上下边界判定
        // 上下移动
        switch (Move) {
            case top:
                if (calcGrid.getRow()<=0) {
                    break;
                } else {
                    calcGrid.setRow(calcGrid.getRow()-1);
                }
                return;
            case down:
                if (calcGrid.getRow()>=11) {
                    break;
                } else {
                    calcGrid.setRow(calcGrid.getRow()+1);
                }
                return;
            case left:
                if (calcGrid.getCol()<=0) {
                    break;
                } else {
                    calcGrid.setCol(calcGrid.getCol()-1);
                }
                return;
            case right:
                if (calcGrid.getCol()>=15) {
                    break;
                } else {
                    calcGrid.setCol(calcGrid.getCol()+1);
                }
                return;
            case stop:
                break;
            default:
                break;
        }
    }

    // 角色方向发生变化时，图片的切换
    private void switchImg() {
        if (time >= getFPS()/4) {
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

    // 判断人物能否向目标方向移动
    private boolean canIMove() {
        //初始化地图数组，有东西为1
        List<SuperElement> list = ElementManager.getElementManager().getElementList("Bomb");
        int[][] gameMap = ElementManager.getElementManager().getPosition();
        for (SuperElement x:list) {
            gameMap[x.getCalcGrid().getRow()][x.getCalcGrid().getCol()] = 3;
        }
        //玩家的坐标
        int playerX = this.calcGrid.getRow();
        int playerY = this.calcGrid.getCol();
        switch (Move){
            case top:
                if (playerX-1 >= 0) {
                    if (gameMap[playerX-1][playerY]==0 || gameMap[playerX-1][playerY]>=4) return true;
                    else return false;
                }
            case down:
                if (playerX+1 <= 11) {
                    if (gameMap[playerX+1][playerY]==0 || gameMap[playerX+1][playerY]>=4) return true;
                    else return false;
                }
            case left:
                if (playerY-1 >= 0) {
                    if (gameMap[playerX][playerY-1]==0 || gameMap[playerX][playerY-1]>=4) return true;
                    else return false;
                }
            case right:
                if (playerY+1 <= 15) {
                    if (gameMap[playerX][playerY+1]==0 || gameMap[playerX][playerY+1]>=4) return true;
                    else return false;
                }
            case stop:
                return false;
            default:
                return false;
        }
    }

    // player开火方法
    public void plantBomb() {
        // 如果处于攻击状态且可放置的炸弹数量不为0，才允许设置炸弹
        if (isPk() && bombPlanted.size()<bombNum) {
            bombPlanted.add(Integer.parseInt(ElementLoader.getElementLoader().getElementConfig("BombExplodeTime")));
            setPk(false);
            List<SuperElement> list = ElementManager.getElementManager().getElementList("Bomb");
            SuperElement bomb = ElementFactory.eFactory("Bomb",getCalcGrid().getRow(),getCalcGrid().getCol());
            ((Bomb) bomb).setPower(getPower());
            list.add(bomb);
            int[][] gameMap = ElementManager.getElementManager().getPosition();
            gameMap[getCalcGrid().getRow()][getCalcGrid().getCol()] = 3;
        }
    }

    // 炸弹数量恢复
    public void recoverBomb() {
        if (bombPlanted.size()!=0) {
            for (int i=bombPlanted.size()-1;i>=0;i--) {
                int x = (int)bombPlanted.get(i);
                if (x>0) {
                    bombPlanted.set(i,--x);
                } else {
                    bombPlanted.remove(i);
                }
            }
        }
    }

    // 对于人物的碰撞事件
    public void gameControl() {
        int[][] gameMap = ElementManager.getElementManager().getPosition();
//        for (int i=0; i<12; i++) {
//            for (int j=0; j<16; j++) {
//                System.out.print(gameMap[i][j] + " ");
//            }
//            System.out.println();
//        }
//        System.out.println("x: " + getCalcGrid().getRow() + " y:" + getCalcGrid().getCol());
//        System.out.println("------------------------------------");
        // 对于BombTrack的碰撞事件
        if (gameMap[getCalcGrid().getRow()][getCalcGrid().getCol()] == 4) {
            System.out.println("hp--");
            this.hp--;
        }
//        if (gameMap[getCalcGrid().getRow()][getCalcGrid().getCol()] == 3) {
//            this.hp--;
//        }
//        List<SuperElement> list = ElementManager.getElementManager().getElementList("BombTrack");
//        for (SuperElement x:list) {
//            if (x.getCalcGrid().getRow() == this.getCalcGrid().getRow()) {
//                if (x.getCalcGrid().getCol() == this.getCalcGrid().getCol()) {
//                    // 如果人物位置正好在bombTrack上，则生命值-1
//                    this.hp--;
//                }
//            }
//        }

        // 对于Tool的碰撞事件
        List<SuperElement> list1 = ElementManager.getElementManager().getElementList("Tool");
        if (gameMap[getCalcGrid().getRow()][getCalcGrid().getCol()] == 5) {
            for (SuperElement x:list1) {
                if (x.getCalcGrid().getRow()==getCalcGrid().getRow() && x.getCalcGrid().getCol()==getCalcGrid().getCol()) {
                    this.addBuff(x);
                    x.destroy();
                    x.setVisible(false);
                }
            }
        }

//        for (SuperElement x:list1) {
//            if (x.getCalcGrid().getRow() == this.getCalcGrid().getRow()) {
//                if (x.getCalcGrid().getCol() == this.getCalcGrid().getCol()) {
//                    // 如果人物位置正好在tool上，人物增加相应buff
//                    this.addBuff(x);
//                    // 道具消失
//                    x.setVisible(false);
//                }
//            }
//        }
    }

    // 人物拾取道具之后添加buff
    private void addBuff(SuperElement s) {
        if (s instanceof Tool) {
            switch(((Tool) s).getToolType()) {
                case life:
                    System.out.println("life++");
                    this.hp++;
                    break;
                case attack:
                    System.out.println("power++");
                    this.power++;
                    break;
                case dontMove:
                    System.out.println("dontmove");
                    this.setMoveAble(false);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void update() {
        // 如果有super，就是在父类update方法上追加
        super.update();
        this.gameControl();
        this.destroy();
        this.plantBomb();
        this.recoverBomb();
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

    @Override
    public String toString() {
        DataPackage dataPackage = new DataPackage(3, getIndex(), calcGrid.getRow(), calcGrid.getCol());
        String s = JSON.toJSONString(dataPackage);
        return s;
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
