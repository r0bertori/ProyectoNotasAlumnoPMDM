package com.example.proyecto1aplicacionnotasalumnos;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.proyecto1aplicacionnotasalumnos.datos.DatosAlumnos;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class SeleccionAlumno extends AppCompatActivity {

    private LinearLayout layoutScrollView;
    private Button btnCancelar;
    private Button btnAceptar;
    private EditText etAlumnoSeleccionado;
    private List<String> alumnosConRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_seleccion_alumno);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        layoutScrollView = findViewById(R.id.layoutButtons);
        btnCancelar = findViewById(R.id.btnCancelar);
        btnAceptar = findViewById(R.id.btnAceptar);
        etAlumnoSeleccionado = findViewById(R.id.etAlumnoSeleccionado);

        // Vemos si se viene de 'ConsultaNotas'
        Boolean vieneDeConsultarNotas = getIntent().getBooleanExtra("vieneDeConsultarNotas", false);

        // Guardar alumnos que tienen datos registrados
        if (vieneDeConsultarNotas) {
            alumnosConRegistro = new ArrayList<>();
            File fichero = new File(getExternalFilesDir(null), "alumnos.dat");
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichero))){
                boolean b = false;
                Alumno alum = null;
                while (!b) {
                    try {
                        alum = (Alumno) ois.readObject();
                        alumnosConRegistro.add(alum.getNombre());
                    } catch (IOException | ClassNotFoundException e) {
                        b = true;
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        // Crear botones de los alumnos
        for (String alumno : DatosAlumnos.alumnos) {

            // Si se viene de 'ConsultaNotas' se crean botones para los alumnos con registros
            if (vieneDeConsultarNotas) {
                if (alumnosConRegistro.contains(alumno)) {
                    Button btnAlumno = new Button(this);
                    btnAlumno.setText(alumno);

                    // Listener para enviar el contenido de la persona a través del intent
                    btnAlumno.setOnClickListener(v -> {
                        etAlumnoSeleccionado.setText(alumno);
                    });

                    layoutScrollView.addView(btnAlumno);
                }
            }
            // Si viene de 'RegistroNota' se crea botones para todos los alumnos
            else {
                Button btnAlumno = new Button(this);
                btnAlumno.setText(alumno);

                // Listener para enviar el contenido de la persona a través del intent
                btnAlumno.setOnClickListener(v -> {
                    etAlumnoSeleccionado.setText(alumno);
                });

                layoutScrollView.addView(btnAlumno);
            }

        }

        // Botón aceptar
        btnAceptar.setOnClickListener(v -> {
            Intent resultado = new Intent();
            resultado.putExtra("alumno", etAlumnoSeleccionado.getText().toString());
            setResult(RESULT_OK, resultado);
            finish();
        });

        // Botón cancelar
        btnCancelar.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });

    }

}