package com.example.proyecto1aplicacionnotasalumnos;

import java.io.Serializable;

public class Alumno implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nombre;
    private String asignatura;
    private Double notaFinal;

    public Alumno(String nombre, String asignatura, Double notaFinal) {
        this.nombre = nombre;
        this.asignatura = asignatura;
        this.notaFinal = notaFinal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }

    public Double getNotaFinal() {
        return notaFinal;
    }

    public void setNotaFinal(Double notaFinal) {
        this.notaFinal = notaFinal;
    }
}
