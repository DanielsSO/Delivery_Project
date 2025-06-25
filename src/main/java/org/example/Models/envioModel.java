package org.example.Models;

import java.time.LocalDate;

public class envioModel {
    private int id;
    private String nombreEnvia;
    private String nombreRecibe;
    private String direccionEnvia;
    private String otrosEnvia;
    private String direccionRecibe;
    private String otrosRecibe;
    private String numeroRastreo;
    private LocalDate fechaEnvio;

    public envioModel() {}

    public envioModel(String nombreEnvia, String nombreRecibe, String direccionEnvia, String otrosEnvia, String direccionRecibe, String otrosRecibe, String numeroRastreo, LocalDate fechaEnvio) {
        this.nombreEnvia = nombreEnvia;
        this.nombreRecibe = nombreRecibe;
        this.direccionEnvia = direccionEnvia;
        this.otrosEnvia = otrosEnvia;
        this.direccionRecibe = direccionRecibe;
        this.otrosRecibe = otrosRecibe;
        this.numeroRastreo = numeroRastreo;
        this.fechaEnvio = fechaEnvio;
    }


    public envioModel(int id, String nombreEnvia, String nombreRecibe, String direccionEnvia, String otrosEnvia, String direccionRecibe, String otrosRecibe, String numeroRastreo, LocalDate fechaEnvio) {
        this.id = id;
        this.nombreEnvia = nombreEnvia;
        this.nombreRecibe = nombreRecibe;
        this.direccionEnvia = direccionEnvia;
        this.otrosEnvia = otrosEnvia;
        this.direccionRecibe = direccionRecibe;
        this.otrosRecibe = otrosRecibe;
        this.numeroRastreo = numeroRastreo;
        this.fechaEnvio = fechaEnvio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreEnvia() {
        return nombreEnvia;
    }

    public void setNombreEnvia(String nombreEnvia) {
        this.nombreEnvia = nombreEnvia;
    }

    public String getNombreRecibe() {
        return nombreRecibe;
    }

    public void setNombreRecibe(String nombreRecibe) {
        this.nombreRecibe = nombreRecibe;
    }

    public String getDireccionEnvia() {
        return direccionEnvia;
    }

    public void setDireccionEnvia(String direccionEnvia) {
        this.direccionEnvia = direccionEnvia;
    }

    public String getOtrosEnvia() {
        return otrosEnvia;
    }

    public void setOtrosEnvia(String otrosEnvia) {
        this.otrosEnvia = otrosEnvia;
    }

    public String getDireccionRecibe() {
        return direccionRecibe;
    }

    public void setDireccionRecibe(String direccionRecibe) {
        this.direccionRecibe = direccionRecibe;
    }

    public String getOtrosRecibe() {
        return otrosRecibe;
    }

    public void setOtrosRecibe(String otrosRecibe) {
        this.otrosRecibe = otrosRecibe;
    }

    public String getNumeroRastreo() {
        return numeroRastreo;
    }

    public void setNumeroRastreo(String numeroRastreo) {
        this.numeroRastreo = numeroRastreo;
    }

    public LocalDate getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(LocalDate fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }
}
