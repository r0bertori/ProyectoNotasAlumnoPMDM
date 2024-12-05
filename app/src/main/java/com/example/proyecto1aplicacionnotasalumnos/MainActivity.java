package com.example.proyecto1aplicacionnotasalumnos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    Button btnVerNota;
    Button btnRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnVerNota = findViewById(R.id.btnVerNota);
        Button btnRegistro = findViewById(R.id.btnRegistro);
    }

    public void registrarNota(View view) {
        Intent intent = new Intent(MainActivity.this, RegistroNotaAcv.class);
        startActivity(intent);
    }

    public void consultarNota(View view) {
        Intent intent = new Intent(MainActivity.this, ConsultaNotas.class);
        startActivity(intent);
    }

    public void mandarCorreo(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822"); // MIME o tipo de datos específicos para correos electrónicos
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"ayuda@uem.es"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Petición de ayuda");
        intent.putExtra(Intent.EXTRA_TEXT, "Motivo : ");

        // Puede fallar porque no reconozca el cliente gmail, usamos un seleccionador de aplicaciones
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(intent, "Elige un cliente de correo"));
        } else {
            Toast.makeText(this, "No se encontró ninguna app de correo electrónico", Toast.LENGTH_SHORT).show();
        }
    }
}