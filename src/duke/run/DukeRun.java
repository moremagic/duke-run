/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duke.run;

/**
 *
 * @author mitsu
 */
public class DukeRun {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DukeFrame2 frame = new DukeFrame2();
        java.awt.EventQueue.invokeLater(() -> {
            frame.setVisible(true);
        });
    }
}
