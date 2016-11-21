package com.aiviktor.aquatekapp.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.aiviktor.aquatekapp.R;
import com.aiviktor.aquatekapp.gui.fragments.FragmentAbout;
import com.aiviktor.aquatekapp.gui.fragments.FragmentConf;
import com.aiviktor.aquatekapp.gui.fragments.FragmentGrafica;
import com.aiviktor.aquatekapp.gui.fragments.FragmentIncidencias;
import com.aiviktor.aquatekapp.gui.fragments.FragmentInicio;
import com.aiviktor.aquatekapp.services.SyncService;

/**
 * Actividad principal de la app,
 * muestra un drawerlayout como menu para acceder a disitintos fragments
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Intent syncIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setTitle("Inicio");
        setFragment(0);

        //Se inicializa el servicio de sincronizacion de datos con el servidor
        syncIntent = new Intent(this, SyncService.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Se inicia el servicio
        startService(syncIntent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_dash) {
            getSupportActionBar().setTitle("Inicio");
            setFragment(0);

        } else if (id == R.id.nav_conc) {
            getSupportActionBar().setTitle("Historial de muestreo");
            setFragment(1);
        } else if (id == R.id.nav_inc) {
            getSupportActionBar().setTitle("Historial de incidencias");
            setFragment(2);
        } else if (id == R.id.nav_conf) {
            getSupportActionBar().setTitle("Configuraciones");
            setFragment(3);
        } else if (id == R.id.nav_about) {
            getSupportActionBar().setTitle("Acerca de");
            setFragment(4);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setFragment(int position){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment=null;
        switch (position){
            case 0:
                fragment = new FragmentInicio();
                break;
            case 1:
                fragment = new FragmentGrafica();
                break;
            case 2:
                fragment = new FragmentIncidencias();
                break;
            case 3:
                fragment = new FragmentConf();
                break;
            case 4:
                fragment = new FragmentAbout();
                break;
            default:
                break;
        }
        if(fragment!=null){
            fragmentTransaction.replace(R.id.content_main,fragment);
            fragmentTransaction.commit();
        }
    }
}
