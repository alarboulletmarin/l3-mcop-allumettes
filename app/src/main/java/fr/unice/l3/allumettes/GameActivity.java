package fr.unice.l3.allumettes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import fr.unice.l3.allumettes.engine.JeuDesAllumettes;

import fr.unice.l3.allumettes.player.JoueurHumain;
import fr.unice.l3.allumettes.player.JoueurRandom;
import fr.unice.l3.allumettes.player.JoueurSmart;

import fr.unice.l3.allumettes.view.Allumettes;

import fr.unice.l3.matchesgame.control.Controleur;
import fr.unice.l3.matchesgame.control.InteractionTactile;


public class GameActivity extends AppCompatActivity {
    private JeuDesAllumettes jeu;
    private Controleur controleur;
    private Allumettes allumettesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        InteractionTactile interactionTactile = new InteractionTactile(this);

        jeu = new JeuDesAllumettes(21);
//        jeu.ajouterJoueur(new JoueurHumain("Joueur 1", interactionTactile));

        String player1Name = getIntent().getStringExtra("player1_name");
        int player1Type = getIntent().getIntExtra("player1_type", 0);
        String player2Name = getIntent().getStringExtra("player2_name");
        int player2Type = getIntent().getIntExtra("player2_type", 0);

        switch (player1Type) {
            case 0: // Joueur Humain
                jeu.ajouterJoueur(new JoueurHumain(player1Name, interactionTactile));
                break;
            case 1: // Joueur Smart
                jeu.ajouterJoueur(new JoueurSmart(player1Name));
                break;
            case 2: // Joueur Random
                jeu.ajouterJoueur(new JoueurRandom(player1Name));
                break;
        }

        switch (player2Type) {
            case 0: // Joueur Humain
                jeu.ajouterJoueur(new JoueurHumain(player2Name, interactionTactile));
                break;
            case 1: // Joueur Smart
                jeu.ajouterJoueur(new JoueurSmart(player2Name));
                break;
            case 2: // Joueur Random
                jeu.ajouterJoueur(new JoueurRandom(player2Name));
                break;
        }



        jeu.ajouterJoueur(new JoueurSmart("JoueurSmart 1"));
        jeu.ajouterJoueur(new JoueurSmart("JoueurSmart 2"));


        allumettesView = findViewById(R.id.allumettes);
        controleur = new Controleur(jeu, this, allumettesView);


        Button startButton = findViewById(R.id.start_game_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controleur.start();
            }
        });

        Button validateButton = findViewById(R.id.validate_button);
        validateButton.setOnClickListener(interactionTactile);
        validateButton.requestFocus();
        allumettesView.setOnClickListener(interactionTactile);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        controleur.stop();
    }

    public void updateView(String message) {
        TextView historyTextView = findViewById(R.id.historique_text);
        historyTextView.append(message);

        ScrollView scrollView = findViewById(R.id.historique_scroll);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }

    public void updateGameView(int nbSelected) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                allumettesView.setSelectedCount(nbSelected);
                allumettesView.invalidate();
            }
        });
    }
}
