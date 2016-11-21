package com.aiviktor.aquatekapp.gui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aiviktor.aquatekapp.R;

/**
 * Actividad para iniciar sesion en la aplicacion
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText etNombre,etPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Se incializan los elementos de la interfaz
        etNombre = (EditText) findViewById(R.id.txtName);
        etPass = (EditText) findViewById(R.id.txtPass);

        Button btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.btnLogin){
            String name = etNombre.getText().toString();
            String pass = etPass.getText().toString();
            if(name.equals("admin")&&pass.equals("admin")){

                //Se abre las preferencias de la app
                SharedPreferences preferences = getApplication().getSharedPreferences("aquatek",MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("login_status",true); //Se setea login_status a true
                editor.apply();

                //Se pasa a la actividad principal
                Intent intent = new Intent(this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Toast.makeText(this,"Sesion iniciada",Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }else{
                etNombre.setText(null);
                etPass.setText(null);
                Toast.makeText(this,"Usuario o password incorrectos",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
