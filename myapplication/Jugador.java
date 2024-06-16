package es.studium.myapplication;

public class Jugador {
    private String usuario;
    private String clave;
    private int puntos;

    public Jugador(String username, String password, int points) {
        this.usuario = username;
        this.clave = password;
        this.puntos = points;
    }

    // Métodos getters y setters
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }
}
