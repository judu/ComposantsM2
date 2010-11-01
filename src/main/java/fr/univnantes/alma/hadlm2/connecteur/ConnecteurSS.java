package fr.univnantes.alma.hadlm2.connecteur;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author judu
 */
public abstract class ConnecteurSS extends Connecteur<Method,Method> {

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
