package com.aiviktor.aquatekapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by VICTOR on 27/10/2016.
 *
 * Base de datos de la app, se define la estructura de las tablas
 * como tambien la insercion de valores por defecto
 */

public class DataBase extends SQLiteOpenHelper {

    public DataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL1 = "create table analyzer(id integer primary key, valor double, tiempo datetime)";
        db.execSQL(SQL1);
        String SQL2 = "create table offsets(id integer primary key, valor double)";
        db.execSQL(SQL2);
        String SQL3 = "insert into offsets values (1,5.0)";
        db.execSQL(SQL3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
