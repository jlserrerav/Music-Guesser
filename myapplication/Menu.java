package es.studium.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Menu extends AppCompatActivity {
    Button btnJugar;
    Button btnAyuda;
    Button btnSalir;

    Button btnRanking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        btnJugar = findViewById(R.id.btnJugar);
        btnAyuda = findViewById(R.id.btnAyuda);
        btnSalir = findViewById(R.id.btnEntrar);
        btnRanking = findViewById(R.id.btnRanking);

        btnJugar.setBackgroundColor(Color.BLACK);
        btnAyuda.setBackgroundColor(Color.BLACK);
        btnSalir.setBackgroundColor(Color.BLACK);
        btnRanking.setBackgroundColor(Color.BLACK);

        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salir();
            }
        });
        btnJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirDificultad();
            }
        });

        btnAyuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirAyuda();
            }
        });

        btnRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirRanking();
            }
        });
    }

    private void abrirAyuda() {
        Intent intent = new Intent(Menu.this, Ayuda.class);
        startActivity(intent);
    }

    private void abrirRanking() {
        Intent intent = new Intent(Menu.this, ListaActivity.class);
        startActivity(intent);
    }

    private void abrirDificultad() {
        Intent intent = new Intent(Menu.this, Dificultad.class);
        startActivity(intent);
    }

    private void salir() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}


