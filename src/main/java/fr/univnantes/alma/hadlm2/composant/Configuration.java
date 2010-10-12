/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.univnantes.alma.hadlm2.composant;

import fr.univnantes.alma.hadlm2.connecteur.Connecteur;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Observer;

/**
 *
 * @author Julien Durillon
 */
public abstract class Configuration extends Composant implements Observer {
   List<Composant> composants;
   List<Connecteur> connecteurs;


   public abstract Connecteur getConnecteur(Method roleFrom);

}
