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

    public static SuperElement eFactory(String s) {
        switch (s) {
            case "Bomb":
                String urlBomb = ElementLoader.getElementLoader().getElementConfig("BombImgSrc1");
                return Bomb.createBomb(0,0,urlBomb);
            case "BombTrack":
                String urlBombTrack = ElementLoader.getElementLoader().getElementConfig("TrackImgSrc1");
                return BombTrack.createBombTrack(0,0,urlBombTrack);
            case "Wall":
                String urlWall = ElementLoader.getElementLoader().getElementConfig("WallImgSrc1");
                return Wall.createWall(1,1,urlWall);
            case "Player":
                String type = ElementLoader.getElementLoader().getElementConfig("PlayerPlayType");
                String urlPlayer = ElementLoader.getElementLoader().getElementConfig("PlayerImgSrc"+type);
                return Player.createPlayer(0,0,urlPlayer);
            case "Tool":
                String urlTool = ElementLoader.getElementLoader().getElementConfig("ToolImgAttack");
                return Tool.createTool(15,11,urlTool);
            default:
                return new NullElement();
        }
    }
}
