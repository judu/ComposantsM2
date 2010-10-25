package fr.univnantes.alma.hadlm2.composant;

import java.util.Observable;

/**
 *
 * @author judu
 */
public abstract class Composant extends Observable {

    protected Configuration parent;

    public void call(String service, Object... args) {
        Object[] toObs = {service, args};
        this.notifyObservers(toObs);
    }

    public void setParent(Configuration c) {
        parent = c;
    }
}
