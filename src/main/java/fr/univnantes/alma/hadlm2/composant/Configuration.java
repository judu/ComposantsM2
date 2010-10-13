package fr.univnantes.alma.hadlm2.composant;

import fr.univnantes.alma.hadlm2.connecteur.Connecteur;
import fr.univnantes.alma.hadlm2.exceptions.WrongTypeException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author Julien Durillon
 */
public abstract class Configuration extends Composant implements Observer {
   List<Composant> composants;
   List<Connecteur> connecteurs;


   public abstract Connecteur getConnecteur(Method roleFrom);

   public void addComposant(Composant comp) {
      composants.add(comp);
      comp.addObserver(this);
   }

   @Override
   public void update(Observable o, Object arg) {

      if(!(o instanceof Composant)) {
         //TODO: add logs
         return;
      }


      if(!(arg instanceof Object[])) {
         //TODO: add logs
         return;
      }
      
      Object[] args = (Object[]) arg;

      if(args.length < 2) {
         //TODO: add logs
         return;
      }
      
      String from = (String) args[0];
      Object[] reste = (Object[]) args[1];

      


      
   }

}
