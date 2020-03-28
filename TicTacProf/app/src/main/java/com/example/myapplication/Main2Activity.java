package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    private int compteurCases;
    private int PointDuJoueur1;
    private int PointDuJoueur2;

    private boolean TourDuJoueur1 = true;

    private MediaPlayer mediaplayer;
    private Button[][] bouton = new Button[3][3];

    private TextView textViewDuJoueur1;
    private TextView textViewDuJoueur2;
    private TextView textViewScoreDuJoueur1;
    private TextView textViewScoreDuJoueur2;

    final String EXTRA_TEXT1 = "text_to_display";
    final String EXTRA_TEXT2 = "text_to_display";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();

        this.mediaplayer = MediaPlayer.create(getApplicationContext(), R.raw.my_sound);

        textViewDuJoueur1 = findViewById(R.id.text_view_j1);
        textViewDuJoueur2 = findViewById(R.id.text_view_j2);
        textViewScoreDuJoueur1 = findViewById(R.id.point_j1);
        textViewScoreDuJoueur2 = findViewById(R.id.point_j2);

        //Recuperation des données de l'activité précédente
        textViewDuJoueur1.setText(intent.getStringExtra("EXTRA_TEXT1"), TextView.BufferType.SPANNABLE);
        textViewDuJoueur2.setText(intent.getStringExtra("EXTRA_TEXT2"), TextView.BufferType.SPANNABLE);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String emplacementBouton = "bouton_" + i + j;
                //on retrouve alors les identifiants des boutons créés dans le activity-main.
                int res = getResources().getIdentifier(emplacementBouton, "id", getPackageName());
                bouton[i][j] = findViewById(res);
                bouton[i][j].setOnClickListener(this);
            }
        }
        Button recommencer = findViewById(R.id.bouton_recommencer);
        recommencer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remiseAZeroDesPoints();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (!((Button) view).getText().toString().equals("")) {
            return;
        }
        if (TourDuJoueur1) {
            ((Button) view).setText(textViewDuJoueur1.getText());

        } else {
            ((Button) view).setText(textViewDuJoueur2.getText());
        }

        compteurCases++;

        //Partie pour le comptage des points
        if (quiAGagne()) {
            if (TourDuJoueur1) {
                joueur1Gagne();
            } else {
                joueur2Gagne();
            }
        } else if (compteurCases == 9) {
            affichageFinDuJeu();
        } else {
            TourDuJoueur1 = !TourDuJoueur1;
        }

    }

    //Fonction pour savoir qui gagne, appeler si dessus dans le onClick.
    private boolean quiAGagne() {
        String[][] grille = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grille[i][j] = bouton[i][j].getText().toString();
            }
        }
        //Si la ligne est complete c'est true
        for (int i = 0; i < 3; i++) {
            if (grille[i][0].equals(grille[i][1]) && grille[i][0].equals(grille[i][2]) && !grille[i][0].equals("")) {
                return true;
            }
        }
        //Si la colonne est complete c'est true
        for (int i = 0; i < 3; i++) {
            if (grille[0][i].equals(grille[1][i]) && grille[0][i].equals(grille[2][i]) && !grille[0][i].equals("")) {
                return true;
            }
        }
        //Si la diagonale partant de la première case de la premiere ligne est complete c'est true
        if (grille[0][0].equals(grille[1][1]) && grille[0][0].equals(grille[2][2]) && !grille[0][0].equals("")) {
            return true;
        }
        //Si la diagonal partant de la derniere case de la premiere ligne est complete c'est true
        if (grille[0][2].equals(grille[1][1]) && grille[0][2].equals(grille[2][0]) && !grille[0][2].equals("")) {
            return true;
        }
        return false;
    }

    //Petite notification de fin de jeu cas joueur 1 gagne
    private void joueur1Gagne() {
        PointDuJoueur1 = PointDuJoueur1 + 1;
        Toast.makeText(this, textViewDuJoueur1.getText() + " à gagné", Toast.LENGTH_SHORT).show();
        miseAJourDesPoints();
        remiseAZeroDeLaGrille();
    }

    //Petite notification de fin de jeu cas joueur 2 gagne
    private void joueur2Gagne() {
        PointDuJoueur2 = PointDuJoueur2 + 1;
        Toast.makeText(this, textViewDuJoueur2.getText() + " à gagné", Toast.LENGTH_SHORT).show();
        miseAJourDesPoints();
        remiseAZeroDeLaGrille();
    }

    //Petite notification de fin de jeu cas égalité
    private void affichageFinDuJeu() {
        Toast.makeText(this, "Fin du jeu, personne n'a gagné", Toast.LENGTH_SHORT).show();
        remiseAZeroDeLaGrille();
    }

    //compteur des points
    private void miseAJourDesPoints() {
        textViewScoreDuJoueur1.setText("" + PointDuJoueur1);
        textViewScoreDuJoueur2.setText("" + PointDuJoueur2);
    }

    //Fonction utile pour recommencer un tour d'une partie, appeler lorsqu'on fini les 9 cases
    private void remiseAZeroDeLaGrille() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                bouton[i][j].setText("");
            }
        }
        compteurCases = 0;
        TourDuJoueur1 = true;
    }

    //Fonction qui est appeler lorsqu'on veut remettre la partie à zéro, même pour les points
    private void remiseAZeroDesPoints() {
        PointDuJoueur1 = 0;
        PointDuJoueur2 = 0;
        miseAJourDesPoints();
        remiseAZeroDeLaGrille();
    }

    //Fonction qui permet de lancer la musique
    public void jouerMusique(View view) {
        mediaplayer.start();
    }
}
