package com.bubblehub.model.factory;

/**
 * @Author Fisher
 * @Date 2019/4/11 10:13
 **/

import com.bubblehub.model.loader.ElementLoader;
import com.bubblehub.model.vo.*;
import utils.ToolType;

import java.util.Random;

/**
 * 工厂类，负责生产vo对象
 */
public class ElementFactory {

    private static Random r = new Random();

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
                // 随机选择墙体图片
                int no = r.nextInt(3) + 1;
                Wall wall = Wall.createWall(row,col,urlWall);
                wall.getCutImg().setNoY(no);
                return wall;
            case "Box":
                String urlBox = ElementLoader.getElementLoader().getElementConfig("BoxImgSrc");
                Box box = Box.createBox(row,col,urlBox);
                box.getCutImg().setNoY(r.nextInt(3));
                return box;
            case "Player0":
                String type = "1";
                String urlPlayer = ElementLoader.getElementLoader().getElementConfig("PlayerImgSrc"+type);
                Player player = Player.createPlayer(row,col,urlPlayer);
                player.setPlayType(1);
                return player;
            case "Player1":
                String type1 = "2";
                String urlPlayer1 = ElementLoader.getElementLoader().getElementConfig("PlayerImgSrc"+type1);
                Player player1 = Player.createPlayer(row,col,urlPlayer1);
                player1.setPlayType(2);
                return player1;
            case "Tool0":
                String urlTool0 = ElementLoader.getElementLoader().getElementConfig("ToolImgAttack");
                Tool tool0 = Tool.createTool(row,col,urlTool0);
                tool0.setToolType(ToolType.attack);
                return tool0;
            case "Tool1":
                String urlTool1 = ElementLoader.getElementLoader().getElementConfig("ToolImgLife");
                Tool tool1 = Tool.createTool(row,col,urlTool1);
                tool1.setToolType(ToolType.life);
                return tool1;
            case "Tool2":
                String urlTool2 = ElementLoader.getElementLoader().getElementConfig("ToolImgMove");
                Tool tool2 = Tool.createTool(row,col,urlTool2);
                tool2.setToolType(ToolType.dontMove);
                return Tool.createTool(row,col,urlTool2);
            default:
                return new NullElement();
        }
    }
}
