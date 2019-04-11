package com.bubblehub.model.vo;

import java.awt.*;

/**
 * @Author Fisher
 * @Date 2019/4/11 21:29
 **/

/**
 * 空元素类，用于工厂返回不能识别的指令
 */
public class NullElement extends SuperElement {
    public NullElement() {
        super();
    }

    public NullElement(int x, int y, int mapX, int mapY, String name, String url) {
        super(x, y, mapX, mapY, name, url);
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
}
