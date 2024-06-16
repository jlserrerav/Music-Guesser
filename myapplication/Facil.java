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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Facil extends AppCompatActivity {

    ImageView imgPlay;
    private int intentosRestantes = 2;
    private int puntos = 0; // Variable para almacenar los puntos
    private int respuestasConsecutivas = 0; // Contador para respuestas correctas consecutivas
    private int[] listaCanciones = {R.raw.acdc, R.raw.fireball, R.raw.gangam, R.raw.wakawaka, R.raw.queen_champion}; // Lista de canciones
    private List<Integer> cancionesRestantes = new ArrayList<>(); // Lista para gestionar canciones restantes

    TextView nIntentos;
    SeekBar seekBar;
    Handler handler;

    int progreso = 0;
    final int tiempo = 1000;  // Tiempo entre actualizaciones en milisegundos (1 segundo)
    final int duracionTotal = 15;  // Duración total en segundos

    MediaPlayer mediaPlayer;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facil);

        imgPlay = findViewById(R.id.imgPlay);
        seekBar = findViewById(R.id.seekBar);
        handler = new Handler();
        nIntentos = findViewById(R.id.txtIntentos);

        // Inicializar la lista de canciones restantes
        for (int cancion : listaCanciones) {
            cancionesRestantes.add(cancion);
        }
        seekBar.setMax(duracionTotal); // Configura el max de la SeekBar para 15 segundos
        siguienteCancion();


        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progreso = 0;
                seekBar.setProgress(progreso);
                progresoBarra();
                mediaPlayer.start();
            }
        });

        // Llamar a la función para mostrar las opciones de respuesta
        mostrarOpciones(cancionesRestantes.get(0));
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

        // Configurar las opciones de respuesta y la respuesta correcta según la canción actual
        if (cancion == R.raw.acdc) {
            opciones.add("Back in Black - AC/DC");
            opciones.add("Thunderstruck - AC/DC");
            opciones.add("Highway to Hell - AC/DC");
            opciones.add("You Shook Me All Night Long - AC/DC");
            respuestaCorrecta = "Back in Black - AC/DC";
        } else if (cancion == R.raw.fireball) {
            opciones.add("Give Me Everything - Pitbull");
            opciones.add("Timber - Pitbull");
            opciones.add("Don't Stop the Party - Pitbull");
            opciones.add("Fireball - Pitbull");
            respuestaCorrecta = "Fireball - Pitbull";
        } else if (cancion == R.raw.gangam) {
            opciones.add("Gangnam Style - PSY");
            opciones.add("Gentleman - PSY");
            opciones.add("Hangover - PSY");
            opciones.add("Oppa Is Just My Style - PSY");
            respuestaCorrecta = "Gangnam Style - PSY";
        } else if (cancion == R.raw.wakawaka) {
            opciones.add("Hips Don't Lie - Shakira");
            opciones.add("La Tortura - Shakira");
            opciones.add("Waka Waka (This Time for Africa) - Shakira");
            opciones.add("Whenever, Wherever - Shakira");
            respuestaCorrecta = "Waka Waka (This Time for Africa) - Shakira";
        } else if (cancion == R.raw.queen_champion) {
            opciones.add("Shape of You - Ed Sheeran");
            opciones.add("Hey Jude - Beatles");
            opciones.add("We Are the Champions - Queen");
            opciones.add("Imagine - John Lennon");
            respuestaCorrecta = "We Are the Champions - Queen";
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
                if (intentosRestantes > 0) {
                    acertado();
                }
            }
        };

        View.OnClickListener listenerIncorrecto = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (intentosRestantes > 0) {
                    mostrarMensaje("Te has equivocado" + "\n" + "Te queda un intento ", Color.RED);
                    intentosRestantes--;
                    respuestasConsecutivas = 0; // Reiniciar contador de respuestas correctas consecutivas

                    // Log y Toast para ver los puntos después de fallar
                    Log.d("Puntos", "Respuesta incorrecta. Total de puntos: " + puntos);
                    Toast.makeText(Facil.this, "Respuesta incorrecta. Total de puntos: " + puntos, Toast.LENGTH_SHORT).show();

                    actualizarIntentos();
                    if (intentosRestantes == 0) {
                        mostrarDialogoReintentarSalir();
                    }
                }
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
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void acertado() {
        respuestasConsecutivas++; // Incrementar contador de respuestas correctas consecutivas
        int puntosGanados = (int) Math.pow(2, respuestasConsecutivas - 1);
        puntos += puntosGanados; // Actualizar puntos según la fórmula (1, 2, 4, 8, ...)

        // Mostrar Toast con los puntos ganados
        Toast.makeText(this, "Has ganado " + puntosGanados + " puntos. Total de puntos: " + puntos, Toast.LENGTH_SHORT).show();

        // Log para registrar los puntos ganados y el total de puntos
        Log.d("Puntos", "Has ganado " + puntosGanados + " puntos. Total de puntos: " + puntos);
        System.out.println("Has ganado " + puntosGanados + " puntos. Total de puntos: " + puntos);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Has Acertado y has ganado " + puntosGanados + " puntos. Total de puntos: " + puntos)
                .setPositiveButton("Siguiente Canción", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        siguienteCancion();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Ver Listado", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        abrirRanking();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void siguienteCancion() {
        // Detener la reproducción actual si está en progreso
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }



        // Seleccionar una nueva canción aleatoria de las canciones restantes
        Random random = new Random();
        int indiceCancionAleatoria = random.nextInt(cancionesRestantes.size());
        int cancionActual = cancionesRestantes.remove(indiceCancionAleatoria);

        mediaPlayer = MediaPlayer.create(this, cancionActual);

        mostrarOpciones(cancionActual);
    }

    private void actualizarIntentos() {
        nIntentos.setText("Intentos: " + intentosRestantes);
        if (intentosRestantes == 0) {
            mostrarDialogoReintentarSalir();
        }
    }

    private void mostrarDialogoReintentarSalir() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Te has quedado sin intentos, puntos: " + puntos + ". ¿Quieres volver a intentar o salir?")
                .setPositiveButton("Reintentar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        intentosRestantes = 2; // Reiniciar intentos
                        actualizarIntentos(); // Actualizar visualmente
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



    private void abrirRanking() {
        Intent intent = new Intent(Facil.this, ListaActivity.class);
        startActivity(intent);
    }
}
