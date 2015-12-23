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
import java.awt.Point;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author mitsu
 */
public class DukeFrame2 extends JFrame {
    
    private static final ImageIcon image_fly = new ImageIcon(DukeFrame2.class.getResource("/duke/run/resouces/duke.png"));
    private static final ImageIcon image1 = new ImageIcon(DukeFrame2.class.getResource("/duke/run/resouces/run1.gif"));
    private static final ImageIcon image2 = new ImageIcon(DukeFrame2.class.getResource("/duke/run/resouces/run2.gif"));
    private boolean imageFlg = true;
    private boolean stopFlg = true;
    private boolean turnFlg = false;
    
    public DukeFrame2() {
        initComponents();
        actionThread.start();
    }
    
    public void setStopFlg(boolean stopFlg) {
        this.stopFlg = stopFlg;
        repaint();
    }
    
    public void setTurnFlg(boolean turnFlg) {
        this.turnFlg = turnFlg;
        repaint();
    }
    
    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setBackground(new Color(0x0, true));
        setResizable(false);
        setAlwaysOnTop(true);
        setMinimumSize(new Dimension(image1.getIconWidth(), image1.getIconHeight()));
        
        DragWindowListener dwl = new DragWindowListener();
        addMouseListener(dwl);
        addMouseMotionListener(dwl);
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        java.awt.GraphicsEnvironment env = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment();
        java.awt.Rectangle desktopBounds = env.getMaximumWindowBounds();
        if (desktopBounds.getHeight() > (getLocation().y + getHeight())) {
            paintFly(g);
        } else {
            paintRun(g);
        }
        
    }
    
    public void paintRun(Graphics g) {
        if (!stopFlg) {
            imageFlg = !imageFlg;
        }
        
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
    
    public void paintFly(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform attr = AffineTransform.getScaleInstance(0.35d, 0.35d);
        
        Image img;
        img = image_fly.getImage();
        
        if (turnFlg) {
            attr = AffineTransform.getScaleInstance(-0.35d, 0.35d);
            attr.translate(-img.getWidth(this), 0);
        }
        
        g2d.drawImage(img, attr, this);
    }

    //=======================================
    private final Thread actionThread = new Thread(new Runnable() {
        @Override
        public void run() {
            dropDuke().run();
            while (isVisible()) {
                dropDuke().run();
                createMoveRun(30, 5, 0).run();
                waitDuke(3).run();
                createMoveRun(30, 8, -10).run();
                waitDuke(5).run();
                createMoveRun(30, -10, 0).run();
                waitDuke(2).run();
                createMoveRun(30, -3, -20).run();
                waitDuke(5).run();
            }
        }
    });
    
    private Runnable createMoveRun(int move, int dx, int dy) {
        return () -> {
            java.awt.GraphicsEnvironment env = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment();
            java.awt.Rectangle desktopBounds = env.getMaximumWindowBounds();
            if (desktopBounds.getHeight() > (getLocation().y + getHeight())) {
                dropDuke().run();
            }

            //右側に
            java.awt.EventQueue.invokeLater(() -> {
                setStopFlg(false);
            });
            java.awt.EventQueue.invokeLater(() -> {
                setTurnFlg(dx < 0);
            });
            for (int i = 0; i < move; i++) {
                java.awt.EventQueue.invokeLater(() -> {
                    setLocation(getLocation().x + dx, getLocation().y + dy);
                });
                try {
                    Thread.sleep(50); //Sleepする
                } catch (InterruptedException e) {
                }
                
                if (desktopBounds.getWidth() <= getLocation().x || 0 >= getLocation().x) {
                    break;
                }
            }
        };
    }
    
    private Runnable waitDuke(int cnt) {
        return () -> {
            java.awt.GraphicsEnvironment env = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment();
            java.awt.Rectangle desktopBounds = env.getMaximumWindowBounds();
            if (desktopBounds.getHeight() > (getLocation().y + getHeight())) {
                return;
            }
            
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
                    Thread.sleep(1000); //Sleepする
                    bTurn = !bTurn;
                } catch (InterruptedException e) {
                }
            }
        };
    }
    
    private Runnable dropDuke() {
        return () -> {
            java.awt.GraphicsEnvironment env = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment();
            java.awt.Rectangle desktopBounds = env.getMaximumWindowBounds();

            //動き止める
            java.awt.EventQueue.invokeLater(() -> {
                setStopFlg(true);
            });
            //落下させる
            for (int i = 0; i < 1000; i++) {
                java.awt.EventQueue.invokeLater(() -> {
                    setLocation(getLocation().x, getLocation().y + 5);
                });
                try {
                    Thread.sleep(50); //Sleepする
                } catch (InterruptedException e) {
                }
                
                if (desktopBounds.getHeight() <= (getLocation().y + getHeight())) {
                    break;
                }
            }
            //動き始める
            java.awt.EventQueue.invokeLater(() -> {
                setStopFlg(false);
            });
        };
    }
    
}

class DragWindowListener extends MouseAdapter {
    
    private final Point startPt = new Point();
    private Window window;
    
    @Override
    public void mousePressed(MouseEvent me) {
        startPt.setLocation(me.getPoint());
    }
    
    @Override
    public void mouseDragged(MouseEvent me) {
        if (window == null) {
            if (me.getComponent() instanceof Window) {
                window = (Window) me.getComponent();
            } else {
                window = SwingUtilities.windowForComponent(me.getComponent());
            }
        }
        Point eventLocationOnScreen = me.getLocationOnScreen();
        window.setLocation(eventLocationOnScreen.x - startPt.x,
                eventLocationOnScreen.y - startPt.y);
    }
}
