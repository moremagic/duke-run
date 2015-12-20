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
    
    public void setStopFlg(boolean stopFlg){
        this.stopFlg = stopFlg;
    }
    
    public void setTurnFlg(boolean turnFlg){
        this.turnFlg = turnFlg;
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setBackground(new Color(0x0, true));
        setResizable(false);
        this.setMinimumSize(new Dimension(200, 200));

        new Thread(new Runnable() {
            public void run() {
                while(!isVisible()){
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
        }else{
            img = image2.getImage();
        }
        
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform attr= AffineTransform.getTranslateInstance(1.0d, 1.0d);
        if(turnFlg){
            attr= AffineTransform.getScaleInstance(-1.0d, 1.0d);
            attr.translate(-img.getWidth(this), 0);
        }
        
        g2d.drawImage(img, attr, this);
    }

}
