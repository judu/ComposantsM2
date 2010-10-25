/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.univnantes.alma.hadlm2.connecteur;

import fr.univnantes.alma.hadlm2.composant.Composant;
import fr.univnantes.alma.hadlm2.exceptions.NoSuchInterfaceException;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author judu
 */
public class PortBinding extends Binding {

   Field targetField;

   public PortBinding(String src, Composant target, String targetPort) throws NoSuchInterfaceException {
      super(src, target, targetPort);
      Class tgClass = this.target.getClass();
      try {
         this.targetField = tgClass.getField(targetPort);
      } catch (NoSuchFieldException ex) {
         throw new NoSuchInterfaceException("The component "+ tgClass.getName() + " does not have the port " + targetPort);
      } catch (SecurityException ex) {
         Logger.getLogger(PortBinding.class.getName()).log(Level.SEVERE, null, ex);
      }
   }






   @Override
   public void glue(Object... args) {
      if(args.length > 1) {
         //TODO: log error
         return;
      }

      Object value = args[0];
      
   }

}
