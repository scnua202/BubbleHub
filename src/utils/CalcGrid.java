package utils;

/**
 * @Author Fisher
 * @Date 2019/4/12 20:42
 **/

import com.bubblehub.model.loader.ElementLoader;

/**
 * 将坐标换算为二维格子
 */
public class CalcGrid {

    // 屏幕分辨率
    private int WIDTH = Integer.parseInt(ElementLoader.getElementLoader().getGlobalConfig("Width"));

    // 屏幕分辨率
    private int HEIGHT = Integer.parseInt(ElementLoader.getElementLoader().getGlobalConfig("Height"));

    // 格子列宽
    private int GRIDCOL = Integer.parseInt(ElementLoader.getElementLoader().getGlobalConfig("GridCol"));

    // 格子行高
    private int GRIDROW = Integer.parseInt(ElementLoader.getElementLoader().getGlobalConfig("GridRow"));

    private int x;

    private int y;

    private int col;

    private int row;

    public CalcGrid() {
    }


    public CalcGrid(int row, int col) {
        this.col = col;
        this.row = row;
        parsePixel(row, col);
    }

    /**
     * 像素点转格子
     * @param x
     * @param y
     */
    public void parseGrid(int x, int y) {
        this.x = x;
        this.y = y;
        col = x/GRIDCOL;
        row = y/GRIDROW;
    }

    public void parseGrid() {
        col = x/GRIDCOL;
        row = y/GRIDROW;
    }

    /**
     * 格子转像素点
     * @param row
     * @param col
     */
    public void parsePixel(int row, int col) {
        x = col*GRIDCOL;
        y = row*GRIDROW;
    }

    public void parsePixel() {
        x = col*GRIDCOL;
        y = row*GRIDROW;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public void setWIDTH(int WIDTH) {
        this.WIDTH = WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public void setHEIGHT(int HEIGHT) {
        this.HEIGHT = HEIGHT;
    }

    public int getGRIDCOL() {
        return GRIDCOL;
    }

    public void setGRIDCOL(int GRIDCOL) {
        this.GRIDCOL = GRIDCOL;
    }

    public int getGRIDROW() {
        return GRIDROW;
    }

    public void setGRIDROW(int GRIDROW) {
        this.GRIDROW = GRIDROW;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }
}
