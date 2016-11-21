package com.aiviktor.aquatekapp.structures;

/**
 * Created by VICTOR on 27/10/2016.
 *
 * Clase valor que contiene el id de bd, el tiempo y magnitud
 */

public class Valor {

    private int id;
    private String tiempo;
    private double valor;

    public Valor(int id, double valor,String tiempo){
        this.id=id;
        this.tiempo=tiempo;
        this.valor=valor;
    }

    public void setId(int id){
        this.id=id;
    }

    public int getId(){
        return id;
    }

    public String getTiempo() {
        return tiempo;
    }

    public double getValor() {
        return valor;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
