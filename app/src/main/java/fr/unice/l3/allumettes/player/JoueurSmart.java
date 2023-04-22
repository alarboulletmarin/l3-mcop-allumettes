package fr.unice.l3.allumettes.player;

public class JoueurSmart extends Joueur {

    public JoueurSmart(String s) {
        super(s);
    }

    @Override
    public int jouer(int nbAllumettesVisibles) {
        int restantPourGagner = (nbAllumettesVisibles - 1) % 4;

        if (restantPourGagner > 0) {
            return restantPourGagner;
        } else {
            return 1;
        }
    }
}

