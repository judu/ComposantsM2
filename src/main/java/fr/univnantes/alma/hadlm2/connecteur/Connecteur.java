/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.univnantes.alma.hadlm2.connecteur;

import fr.univnantes.alma.hadlm2.composant.Composant;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author judu
 */
public abstract class Connecteur {
   Composant source;
   Composant cible;

   Method roleFrom;
   Method roleTo;


   public abstract Object[] before(Object... args);


   public void glue(Object... args) {
      try {
         roleTo.invoke(cible, roleFrom.invoke(source, before(args)));
      } catch (IllegalAccessException ex) {
         Logger.getLogger(Connecteur.class.getName()).log(Level.SEVERE, null, ex);
      } catch (IllegalArgumentException ex) {
         Logger.getLogger(Connecteur.class.getName()).log(Level.SEVERE, null, ex);
      } catch (InvocationTargetException ex) {
         Logger.getLogger(Connecteur.class.getName()).log(Level.SEVERE, null, ex);
      }
   }
   

}
