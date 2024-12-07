package com.example.proyecto1aplicacionnotasalumnos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NotaFragment extends Fragment {

    public static NotaFragment newInstance(String asignatura, Double nota) {
        NotaFragment fragment = new NotaFragment();
        Bundle args = new Bundle();
        args.putString("asignatura", asignatura);
        args.putDouble("nota", nota);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nota, container, false);

        // Recuperar datos pasados al fragmento
        String asignatura = getArguments().getString("asignatura");
        Double nota = getArguments().getDouble("nota");

        // Mostrar datos en la interfaz del fragmento
        TextView tvAsignatura = view.findViewById(R.id.tvAsignatura);
        TextView tvNota = view.findViewById(R.id.tvNota);

        tvAsignatura.setText("Asignatura: " + asignatura);
        tvNota.setText("Nota: " + nota);

        return view;
    }
}