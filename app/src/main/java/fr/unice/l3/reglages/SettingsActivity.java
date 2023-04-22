package fr.unice.l3.reglages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import fr.unice.l3.allumettes.GameActivity;
import fr.unice.l3.allumettes.R;

public class SettingsActivity extends AppCompatActivity {
    // ...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Configurez les spinners et les autres éléments de l'interface ici

        Button submitButton = findViewById(R.id.submit_settings_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String player1Name = ((EditText) findViewById(R.id.player1_name)).getText().toString();
                int player1Type = ((Spinner) findViewById(R.id.player1_type)).getSelectedItemPosition();
                String player2Name = ((EditText) findViewById(R.id.player2_name)).getText().toString();
                int player2Type = ((Spinner) findViewById(R.id.player2_type)).getSelectedItemPosition();

                Intent intent = new Intent(SettingsActivity.this, GameActivity.class);
                intent.putExtra("player1_name", player1Name);
                intent.putExtra("player1_type", player1Type);
                intent.putExtra("player2_name", player2Name);
                intent.putExtra("player2_type", player2Type);
                startActivity(intent);
            }
        });
    }
}
