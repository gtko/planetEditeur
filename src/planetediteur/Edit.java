/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetediteur;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
//import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author user1
 */
public class Edit {

    
    protected String nom = "";
    protected int tab = 0;
    
    protected File fichier = null;
    protected String rep = null;
    private javax.swing.JTextArea jTexte;
    private javax.swing.JScrollPane jScroll;
    
    public Edit() {
        
        jTexte = new JTextArea();
        jTexte.setVisible(true);
        jTexte.setTabSize(3);
        jScroll = new JScrollPane();
        jScroll.setViewportView(jTexte);

    }

    public JTextArea getjTexte() {
        return jTexte;
    }

    public JScrollPane getjScroll() {
        return jScroll;
    }
    
    


    //On gere les entrer sortie de l'objet
    
    public File getFichier() {
        return fichier;
    }

    public void setFichier(File fichier) {
        this.fichier = fichier;
        this.rep = fichier.getPath();
        jTexte.setText(this.lireFile());
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
        
    }

    public int getTab() {
        return tab;
    }

    public void setTab(int tab) {
        
        this.tab = tab;
        
    }

    public String getRep() {
        return rep;
    }

    public void setRep(String rep) {
        this.rep = rep;
    }
    
    
    
    
    /**
     * Permet de faire une sauvegarde du fichier
     * @param text    protected int tab = 0;
     * @param nomFile
     * @throws IOException 
     */
    public void sauvegarde() throws IOException
    {
       
        File file = new File(this.rep);
        //on cree le fichier si il existe il me retourne une erreur exeption
        try{
            file.createNewFile();
        }
        catch(IOException e)
        {
            System.out.println("Impossible de créer le fichier");
        }    
        
        //je crée le flux que je veux enregistrer dans le fichier
        
        try{
            FileOutputStream fluxFichier = new FileOutputStream(this.rep);
            
            String text = jTexte.getText();
            for(int i=0;i<text.length();i++)
            {
                fluxFichier.write(text.charAt(i));
            }    
            
            
            try{
                fluxFichier.close(); // Toujours dans un TRY 
            }
            catch (IOException e) 
            { 
                  System.out.println("Impossible de fermer le fichier");        
            }
        }
        catch(FileNotFoundException e)
        {
          System.out.println("Impossible de trouver le fichier");      
        }
    }
    
    
    
    protected String lireFile(){
        
        String contenu = ""; 
        String ligne = "";
        
        BufferedReader ficTexte;
	try {
            ficTexte = new BufferedReader(new FileReader(this.fichier));
            
            if (ficTexte == null) {
                    throw new FileNotFoundException("Fichier non trouvé: " + this.fichier);
            }
            while ((ligne=ficTexte.readLine())!=null) 
            {
               contenu += ligne + "\n";
           
            }
            ficTexte.close();

        } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
        } catch (IOException e) {
                System.out.println(e.getMessage());
        }
        
        return contenu;
    }

    
    
    
    
}
