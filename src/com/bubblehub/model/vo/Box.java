package com.bubblehub.model.vo;

import com.bubblehub.model.factory.ElementFactory;
import com.bubblehub.model.loader.ElementLoader;
import com.bubblehub.model.manager.ElementManager;
import utils.CutImg;

import java.awt.*;
import java.util.List;
import java.util.Random;

/**
 * @Author Fisher
 * @Date 2019/4/15 18:21
 **/


public class Box extends SuperElement {

    // 记录图片左上角像素坐标
    private CutImg cutImg;

    // 随机产生道具
    private Random random;

    public Box() {
    }

    public Box(int MapCol, int MapRow, String url) {
        super(MapCol, MapRow, "Box", url);
        if (Boolean.parseBoolean(ElementLoader.getElementLoader().getElementConfig("BoxCut"))) {
            this.cutImg = new CutImg(getImg(),
                    Integer.parseInt(ElementLoader.getElementLoader().getElementConfig("BoxCutX")),
                    Integer.parseInt(ElementLoader.getElementLoader().getElementConfig("BoxCutY")));
        }
        random = new Random();
        cutImg.setNoX(1);
        setName("Box");
    }

    public static Box createBox(int MapX, int MapY, String url) {
        return new Box(MapX,MapY,url);
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
        int[][] gameMap = ElementManager.getElementManager().getPosition();
        if (gameMap[calcGrid.getRow()][calcGrid.getCol()] != 2) {
            this.destroy();
        }
    }

    @Override
    public void destroy() {
        // 随机产生道具
        if (random.nextBoolean()) {
            int[][] gameMap = ElementManager.getElementManager().getPosition();
            List<SuperElement> list = ElementManager.getElementManager().getElementList("Tool");
            int no = random.nextInt(2);
            SuperElement tool = ElementFactory.eFactory("Tool"+no, getCalcGrid().getRow(), getCalcGrid().getCol());
            list.add(tool);
            gameMap[getCalcGrid().getRow()][getCalcGrid().getCol()] = 5;
        }
        setVisible(false);
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
