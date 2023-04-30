package fr.unice.l3.allumettes.player;

import fr.unice.l3.matchesgame.control.InteractionTactile;

public class JoueurHumain extends Joueur {

    private final InteractionTactile interaction;

    public JoueurHumain(String s, InteractionTactile interaction) {
        super(s);
        this.interaction = interaction;
    }

    /**
     * Méthode appelée par le jeu pour demander au joueur de jouer
     * @param nbAllumettesVisibles le nombre d'allumettes visibles sur le plateau
     * @return le nombre d'allumettes que le joueur souhaite retirer
     * @throws InterruptedException
     */
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

    /**
     * Met en pause l'exécution pour 1 seconde. Peut être utiliser par le Contrôleur
     * pour simuler le temps de jeu d'un tour pour un joueur IA.
     */
    @Override
    public void attendre() {
        // Pas besoin d'attente artificielle pour un joueur humain
    }
}
