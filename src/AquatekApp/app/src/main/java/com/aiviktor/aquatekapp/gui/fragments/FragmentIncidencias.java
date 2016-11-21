package com.aiviktor.aquatekapp.gui.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.aiviktor.aquatekapp.R;
import com.aiviktor.aquatekapp.database.Datos;
import com.aiviktor.aquatekapp.services.SyncService;
import com.aiviktor.aquatekapp.structures.Grafico;
import com.aiviktor.aquatekapp.structures.Valor;
import com.aiviktor.aquatekapp.utils.GraficoAdapter;
import com.aiviktor.aquatekapp.utils.TableAdapter;

import java.util.ArrayList;

/**
 * Created by VICTOR on 27/10/2016.
 *
 * Fragment que muestra las veces que se supero el offset
 * mostrando la fecha hora y el valor
 */

public class FragmentIncidencias extends Fragment {
    private BroadcastReceiver brSync;
    private ListView listView;

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
        return inflater.inflate(R.layout.fragment_incidencias, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Se inicializa la listview que contiene la tabla
        listView = (ListView) view.findViewById(R.id.listTable);

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
     * Se actualiza la tabla
     */
    private void updateValues() {

        ArrayList<Valor> incidentes = Datos.incidentes(getContext());
        if(incidentes!=null) {
            TableAdapter adapter = new TableAdapter(getContext(),incidentes);

            listView.setAdapter(adapter);
            listView.invalidate();
        }

    }
}
