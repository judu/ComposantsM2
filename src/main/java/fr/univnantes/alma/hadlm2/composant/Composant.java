package fr.univnantes.alma.hadlm2.composant;

import java.util.Observable;

/**
 *
 * @author judu
 */
public abstract class Composant extends Observable {
   
	public void call(String service, Object... args){
		Object[] toObs = {service, args};
		this.notifyObservers(toObs);
	}
	
}
