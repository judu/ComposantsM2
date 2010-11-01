/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univnantes.alma.hadlm2.connecteur;

import fr.univnantes.alma.hadlm2.composant.Composant;
import java.lang.reflect.AccessibleObject;

/**
 *
 * @author judu
 */
public class Binding<T extends AccessibleObject> {

    protected T src;
    protected Composant target;
    protected T targetInterface;

    public Binding(T src, Composant target, T targetI) {
        this.src = src;
        this.target = target;
        this.targetInterface = targetI;
    }

    public T getSource() {
        return src;
    }

    public Composant getTarget() {
        return target;
    }

    public T getTargetInterface() {
        return targetInterface;
    }
}
