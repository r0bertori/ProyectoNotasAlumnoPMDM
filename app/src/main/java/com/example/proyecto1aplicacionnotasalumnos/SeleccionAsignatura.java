package com.example.proyecto1aplicacionnotasalumnos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto1aplicacionnotasalumnos.datos.DatosAlumnos;

public class SeleccionAsignatura extends ComponentActivity {


    private LinearLayout layoutScrollView;
    private Button btnCancelar;
    private Button btnAceptar;
    private EditText etAlumnoSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_seleccion_asignatura);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        layoutScrollView = findViewById(R.id.layoutButtons);
        btnCancelar = findViewById(R.id.btnCancelar);
        btnAceptar = findViewById(R.id.btnAceptar);
        etAlumnoSeleccionado = findViewById(R.id.etAlumnoSeleccionado);

        for (String asignatura : DatosAlumnos.asignaturas) {

            Button btnAlumno = new Button(this);
            btnAlumno.setText(asignatura);

            // Listener para enviar el contenido de la persona a través del intent
            btnAlumno.setOnClickListener(v -> {
                etAlumnoSeleccionado.setText(asignatura);
            });

            layoutScrollView.addView(btnAlumno);

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
