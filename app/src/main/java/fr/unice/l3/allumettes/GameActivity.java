package fr.unice.l3.allumettes;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import fr.unice.l3.allumettes.view.Allumettes;


public class GameActivity extends AppCompatActivity {

    private Allumettes allumettesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        allumettesView = findViewById(R.id.allumettes);

        // Ceci est juste pour tester la sélection d'allumettes, vous pouvez remplacer cette partie
        // par la logique de votre jeu pour mettre à jour le nombre d'allumettes sélectionnées
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                allumettesView.setSelectedCount(3);
            }
        }, 3000);
    }
}