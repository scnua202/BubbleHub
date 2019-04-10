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
 *      上 -> 38
 *      下 -> 40
 *      左 -> 37
 *      右 -> 39
 *      空格 -> 32
 */

public class GameListener implements KeyListener {

    // 主角对象List
    private List<?> list;

    // 键盘按下
    @Override
    public void keyPressed(KeyEvent e) {
//        System.out.println("keyPressed: " + e.getKeyCode());
        list = ElementManager.getElementManager().getElementList("Player");
        Player player = (Player)list.get(0);

        // 上下移动(解决相反的两个方向键同时按不停止)
        switch (e.getKeyCode()) {
            case 38:
                if (player.getUDMove() == MoveEnum.down) {
                    player.setUDMove(MoveEnum.stop);
                } else {
                    player.setUDMove(MoveEnum.top);
                }
                break;
            case 40:
                if (player.getUDMove() == MoveEnum.top) {
                    player.setUDMove(MoveEnum.stop);
                } else {
                    player.setUDMove(MoveEnum.down);
                }
                break;
            case 37:
                player.setLRMove(MoveEnum.left);
                break;
            case 39:
                player.setLRMove(MoveEnum.right);
                break;
            case 32:
                player.setPk(true);
                break;
            default:
                break;
        }
    }

    // 键盘松开
    @Override
    public void keyReleased(KeyEvent e) {
//        System.out.println("keyReleased: " + e.getKeyCode());
        list = ElementManager.getElementManager().getElementList("Player");
        Player player = (Player)list.get(0);
        // 同时按住2个键，松开一个键之后不停顿的解决方法
        switch (e.getKeyCode()) {
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
            case 32:
                player.setPk(false);
                break;
            default:
                break;
        }
    }

    // 键盘字母区输入
    @Override
    public void keyTyped(KeyEvent e) {
//        System.out.println("keyTyped: " + e.getKeyCode());
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

}
