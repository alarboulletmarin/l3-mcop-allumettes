package fr.unice.l3.matchesgame.control;

import android.view.View;

import fr.unice.l3.allumettes.GameActivity;
import fr.unice.l3.allumettes.R;
import fr.unice.l3.allumettes.view.Allumettes;

public class InteractionTactile implements View.OnClickListener {

    private final Object synchro;
    private int nbAllumettesSelectionnees;
    private boolean active;
    private final GameActivity gameActivity;

    /**
     * Constructeur
     *
     * @param gameActivity l'activité du jeu
     */
    public InteractionTactile(GameActivity gameActivity) {
        this.gameActivity = gameActivity;
        synchro = new Object();
        nbAllumettesSelectionnees = 1;
        active = false;
    }


    public Object startTurn() {
        active = true;
        return synchro;
    }

    public int endTurn() {
        active = false;
        return nbAllumettesSelectionnees;
    }

    /**
     * Méthode appelée par le jeu pour demander au joueur de jouer
     *
     * @param v la vue sur laquelle l'utilisateur a cliqué
     * @return le nombre d'allumettes que le joueur souhaite retirer
     * @throws InterruptedException
     */
    @Override
    public void onClick(View v) {
        if (active) {
            if (v instanceof Allumettes) {
                nbAllumettesSelectionnees = (nbAllumettesSelectionnees % 3) + 1;
                gameActivity.updateGameView(nbAllumettesSelectionnees);
            } else if (v.getId() == R.id.validate_button) {
                synchronized (synchro) {
                    synchro.notify();
                }
            }
        }
    }
}
