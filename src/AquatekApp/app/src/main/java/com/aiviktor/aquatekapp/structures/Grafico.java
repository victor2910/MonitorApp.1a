package com.aiviktor.aquatekapp.structures;

import android.content.Context;

import com.aiviktor.aquatekapp.database.Datos;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;

/**
 * Created by VICTOR on 27/10/2016.
 *
 * Estructura del grafico
 */

public class Grafico {

    private String titulo,ultimaFecha;
    private double offset,ultimoValor,promedio;

    ArrayList<Valor> valores;

    public Grafico(Context context){
        titulo="Historial de muestreo";
        valores= Datos.valores(context);
        if(valores!=null) {
            if(valores.size()>0) {
                ultimoValor = valores.get(valores.size() - 1).getValor();
                ultimaFecha = valores.get(valores.size() - 1).getTiempo();
            }
        }else {
            ultimoValor=-1;
            ultimaFecha="";
        }
        promedio=Datos.promedio(context);
        offset=Datos.offset(context);
    }

    /**
     * Devuelbe la lista de entradas de medidas
     * @return arraylist de entrys
     */
    public ArrayList<Entry> getEntries(){
        if(valores!=null) {
            ArrayList<Entry> e = new ArrayList<>();
            for (int i = 0; i < this.valores.size(); i++) {
                e.add(new Entry(i, (float) this.valores.get(i).getValor()));
            }
            return e;
        }else{
            return null;
        }
    }

    public ArrayList<String> getLabels(){
        if(valores.size()>0) {
            ArrayList<String> l = new ArrayList<>();
            for (int i = 0; i < this.valores.size(); i++) {
                l.add(valores.get(i).getTiempo());
            }
            return l;
        }else {
            return null;
        }
    }

    public String getTitulo(){
        return this.titulo;
    }

    public String getUltimaFecha(){
        return this.ultimaFecha;
    }

    public double getOffset(){
        return this.offset;
    }

    public double getUltimoValor(){
        return this.ultimoValor;
    }

    public double getPromedio(){
        return this.promedio;
    }

}
