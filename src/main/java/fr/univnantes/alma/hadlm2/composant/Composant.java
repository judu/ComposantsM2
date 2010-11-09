package fr.univnantes.alma.hadlm2.composant;

import java.lang.reflect.AccessibleObject;
import java.util.Observable;

/**
 *
 * @author judu
 */
public abstract class Composant extends Observable {

    protected Configuration parent;

    public void call(String service) {
        this.notifyObservers(service);
    }

    protected abstract <I extends AccessibleObject> boolean hasInterface(I iface);

    public void setParent(Configuration c) {
        parent = c;
    }
}
