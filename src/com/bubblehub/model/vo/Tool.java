package com.bubblehub.model.vo;

import com.bubblehub.model.loader.ElementLoader;
import com.bubblehub.model.vo.SuperElement;
import utils.CutImg;

import java.awt.*;

/**
 * @Author Fisher
 * @Date 2019/4/11 22:35
 **/


public class Tool extends SuperElement {

    private CutImg cutImg;

    public Tool() {
        super();
    }

    public Tool(int x, int y, int mapX, int mapY, String url) {
        super(x, y, mapX, mapY, "Tool", url);
        if (Boolean.parseBoolean(ElementLoader.getElementLoader().getElementConfig("ToolCut"))) {
            this.cutImg = new CutImg(getImg(),
                    Integer.parseInt(ElementLoader.getElementLoader().getElementConfig("ToolCutX")),
                    Integer.parseInt(ElementLoader.getElementLoader().getElementConfig("ToolCutY")));
        }
    }

    public static Tool createTool(int x, int y, int mapX, int mapY, String url) {
        return new Tool(x,y,mapX,mapY,url);
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

    public CutImg getCutImg() {
        return cutImg;
    }

    public void setCutImg(CutImg cutImg) {
        this.cutImg = cutImg;
    }
}
