package fr.univnantes.alma.hadlm2.composant;

import fr.univnantes.alma.hadlm2.connecteur.Binding;
import fr.univnantes.alma.hadlm2.connecteur.Connecteur;
import fr.univnantes.alma.hadlm2.connecteur.ConnecteurPool;
import fr.univnantes.alma.hadlm2.exceptions.NoSuchComponentException;
import fr.univnantes.alma.hadlm2.exceptions.NoSuchInterfaceException;
import fr.univnantes.alma.hadlm2.exceptions.WrongTypeException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author Julien Durillon
 */
public abstract class Configuration extends Composant implements Observer {

   private List<Composant> composants;

   private ConnecteurPool connecteurs;

   private List<Binding> bindings;

   public Configuration() {
      composants = new ArrayList<Composant>();
      connecteurs = new ConnecteurPool();
      bindings = new ArrayList<Binding>();
   }

   @Override
   public void call(String service) {
      for (Binding b : bindings) {
         if (b.getSource() instanceof Method
                 && ((Method) b.getSource()).getName().equals(service)) {
            b.getTarget().call(((Method) b.getTargetInterface()).getName());
            break;
         }
      }
      this.setChanged();
      this.notifyObservers(service);
   }

   @Override
   public void set(String port, Object value) {
      for (Binding b : bindings) {
         if (b.getSource() instanceof Field
                 && ((Field) b.getSource()).getName().equals(port)
                 && ((Field) b.getSource()).getType().equals(value.getClass())) {
            b.getTarget().set(((Field) b.getTargetInterface()).getName(), value);
            break;
         }
      }
   }

   public final List<Connecteur> getConnecteurs(Composant source, Method roleFrom) {
      return connecteurs.get(source, roleFrom);
   }

   public final void addConnecteur(Connecteur conn) throws NoSuchComponentException {
      Composant source = conn.getSource();
      Composant cible = conn.getCible();
      AccessibleObject from = conn.getRoleFrom();
      AccessibleObject to = conn.getRoleTo();
      checkSource:
      {
         for (Composant comp : composants) {
            if (comp.equals(source) && comp.hasInterface(from)) {
               break checkSource;
            } // if
         } // for
         throw new NoSuchComponentException("No source component");
      } // checkSource
      checkCible:
      {
         for (Composant comp : composants) {
            if (comp.equals(cible) && comp.hasInterface(to)) {
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

   public final void addBinding(Field source, Composant target, Field targetPort)
           throws NoSuchInterfaceException, NoSuchComponentException, WrongTypeException {
      if (!source.getType().equals(targetPort.getType())) {
         throw new WrongTypeException("addBinding: source field type does not match target field type.");
      }
      this.pushBinding(source, target, targetPort);
   }

   public final void addBinding(Method source, Composant target, Method targetService)
           throws NoSuchInterfaceException, NoSuchComponentException, WrongTypeException {
      if (!source.getReturnType().equals(targetService.getReturnType())) {
         throw new WrongTypeException("addBinding: source service return type does not match target service return type.");
      }
      if (!Arrays.equals(source.getParameterTypes(), targetService.getParameterTypes())) {
         throw new WrongTypeException("addBinding: source service parameters types do not match target service parameters types.");
      }
      this.pushBinding(source, target, targetService);
   }

   private <I extends AccessibleObject> void pushBinding(I source, Composant target, I targetInterface)
           throws NoSuchInterfaceException, NoSuchComponentException {
      if (!this.hasInterface(source)) {
         throw new NoSuchInterfaceException("addBinding: no source interface.");
      }
      checkComposant:
      {
         for (Composant c : composants) {
            if (c.equals(target)) {
               break checkComposant;
            } // if
         } // for
         throw new NoSuchComponentException("addBinding: no target component.");
      }
      if (!target.hasInterface(targetInterface)) {
         throw new NoSuchInterfaceException("addBinding: no targetInterface interface in target Component.");
      }
      bindings.add(new Binding<I>(source, target, targetInterface));
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
      loops:
      {
         for (Field f : source.getClass().getDeclaredFields()) {
            if (f.getName().equals(name)) {
               field = f;
               break loops;
            }
         }
         for (Method m : source.getClass().getDeclaredMethods()) {
            if (m.getName().equals(name)) {
               method = m;
               break loops;
            }
         }
         return;
      }

      List<Connecteur> conns = null;
      if (field != null) {
         conns = this.connecteurs.get(source, field);
      } else if (method != null) {
         conns = this.connecteurs.get(source, method);
      }
      for (Connecteur conn : conns) {
         conn.glue();
      } // for

   }

   public List<Composant> getComposants() {
      return Collections.unmodifiableList(composants);
   }
}
