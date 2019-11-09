package com.example.logger.customfonts;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class conexionSQLiteHelper extends SQLiteOpenHelper {

    final String CREAR_TABLA_USERS="CREATE TABLE users (correo TEXT  PRIMARY KEY, nombre TEXT, usuario TEXT, contrasena TEXT, genero TEXT)";
    final String ELIMINAR_TABLA_USERS="DROP TABLE IF EXISTS users";

    final String CREAR_TABLA_PREGUNTA="CREATE TABLE preguntas (fkformulario INTEGER, pregunta TEXT, respuesta TEXT, otro TEXT)";
    final String ELIMINAR_TABLA_PREGUNTA="DROP TABLE IF EXISTS preguntas";

    final String CREAR_TABLA_FORMULARIO="CREATE TABLE formularios (idformulario INTEGER PRIMARY KEY AUTOINCREMENT, correo TEXT, nombreSupervisor TEXT, ruta TEXT, institucionEducativa TEXT, sede TEXT, grupo TEXT, sincronizado INTEGER )";
    final String ELIMINAR_TABLA_FORMULARIO="DROP TABLE IF EXISTS formularios";


    public conexionSQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREAR_TABLA_USERS);
        sqLiteDatabase.execSQL(CREAR_TABLA_PREGUNTA);
        sqLiteDatabase.execSQL(CREAR_TABLA_FORMULARIO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int venrsionAntigua, int versionNueva) {
        sqLiteDatabase.execSQL(ELIMINAR_TABLA_USERS);
        sqLiteDatabase.execSQL(ELIMINAR_TABLA_PREGUNTA);
        sqLiteDatabase.execSQL(ELIMINAR_TABLA_FORMULARIO);

    }


}
