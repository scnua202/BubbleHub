package com.bubblehub.thread;

import com.bubblehub.model.manager.ElementManager;
import com.bubblehub.model.vo.Player;
import utils.MoveEnum;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

/**
 * @Author Fisher
 * @Date 2019/4/9 16:00
 *
 * package manager
 **/


/**
 * 键盘按键键位代码：
 *      W -> 87
 *      A -> 83
 *      S -> 65
 *      D -> 68
 *      空格 -> 32
 *
 *      方向键上 -> 38
 *      方向键下 -> 40
 *      方向键左 -> 37
 *      方向键右 -> 39
 *      "/" -> 47
 */

public class GameListener implements KeyListener {

    // 主角对象List
    private List<?> list;

    // 键盘按下
    @Override
    public void keyPressed(KeyEvent e) {
//        System.out.println("keyPressed: " + e.getKeyCode());
//        list = ElementManager.getElementManager().getElementList("Player");
//        Player player = (Player)list.get(0);
        Player player = null;

        this.playerPressOperate(e, player);
    }

    // 键盘松开
    @Override
    public void keyReleased(KeyEvent e) {
//        System.out.println("keyReleased: " + e.getKeyCode());
//        list = ElementManager.getElementManager().getElementList("Player");
//        Player player = (Player)list.get(0);
        Player player = null;

        this.playerReleaseOperate(e, player);
    }

    // 键盘字母区输入
    @Override
    public void keyTyped(KeyEvent e) {
//        System.out.println("keyTyped: " + e.getKeyCode());
    }

    // 用户方向操作
    private void playerPressOperate(KeyEvent e, Player player) {
//        System.out.println("Player operate: " + e.getKeyCode());
        // 上下移动(解决相反的两个方向键同时按不停止)
        if (player == null) {
            return;
        } else {
            switch (e.getKeyCode()) {
                // 玩家1WASD移动
                case 87:
                    if (player.getUDMove() == MoveEnum.down) {
                        player.setUDMove(MoveEnum.stop);
                    } else {
                        player.setUDMove(MoveEnum.top);
                    }
                    break;
                case 83:
                    if (player.getUDMove() == MoveEnum.top) {
                        player.setUDMove(MoveEnum.stop);
                    } else {
                        player.setUDMove(MoveEnum.down);
                    }
                    break;
                case 65:
                    player.setLRMove(MoveEnum.left);
                    break;
                case 68:
                    player.setLRMove(MoveEnum.right);
                    break;
                case 32:
                    player.setPk(true);
                    break;
                // 玩家2小键盘方向键移动
                case 38:
                    player.setUDMove(MoveEnum.top);
                    break;
                case 40:
                    player.setUDMove(MoveEnum.down);
                    break;
                case 37:
                    player.setLRMove(MoveEnum.left);
                    break;
                case 39:
                    player.setLRMove(MoveEnum.right);
                    break;
                case 47:
                    player.setPk(true);
                    break;
                default:
                    break;
            }
        }
    }

    public void playerReleaseOperate(KeyEvent e, Player player) {
        // 同时按住2个键，松开一个键之后不停顿的解决方法
        if (player == null) {
            return;
        } else {
            switch (e.getKeyCode()) {
                // 玩家1WASD移动
                case 87:
                    if (player.getUDMove() == MoveEnum.top)
                        player.setUDMove(MoveEnum.stop);
                    break;
                case 83:
                    if (player.getUDMove() == MoveEnum.down)
                        player.setUDMove(MoveEnum.stop);
                    break;
                case 65:
                    if (player.getUDMove() == MoveEnum.left)
                        player.setUDMove(MoveEnum.stop);
                    break;
                case 68:
                    if (player.getUDMove() == MoveEnum.right)
                        player.setUDMove(MoveEnum.stop);
                    break;
                case 32:
                    player.setPk(false);
                    break;
                // 玩家2小键盘移动
                case 38:
                    if (player.getUDMove() == MoveEnum.top)
                        player.setUDMove(MoveEnum.stop);
                    break;
                case 40:
                    if (player.getUDMove() == MoveEnum.down)
                        player.setUDMove(MoveEnum.stop);
                    break;
                case 37:
                    if (player.getLRMove() == MoveEnum.left)
                        player.setLRMove(MoveEnum.stop);
                    break;
                case 39:
                    if (player.getLRMove() == MoveEnum.right)
                        player.setLRMove(MoveEnum.stop);
                    break;
                case 47:
                    player.setPk(false);
                    break;
                default:
                    break;
            }
        }
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

}
