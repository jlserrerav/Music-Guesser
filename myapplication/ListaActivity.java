package es.studium.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ListaActivity extends AppCompatActivity {

    ListView listView;
    Button btnVolver;
    BDLogica BDLogica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ranking);

        btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setBackgroundColor(Color.BLACK);
        listView = findViewById(R.id.listView);
        BDLogica = new BDLogica(this);
        BDLogica.open();

        // Obtener la lista de jugadores ordenada por puntos de mayor a menor
        List<Jugador> listaJugadores = BDLogica.obtenerTopJugadores(10);

        // Crear el adaptador personalizado y establecerlo en el ListView
        JugadorAdapter adapter = new JugadorAdapter(this, listaJugadores);
        listView.setAdapter(adapter);

        // Agregar el OnClickListener al botón Volver
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListaActivity.this, Menu.class);
                startActivity(intent); // Terminar la actividad actual y volver a la actividad anterior
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BDLogica.close();
    }
}
