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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ConsultaNotas extends AppCompatActivity {

    private Button btnSeleccionarAlumno;
    private EditText etAlumnoSeleccionado;
    private TextView tvDatosAlumno;
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
        tvDatosAlumno = findViewById(R.id.tvDatosAlumno);
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
        contrato.launch(intent);
    }

    public void leerDatos(String alumno) {
        File fichero = new File(getExternalFilesDir(null), "alumnos.dat");
        boolean tieneDatos = false;

        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichero))) {
            boolean b = false;
            Alumno alum = null;
            while (!b) {
                try {
                    alum = (Alumno) ois.readObject();
                    String dato = alum.getAsignatura() + "," + alum.getNotaFinal();
                    Log.d("deb2", dato);
                    if (etAlumnoSeleccionado.getText().toString().equals(alum.getNombre())) {
                        tvDatosAlumno.setText(tvDatosAlumno.getText() + "\n"
                        + alum.getAsignatura() + " " + alum.getNotaFinal());
                        tieneDatos = true;
                    }
                } catch (IOException | ClassNotFoundException e) {
                    b = true;
                }
            }

            if (!tieneDatos) {
                tvDatosAlumno.setText("El alumno seleccionado no tiene notas registradas.");
            }

        } catch (FileNotFoundException e) {
            Toast.makeText(this, "No se ha encontrado el fichero", Toast.LENGTH_SHORT).show();
            Log.d("err1", "No se ha encontrado el fichero");
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(this, "Error al leer el fichero", Toast.LENGTH_SHORT).show();
            Log.d("err2", "No se ha encontrado el fichero");
            e.printStackTrace();
        }
    }

    public void volverPantallaInicio(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}