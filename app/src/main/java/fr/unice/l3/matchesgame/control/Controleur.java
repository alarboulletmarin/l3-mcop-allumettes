package fr.unice.l3.matchesgame.control;

import android.os.AsyncTask;
import android.util.Log;

import fr.unice.l3.allumettes.GameActivity;
import fr.unice.l3.allumettes.engine.JeuDesAllumettes;
import fr.unice.l3.allumettes.player.Joueur;
import fr.unice.l3.allumettes.view.Allumettes;

public class Controleur {
    private JeuDesAllumettes jeu;
    private AsyncTask<Void, UpdateMessage, String> partie;
    private GameActivity gameActivity;
    private Allumettes allumettesView;

    public Controleur(JeuDesAllumettes jeu, GameActivity gameActivity, Allumettes allumettesView) {
        this.jeu = jeu;
        this.gameActivity = gameActivity;
        this.allumettesView = allumettesView;
    }

    /**
     * Démarre une nouvelle partie
     */
    public void start() {
        partie = new Partie();
        partie.execute();
    }

    /**
     * Arrête la partie en cours
     */
    public void stop() {
        if (partie != null) {
            partie.cancel(true);
        }
    }

    /**
     * Met à jour l'affichage du nombre d'allumettes sélectionnées
     *
     * @param count le nombre d'allumettes sélectionnées
     */
    public void setSelectedCount(int count) {
        allumettesView.setSelectedCount(count);
    }

    /**
     * Met à jour l'affichage du nombre d'allumettes visibles
     *
     * @param count le nombre d'allumettes visibles
     */
    public void setVisibleCount(int count) {
        allumettesView.setVisibleCount(count);
    }

    /**
     * Classe interne pour la gestion de la partie
     */
    private class Partie extends AsyncTask<Void, UpdateMessage, String> {
        @Override
        protected String doInBackground(Void... params) {
            try {
                jeu.démarrerNouvellePartie();
                while (jeu.partieEnCours()) {
                    Joueur j = jeu.aQuiDeJouer();
                    int nbAllumettesChoisies = j.jouer(jeu.getNombreAllumettesVisible());
                    setSelectedCount(nbAllumettesChoisies);
                    publishProgress(new MainUpdateMessage(j, nbAllumettesChoisies));
                    j.attendre();
                    jeu.jouerUnTour();
                    int nbAllumettesVisibles = jeu.getNombreAllumettesVisible();
                    setVisibleCount(nbAllumettesVisibles);
                    publishProgress(new PostUpdateMessage(j, nbAllumettesVisibles));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Joueur gagnant = jeu.getGagnant();
            return "Le gagnant est " + gagnant;
        }

        /**
         * Méthode appelée par le thread UI pour mettre à jour la vue
         *
         * @param values
         */
        protected void onProgressUpdate(UpdateMessage... values) {
            super.onProgressUpdate(values);
            UpdateMessage updateMessage = values[0];
            updateMessage.updateView();
        }

        /**
         * Méthode appelée par le thread UI pour mettre à jour la vue
         *
         * @param s
         */
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            gameActivity.updateView(s);
        }
    }

    /**
     * Classe abstraite pour les messages de mise à jour de la vue
     */
    private abstract class UpdateMessage {
        protected int nbAllumettesChoisies;
        protected Joueur j;

        /**
         * Constructeur
         *
         * @param j            le joueur
         * @param nbAllumettesChoisies le nombre d'allumettes
         */
        public UpdateMessage(Joueur j, int nbAllumettesChoisies) {
            this.j = j;
            this.nbAllumettesChoisies = nbAllumettesChoisies;
        }

        /**
         * Méthode appelée par le thread UI pour mettre à jour la vue
         */
        public abstract void updateView();
    }

    /**
     * Classe interne pour les messages de mise à jour de la vue
     */
    private class MainUpdateMessage extends UpdateMessage {
        public MainUpdateMessage(Joueur j, int nbAllumettesChoisies) {
            super(j, nbAllumettesChoisies);
        }

        /**
         * Méthode appelée par le thread UI pour mettre à jour la vue.
         * Elle affiche le nombre d'allumettes choisies
         */
        @Override
        public void updateView() {
            gameActivity.updateView("Le joueur : " + j.toString() + " choisit " + " " + nbAllumettesChoisies + " allumettes.\n\n");
        }
    }

    /**
     * Classe interne pour les messages de mise à jour de la vue.
     * Elle affiche le nombre d'allumettes restantes
     */
    private class PostUpdateMessage extends UpdateMessage {
        public PostUpdateMessage(Joueur j, int nbAllumettesVisibles) {
            super(j, nbAllumettesVisibles);
        }

        /**
         * Méthode appelée par le thread UI pour mettre à jour la vue.
         * Elle affiche le nombre d'allumettes restantes
         */
        @Override
        public void updateView() {
            gameActivity.updateView("Il reste " + nbAllumettesChoisies + " allumettes.\n\n**********\n\n ");
        }
    }
}



