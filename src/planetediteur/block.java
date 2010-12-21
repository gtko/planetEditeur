/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetediteur;

/**
 *
 * @author ohanessian
 */
public class block {

    public int coldebut;
    public int colfin;
    public String name;
    public String contenu;
    public String attribut;
    
    
    @Override
    public String toString() {
        return "\nbalise=" + this.name +
                "\ncolloneStart = "+ this.coldebut +
                "\ncolloneEnd = " + this.colfin +
               "\nattribute = " + this.attribut;
    }


}
