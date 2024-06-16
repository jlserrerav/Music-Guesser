package es.studium.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Dificultad extends AppCompatActivity {
    Button btnFacil;
    Button btnNormal;
    Button btnDificil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dificultad);

        btnFacil = findViewById(R.id.btnFacil);
        btnNormal = findViewById(R.id.btnNormal);
        btnDificil = findViewById(R.id.btnDificil);

        btnFacil.setBackgroundColor(Color.BLACK);
        btnNormal.setBackgroundColor(Color.BLACK);
        btnDificil.setBackgroundColor(Color.BLACK);


        btnFacil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirDificultadFacil();
            }
        });

        btnNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirDificultadNormal();
            }
        });

        btnDificil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirDificultadDificil();
            }
        });
    }

    private void abrirDificultadFacil() {
        Intent intent = new Intent(Dificultad.this, Facil.class);
        intent.putExtra("username", "el_nombre_de_usuario");
        startActivity(intent);

    }

    private void abrirDificultadNormal() {
        Intent intent = new Intent(Dificultad.this, Normal.class);
        startActivity(intent);
    }

    private void abrirDificultadDificil() {
        Intent intent = new Intent(Dificultad.this, Dificil.class);
        startActivity(intent);
    }
}