package com.example.proyecto1aplicacionnotasalumnos;

import android.content.Intent;
import android.os.Bundle;
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
    private EditText etAsignaturaSeleccionada;

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
        etAsignaturaSeleccionada = findViewById(R.id.etAlumnoSeleccionado);

        for (String asignatura : DatosAlumnos.asignaturas) {

            Button btnAlumno = new Button(this);
            btnAlumno.setText(asignatura);

            // Listener para enviar el contenido de la persona a través del intent
            btnAlumno.setOnClickListener(v -> {
                etAsignaturaSeleccionada.setText(asignatura);
            });

            layoutScrollView.addView(btnAlumno);

        }

        // Botón aceptar
        btnAceptar.setOnClickListener(view -> {
            String asignatura = etAsignaturaSeleccionada.getText().toString();
            if (asignatura.isEmpty()) {
                Toast.makeText(this, getString(R.string.seleccionaAsignatura), Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent();
                intent.putExtra("asignatura", asignatura);
                setResult(RESULT_OK, intent);
                finish();
            }
        });


        // Botón cancelar
        btnCancelar.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });

    }
}
