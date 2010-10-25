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
public final class ConnecteurPool {

    private List<ConnecteurSS> connecteurList;

    public ConnecteurPool() {
        connecteurList = new ArrayList<ConnecteurSS>();
    } // ConnecteurPool()

    public void addConnecteur(ConnecteurSS c){
        connecteurList.add(c);
    } // addConnecteur(Connecteur)

    public ConnecteurSS get(Composant source, String roleFrom) {
        for (ConnecteurSS connecteur : connecteurList) {
            if (connecteur.getSource().equals(source)
                    && connecteur.getRoleFrom().equals(roleFrom)) {
                return connecteur;
            } // if
        } // for
        return null;
    } // Connecteur getBySource(Composant)

   public void add(ConnecteurSS conn) {
      connecteurList.add(conn);
   }
}
