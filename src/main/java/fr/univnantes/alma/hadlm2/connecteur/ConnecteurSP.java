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
public abstract class ConnecteurSP extends Connecteur<Method, Field> {


   public ConnecteurSP(Composant source, Method roleFrom, Composant cible, Field roleTo) throws WrongTypeException {
      super(source, roleFrom, cible, roleTo);
   }

   @Override
    public final void glue() {
        try {
            roleTo.set(cible, before(roleFrom.invoke(source)));
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(ConnecteurSP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ConnecteurSP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(ConnecteurSP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
