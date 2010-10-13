/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univnantes.alma.hadlm2.connecteur;

import fr.univnantes.alma.hadlm2.composant.Composant;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author indy
 */
public class ConnecteurPool {

    private List<Connecteur> connecteurList;

    public ConnecteurPool() {
        connecteurList = new ArrayList<Connecteur>();
    } // ConnecteurPool()

    public Connecteur get(Composant source, String roleFrom) {
        for (Connecteur connecteur : connecteurList) {
            if (connecteur.getSource().equals(source)
                    && connecteur.getRoleFrom().equals(roleFrom)) {
                return connecteur;
            } // if
        } // for
        return null;
    } // Connecteur getBySource(Composant)
}