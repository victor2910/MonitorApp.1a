package com.aiviktor.aquatekapp.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aiviktor.aquatekapp.R;
import com.aiviktor.aquatekapp.structures.Grafico;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

/**
 * Created by VICTOR on 27/10/2016.
 *
 * Adapter de listview para mostrar graficos
 */

public class GraficoAdapter extends BaseAdapter {

    private Context context;
    private  ArrayList<Grafico> graficos;

    public GraficoAdapter(Context context,ArrayList<Grafico> graficos){
        this.context=context;
        this.graficos=graficos;
    }

    @Override
    public int getCount(){return graficos.size();
    }

    @Override
    public Object getItem(int position) {
        return graficos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inf = LayoutInflater.from(context);
        View v = inf.inflate(R.layout.grafica_list,null);

        Grafico g = graficos.get(position);

        TextView txtTitulo = (TextView) v.findViewById(R.id.titulo);
        TextView txtOffset = (TextView) v.findViewById(R.id.txtOffset);
        TextView txtPromedio = (TextView) v.findViewById(R.id.txtPromedio);
        TextView txtUltimo = (TextView) v.findViewById(R.id.txtUltimo);
        TextView txtDate = (TextView) v.findViewById(R.id.txtDate);

        LineChart lc = (LineChart) v.findViewById(R.id.chart);

        txtTitulo.setText(g.getTitulo());
        txtDate.setText(g.getUltimaFecha());
        String prom = g.getPromedio()+"";
        txtPromedio.setText(prom);
        String ulti = g.getUltimoValor()+" ug/L";
        txtUltimo.setText(ulti);
        String off = g.getOffset()+" ug/L";
        txtOffset.setText(off);

        if(g.getEntries()!=null) {
            LineDataSet lineDataSet = new LineDataSet(g.getEntries(), "concentracion ug/L");
            lineDataSet.setFillColor(Color.CYAN);
            lineDataSet.setDrawFilled(true);

            LineData lineData = new LineData(lineDataSet);


            XAxis xAxis = lc.getXAxis();
            xAxis.setLabelRotationAngle(45);
            YAxis leftAxis = lc.getAxisLeft();

            LimitLine ll = new LimitLine((float) g.getOffset()); // set where the line should be drawn
            ll.setLineColor(Color.RED);
            ll.setLineWidth(4f);
            ll.setLabel("Límite");

            leftAxis.addLimitLine(ll);

            lc.setData(lineData);
            lc.setVisibleXRangeMaximum(10);
            lc.moveViewToX(g.getEntries().size() - 10);
        }

        return v;
    }
}
