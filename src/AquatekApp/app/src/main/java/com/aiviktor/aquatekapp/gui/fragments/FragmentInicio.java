package com.aiviktor.aquatekapp.gui.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.aiviktor.aquatekapp.R;
import com.aiviktor.aquatekapp.database.Datos;
import com.aiviktor.aquatekapp.services.SyncService;

/**
 * Created by VICTOR on 27/10/2016.
 *
 * Fragmento para mostrar al inicio de la actividad principal
 * se muestra el ultimo valor de las mediciones
 * su hora y fecha, como tambien el numero de veces que se supera
 * el offset establecido en la configuraciones
 *
 * El valor final se mostrara en rojo si se pasa el offset,
 * en negro si ocurre lo contrario
 */

public class FragmentInicio extends Fragment {

    private BroadcastReceiver brSync;
    private TextView txtUltimo,txtFecha,txtOffset,txtIncidencias;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
            Se crea un BroadCastReceiver para actualizar el fragment
            cada vez que se reciven datos del servidor
         */
        brSync = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                updateValues();
            }
        };

    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inicio, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*
            Se inicializan los objetos de la vista
         */
        txtUltimo = (TextView) view.findViewById(R.id.txtUltimo);
        txtFecha = (TextView) view.findViewById(R.id.txtFecha);
        txtOffset = (TextView) view.findViewById(R.id.txtOffset);
        txtIncidencias = (TextView) view.findViewById(R.id.txtNumInc);

        //Se pinta la pantalla
        updateValues();

    }

    @Override
    public void onStart() {
        super.onStart();
        //Se inicia el broadcastreceiver
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(brSync,
                new IntentFilter(SyncService.SYNC_ACTION));
    }

    @Override
    public void onStop() {
        super.onStop();
        //Se detiene el broadcastreceiver
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(brSync);
    }

    /**
     * Sirve para actualizar la pantalla
     */
    private void updateValues() {
        double ulti = Datos.ultimoValor(getContext());
        double offs = Datos.offset(getContext());

        String ult = ulti+" ug/L";
        txtUltimo.setText(ult);
        if(ulti>offs){
            txtUltimo.setTextColor(Color.RED);
        }else{
            txtUltimo.setTextColor(Color.BLACK);
        }
        txtFecha.setText(Datos.ultimoFecha(getContext()));
        String off = offs+" ug/L";
        txtOffset.setText(off);
        String num = Datos.numIncidencias(getContext())+"";
        txtIncidencias.setText(num);
    }
}
