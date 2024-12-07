package com.example.proyecto1aplicacionnotasalumnos;

import android.content.ActivityNotFoundException;
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

        btnVerNota = findViewById(R.id.btnVerNota);
        btnRegistro = findViewById(R.id.btnRegistro);

        comprobarExisteFichero();
    }

    private void comprobarExisteFichero() {
        File file = new File(getExternalFilesDir(null), getString(R.string.rutaFicheroDat));
        if (file.exists()) {
            btnVerNota.setText(getString(R.string.consultarNotas));
            btnVerNota.setEnabled(true);
            btnVerNota.setBackgroundColor(getColor(R.color.colorPrimary));
        } else {
            btnVerNota.setText(getString(R.string.registraUnaNota));
            btnVerNota.setEnabled(false);
            btnVerNota.setBackgroundColor(getColor(R.color.disabledButton));
        }
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
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        // Metemos toda la información del email en el setData porque algunos clientes de correo ignoran los 'Extra's
        intent.setData(Uri.parse("mailto:ayuda@uem.es"
                + "?subject=" + Uri.encode("Petición de ayuda")
                + "&body=" + Uri.encode("Motivo : ")));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

        try {
            startActivity(Intent.createChooser(intent, getString(R.string.eligeAppCorreo)));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, getString(R.string.errorAppCorreo), Toast.LENGTH_SHORT).show();
        }
    }
}