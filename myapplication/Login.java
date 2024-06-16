package es.studium.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    Button btnEntrar;
    EditText txtUsuario;
    EditText txtClave;
    BDLogica bdLogica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bdLogica = new BDLogica(this);
        bdLogica.open();

        btnEntrar = findViewById(R.id.btnEntrar);
        txtUsuario = findViewById(R.id.txAUsuario);
        txtClave = findViewById(R.id.txAClave);

        btnEntrar.setBackgroundColor(Color.BLACK);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = txtUsuario.getText().toString();
                String clave = txtClave.getText().toString();

                if (usuario.isEmpty() || clave.isEmpty()) {
                    Toast.makeText(Login.this, "Por favor, ingrese el nombre de usuario y la clave", Toast.LENGTH_LONG).show();
                } else {
                    if (bdLogica.verificarUsuario(usuario, clave)) {
                        Toast.makeText(Login.this, "Datos correctos. Bienvenido " + usuario, Toast.LENGTH_LONG).show();
                        abrirMenu(usuario);
                    } else if (bdLogica.usuarioExiste(usuario)) {
                        Toast.makeText(Login.this, "Usuario existente, pero la clave es incorrecta.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(Login.this, "Usuario no existente. Creando nuevo usuario.", Toast.LENGTH_LONG).show();
                        bdLogica.guardarCredenciales(usuario, clave);
                        abrirMenu(usuario);
                    }
                }
            }
        });
    }

    private void abrirMenu(String usuario) {
        Intent intent = new Intent(Login.this, Menu.class);
        intent.putExtra("username", usuario);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        bdLogica.close();
        super.onDestroy();
    }
}
