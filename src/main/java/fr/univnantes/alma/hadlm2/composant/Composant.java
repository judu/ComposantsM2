package fr.univnantes.alma.hadlm2.composant;

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

    public void setParent(Configuration c) {
        parent = c;
    }
}
