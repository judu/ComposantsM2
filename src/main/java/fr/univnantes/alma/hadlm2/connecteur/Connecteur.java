/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univnantes.alma.hadlm2.connecteur;

import fr.univnantes.alma.hadlm2.composant.Composant;
import fr.univnantes.alma.hadlm2.exceptions.WrongTypeException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 *
 * @author indy
 */
public abstract class Connecteur<F extends AccessibleObject, T extends AccessibleObject> {

   protected Composant source;

   protected Composant cible;

   protected F roleFrom;

   protected T roleTo;

   protected Connecteur() {
   }

   public Connecteur(Composant source, F roleFrom, Composant cible, T roleTo) throws WrongTypeException {
      //Check les types
      Class from;
      Class to;


      if (roleFrom instanceof Method) {
         from = ((Method) roleFrom).getReturnType();
      } else if (roleFrom instanceof Field) {
         from = ((Field) roleFrom).getType();
      } else {
         throw new WrongTypeException("roleFrom should be a Field or a Method");
      }

      if (roleTo instanceof Method) {
         Class[] cto = ((Method) roleTo).getParameterTypes();
         if (cto.length == 1) {
            to = cto[0];
         } else {
            throw new WrongTypeException("roleTo is a Method : it should have only one parameter");
         }

      } else if (roleTo instanceof Field) {
         to = ((Field) roleTo).getType();
      } else {
         throw new WrongTypeException("roleTo should be a Field or a Method");
      }

      if (!to.isAssignableFrom(from)) {
         StringBuilder builder = new StringBuilder();
         builder.append("got ").append(from.getName()).append(" -> ").
                 append(to.getName()).append(". Should be the same type or hierarchy.");
         throw new WrongTypeException(builder.toString());
      }

      this.roleFrom = roleFrom;
      this.roleTo = roleTo;

      this.source = source;
      this.cible = cible;
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

   public final F getRoleFrom() {
      return roleFrom;
   }

   public final T getRoleTo() {
       return roleTo;
   }

   public abstract void glue();
}
