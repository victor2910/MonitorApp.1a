package com.aiviktor.aquatekapp.services;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.aiviktor.aquatekapp.database.DataBase;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by VICTOR on 27/10/2016.
 *
 * Servicio para sincronizar los datos del servidor con la app
 */

public class SyncService extends Service {

    //Token para el filtro de broadcast
    public final static String SYNC_ACTION = "com.aiviktor.aquatekapp.syncservice";

    private LocalBroadcastManager broadcastManager;
    private Intent intent;
    private final Handler handler = new Handler();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        broadcastManager = LocalBroadcastManager.getInstance(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler.removeCallbacks(updateRunnable);
        handler.post(updateRunnable); //1 min
        return START_STICKY;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(updateRunnable);
    }

    /**
     * Hilo de sincronizacion, se solicita datos al servidor cada 1 min
     */
    private Runnable updateRunnable = new Runnable() {
        @Override
        public void run() {
            SharedPreferences preferences = getApplication().getSharedPreferences("aquatek",MODE_PRIVATE);
            if (preferences.getBoolean("constatus",false))
                syncDB();
            intent = new Intent(SYNC_ACTION);
            broadcastManager.sendBroadcast(intent);
            handler.postDelayed(this,60000); //1 min
        }
    };

    /**
     * funcion de sincronizacion, se solicita datos al servidor usando el protocolo
     * http y el metodo GET, a la URL del webservice del servidor
     */
    private void syncDB(){

        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());

        //URL del webservice
        final String URL = "http://54.67.108.131:8080/Aquatek/Analyzer/Data";

        StringRequest strRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        updateDB(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("GET_VALUES","Error");
                    }
                }) {
        };
        mRequestQueue.add(strRequest);

    }

    /**
     * Funcion para actualizar la base de datos con los datos enviados por el servidor
     * @param response datos de respuesta del servidor, estos datos vienen en forma de
     *                 de JSON file
     */
    private void updateDB(String response){
        //Log.d("VALORES_MED",response);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
            String status = jsonObject.getString("status");
            if(status.equals("succes")){
                JSONArray jsonArray = null;
                DataBase dbiot = new DataBase(getApplicationContext(), "aquatek", null, 1);
                SQLiteDatabase db = dbiot.getWritableDatabase();

                try {
                    jsonArray = jsonObject.getJSONArray("analyzer");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            ContentValues nuevo = new ContentValues();
                            nuevo.put("id", jsonArray.getJSONObject(i).getInt("id"));
                            nuevo.put("valor", jsonArray.getJSONObject(i).getDouble("valor"));
                            nuevo.put("tiempo", jsonArray.getJSONObject(i).getString("tiempo"));
                            db.insert("analyzer", null, nuevo);
                            Log.d("VAL","Insertado con exito");
                        } catch (SQLiteException e) {
                            e.printStackTrace();
                            Log.d("VAL", "Ya existe medida");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("VAL", "Error en el archivo JSON");
                        }
                    }
                }

                db.close();
                dbiot.close();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
