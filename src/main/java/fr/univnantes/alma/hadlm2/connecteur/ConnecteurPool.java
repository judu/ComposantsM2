/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univnantes.alma.hadlm2.connecteur;

import fr.univnantes.alma.hadlm2.composant.Composant;
import java.lang.reflect.AccessibleObject;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author indy
 */
public final class ConnecteurPool {

    private List<Connecteur> connecteurList;

    public ConnecteurPool() {
        connecteurList = new ArrayList<Connecteur>();
    } // ConnecteurPool()

    public void addConnecteur(Connecteur c) {
        connecteurList.add(c);
    } // addConnecteur(Connecteur)

    public void add(Connecteur conn) {
        connecteurList.add(conn);
    }

    public List<Connecteur> get(Composant source, AccessibleObject from) {
        List<Connecteur> result = null;
        for (Connecteur connecteur : connecteurList) {
            if (connecteur.getSource().equals(source)
                    && connecteur.getRoleFrom().getClass().equals(from.getClass())
                    && connecteur.getRoleFrom().equals(from)) {
                if (result == null) {
                    result = new ArrayList<Connecteur>();
                } // if
                result.add(connecteur);
            } // if
        } // for
        return result;

    }
}
