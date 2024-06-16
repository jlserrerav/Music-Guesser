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

public class Dificil extends AppCompatActivity {

    ImageView imgPlay;
    SeekBar seekBar;
    Handler handler;
    int progreso = 0;
    final int tiempo = 1000;  // Modificado para 8 segundos
    MediaPlayer mediaPlayer;

    final int duracionTotal = 8;  // Duración total en segundos

    private int[] listaCanciones = {R.raw.despacito, R.raw.davidbisba, R.raw.wakemeup, R.raw.animals, R.raw.vivavida}; // Lista de canciones
    private List<Integer> cancionesRestantes = new ArrayList<>(); // Lista para gestionar canciones restantes

    private int respuestasConsecutivas = 0; // Contador de respuestas correctas consecutivas
    private int puntos = 0; // Variable para almacenar los puntos

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normal);

        imgPlay = findViewById(R.id.imgPlay);
        seekBar = findViewById(R.id.seekBar);
        handler = new Handler();

        // Inicializar la lista de can  ciones restantes
        for (int cancion : listaCanciones) {
            cancionesRestantes.add(cancion);
        }

        // Verificar si la lista de canciones restantes está vacía
        if (cancionesRestantes.isEmpty()) {
            Toast.makeText(this, "No hay canciones disponibles", Toast.LENGTH_SHORT).show();
            return;
        }

        seekBar.setMax(duracionTotal); // Configura el max de la SeekBar para 8 segundos

        // Seleccionar la primera canción
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

        List<String> opciones = new ArrayList<>();
        String respuestaCorrecta = "";

        if (cancion == R.raw.despacito) {
            opciones.add("Aqui estoy yo - Luis Fonsi");
            opciones.add("Échame la culpa - Luis Fonsi");
            opciones.add("Despacito - Luis Fonsi");
            opciones.add("Tanto - Luis Fonsi");
            respuestaCorrecta = "Despacito - Luis Fonsi";
        } else if (cancion == R.raw.vivavida) {
            opciones.add("Paradise - Coldplay");
            opciones.add("Yellow - Coldplay");
            opciones.add("Viva la vida - Coldplay");
            opciones.add("Fix you - Coldplay");
            respuestaCorrecta = "Viva la vida - Coldplay";
        } else if (cancion == R.raw.davidbisba) {
            opciones.add("Dígale - David Bisbal");
            opciones.add("Mi Princesa - David Bisbal");
            opciones.add("Ahora - David Bisbal");
            opciones.add("Ave María - David Bisbal");
            respuestaCorrecta = "Ave María - David Bisbal";
        } else if (cancion == R.raw.animals) {
            opciones.add("Payphone - Maroon 5");
            opciones.add("Moves Like Jagger - Maroon 5");
            opciones.add("Animals - Maroon 5");
            opciones.add("Girls like you - Maroon 5");
            respuestaCorrecta = "Animals - Maroon 5";
        } else if (cancion == R.raw.wakemeup) {
            opciones.add("Levels - Avicii");
            opciones.add("Wake me Up - Avicii");
            opciones.add("Hey Brother - Avicii");
            opciones.add("The Nights - Avicii");
            respuestaCorrecta = "Wake me Up - Avicii";
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
        int puntosGanados = (int) Math.pow(5, respuestasConsecutivas);
        puntos += puntosGanados; // Actualizar puntos según la nueva fórmula 

        Toast.makeText(this, "Has ganado " + puntosGanados + " puntos. Total de puntos: " + puntos, Toast.LENGTH_SHORT).show();

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
        // Detener y liberar el MediaPlayer si ya está reproduciendo
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }

        // Seleccionar una canción aleatoria de la lista de canciones restantes
        Random random = new Random();
        int indice = random.nextInt(cancionesRestantes.size());
        int cancionSeleccionada = cancionesRestantes.get(indice);

        mediaPlayer = MediaPlayer.create(this, cancionSeleccionada);
        mostrarOpciones(cancionSeleccionada);

        // Eliminar la canción seleccionada de la lista de canciones restantes
        cancionesRestantes.remove(indice);

        // Verificar si la lista de canciones restantes está vacía
        if (cancionesRestantes.isEmpty()) {
            Toast.makeText(this, "No hay más canciones disponibles", Toast.LENGTH_SHORT).show();
            mostrarMensaje("¡Juego Terminado! Total de puntos: " + puntos, Color.GREEN);
        }
    }

    private void abrirRanking() {
        Intent intent = new Intent(this, ListaActivity.class);
        startActivity(intent);
    }
}
