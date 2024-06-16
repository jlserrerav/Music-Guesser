package es.studium.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class BDLogica {
    private DataBaseHelper dbHelper;
    private SQLiteDatabase database;

    public BDLogica(Context context) {
        dbHelper = new DataBaseHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }


    public boolean verificarUsuario(String usuario, String clave) {
        Cursor cursor = database.query("users",
                new String[]{"password"},
                "username = ?",
                new String[]{usuario},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String savedPassword = cursor.getString(cursor.getColumnIndexOrThrow("password"));
            cursor.close();
            return savedPassword.equals(clave);
        }

        if (cursor != null) {
            cursor.close();
        }
        return false;
    }

    public boolean usuarioExiste(String usuario) {
        Cursor cursor = database.query("users",
                new String[]{"username"},
                "username = ?",
                new String[]{usuario},
                null, null, null);

        boolean exists = cursor != null && cursor.moveToFirst();

        if (cursor != null) {
            cursor.close();
        }
        return exists;
    }

    public void guardarCredenciales(String usuario, String clave) {
        ContentValues values = new ContentValues();
        values.put("username", usuario);
        values.put("password", clave);
        values.put("points", 0);
        database.insert("users", null, values);
    }

    public List<Jugador> obtenerTopJugadores(int limite) {
        List<Jugador> jugadores = new ArrayList<>();
        Cursor cursor = database.query("users",
                new String[]{"username", "password", "points"},
                null, null, null, null, "points DESC",
                String.valueOf(limite));

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
                int points = cursor.getInt(cursor.getColumnIndexOrThrow("points"));

                // Agregar registro de depuración para verificar los puntos obtenidos de la base de datos
                Log.d("UserDAO", "Usuario: " + username + ", Puntos: " + points);

                Jugador jugador = new Jugador(username, password, points);
                jugadores.add(jugador);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return jugadores;
    }

}
