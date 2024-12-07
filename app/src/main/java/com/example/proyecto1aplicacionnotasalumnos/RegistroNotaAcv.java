package com.example.proyecto1aplicacionnotasalumnos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class RegistroNotaAcv extends AppCompatActivity {

    private EditText edSelAl;
    private EditText edSelAss;
    private EditText edNotaExam;
    private EditText edNotaActividades;
    private Button btnSelAss;
    private Button btnSelAlm;
    private Button btnCalNot;
    private EditText edVacio;
    private Button btnVolver;

    private List<Alumno> alumnos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro_nota);


        edSelAl = findViewById(R.id.edSelAl);
        edSelAss = findViewById(R.id.edSelAss);
        edNotaExam = findViewById(R.id.edNotaExam);
        edNotaActividades = findViewById(R.id.edNotaActividades);
        btnSelAss = findViewById(R.id.btnSelAss);
        btnSelAlm = findViewById(R.id.btnSlectAl);
        btnCalNot = findViewById(R.id.btnCalNot);
        edVacio = findViewById(R.id.edVacio);
        btnVolver = findViewById(R.id.btnVolverRegistroNota);

        rellenarLista();

    }

    public void volverPantallaInicio(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void rellenarLista() {
        File fichero = new File(getExternalFilesDir(null), getString(R.string.rutaFicheroDat));

        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichero))) {
            boolean b = false;
            Alumno alum = null;
            while (!b) {
                try {
                    alum = (Alumno) ois.readObject();
                    alumnos.add(alum);
                    Log.d("rna", alum.getNombre() + " " + alum.getAsignatura() + " " + alum.getNotaFinal());
                } catch (IOException | ClassNotFoundException e) {
                    b = true;
                }
            }
        } catch (FileNotFoundException e) {
            Toast.makeText(this, getString(R.string.fileNotFound), Toast.LENGTH_SHORT).show();
            Log.d("err1", getString(R.string.fileNotFound));
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(this, getString(R.string.readingError), Toast.LENGTH_SHORT).show();
            Log.d("err2", getString(R.string.readingError));
            e.printStackTrace();
        }
    }

    private ActivityResultLauncher<Intent> arlAlumno = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        String resultado = data.getStringExtra(getString(R.string.alumno));
                        edSelAl.setText(resultado);
                        btnSelAlm.setEnabled(false);
                    }
                }
            }
    );

    public void seletAlum(View view) {
        Intent intent = new Intent(this, SeleccionAlumno.class);
        arlAlumno.launch(intent);
    }

    private ActivityResultLauncher<Intent> arlAsignatura = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        String resultado = data.getStringExtra(getString(R.string.asignatura));
                        edSelAss.setText(resultado);
                        btnSelAss.setEnabled(false);
                    }
                }
            }
    );

    public void seletAss(View view) {
        Intent intent = new Intent(this, SeleccionAsignatura.class);
        arlAsignatura.launch(intent);
    }

    public void guardarDta(View view) {
        // Si se pulsa el botón Guardar Datos sin haber introducido todos los datos, se mostrará un mensaje que indique que los datos son obligatorios.

        if (edSelAl.getText().toString().isEmpty()
                || edSelAss.getText().toString().isEmpty()
                || edNotaExam.getText().toString().isEmpty()
                || edNotaActividades.getText().toString().isEmpty()) {
            Toast.makeText(this, getString(R.string.completaCampos), Toast.LENGTH_SHORT).show();
        } else {
            String alumnoSeleccionado = edSelAl.getText().toString();
            String asignaturaSeleccionada = edSelAss.getText().toString();
            Double notaFinal = Double.parseDouble(edVacio.getText().toString());

            Alumno alumno = new Alumno(alumnoSeleccionado, asignaturaSeleccionada, notaFinal);
            escribirFicheroBinario(alumno); // Método para escribir el alumno en el fichero binario
            // despues de guardar los datos se debe msotrar un mensaje y limpiar los campos
            limpiarDta(view);
        }


    }

    private void escribirFicheroBinario(Alumno alumno) {
        //alumnos.add(alumno);
        // TODO getResources().openRawResource(R.raw.alumnos)
        String TAG = "deb";
        boolean alumnoCoincide = false;
        File fichero = new File(getExternalFilesDir(null), getString(R.string.rutaFicheroDat));
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fichero))) {

            for (Alumno a : alumnos) {
                if (a.getNombre().equals(alumno.getNombre()) && a.getAsignatura().equals(alumno.getAsignatura())) {
                    alumnoCoincide = true;
                    a.setNotaFinal(alumno.getNotaFinal());
                }
            }

            if (!alumnoCoincide) {
                alumnos.add(alumno);
            }

            for (Alumno a : alumnos) {
                oos.writeObject(a);
            }

            Log.d(TAG, fichero.getAbsolutePath().toString());
            Toast.makeText(this, getString(R.string.datosGuardados), Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, getString(R.string.fileNotFound), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, getString(R.string.writingError), Toast.LENGTH_SHORT).show();
        }

    }



    public void limpiarDta(View view) {
        edSelAl.setText("");
        edSelAss.setText("");
        edNotaExam.setText("");
        edNotaActividades.setText("");
        edVacio.setText("");
        btnSelAss.setEnabled(true);
        btnSelAlm.setEnabled(true);
        btnCalNot.setEnabled(true);
    }

    public void calcularNota(View view) {

        Double notaExam = Double.parseDouble(edNotaExam.getText().toString());
        Double notaActividades = Double.parseDouble((edNotaActividades.getText().toString()));

        if ( edNotaExam.getText().toString().isEmpty() && edNotaActividades.getText().toString().isEmpty()) {
            Toast.makeText(this, getString(R.string.pedirCamposNecesarios), Toast.LENGTH_SHORT).show();
        } else if (notaExam < 0 || notaExam > 10 || notaActividades < 0 || notaActividades > 10) {
            Toast.makeText(this, getString(R.string.errorNotas), Toast.LENGTH_SHORT).show();
        } else {
            // despues de calcular la nota se debe msotrar un mensaje y limpiar los campos
            btnCalNot.setEnabled(false);
            Double notaFinal;
            if (notaExam >= 4.5  && notaActividades >= 7.5) {
                notaFinal = notaExam * 0.7 + notaActividades * 0.3;
            } else if (notaExam < 4.5) {
                notaFinal = notaExam;
            } else {
                notaFinal = notaExam * 0.7 + notaActividades * 0.3;
            }
            Double notaFinalRound = Math.round(notaFinal * 100) / 100.0;
            edVacio.setText(notaFinalRound.toString());

        }
    }
}