package com.bubblehub.thread;

import com.bubblehub.model.manager.ElementManager;
import com.bubblehub.model.vo.Player;
import com.bubblehub.model.vo.SuperElement;
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
 *      S -> 83
 *      A -> 65
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
        list = ElementManager.getElementManager().getElementList("Player");
        if (list.size() <= 0 ) {
            return;
        } else {
            for (int i=0; i<list.size(); i++) {
                Player player = (Player)list.get(i);
                this.playerPressOperate(e, player);
            }
        }
    }

    // 键盘松开
    @Override
    public void keyReleased(KeyEvent e) {
//        System.out.println("keyReleased: " + e.getKeyCode());
        list = ElementManager.getElementManager().getElementList("Player");
        if (list.size() <= 0) {
            return;
        } else {
            for (int i=0; i<list.size(); i++) {
                Player player = (Player)list.get(i);
                this.playerReleaseOperate(e, player);
            }
        }
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
            if (player.getPlayType() == 1) {
                switch (e.getKeyCode()) {
                    // 玩家1WASD移动
                    case 87:
                        if (player.getMove() == MoveEnum.down) {
                            player.setMove(MoveEnum.stop);
                        } else {
                            player.setMove(MoveEnum.top);
                        }
                        break;
                    case 83:
                        if (player.getMove() == MoveEnum.top) {
                            player.setMove(MoveEnum.stop);
                        } else {
                            player.setMove(MoveEnum.down);
                        }
                        break;
                    case 65:
                        player.setMove(MoveEnum.left);
                        break;
                    case 68:
                        player.setMove(MoveEnum.right);
                        break;
                    case 32:
                        player.setPk(true);
                        break;
                    default:
                        break;
                }
            }
            if (player.getPlayType() == 2) {
                switch (e.getKeyCode()) {
                    // 玩家2小键盘方向键移动
                    case 38:
                        player.setMove(MoveEnum.top);
                        break;
                    case 40:
                        player.setMove(MoveEnum.down);
                        break;
                    case 37:
                        player.setMove(MoveEnum.left);
                        break;
                    case 39:
                        player.setMove(MoveEnum.right);
                        break;
                    case 47:
                        player.setPk(true);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public void playerReleaseOperate(KeyEvent e, Player player) {
        // 同时按住2个键，松开一个键之后不停顿的解决方法
        if (player == null) {
            return;
        } else {
            if (player.getPlayType() == 1) {
                switch (e.getKeyCode()) {
                    // 玩家1WASD移动
                    case 87:
                        if (player.getMove() == MoveEnum.top)
                            player.setMove(MoveEnum.stop);
                        break;
                    case 83:
                        if (player.getMove() == MoveEnum.down)
                            player.setMove(MoveEnum.stop);
                        break;
                    case 65:
                        if (player.getMove() == MoveEnum.left)
                            player.setMove(MoveEnum.stop);
                        break;
                    case 68:
                        if (player.getMove() == MoveEnum.right)
                            player.setMove(MoveEnum.stop);
                        break;
                    case 32:
                        player.setPk(false);
                        break;
                    default:
                        break;
                }
            }
            if (player.getPlayType() == 2) {
                switch (e.getKeyCode()) {
                    // 玩家2小键盘移动
                    case 38:
                        if (player.getMove() == MoveEnum.top)
                            player.setMove(MoveEnum.stop);
                        break;
                    case 40:
                        if (player.getMove() == MoveEnum.down)
                            player.setMove(MoveEnum.stop);
                        break;
                    case 37:
                        if (player.getMove() == MoveEnum.left)
                            player.setMove(MoveEnum.stop);
                        break;
                    case 39:
                        if (player.getMove() == MoveEnum.right)
                            player.setMove(MoveEnum.stop);
                        break;
                    case 47:
                        player.setPk(false);
                        break;
                    default:
                        break;
                }
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
