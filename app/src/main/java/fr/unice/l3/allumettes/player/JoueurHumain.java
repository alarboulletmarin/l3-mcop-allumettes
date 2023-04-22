package fr.unice.l3.allumettes.player;

import fr.unice.l3.matchesgame.control.InteractionTactile;

public class JoueurHumain extends Joueur {

    private final InteractionTactile interaction;

    public JoueurHumain(String s, InteractionTactile interaction) {
        super(s);
        this.interaction = interaction;
    }

    @Override
    public int jouer(int nbAllumettesVisibles) throws InterruptedException {
        int nbSelected;
        Object synchro = interaction.startTurn();
        synchronized (synchro) {
            synchro.wait();
            nbSelected = interaction.endTurn();
        }
        return nbSelected;
    }

    @Override
    public void attendre() {
        // Pas besoin d'attente artificielle pour un joueur humain
    }
}
