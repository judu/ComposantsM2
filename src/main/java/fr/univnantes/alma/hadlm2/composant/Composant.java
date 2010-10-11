package fr.univnantes.alma.hadlm2.composant;

import java.lang.reflect.Method;
import java.util.Observable;

/**
 *
 * @author judu
 */
public abstract class Composant extends Observable {
   
	
	
	public void call(Method method, Object... args){
		Object[] toObs = {method, args};
		this.notifyObservers(toObs);
	}
	
}
