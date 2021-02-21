/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm;

import javax.swing.JOptionPane;

/**
 *
 * @author jean pierre
 */
public class Notifier {

    public static void notifier(boolean done) {
        if (done) {
            JOptionPane.showMessageDialog(null, "Operation done successfully", "Sucess Operation", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Operation failed. Check your data format", "Failure in Operation", JOptionPane.ERROR_MESSAGE);
        }
    }
}
