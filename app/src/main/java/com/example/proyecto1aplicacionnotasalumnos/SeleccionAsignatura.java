package com.example.proyecto1aplicacionnotasalumnos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SeleccionAsignatura extends ComponentActivity {

    private Button btnPmdm;
    private Button btnAsgAd;
    private Button btnAsgPsp;
    private Button btnAsgDi;
    private Button btnAsgSge;
    private Button btnAsgIacc;
    private Button btnAsgIos;
    private EditText edSelAss;
    private Button btnAceptar;
    private Button btnCancel;
    private Button btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);setContentView(R.layout.activity_seleccion_asignatura);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnPmdm = findViewById(R.id.btnPmdm);
        btnAsgAd = findViewById(R.id.btnAsgAd);
        btnAsgPsp = findViewById(R.id.btnAsgPsp);
        btnAsgDi = findViewById(R.id.btnAsgDi);
        btnAsgSge = findViewById(R.id.btnAsgSge);
        btnAsgIacc = findViewById(R.id.btnAsgIacc);
        edSelAss = findViewById(R.id.edSelAss);
        btnAsgIos = findViewById(R.id.btnAsgIos);
        btnAceptar = findViewById(R.id.btnAceptar);
        btnCancel = findViewById(R.id.btnCancel);
        btnVolver = findViewById(R.id.btnVolverSeleccionAsignatura);

        btnPmdm.setOnClickListener(view -> {
            edSelAss.setText("PMDM");
        });

        btnAsgAd.setOnClickListener(view -> {
            edSelAss.setText("AD");
        });

        btnAsgPsp.setOnClickListener(view -> {
            edSelAss.setText("PSP");
        });

        btnAsgDi.setOnClickListener(view -> {
            edSelAss.setText("DI");
        });

        btnAsgSge.setOnClickListener(view -> {
            edSelAss.setText("SGE");
        });

        btnAsgIacc.setOnClickListener(view -> {
            edSelAss.setText("IACC");
        });

        btnAsgIos.setOnClickListener(view -> {
            edSelAss.setText("IOS");
        });

        btnAceptar.setOnClickListener(view -> {
            String asignatura = edSelAss.getText().toString();
            if (asignatura.isEmpty()) {
                Toast.makeText(this, "Por favor, selecciona una asignatura", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent();
                intent.putExtra("asigantura", asignatura);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        btnCancel.setOnClickListener(view -> {
            finish();
        });

        btnVolver.setOnClickListener(v -> {
            finish();
        });

    }

}
