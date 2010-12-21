/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Events;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
public class appFocusListener implements FocusListener
{
    private String name = null;
        
    public appFocusListener(String name)
    {
        this.name = name;
    }
    
    public void focusGained(FocusEvent e) {
        //System.out.print(this.name + "gagner");
    }

    public void focusLost(FocusEvent e) {
        //System.out.print(this.name + "perdu");
    }
    
}
