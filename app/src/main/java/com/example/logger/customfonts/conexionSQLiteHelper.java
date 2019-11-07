package com.example.logger.customfonts;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class conexionSQLiteHelper extends SQLiteOpenHelper {

    final String CREAR_TABLA_USER="CREATE TABLE users (correo TEXT  PRIMARY KEY, nombre TEXT, usuario TEXT, contrasena TEXT, genero TEXT)";
    final String ELIMINAR_TABLA_USER="DROP TABLE IF EXISTS users";


    public conexionSQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREAR_TABLA_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int venrsionAntigua, int versionNueva) {
        sqLiteDatabase.execSQL(ELIMINAR_TABLA_USER);
    }
}
