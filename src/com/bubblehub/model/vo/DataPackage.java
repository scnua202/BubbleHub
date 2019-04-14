package com.bubblehub.model.vo;

/**
 * @Author Fisher
 * @Date 2019/4/14 15:23
 **/


public class DataPackage {
    private int type;
    private int index;
    private int row;
    private int col;

    public DataPackage(int type, int index, int row, int col) {
        this.type = type;
        this.index = index;
        this.row = row;
        this.col = col;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}
