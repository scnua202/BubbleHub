package utils;

/**
 * @Author Fisher
 * @Date 2019/4/10 14:58
 **/

import javax.swing.*;

/**
 * 图片切割类
 * 负责图片的分割
 * 返回图片坐标
 */
public class CutImg {
    /**
     * 整张图片左上角坐标，默认为0
     */
    private int ImgTopX=0;

    /**
     * 整张图片左下角坐标，默认为0
     */
    private int ImgTopY=0;

    /**
     * 图片的宽度
     */
    private int ImgWidth;

    /**
     * 图片的高度
     */
    private int ImgHeight;

    /**
     * 第XY张小图片的x值，默认为0
     */
    private int NoX=0;

    /**
     * 第XY张小图片的Y值，默认为0
     */
    private int NoY=0;

    /**
     * 图片一共分割为X列
     */
    private int MaxX;

    /**
     * 图片一共分割为Y行
     */
    private int MaxY;

    public CutImg() {
    }

    public CutImg(ImageIcon imageIcon, int maxX, int maxY) {
        ImgWidth = imageIcon.getIconWidth();
        ImgHeight = imageIcon.getIconHeight();
        MaxX = maxX;
        MaxY = maxY;
    }

    /**
     * 设置切换到第XY张图片
     * @return boolean
     */
    public boolean changeImg(int Nox, int Noy) {
        setNoX(Nox);
        setNoY(Noy);
        return true;
    }

    /**
     * 获取XY张图片左上角X坐标
     * @return int
     */
    public int getTopX() {
        return getImgTopX()+(getImgWidth()/getMaxY()*getNoY());
    }

    /**
     * 获取XY张图片左上角Y坐标
     * @return int
     */
    public int getTopY() {
        return getImgTopY()+(getImgHeight()/getMaxX()*getNoX());
    }

    /**
     * 获取XY张图片右下角X坐标
     * @return int
     */
    public int getBottomX() {
        return getImgTopX()+(getImgWidth()/getMaxY()*(getNoY()+1));
    }

    /**
     * 获取XY张图片右下角Y坐标
     * @return
     */
    public int getBottomY() {
        return getImgTopY()+(getImgHeight()/getMaxX()*(getNoX()+1));
    }

    public int getImgTopX() {
        return ImgTopX;
    }

    public void setImgTopX(int imgTopX) {
        ImgTopX = imgTopX;
    }

    public int getImgTopY() {
        return ImgTopY;
    }

    public void setImgTopY(int imgTopY) {
        ImgTopY = imgTopY;
    }

    public int getImgWidth() {
        return ImgWidth;
    }

    public void setImgWidth(int imgWidth) {
        ImgWidth = imgWidth;
    }

    public int getImgHeight() {
        return ImgHeight;
    }

    public void setImgHeight(int imgHeight) {
        ImgHeight = imgHeight;
    }

    public int getNoX() {
        return NoX;
    }

    public void setNoX(int noX) {
        NoX = noX;
    }

    public int getNoY() {
        return NoY;
    }

    public void setNoY(int noY) {
        NoY = noY;
    }

    public int getMaxX() {
        return MaxX;
    }

    public void setMaxX(int maxX) {
        MaxX = maxX;
    }

    public int getMaxY() {
        return MaxY;
    }

    public void setMaxY(int maxY) {
        MaxY = maxY;
    }
}
