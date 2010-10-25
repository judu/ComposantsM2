package fr.univnantes.alma.hadlm2.composant;

import fr.univnantes.alma.hadlm2.connecteur.Connecteur;
import fr.univnantes.alma.hadlm2.connecteur.ConnecteurSS;
import fr.univnantes.alma.hadlm2.connecteur.ConnecteurPool;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

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

    public abstract ConnecteurSS getConnecteur(Composant source, Method roleFrom);

    public void addConnecteur(ConnecteurSS conn) {
        this.connecteurs.add(conn);
    }

    public final void addComposant(Composant comp) {
        composants.add(comp);
        comp.addObserver(this);
        comp.setParent(this);
    }

    @Override
    public void update(Observable o, Object arg) {

        if (!(o instanceof Composant)) {
            //TODO: add logs
            return;
        }

        Composant source = (Composant) o;

        if (arg instanceof Field) {
            Field from = (Field) arg;
            List<Connecteur> conns = this.connecteurs.get(source, from);
            for (Connecteur conn : conns) {
                conn.glue();
            }
        } else if (arg instanceof Method) {
            Method from = (Method) arg;
            List<Connecteur> conns = this.connecteurs.get(source, from);
            for (Connecteur conn : conns) {
                conn.glue();
            }
        } else {
            //TODO: add logs
        }

    }
}
