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
