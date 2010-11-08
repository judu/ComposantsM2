/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.univnantes.alma.hadlm2.connecteur;

import fr.univnantes.alma.hadlm2.composant.Composant;
import fr.univnantes.alma.hadlm2.exceptions.WrongTypeException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author indy
 */
public abstract class ConnecteurPS extends Connecteur<Field,Method> {

   public ConnecteurPS(Composant source, Field roleFrom, Composant cible, Method roleTo) throws WrongTypeException {
      super(source, roleFrom, cible, roleTo);
   }

   @Override
    public final void glue(){
        try {
            roleTo.invoke(cible, before(roleFrom.get(source)));
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ConnecteurPS.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(ConnecteurPS.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(ConnecteurPS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
