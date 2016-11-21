package com.aiviktor.aquatekapp.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aiviktor.aquatekapp.R;
import com.aiviktor.aquatekapp.database.Datos;
import com.aiviktor.aquatekapp.structures.Valor;

import java.util.ArrayList;

/**
 * Created by VICTOR on 27/10/2016.
 *
 * Adapter para mostrar una tabla con los valores pasados del offset
 */

public class TableAdapter extends BaseAdapter {

    Context context;
    ArrayList<Valor> incidentes;

    public TableAdapter(Context context,ArrayList<Valor> incidentes){
        this.context=context;
        this.incidentes=incidentes;
    }

    @Override
    public int getCount() {
        return incidentes.size();
    }

    @Override
    public Object getItem(int position) {
        return incidentes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inf = LayoutInflater.from(context);
        View v = inf.inflate(R.layout.incidencias_list,null);

        TextView txtFecha = (TextView) v.findViewById(R.id.txtFecha);
        TextView txtValor = (TextView) v.findViewById(R.id.txtValor);

        if(incidentes!=null) {
            Valor valor = incidentes.get(position);

            txtFecha.setText(valor.getTiempo());
            String val = valor.getValor() + "";
            txtValor.setText(val);
        }
        return v;
    }
}
