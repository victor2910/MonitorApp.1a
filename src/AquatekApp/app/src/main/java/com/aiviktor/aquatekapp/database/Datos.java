package com.aiviktor.aquatekapp.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.aiviktor.aquatekapp.structures.Valor;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by VICTOR on 27/10/2016.
 *
 * Funciones estaticas para extraer informacion de la base de datos
 */

public class Datos {

    /**
     * Devuelve una lista de valores de la tabla de medidas
     * @param context contexto
     * @return arraylist de valores
     */
    public static ArrayList<Valor> valores(Context context){
        String sql="select id,valor,tiempo from analyzer order by id desc";
        ArrayList<Valor> r=new ArrayList<>();
        try {
            DataBase dbiot = new DataBase(context, "aquatek", null, 1);
            SQLiteDatabase db = dbiot.getReadableDatabase();

            Cursor c = db.rawQuery(sql, null);
            c.moveToFirst();
            for(int i=0;i<c.getCount();i++){
                r.add(new Valor(c.getInt(0),c.getDouble(1),c.getString(2)));
                c.moveToNext();
            }
            c.close();
            db.close();
            Collections.reverse(r);
            return r;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Devuelve una lista con los valores que sobrepasan el offset establecido
     * @param context contexto
     * @return arraylist de valores
     */
    public static ArrayList<Valor> incidentes(Context context){
        String sql="select a.id,a.valor,a.tiempo from analyzer a where a.valor>(select o.valor from offsets o where o.id=1)  order by a.id asc";
        ArrayList<Valor> r=new ArrayList<>();
        try {
            DataBase dbiot = new DataBase(context, "aquatek", null, 1);
            SQLiteDatabase db = dbiot.getReadableDatabase();

            Cursor c = db.rawQuery(sql, null);
            c.moveToFirst();
            for(int i=0;i<c.getCount();i++){
                r.add(new Valor(c.getInt(0),c.getDouble(1),c.getString(2)));
                c.moveToNext();
            }
            c.close();
            db.close();
            Collections.reverse(r);
            return r;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Devuelve el numero de veces que se sobrepasa el offset
     * @param context contexto
     * @return numero de incidencias
     */
    public static int numIncidencias(Context context){
        String sql="select count(*) from analyzer a where a.valor>(select o.valor from offsets o where o.id=1)";
        int r = -1;

        try{
            DataBase dbiot = new DataBase(context, "aquatek", null, 1);
            SQLiteDatabase db = dbiot.getReadableDatabase();

            Cursor c = db.rawQuery(sql, null);
            if(c.moveToFirst()) {
                r=c.getInt(0);
            }
            c.close();
            db.close();
            return r;
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Devuelve el ultimo valor de la tabla
     * @param context contexto
     * @return valor ultimo
     */
    public static double ultimoValor(Context context){
        String sql="select valor from analyzer order by id desc limit 1";
        double r = -1.00;

        try{
            DataBase dbiot = new DataBase(context, "aquatek", null, 1);
            SQLiteDatabase db = dbiot.getReadableDatabase();

            Cursor c = db.rawQuery(sql, null);
            if(c.moveToFirst()) {
                r=c.getDouble(0);
            }
            c.close();
            db.close();
            return r;
        }catch (Exception e){
            e.printStackTrace();
            return -1.00;
        }
    }

    /**
     * Se devuelve la fecha del ultimo valor
     * @param context contexto
     * @return fecha ultima
     */
    public static String ultimoFecha(Context context){
        String sql="select tiempo from analyzer order by id desc limit 1";
        String r = "";

        try{
            DataBase dbiot = new DataBase(context, "aquatek", null, 1);
            SQLiteDatabase db = dbiot.getReadableDatabase();

            Cursor c = db.rawQuery(sql, null);
            if(c.moveToFirst()) {
                r=c.getString(0);
            }
            c.close();
            db.close();
            return r;
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Se devuelve el promedio de valores
     * @param context contexto
     * @return promedio
     */
    public static double promedio(Context context){
        String sql="select avg(valor) from analyzer";
        double r = -1.00;

        try{
            DataBase dbiot = new DataBase(context, "aquatek", null, 1);
            SQLiteDatabase db = dbiot.getReadableDatabase();

            Cursor c = db.rawQuery(sql, null);
            if(c.moveToFirst()) {
                r=c.getDouble(0);
            }
            c.close();
            db.close();
            return r;
        }catch (Exception e){
            e.printStackTrace();
            return -1.00;
        }
    }

    /**
     * Se devuelve el offset establecido
     * @param context contexto
     * @return valor del offset
     */
    public static double offset(Context context){
        String sql="select valor from offsets where id=1";
        double r = -1.00;

        try{
            DataBase dbiot = new DataBase(context, "aquatek", null, 1);
            SQLiteDatabase db = dbiot.getReadableDatabase();

            Cursor c = db.rawQuery(sql, null);
            if(c.moveToFirst()) {
                r=c.getDouble(0);
            }
            c.close();
            db.close();
            Log.d("OFFSET","OFFSET SACADO"+r);
            return r;
        }catch (Exception e){
            e.printStackTrace();
            Log.d("OFFSET","FALLOO");
            return -1.00;
        }
    }



}
