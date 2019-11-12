package com.example.logger;
import java.util.ArrayList;
import java.util.List;

public class formulario {

    Integer idformulario;
    String correo;
    String nombreSupervisor;
    String ruta;
    String institucionEducativa;
    String sede;
    String grupo;
    Integer sincronizado; //o NO / 1 SI

    List<pregunta> preguntas;


    public formulario() {
        this.preguntas = new ArrayList<>();
    }

    public Integer getIdformulario() {
        return idformulario;
    }

    public void setIdformulario(Integer idformulario) {
        this.idformulario = idformulario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombreSupervisor() {
        return nombreSupervisor;
    }

    public void setNombreSupervisor(String nombreSupervisor) {
        this.nombreSupervisor = nombreSupervisor;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getInstitucionEducativa() {
        return institucionEducativa;
    }

    public void setInstitucionEducativa(String institucionEducativa) {
        this.institucionEducativa = institucionEducativa;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public Integer isSincronizado() {
        return sincronizado;
    }

    public void setSincronizado(Integer sincronizado) {
        this.sincronizado = sincronizado;
    }

    public List<pregunta> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(List<pregunta> preguntas) {
        this.preguntas = preguntas;
    }
}