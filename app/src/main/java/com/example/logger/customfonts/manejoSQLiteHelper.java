package com.example.logger.customfonts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;

import com.example.logger.User;
import com.example.logger.formulario;
import com.example.logger.pregunta;

import java.util.ArrayList;
import java.util.List;

public class manejoSQLiteHelper {

    public manejoSQLiteHelper() {
    }

    public void guardarUsuario(Context contexto, User usuario){
        conexionSQLiteHelper conn = new conexionSQLiteHelper(contexto,"bd_user",null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("correo", usuario.getEmail());
        values.put("nombre", usuario.getName());
        values.put("usuario", usuario.getUsername());
        values.put("contrasena", usuario.getPassword());
        values.put("genero", usuario.getGender());

        long idResultante = db.insert("users","correo",values);
        db.close();
    }

    public void guardarFormulario(Context contexto, formulario formulario){
        utilFormulario util = new utilFormulario();
        conexionSQLiteHelper conn = new conexionSQLiteHelper(contexto,util.NOMBRE_BASEDATOS,null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(util.CAMPO_CORREO, formulario.getCorreo());
        values.put(util.CAMPO_NOMBRESUPERVISOR, formulario.getNombreSupervisor());
        values.put(util.CAMPO_RUTA, formulario.getRuta());
        values.put(util.CAMPO_INSTITUCIONEDUCATIVA, formulario.getInstitucionEducativa());
        values.put(util.CAMPO_SEDE, formulario.getSede());
        values.put(util.CAMPO_GRUPO, formulario.getGrupo());
        values.put(util.CAMPO_SINCRONIZADO, formulario.isSincronizado());

        Long idResultante = db.insert("users","correo",values);
        db.close();
        guardarPreguntas(contexto,formulario.getPreguntas(),idResultante);
    }

    public void guardarPreguntas(Context contexto, List<pregunta> preguntas, Long fkformulario){
        utilFormulario util = new utilFormulario();
        conexionSQLiteHelper conn = new conexionSQLiteHelper(contexto,util.NOMBRE_BASEDATOS,null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        for(pregunta pregunta: preguntas){
            ContentValues values = new ContentValues();
            values.put(util.CAMPO_PREGUNTA, pregunta.getPregunta());
            values.put(util.CAMPO_RESPUESTA, pregunta.getRespuesta());
            values.put(util.CAMPO_OTRO, pregunta.getOtro());
            values.put(util.CAMPO_FKFORMULARIO, fkformulario);
            db.insert(util.TABLA_PREGUNTAS,util.CAMPO_FKFORMULARIO,values);
        }

        db.close();
    }

    public List<formulario> consultarFromularios(Context contexto){
        utilFormulario util = new utilFormulario();
        List<formulario> formularios = new ArrayList<>();
        conexionSQLiteHelper conn = new conexionSQLiteHelper(contexto,util.NOMBRE_BASEDATOS,null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        String [] parametros = {"0"};
        String [] campos = {util.CAMPO_IDFORMULARIO,util.CAMPO_CORREO,util.CAMPO_NOMBRESUPERVISOR,util.CAMPO_RUTA,util.CAMPO_INSTITUCIONEDUCATIVA,util.CAMPO_SEDE,util.CAMPO_GRUPO,util.CAMPO_SINCRONIZADO};

        Cursor cursor = db.query(util.TABLA_FORMULARIOS,campos,util.CAMPO_SINCRONIZADO + "=?",parametros,null,null,null);
        while(cursor.moveToNext()){
            formulario formulario = new formulario();
            formulario.setIdformulario(cursor.getInt(0));
            formulario.setCorreo(cursor.getString(1));
            formulario.setNombreSupervisor(cursor.getString(2));
            formulario.setRuta(cursor.getString(3));
            formulario.setInstitucionEducativa(cursor.getString(4));
            formulario.setSede(cursor.getString(5));
            formulario.setGrupo(cursor.getString(6));
            formulario.setSincronizado(cursor.getInt(7));
            db.close();
            formulario.getPreguntas().addAll(consultarPreguntas(contexto, formulario.getIdformulario()));
        }
        return formularios;
    }

    public List<pregunta> consultarPreguntas(Context contexto, Integer fkFormulario){
        utilFormulario util = new utilFormulario();
        List<pregunta> preguntas = new ArrayList<>();
        conexionSQLiteHelper conn = new conexionSQLiteHelper(contexto,util.NOMBRE_BASEDATOS,null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        String [] parametros = {fkFormulario+""};
        String [] campos = {util.CAMPO_FKFORMULARIO,util.CAMPO_PREGUNTA,util.CAMPO_RESPUESTA,util.CAMPO_OTRO};

        Cursor cursor = db.query(util.TABLA_PREGUNTAS,campos,util.CAMPO_FKFORMULARIO + "=?",parametros,null,null,null);
        while(cursor.moveToNext()){
            pregunta pregunta = new pregunta();
            pregunta.setFkformulario(cursor.getInt(0));
            pregunta.setPregunta(cursor.getString(1));
            pregunta.setRespuesta(cursor.getString(2));
            pregunta.setOtro(cursor.getString(3));
            preguntas.add(pregunta);
        }
        db.close();

        return preguntas;
    }

    public void prueba(Context contexto){
        pregunta pregunta = new pregunta();
        pregunta pregunta1 = new pregunta();
        formulario formulario = new formulario();
        formulario.setCorreo("11@11.com");
        formulario.setGrupo("2");
        formulario.setInstitucionEducativa("Unilibre");
        formulario.setRuta("boyaca");
        formulario.setNombreSupervisor("jose");
        formulario.setSincronizado(0);
        pregunta.setPregunta("pregunta");
        pregunta.setRespuesta("Respuesta");
        pregunta.setOtro("otro");
        List<pregunta> preguntas = new ArrayList<>();
        preguntas.add(pregunta);
        formulario.getPreguntas().add(pregunta);
        guardarFormulario(contexto, formulario);
    }
}
