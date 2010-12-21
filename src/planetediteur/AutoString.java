/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetediteur;

import java.util.Enumeration;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author ohanessian
 */
public class AutoString {
    
    protected String texte = ""; //texte complet
    protected String recherche = "";  //texte rechercher
    protected String texteComp = ""; // texte complet d'entrer
    protected int position = 0; // definit la position du curseur dans le texte
    
    protected Vector<block> result = new Vector(); //collection des block trouver dans le texte
    protected String replace =""; 
    
    protected String mots ="";
    protected String motsR ="";
    
    protected int nbrMots =0;
    protected block balise = null; //balise actuel pointer par le curseur
  
    protected String sortieAttribut = "";
    protected int nbrAttributSearch=0;
    protected String attributPointer="";
    /*******************************/
    /********* Constructeur ********/
    /*******************************/
    
    public AutoString() {
        
    }

    
    /********************************/
    /********* GEtter Setter ********/
    /********************************/
    
    
    public int getNbrAttributSearch() {
        return nbrAttributSearch;
    }

    public String getSortieAttribut() {
        return sortieAttribut;
    }

    
    public block getBalise() {
        return balise;
    }
    
    public Vector<block> getResult() {
        return result;
    }
    
    public int getPosition() {
        return position;
        
    }

    public void setPosition(int position) {
        this.position = position;
    }
        
    public int getNbrMots() {
        return this.nbrMots;
    }

    public String getMots() {
        return this.mots;
    }

    public String getReplace() {
        return replace;
    }
    
    
    
    public String getRecherche() {
        return recherche;
    }

    
    /**
     * Permet de definir les mots rechercher
     * @param recherche 
     */
    public void setRecherche(String recherche) {
          this.mots = "";
          this.texteComp = recherche;
        
          int pos = this.position;
          int longueurR = recherche.length();
          String textR = "";
          int lon = 0;
  
          
          int i = pos -1;
         
         //on remonte le mots jusqu'au premiers blanc 
         while(i > 0)
         {
             if(i == 0)
             {
                 break;
             }    
             
             if(recherche.charAt(i) != ' ' && recherche.charAt(i) != '<' && recherche.charAt(i) != '\n')
             {
                 lon++;
             }
             else
             {
                 break;
             }
             
             if(i>=1)
             {
                  i--;
             }
         }
          this.recherche = recherche.substring(pos - lon, pos);
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }
    
    
    /*********************************/
    /********* Methode Public ********/
    /*********************************/
    
    
    /**
     * cherche un texte dans le parametre texte
     * @return boolean
     */
    public boolean search()
    {
        
        Pattern p = Pattern.compile(".* "+this.recherche+".*");
        Matcher m = p.matcher(this.texte);
        boolean b = m.matches();
        return  b;
    }
    
    /**
     * Fait la completion du mots en cours
     * @return String // retourne tous le texte completer 
     */
    public String complete()
    {


         String sortie = "";
         String mots = this.motsR.trim();
        
         //position du curseur dans la string
       
         int positionCursor = this.position;
             int tab = 0;
             for(int i=0;i<texteComp.length()+1;i++)
             {
                if(i<positionCursor) 
                {    
                     if(texteComp.charAt(i) == '\t')
                     {
                         tab++;
                     }    

                     if(texteComp.charAt(i) == '\n')
                     {
                          tab=0;

                     }
                } 

                 if(i == positionCursor)
                 {
                   mots = this.motsR.replaceAll(this.recherche, ""); 
                   mots = mots.trim()+">\n"+this.tabulation(tab)+"\t%@\n"+this.tabulation(tab) +"</"+this.motsR.trim()+">\n";
                   sortie += mots;
                 }
                 else if(i < texteComp.length())
                 {
                    sortie += texteComp.charAt(i);
                 }
             }    
             this.position = sortie.indexOf("%@"); 

             sortie = sortie.replace("%@","");
             this.recherche = "";
    
         return sortie;
    }

     
    /**
     * parse tous la string entrer dans recherche et texte , en deduis les mots proche de ceux taper.
     */
    public void replace()
    {
        
        this.nbrMots=0;
         Pattern p = Pattern.compile(this.recherche);
         Matcher m = p.matcher(this.texte);
         
         while(m.find())
         {
                this.replace = this.texte.replaceAll(" ("+m.group()+")([!a-zA-Z0-9_]*)", "<strong color=\"red\">$1</strong><strong color=\"blue\">$2</strong>");
                                     
         }    
         
         p = Pattern.compile(" "+this.recherche+"[a-zA-Z0-9_]*");
         m = p.matcher(this.texte);
         while(m.find())
         {
                this.nbrMots++;
                this.motsR = m.group(); 
                this.mots += m.group()+"\n";
                              
         }  
    }
    
    
    /**
     * Retrouve tous les block d'un texte et construit la collection Block
     * @param texte //texte a parser
     */
    public void update(String texte) {
        
        //mise a zero du vector
        result.clear();
        
        int lon = texte.length();
        int i = 0;

        int collonestart=0;//on compte la collone de depart
        int collonefin = 0;//on compte la collone de fin
        
        boolean capture=false;
        String cap = "";
        String name = "";

        for(i=0;i<lon;i++)
        {
            char caract = texte.charAt(i);
            if(caract == '<')
            {
               collonestart = i;
               capture = true;
            }
            
            if(capture = true && caract != '\t' && caract!='\n')
            {
               cap += caract;
              
            }    
            
            if(caract == '>')
            {
               collonefin = i;  
               capture = false;
               
               block entrer = new block();
               entrer.coldebut = collonestart;
               entrer.colfin = collonefin;
               
               //extraire le nom du contenu
               Pattern p = Pattern.compile("</?[a-zA-Z]+");
               Matcher m = p.matcher(cap);
               
               while(m.find())
               {
                  
                   entrer.name = m.group().replaceAll("<", "").replaceAll("/","");
               }    
               
               
               entrer.attribut = "id class lang maison";

               entrer.contenu = cap;
               result.add(entrer);
               cap = "";
               name= "";
            }
            
        }
    }
    
