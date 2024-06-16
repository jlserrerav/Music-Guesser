package es.studium.myapplication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Normal extends AppCompatActivity {

    ImageView imgPlay;
    SeekBar seekBar;
    Handler handler;
    int progreso = 0;

    private int puntos = 0; // Variable para almacenar los puntos

    final int tiempo = 1000;  // Tiempo entre actualizaciones en milisegundos (1 segundo)
    final int duracionTotal = 8;  // Duración total en segundos

    MediaPlayer mediaPlayer;
    private int[] listaCanciones = {R.raw.intheend, R.raw.letsgeit, R.raw.allstar, R.raw.november, R.raw.gansta}; // Lista de canciones
    private List<Integer> cancionesRestantes = new ArrayList<>();

    private int respuestasConsecutivas = 0;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normal);

        imgPlay = findViewById(R.id.imgPlay);
        seekBar = findViewById(R.id.seekBar);
        handler = new Handler();

        for (int cancion : listaCanciones) {
            cancionesRestantes.add(cancion);
        }

        seekBar.setMax(duracionTotal); // Configura el max de la SeekBar para 8 segundos
        siguienteCancion();

        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;  // Evita la interacción del usuario
            }
        });

        imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progreso = 0;
                seekBar.setProgress(progreso);
                progresoBarra();
                if (mediaPlayer != null) {
                    mediaPlayer.start();
                }
            }
        });
    }

    private void progresoBarra() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progreso++;
                seekBar.setProgress(progreso);
                if (progreso < duracionTotal) {
                    handler.postDelayed(this, tiempo);
                }
            }
        }, tiempo);

    }

    private void mostrarOpciones(int cancion) {
        Button opcion1 = findViewById(R.id.opcion1);
        Button opcion2 = findViewById(R.id.opcion2);
        Button opcion3 = findViewById(R.id.opcion3);
        Button opcion4 = findViewById(R.id.opcion4);

        // Restablecer el color de las opciones
        opcion1.setBackgroundColor(Color.BLACK);
        opcion2.setBackgroundColor(Color.BLACK);
        opcion3.setBackgroundColor(Color.BLACK);
        opcion4.setBackgroundColor(Color.BLACK);

        // Lista para almacenar las opciones de respuesta
        List<String> opciones = new ArrayList<>();
        String respuestaCorrecta = "";

        // Inicializa las opciones correctas
        if (cancion == R.raw.gansta) {
            opciones.add("Gangsta Paradise - Coolio");
            opciones.add("C U When You Get There - Coolio");
            opciones.add("Fantastic Voyage - Coolio");
            opciones.add("2 Minutes & 21 Seconds Of Funk - Coolio");
            respuestaCorrecta = "Gangsta Paradise - Coolio";
        } else if (cancion == R.raw.november) {
            opciones.add("November Rain - Gun N Roses");
            opciones.add("Paradise City - Gun N Roses");
            opciones.add("You Could Be Mine - Gun N Roses");
            opciones.add("Civil War - Gun N Roses");
            respuestaCorrecta = "November Rain - Gun N Roses";
        } else if (cancion == R.raw.intheend) {
            opciones.add("In the End - Linkin Park");
            opciones.add("Numb - Linkin Park");
            opciones.add("Crawling - Linkin Park");
            opciones.add("What I've Done - Linkin Park");
            respuestaCorrecta = "In the End - Linkin Park";
        } else if (cancion == R.raw.allstar) {
            opciones.add("All Star - Smash Mouth");
            opciones.add("So Insane - Smash Mouth");
            opciones.add("Every Day Superhero - Smash Mouth");
            opciones.add("Come on Come on - Smash Mouth");
            respuestaCorrecta = "All Star - Smash Mouth";
        } else if (cancion == R.raw.letsgeit) {
            opciones.add("Let's Get It Started - Black Eyed Peas");
            opciones.add("Pump It - Black Eyed Peas");
            opciones.add("Shut Up - Black Eyed Peas");
            opciones.add("I Gotta Feeling - Black Eyed Peas");
            respuestaCorrecta = "Let's Get It Started - Black Eyed Peas";
        }

        // Mezclar las opciones de respuesta
        Collections.shuffle(opciones);

        // Asignar las opciones a los botones
        opcion1.setText(opciones.get(0));
        opcion2.setText(opciones.get(1));
        opcion3.setText(opciones.get(2));
        opcion4.setText(opciones.get(3));

        // Definir el comportamiento de los botones según la opción correcta
        View.OnClickListener listenerCorrecto = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acertado();
            }
        };

        View.OnClickListener listenerIncorrecto = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarMensaje("Te has equivocado" + "\n" + "Total de puntos: " + puntos, Color.RED);
                respuestasConsecutivas = 0; // Reiniciar contador de respuestas correctas consecutivas
            }
        };

        // Asignar los listeners a los botones
        opcion1.setOnClickListener(opcion1.getText().equals(respuestaCorrecta) ? listenerCorrecto : listenerIncorrecto);
        opcion2.setOnClickListener(opcion2.getText().equals(respuestaCorrecta) ? listenerCorrecto : listenerIncorrecto);
        opcion3.setOnClickListener(opcion3.getText().equals(respuestaCorrecta) ? listenerCorrecto : listenerIncorrecto);
        opcion4.setOnClickListener(opcion4.getText().equals(respuestaCorrecta) ? listenerCorrecto : listenerIncorrecto);
    }

    private void mostrarMensaje(String mensaje, int color) {
        SpannableString spannableString = new SpannableString(mensaje);
        spannableString.setSpan(new ForegroundColorSpan(color), 0, mensaje.length(), 0);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(spannableString)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        Intent intent = new Intent(Normal.this, Dificultad.class);
                        startActivity(intent);
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void acertado() {
        respuestasConsecutivas++; // Incrementar contador de respuestas correctas consecutivas
        int puntosGanados = 5 * respuestasConsecutivas;
        puntos += puntosGanados; // Actualizar puntos según la nueva fórmula (5, 10, 15, 20, ...)

        Toast.makeText(this, "Has ganado " + puntosGanados + " puntos. Total de puntos: " + puntos, Toast.LENGTH_SHORT).show();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Has Acertado y has ganado " + puntosGanados + " puntos. Total de puntos: " + puntos)
                .setPositiveButton("Ver Ranking", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        abrirRanking();
                    }
                })
                .setPositiveButton("Siguiente ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        siguienteCancion();
                    }
                })
                .setNegativeButton("Salir", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void siguienteCancion() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }

        // Verificar si quedan canciones restantes
        if (cancionesRestantes.isEmpty()) {
            mostrarRankingFinal();
            return;
        }

        // Seleccionar una nueva canción aleatoria de las canciones restantes
        Random random = new Random();
        int indiceCancionAleatoria = random.nextInt(cancionesRestantes.size());
        int cancionActual = cancionesRestantes.remove(indiceCancionAleatoria);

        mediaPlayer = MediaPlayer.create(this, cancionActual);

        mostrarOpciones(cancionActual);
    }

    private void mostrarRankingFinal() {
        // Mostrar Toast y Log con los puntos finales
        Toast.makeText(this, "Juego terminado. Total de puntos: " + puntos, Toast.LENGTH_LONG).show();
        Log.d("Puntos", "Juego terminado. Total de puntos: " + puntos);
        System.out.println("Juego terminado. Total de puntos: " + puntos);

        // Abrir la actividad de ranking
        abrirRanking();
    }

    private void abrirRanking() {
        Intent intent = new Intent(Normal.this, ListaActivity.class);
        startActivity(intent);
    }
}
