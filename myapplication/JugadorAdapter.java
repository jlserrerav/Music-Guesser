package es.studium.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class JugadorAdapter extends ArrayAdapter<Jugador> {

    public JugadorAdapter(Context context, List<Jugador> jugadores) {
        super(context, 0, jugadores);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtener el objeto Jugador para esta posición
        Jugador jugador = getItem(position);

        // Verificar si la vista actual está siendo reutilizada, de lo contrario, inflarla
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ranking_item, parent, false);
        }

        // Buscar los TextViews en el layout
        TextView txtUsername = convertView.findViewById(R.id.textViewUsername);

        // Mostrar la información del jugador en los TextViews
        txtUsername.setText(jugador.getUsuario());

        // Devolver la vista inflada y modificada para que se muestre en la lista
        return convertView;
    }
}
