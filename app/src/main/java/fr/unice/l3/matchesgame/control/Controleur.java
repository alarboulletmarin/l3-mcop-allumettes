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

    public void start() {
        partie = new Partie();
        partie.execute();
    }

    public void stop() {
        if (partie != null) {
            partie.cancel(true);
        }
    }

    public void setSelectedCount(int count) {
        allumettesView.setSelectedCount(count);
    }

    public void setVisibleCount(int count) {
        allumettesView.setVisibleCount(count);
    }

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

        protected void onProgressUpdate(UpdateMessage... values) {
            super.onProgressUpdate(values);
            UpdateMessage updateMessage = values[0];
            updateMessage.updateView();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            gameActivity.updateView(s);
        }
    }

    private abstract class UpdateMessage {
        protected int nbAllumettes;
        protected Joueur j;

        public UpdateMessage(Joueur j, int nbAllumettes) {
            this.j = j;
            this.nbAllumettes = nbAllumettes;
        }

        public abstract void updateView();
    }

    private class MainUpdateMessage extends UpdateMessage {
        public MainUpdateMessage(Joueur j, int nbAllumettesChoisies) {
            super(j, nbAllumettesChoisies);
        }

        @Override
        public void updateView() {
            gameActivity.updateView("Le joueur : " + j.toString() + " choisit " + " " + nbAllumettes + " allumettes.\n\n");
        }
    }

    private class PostUpdateMessage extends UpdateMessage {
        public PostUpdateMessage(Joueur j, int nbAllumettesVisibles) {
            super(j, nbAllumettesVisibles);
        }

        @Override
        public void updateView() {
            gameActivity.updateView("Il reste " + nbAllumettes + " allumettes.\n\n**********\n\n ");
        }
    }
}



