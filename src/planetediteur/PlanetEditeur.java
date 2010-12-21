/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetediteur;

import javax.swing.UIManager;
import planetediteur.gui.gui;

/**
 *
 * @author ohanessian
 */
public class PlanetEditeur {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         try {
              UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
              gui appli = new gui();
          
              appli.setVisible(true);
            }catch (Exception e) {
                
            }
         
      
       
    }

}
