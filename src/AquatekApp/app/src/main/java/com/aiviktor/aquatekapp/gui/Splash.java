package com.aiviktor.aquatekapp.gui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aiviktor.aquatekapp.R;

/**
 * Actividad de splash (estetico)
 * Comprueba si esta logueado o no
 * si es verdad, pasa a activida principal, sino pasa a actividad de login
 * Dura 2 segundos
 */
public class Splash extends AppCompatActivity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Se abre las preferencias de la app
        SharedPreferences sharedPref = getApplication().getSharedPreferences("aquatek",MODE_PRIVATE);

        //Si ya esta logueado, abre el MainActivity, sino LoginAcitvity
        if(sharedPref.getBoolean("login_status",false)){ //Se lee login_status
            intent = new Intent(Splash.this,MainActivity.class);
        }else{
            intent = new Intent(Splash.this,LoginActivity.class);
        }

        //Hilo de espera del splash
        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(2000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }
}