    /**
     * Rechercher un block dans la collection Block
     * @param position // position demander
     * @return Boolean // retour si un block a été trouver a la position demander
     */
    public boolean rechercher(int position)
    {
      block elements = null;
      boolean retour = false;
      
      for (Enumeration<block> e = this.result.elements() ; e.hasMoreElements() ;) {
         elements = e.nextElement();
            
             if(position > elements.coldebut && position <= elements.colfin )
             {    
                retour = true;
                this.balise = elements;
             }
         }
       return retour; 
    }   
    
    
    public String completeAttribut()
    {
        String sortie= "";
        
        //String replace = balise.contenu.replaceAll(this.attributPointer ,  this.sortieAttribut.trim()+"=\"%@\"");

        //this.position = texte.indexOf("%@"); 
        //texte.replaceAll("%@", "");
        
         String mots = "";
          
         //position du curseur dans la string
       
             int positionCursor = this.position;
             System.out.print(this.position);
             System.out.print(this.texte);
             for(int i=0;i<this.texte.length()+1;i++)
             {
                 if(i == positionCursor && !this.attributPointer.isEmpty())
                 {
                   System.out.print(this.attributPointer);
                   mots = this.sortieAttribut.replaceAll(this.attributPointer, ""); 
                   mots = mots.trim()+"=\"%@\" ";
                   System.out.print(mots);
                   sortie += mots.replaceAll("\n","").trim();
                 }
                 else if(i < this.texte.length())
                 {
                    sortie += this.texte.charAt(i);
                 }
             }    
             this.position = sortie.indexOf("%@"); 

             sortie = sortie.replace("%@","");
             
         System.out.print(sortie);
         this.attributPointer ="";
         this.sortieAttribut ="";
         return sortie;
    }
    
    public Boolean attribut()
    {
       
        this.sortieAttribut = "";    
        Pattern p =null;
        Matcher m =null;
        boolean attributCheck = false;
       
        //on va extraire le texte rechercher
        Pattern patter = Pattern.compile("( )+[a-zA-Z]+( )*");
        Matcher match = patter.matcher(balise.contenu);
               
        while(match.find())
        {
           p = Pattern.compile("( )+"+match.group().trim()+"[a-zA-Z0-9_]*( )*");
           m = p.matcher(balise.attribut);

           this.sortieAttribut = ""; 
           this.nbrAttributSearch=0;
           while(m.find())
           {
               attributCheck = true;
               this.nbrAttributSearch++;
               this.attributPointer = match.group();
               this.sortieAttribut += m.group()+"\n";
                              
           }  
       
        }    

        return attributCheck;
    }
    
    /*********************************/
    /********* Methode priver ********/
    /*********************************/
    
    /**
     * Permet de placer un nombre n de tabulation
     * @param nbr //le nombre de tabulation voulu
     * @return String //nombre de tabulation
     */    
    private String tabulation(int nbr)
    {
        String tab = "";
        
        
        for(int i=0;i <nbr;i++)
        {
            tab += "\t";
        }    
                
        return tab;
    }
    
}


