package fr.unice.l3.allumettes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import fr.unice.l3.allumettes.engine.JeuDesAllumettes;

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
