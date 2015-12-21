/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duke.run;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author mitsu
 */
public class DukeFrame2 extends JFrame {

    private static final ImageIcon image1 = new ImageIcon(DukeFrame2.class.getResource("/duke/run/resouces/run1.gif"));
    private static final ImageIcon image2 = new ImageIcon(DukeFrame2.class.getResource("/duke/run/resouces/run2.gif"));
    private boolean imageFlg = true;
    private boolean stopFlg = true;
    private boolean turnFlg = false;

    public DukeFrame2() {
        initComponents();
    }

    public void setStopFlg(boolean stopFlg) {
        this.stopFlg = stopFlg;
        if (!this.stopFlg) {
            actionThread.start();
        }

    }

    public void setTurnFlg(boolean turnFlg) {
        this.turnFlg = turnFlg;
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setBackground(new Color(0x0, true));
        setResizable(false);
        setAlwaysOnTop(true);
        setMinimumSize(new Dimension(image1.getIconWidth(), image1.getIconHeight()));

        new Thread(new Runnable() {
            public void run() {
                while (!isVisible()) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                    }
                }
                while (isVisible()) {
                    if (!stopFlg) {
                        imageFlg = !imageFlg;
                    }

                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            repaint();
                        }
                    });

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                    }
                }
            }
        }).start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Image img;
        if (imageFlg) {
            img = image1.getImage();
        } else {
            img = image2.getImage();
        }

        Graphics2D g2d = (Graphics2D) g;
        AffineTransform attr = AffineTransform.getTranslateInstance(1.0d, 1.0d);
        if (turnFlg) {
            attr = AffineTransform.getScaleInstance(-1.0d, 1.0d);
            attr.translate(-img.getWidth(this), 0);
        }

        g2d.drawImage(img, attr, this);
    }

    //=======================================
    private final Thread actionThread = new Thread(new Runnable() {
        @Override
        public void run() {
            while (isVisible()) {
                createRightRun(30).run();
                waitDuke(5).run();
                createRightRun(30).run();
                waitDuke(5).run();
                createLeftRun(30).run();
                waitDuke(5).run();
                createLeftRun(30).run();
                waitDuke(5).run();
            }
        }
    });

    private Runnable createRightRun(int move) {
        return () -> {
            java.awt.GraphicsEnvironment env = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment();
            java.awt.Rectangle desktopBounds = env.getMaximumWindowBounds();
            
            //右側に
            java.awt.EventQueue.invokeLater(() -> {
                setStopFlg(false);
            });
            java.awt.EventQueue.invokeLater(() -> {
                setTurnFlg(false);
            });
            for (int i = 0; i < move; i++) {
                java.awt.EventQueue.invokeLater(() -> {
                    setLocation(getLocation().x + 5, getLocation().y);
                });
                try {
                    Thread.sleep(50); //Sleepする
                } catch (InterruptedException e) {
                }
                
                if (desktopBounds.getWidth() <= getLocation().x || Thread.currentThread().isInterrupted()) {
                    break;
                }
            }
        };
    }

    private Runnable createLeftRun(int move) {
        return () -> {
            java.awt.GraphicsEnvironment env = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment();
            java.awt.Rectangle desktopBounds = env.getMaximumWindowBounds();
            
            //左側に
            java.awt.EventQueue.invokeLater(() -> {
                setStopFlg(false);
            });
            java.awt.EventQueue.invokeLater(() -> {
                setTurnFlg(true);
            });
            for (int i = 0; i < move; i++) {
                java.awt.EventQueue.invokeLater(() -> {
                    setLocation(getLocation().x - 5, getLocation().y);
                });
                try {
                    Thread.sleep(50); //300ミリ秒Sleepする
                } catch (InterruptedException e) {
                }
                
                if (0 >= getLocation().x) {
                    break;
                }
            }
        };
    }

    private Runnable waitDuke(int cnt) {
        return () -> {
            int exitCnt = cnt;
            boolean bTurn = true;
            java.awt.EventQueue.invokeLater(() -> {
                setStopFlg(true);
            });
            while ((exitCnt -= 1) > -1) {
                boolean b = bTurn;
                java.awt.EventQueue.invokeLater(() -> {
                    setTurnFlg(b);
                });
                try {
                    Thread.sleep(500); //Sleepする
                    bTurn = !bTurn;
                } catch (InterruptedException e) {
                }
            }
        };
    }

}
