package fr.univnantes.alma.hadlm2.composant;

import fr.univnantes.alma.hadlm2.connecteur.Connecteur;
import fr.univnantes.alma.hadlm2.connecteur.ConnecteurPool;
import fr.univnantes.alma.hadlm2.exceptions.NoSuchComponentException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Julien Durillon
 */
public abstract class Configuration extends Composant implements Observer {

   protected List<Composant> composants;

   protected ConnecteurPool connecteurs;

   public Configuration() {
      composants = new ArrayList<Composant>();
      connecteurs = new ConnecteurPool();
   }

   public abstract List<Connecteur> getConnecteurs(Composant source, Method roleFrom);

   public void addConnecteur(Connecteur conn) throws NoSuchComponentException {
      Composant source = conn.getSource();
      Composant cible = conn.getCible();
      checkSource:
      {
         for (Composant comp : composants) {
            if (comp.equals(source)) {
               break checkSource;
            } // if
         } // for
         throw new NoSuchComponentException("No source component");
      } // checkSource
      checkCible:
      {
         for (Composant comp : composants) {
            if (comp.equals(cible)) {
               break checkCible;
            } // if
         } // for
         throw new NoSuchComponentException("No target component");
      } // checkCible
      this.connecteurs.add(conn);
   } // addConnecteur(Connecteur)

   public final void addComposant(Composant comp) {
      composants.add(comp);
      comp.addObserver(this);
      comp.setParent(this);
   }

   @Override
   public final void update(Observable o, Object arg) {

      if (!(o instanceof Composant)) {
         //TODO: add logs
         return;
      } // if

      Composant source = (Composant) o;

      String name = (String) arg;
      Field field = null;
      Method method = null;
      Boolean found = Boolean.FALSE;
      Field[] declaredFields = source.getClass().getDeclaredFields();

      int i = 0;
      while (i++ < declaredFields.length && !found) {
         Field f = declaredFields[i];
         if (f.getName().equals(name)) {
            field = f;
            found = Boolean.TRUE;
         }
      }

      if (!found) {
         Method[] declaredMethods = source.getClass().getDeclaredMethods();
         i = 0;
         while (i++ < declaredMethods.length && !found) {
            Method m = declaredMethods[i];
            if (m.getName().equals(name)) {
               method = m;
               found = Boolean.TRUE;
            }
         }
      }
      
      if (found) {
         List<Connecteur> conns = null;
         if (field != null) {
            conns = this.connecteurs.get(source, field);
         } else if (method != null) {
            conns = this.connecteurs.get(source, method);
         }
         for (Connecteur conn : conns) {
            conn.glue();
         } // for
      } else {
         //TODO: add logs
         return;
      } // if
   }
}
