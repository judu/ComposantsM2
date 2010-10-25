package fr.univnantes.alma.hadlm2.composant;

import fr.univnantes.alma.hadlm2.connecteur.Connecteur;
import fr.univnantes.alma.hadlm2.connecteur.ConnecteurPool;
import fr.univnantes.alma.hadlm2.exceptions.NoSuchComponentException;
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

    public abstract List<Connecteur> getConnecteurs(Composant source, Method roleFrom);

    public void addConnecteur(Connecteur conn) throws NoSuchComponentException {
        Composant source = conn.getSource();
        Composant cible = conn.getCible();
        checkSource:
        {
            for (Composant comp : composants) {
                if (comp.equals(source)) {
                    break checkSource;
                } // if
            } // for
            throw new NoSuchComponentException("No source component");
        } // checkSource
        checkCible:
        {
            for (Composant comp : composants) {
                if (comp.equals(cible)) {
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
