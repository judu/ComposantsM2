package fr.univnantes.alma.hadlm2.composant;

import fr.alma.hadl.annotations.ProvidedInterface;
import fr.alma.hadl.annotations.RequiredInterface;
import fr.alma.hadl.annotations.RunInterface;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author judu
 */
public abstract class Composant extends Observable {

   protected Configuration parent;

   public void call(String service) {
      this.setChanged();
      this.notifyObservers(service);
   }

   public void set(String port, Object value) {
      for (Field f : this.getClass().getDeclaredFields()) {
         if (f.getName().equals(port)
                 && f.getType().equals(value.getClass())) {
            try {
               f.set(this, value);
            } catch (IllegalArgumentException ex) {
               Logger.getLogger(Composant.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
               Logger.getLogger(Composant.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.call(port);
            break;
         } // if
      } // for
   }

   protected final <I extends AccessibleObject> boolean hasInterface(I iface) {
      if (iface.getClass().equals(Field.class)) {
         for (Field f : this.getClass().getDeclaredFields()) {
            if (f.equals(iface)) {
               return true;
            }
         }
      } else if (iface.getClass().equals(Method.class)) {
         for (Method m : this.getClass().getDeclaredMethods()) {
            if (m.equals(iface)) {
               return true;
            }
         }
      }
      return false;
   }

   public final AccessibleObject getInterfaceForName(String name, Boolean from) {
      Field[] fs = this.getClass().getDeclaredFields();

      for (int i = 0; i < fs.length; ++i) {
         if (from) {
            ProvidedInterface annot = fs[i].getAnnotation(ProvidedInterface.class);
            if ((annot != null) && annot.value().equals(name)) {
               return fs[i];
            }
         } else {
            RequiredInterface annot = fs[i].getAnnotation(RequiredInterface.class);
            if (annot != null && annot.value().equals(name)) {
               return fs[i];
            }
         }
      }

      Method[] ms = this.getClass().getDeclaredMethods();
      for (int i = 0; i < ms.length; ++i) {
         if (from) {
            ProvidedInterface annot = ms[i].getAnnotation(ProvidedInterface.class);
            if ((annot != null) && annot.value().equals(name)) {
               return ms[i];
            }
         } else {
            RequiredInterface annot = ms[i].getAnnotation(RequiredInterface.class);
            if (annot != null && annot.value().equals(name)) {
               return ms[i];
            }
         }
      }
      return null;
   }

   public final Method getEntryPoint() {
      Method[] ms = this.getClass().getDeclaredMethods();
      for (int i = 0; i < ms.length; ++i) {
         if (ms[i].getAnnotation(RunInterface.class) != null) {
            return ms[i];
         }
      }
      return null;
   }

   public void setParent(Configuration c) {
      parent = c;
   }
}
