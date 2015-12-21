/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duke.run;

import javax.swing.JFrame;

/**
 *
 * @author mitsu
 */
public class DukeRun {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        java.awt.GraphicsEnvironment env = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment();
        java.awt.Rectangle desktopBounds = env.getMaximumWindowBounds();

        DukeFrame2 frame = new DukeFrame2();
        java.awt.EventQueue.invokeLater(() -> {
            frame.setVisible(true);
        });

        //落下させる
        for (int i = 0; i < 1000; i++) {
            final int j = i;
            java.awt.EventQueue.invokeLater(() -> {
                frame.setLocation(0, j * 5);
            });
            try {
                Thread.sleep(50); //300ミリ秒Sleepする
            } catch (InterruptedException e) {
            }

            if (desktopBounds.getHeight() <= (frame.getLocation().y + frame.getHeight())) {
                break;
            }
        }

        //動き始める
        java.awt.EventQueue.invokeLater(() -> {
            frame.setStopFlg(false);
        });


//        //右側に
//        for (int i = 0; i < 1000; i++) {
//            final int j = i;
//            java.awt.EventQueue.invokeLater(() -> {
//                frame.setLocation(frame.getLocation().x + 5, frame.getLocation().y);
//            });
//            try {
//                Thread.sleep(50); //300ミリ秒Sleepする
//            } catch (InterruptedException e) {
//            }
//
//            if (desktopBounds.getWidth() <= frame.getLocation().x) {
//                break;
//            }
//        }
//
//        //ターン
//        java.awt.EventQueue.invokeLater(() -> {
//            frame.setTurnFlg(true);
//        });
//        
//        //左側に
//        for (int i = 0; i < 1000; i++) {
//            final int j = i;
//            java.awt.EventQueue.invokeLater(() -> {
//                frame.setLocation(frame.getLocation().x - 5, frame.getLocation().y);
//            });
//            try {
//                Thread.sleep(50); //300ミリ秒Sleepする
//            } catch (InterruptedException e) {
//            }
//
//            if (0 >= frame.getLocation().x) {
//                break;
//            }
//        }
//
//        //終了
//        System.out.println("exit");
//
//        java.awt.EventQueue.invokeLater(() -> {
//            frame.setVisible(false);
//            frame.dispose();
//        });
        
        //System.exit(0);
    }
}
