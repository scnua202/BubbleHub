package com.bubblehub.model.factory;

/**
 * @Author Fisher
 * @Date 2019/4/11 10:13
 **/

import com.bubblehub.model.loader.ElementLoader;
import com.bubblehub.model.vo.*;

/**
 * 工厂类，负责生产vo对象
 */
public class ElementFactory {

    public static SuperElement eFactory(String s, int col, int row) {
        switch (s) {
            case "Bomb":
                String urlBomb = ElementLoader.getElementLoader().getElementConfig("BombImgSrc1");
                return Bomb.createBomb(row,col,urlBomb);
            case "BombTrack":
                String urlBombTrack = ElementLoader.getElementLoader().getElementConfig("TrackImgSrc1");
                return BombTrack.createBombTrack(row,col,urlBombTrack);
            case "Wall":
                String urlWall = ElementLoader.getElementLoader().getElementConfig("WallImgSrc1");
                return Wall.createWall(row,col,urlWall);
            case "Player":
                String type = ElementLoader.getElementLoader().getElementConfig("PlayerPlayType");
                String urlPlayer = ElementLoader.getElementLoader().getElementConfig("PlayerImgSrc"+type);
                return Player.createPlayer(row,col,urlPlayer);
            case "Tool":
                String urlTool = ElementLoader.getElementLoader().getElementConfig("ToolImgAttack");
                return Tool.createTool(row,col,urlTool);
            default:
                return new NullElement();
        }
    }
}
