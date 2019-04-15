package com.bubblehub.model.vo;

import com.bubblehub.model.loader.ElementLoader;
import com.bubblehub.model.manager.ElementManager;
import com.bubblehub.model.vo.SuperElement;
import utils.CutImg;
import utils.MoveEnum;
import utils.ToolType;

import java.awt.*;

/**
 * @Author Fisher
 * @Date 2019/4/11 22:35
 **/


public class Tool extends SuperElement {

    private CutImg cutImg;

    private ToolType toolType;

    private boolean picked;

    private int time = 0;

    private int no =0;

    public Tool() {
        super();
    }

    public Tool(int mapX, int mapY, String url) {
        super(mapX, mapY, "Tool", url);
        if (Boolean.parseBoolean(ElementLoader.getElementLoader().getElementConfig("ToolCut"))) {
            this.cutImg = new CutImg(getImg(),
                    Integer.parseInt(ElementLoader.getElementLoader().getElementConfig("ToolCutX")),
                    Integer.parseInt(ElementLoader.getElementLoader().getElementConfig("ToolCutY")));
        }
        this.toolType = ToolType.attack;
        setPicked(false);
        setName("Tool");
    }

    public static Tool createTool(int mapX, int mapY, String url) {
        return new Tool(mapX,mapY,url);
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
        if (time >= getFPS()/4) {
            time = 0;
            no = (no+1)%cutImg.getMaxY();
            this.cutImg.setNoY(no);
        } else {
            time++;
        }

    }

    @Override
    public void destroy() {
        if (isPicked()) {
            int[][] gameMap = ElementManager.getElementManager().getPosition();
            gameMap[calcGrid.getRow()][calcGrid.getCol()] = 0;
            setVisible(false);
        }
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

    public ToolType getToolType() {
        return toolType;
    }

    public void setToolType(ToolType toolType) {
        this.toolType = toolType;
    }

    public boolean isPicked() {
        return picked;
    }

    public void setPicked(boolean picked) {
        this.picked = picked;
    }
}
