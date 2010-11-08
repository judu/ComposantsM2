package fr.univnantes.alma.hadlm2.connecteur;

import fr.univnantes.alma.hadlm2.composant.Composant;
import fr.univnantes.alma.hadlm2.exceptions.WrongTypeException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author judu
 */
public abstract class ConnecteurSS extends Connecteur<Method,Method> {


   public ConnecteurSS(Composant source, Method roleFrom, Composant cible, Method roleTo) throws WrongTypeException {
      super(source, roleFrom, cible, roleTo);
   }


   @Override
    public final void glue() {
        try {
            roleTo.invoke(cible, before(roleFrom.invoke(source)));
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ConnecteurSS.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(ConnecteurSS.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(ConnecteurSS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
