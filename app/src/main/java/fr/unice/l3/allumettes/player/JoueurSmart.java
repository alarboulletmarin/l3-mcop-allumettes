package fr.unice.l3.allumettes.player;

public class JoueurSmart extends Joueur {

    public JoueurSmart(String s) {
        super(s);
    }

    /**
     * Méthode appelée par le jeu pour demander au joueur de jouer
     * Il choisira toujours un nombre d'allumettes tel que (nbVisibles - nbChoisies) % 4 == 1 si cela est possible, sinon il choisira 1 allumette.
     *
     * @param nbAllumettesVisibles le nombre d'allumettes visibles sur le plateau
     * @return le nombre d'allumettes que le joueur souhaite retirer
     */
    @Override
    public int jouer(int nbAllumettesVisibles) {
        int nbChoisies = (nbAllumettesVisibles - 1) % 4;
        if (nbChoisies == 0) {
            nbChoisies = 1;
        }
        return nbChoisies;
    }
}

