package com.aiviktor.aquatekapp.gui.fragments;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.aiviktor.aquatekapp.R;
import com.aiviktor.aquatekapp.database.DataBase;
import com.aiviktor.aquatekapp.database.Datos;
import com.aiviktor.aquatekapp.gui.LoginActivity;

/**
 * Created by VICTOR on 27/10/2016.
 *
 * Fragment de configuracion,
 * Se permite al usuario seleccionar entre desconectado y conectado a internet
 * para el ahorro de datos
 * tambien permite cambiar el valor de offset
 * como tambien permitir limpiar la base de datos y cerrar secion
 */

public class FragmentConf extends Fragment {
    TextView txtOffset;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_conf, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Se abre las preferencias de la aplicacion
        SharedPreferences preferences = getContext().getSharedPreferences("aquatek", Context.MODE_PRIVATE);

        //Inicializa elementos del fragment
        Button btnOffset = (Button) view.findViewById(R.id.btnOffset);
        Button btnClearDB = (Button) view.findViewById(R.id.btnClearDB);
        Button btnCerrar = (Button) view.findViewById(R.id.btnCerrar);

        txtOffset = (TextView) view.findViewById(R.id.txtOffset);

        String off = Datos.offset(getContext())+" ug/L";

        txtOffset.setText(off);

        final Switch switchConn = (Switch) view.findViewById(R.id.swCon);
        if(preferences.getBoolean("constatus",false)){
            switchConn.setChecked(true);
            switchConn.setText("Conectado");
        }else {
            switchConn.setChecked(false);
            switchConn.setText("Desconectado");
        }

        btnOffset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarInputDialog();
            }
        });


        btnClearDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarMessageDialog(getContext(),1,"¿Borrar datos internos?");
            }
        });

        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarMessageDialog(getContext(),2,"¿Cerrar sesion?");
            }
        });

        switchConn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                conectar(isChecked);
                if(isChecked){
                    switchConn.setText("Conectado");
                }else {
                    switchConn.setText("Desconectado");
                }
            }
        });
    }

    /**
     * muestra un cuadro de dialogo para ingresar datos por teclado,
     * en este caso para setear el offset
     */
    private void mostrarInputDialog(){
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);


        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Double valor = Double.parseDouble(editText.getText().toString());
                        //Toast.makeText(getContext(),valor+"",Toast.LENGTH_SHORT).show();
                        if(valor>0.0) {

                            String text;
                            text = valor+" ug/L";
                            txtOffset.setText(text);
                            txtOffset.invalidate();
                            setOffset(valor);
                        }
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    /**
     * Conecta o desconecta de internet
     */
    private void conectar(boolean option){
        SharedPreferences preferences = getContext().getSharedPreferences("aquatek",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("constatus",option);
        editor.apply();
    }

    /**
     * Muestra un mensaje de dialogo con opciones de confirmacion
     * @param context contexto
     * @param op opcion valida para esta clase (1 para limpiar bd, y 2 para cerrar sesion)
     * @param mensaje mensaje a mostrar
     */
    private void mostrarMessageDialog(Context context,final int op,final String mensaje){
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View promptView = layoutInflater.inflate(R.layout.message_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setView(promptView);

        final TextView textView = (TextView) promptView.findViewById(R.id.textView);
        textView.setText(mensaje);

        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        switch (op) {
                            case 1: clearDB(getContext());
                                break;
                            case 2: cerrar(getContext());
                        }
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    /**
     * Cierra sesion
     * @param context contexto
     */
    private void cerrar(Context context){
        SharedPreferences preferences = context.getSharedPreferences("aquatek",Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean("login_status",false);
        editor.apply();

        Intent intent = new Intent(context,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        clearDB(context);
        Toast.makeText(context,"Sesion finalizada",Toast.LENGTH_SHORT).show();

        startActivity(intent);
    }

    /**
     * Limpia la base de datos de la app
     * @param context contexto
     */
    private void clearDB(Context context){
        String DEL_QUERY = "delete from analyzer";
        try{
            DataBase dbiot = new DataBase(context, "aquatek", null, 1);
            SQLiteDatabase db = dbiot.getReadableDatabase();
            db.execSQL(DEL_QUERY);
            Toast.makeText(context,"Base de datos limpio",Toast.LENGTH_SHORT).show();
        }catch (Exception e){

        }
    }

    /**
     * Setea el offset para las medidas
     * @param offset valor del nuevo offset
     */
    private void setOffset(double offset){
        DataBase dbiot = new DataBase(getContext(), "aquatek", null, 1);
        SQLiteDatabase db = dbiot.getWritableDatabase();
        try {
            ContentValues update = new ContentValues();
            update.put("valor", offset);
            db.update("offsets", update, "id=1", null);
            Toast.makeText(getContext(),"Offset actualizado",Toast.LENGTH_SHORT).show();
        }catch (SQLiteException e) {
            e.printStackTrace();
            Log.d("VAL", "error en actualizar");
        }
        db.close();
        dbiot.close();
    }
}
