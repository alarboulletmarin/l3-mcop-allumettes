package fr.unice.l3.allumettes.player;

import java.util.Random;

public class JoueurRandom extends Joueur {

    private final Random random;

    public JoueurRandom(String s) {
        super(s);
        random = new Random();
    }

    @Override
    public int jouer(int nbAllumettesVisibles) {
        return random.nextInt(3) + 1; // Choix aléatoire entre 1 et 3 allumettes
    }
}
