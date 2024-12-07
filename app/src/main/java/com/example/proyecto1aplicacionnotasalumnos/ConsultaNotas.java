package com.example.proyecto1aplicacionnotasalumnos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ConsultaNotas extends AppCompatActivity {

    private Button btnSeleccionarAlumno;
    private EditText etAlumnoSeleccionado;
    private Button btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_consulta_notas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnSeleccionarAlumno = findViewById(R.id.btnSeleccionarAlumno);
        etAlumnoSeleccionado = findViewById(R.id.etAlumnoSeleccionado);
        btnVolver = findViewById(R.id.btnVolverConsultaNotas);

    }

    private ActivityResultLauncher<Intent> contrato = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                if (data != null) {
                    String resultado = data.getStringExtra("alumno");
                    etAlumnoSeleccionado.setText(resultado);
                    leerDatos(etAlumnoSeleccionado.getText().toString());
                }
            }
        }
    );

    public void seleccionarAlumno(View view) {
        Intent intent = new Intent(ConsultaNotas.this, SeleccionAlumno.class);
        intent.putExtra("vieneDeConsultarNotas", true);
        contrato.launch(intent);
    }

    public void leerDatos(String alumno) {
        limpiarFragmentos();
        File fichero = new File(getExternalFilesDir(null), "alumnos.dat");
        boolean tieneDatos = false;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichero))) {
            boolean b = false;
            Alumno alum = null;
            while (!b) {
                try {
                    alum = (Alumno) ois.readObject();
                    if (etAlumnoSeleccionado.getText().toString().equals(alum.getNombre())) {
                        // Creamos un nuevo fragmento dinámico para cada asignatura y nota
                        NotaFragment fragment = NotaFragment.newInstance(alum.getAsignatura(), alum.getNotaFinal());

                        // Agregar el fragmento al contenedor
                        getSupportFragmentManager()
                                .beginTransaction()
                                .add(R.id.contenedorFragmentos, fragment)
                                .commit();

                        tieneDatos = true;
                    }
                } catch (IOException | ClassNotFoundException e) {
                    b = true;
                }
            }

            if (!tieneDatos) {
                Toast.makeText(this, "El alumno seleccionado no tiene notas registradas.", Toast.LENGTH_SHORT).show();
            }

        } catch (FileNotFoundException e) {
            Toast.makeText(this, "No se ha encontrado el fichero", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Error al leer el fichero", Toast.LENGTH_SHORT).show();
        }
    }

    private void limpiarFragmentos() {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment instanceof NotaFragment) {
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            }
        }
    }

    public void volverPantallaInicio(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // Comentario para git

}