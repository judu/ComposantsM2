/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.univnantes.alma.hadlm2.connecteur;

import fr.univnantes.alma.hadlm2.composant.Composant;
import fr.univnantes.alma.hadlm2.exceptions.NoSuchInterfaceException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author judu
 */
public class ServiceBinding extends Binding {

   Method tgMethod;


   public ServiceBinding(String src, Composant target, String targetI) throws NoSuchInterfaceException {
      super(src,target,targetI);

      Class tgClass = target.getClass();
      try {
         this.tgMethod = tgClass.getDeclaredMethod(targetI);
      } catch (NoSuchMethodException ex) {
         throw new NoSuchInterfaceException("The component " + tgClass.getName() +" does not provide the service " + targetI);
      } catch (SecurityException ex) {
         Logger.getLogger(ServiceBinding.class.getName()).log(Level.SEVERE, null, ex);
      }
   }





   @Override
   public void glue(Object... args) {
      throw new UnsupportedOperationException("Not supported yet.");
   }



}
