/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univnantes.alma.hadlm2.connecteur;

import fr.univnantes.alma.hadlm2.composant.Composant;

/**
 *
 * @author indy
 */
public abstract class Connecteur<F, T> {

    protected Composant source;
    protected Composant cible;
    protected F roleFrom;
    protected T roleTo;

    public final F getRoleFrom() {
        return roleFrom;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ConnecteurSS other = (ConnecteurSS) obj;
        if (this.roleFrom != other.roleFrom && (this.roleFrom == null || !this.roleFrom.equals(other.roleFrom))) {
            return false;
        }
        if (this.roleTo != other.roleTo && (this.roleTo == null || !this.roleTo.equals(other.roleTo))) {
            return false;
        }
        if (this.source != other.source && (this.source == null || !this.source.equals(other.source))) {
            return false;
        }
        if (this.cible != other.cible && (this.cible == null || !this.cible.equals(other.cible))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.roleFrom != null ? this.roleFrom.hashCode() : 0);
        hash = 59 * hash + (this.roleTo != null ? this.roleTo.hashCode() : 0);
        return hash;
    }

    public abstract <S extends Object, E extends Object> S before(E arg);

    public final Composant getSource() {
        return source;
    }

    public final Composant getCible() {
        return cible;
    }

    public abstract void glue();
}
