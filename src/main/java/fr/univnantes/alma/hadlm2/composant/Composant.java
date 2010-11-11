package fr.univnantes.alma.hadlm2.composant;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author judu
 */
public abstract class Composant extends Observable {

    protected Configuration parent;

    public void call(String service) {
        this.setChanged();
        this.notifyObservers(service);
    }

    public void set(String port, Object value) {
        for (Field f : this.getClass().getDeclaredFields()) {
            if (f.getName().equals(port)
                    && f.getType().equals(value.getClass())) {
                try {
                    f.set(this, value);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(Composant.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(Composant.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.call(port);
                break;
            } // if
        } // for
    }

    protected final <I extends AccessibleObject> boolean hasInterface(I iface) {
        if (iface.getClass().equals(Field.class)) {
            for (Field f : this.getClass().getDeclaredFields()) {
                if (f.equals(iface)) {
                    return true;
                }
            }
        } else if (iface.getClass().equals(Method.class)) {
            for (Method m : this.getClass().getDeclaredMethods()) {
                if (m.equals(iface)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setParent(Configuration c) {
        parent = c;
    }
}
