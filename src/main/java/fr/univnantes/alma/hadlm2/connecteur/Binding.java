/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univnantes.alma.hadlm2.connecteur;

import fr.univnantes.alma.hadlm2.composant.Composant;

/**
 *
 * @author judu
 */
public abstract class Binding {

   protected String src;

   protected  Composant target;

   protected  String targetInterface;


   public Binding(String src, Composant target, String targetI) {
      this.src = src;
      this.target = target;
      this.targetInterface = targetI;
   }



   public abstract void glue(Object... args);

}
