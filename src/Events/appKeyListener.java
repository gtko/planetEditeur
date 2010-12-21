/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Events;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JTextArea;
import planetediteur.AutoString;

public class appKeyListener implements KeyListener
{
    
    private String name = null;
    private JTextArea jList = null;
    private JTextArea jTexte = null;
    private AutoString test = new AutoString();
    
    public appKeyListener(String name , JTextArea jList , JTextArea jTexte)
    {
        this.name = name;
        this.jList = jList;
        this.jTexte = jTexte;
    }
    
    public void keyTyped(KeyEvent e) {
       
    }

    public void keyPressed(KeyEvent e) {
        
     
        
    }

    public void keyReleased(KeyEvent e) {
        String TagHtml =" !doctype abbr acronym address a applet area b base basefont bdo bgsound big blink blockquote body br button caption center cite code col colgroup commment dd del dfn dir div dl dt em embed fieldset font form frame frameset h1 h2 h3 h4 h5 h6 head hr html i iframe img input ins isindex kbd label layer legend li link map marquee menu meta nextid nobr noembed noframes noscript object ol option p param pre q s samp script select small span strike strong style sub sup table tbody td textarea tfoot th thead title tr tt u ul var wbr xmp";        
        
        //TagHtml = " html @attr='oui|non'";
          //System.out.print(this.jTexte.getCaretPosition());  
          int postC = this.jTexte.getCaretPosition() - 1;
          test.update(this.jTexte.getText());
        
          
          boolean info = true;
          
          if(test.rechercher(this.jTexte.getCaretPosition()))
          {
              this.jList.setText(this.test.getBalise().toString());
              info = false;
             
              if(this.test.attribut())
              {
                this.jList.setText(this.test.getSortieAttribut());  
              }   
              
               if(e.getKeyCode() == 10 && test.getNbrAttributSearch() == 1) {
                   
                   System.out.print(this.test.getSortieAttribut().trim());
                    this.test.setTexte(this.jTexte.getText());
                    this.test.setPosition(this.jTexte.getCaretPosition());
                    this.test.setPosition(this.jTexte.getCaretPosition()-1);
                    this.jTexte.setText(test.completeAttribut());
                    this.jTexte.setCaretPosition(test.getPosition());
                    test.replace();       
               }    
          }    
          else
          {
          
              if(e.getKeyCode() != 10 && !this.jTexte.getText().isEmpty() && info)
              {

                char c = this.jTexte.getText().charAt(postC);    

                if( c != ' ' && c!='\t' && c!='\n')
                {    
                    test.setPosition(this.jTexte.getCaretPosition());
                    test.setTexte(TagHtml);
                    test.setRecherche(this.jTexte.getText());
                    test.replace();
                    this.jList.setText(test.getMots());
                }
                else
                {
                   this.jList.setText(TagHtml.replaceAll(" ", " \n")); 
                }


              }


            if(e.getKeyCode() == 10 && test.getNbrMots() == 1) {
                this.jTexte.setText(test.complete());
                this.jTexte.setCaretPosition(test.getPosition());
                test.replace();
                this.jList.setText(test.getMots());
            }
          }
    }



}
