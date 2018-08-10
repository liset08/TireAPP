package com.example.pepe.tireapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pepe.tireapp.Activities.GestionNeumatico;
import com.example.pepe.tireapp.Activities.MenuAuditoriaActivity;
import com.example.pepe.tireapp.Activities.MenuCamionActivity;
import com.example.pepe.tireapp.Activities.NeumaticoListActivity;
import com.example.pepe.tireapp.model.Usuario;
import com.example.pepe.tireapp.repositories.UsuarioRepository;

public class MenuPrincipal extends AppCompatActivity {



    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        boolean usuariologeado = UsuarioRepository.verifyLogeo();



       if(!usuariologeado){
           Usuario user = UsuarioRepository.getUsuario();
           NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

           ImageView photoImage = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.menu_photo);
           photoImage.setBackgroundResource(R.drawable.ic_profile);

           TextView fullnameText = (TextView) navigationView.getHeaderView(0).findViewById(R.id.menu_fullname);
           fullnameText.setText(user.getNombre() + " " + user.getApellido());

           TextView emailText = (TextView) navigationView.getHeaderView(0).findViewById(R.id.menu_email);
           emailText.setText(user.getEmpresa() + " - " + user.getRol());

           inicializate();
       }else{
           Intent intent = new Intent(this,MainActivityIngreso.class);
           startActivity(intent);
           finish();
       }




    }


    private void inicializate(){
        // Set DrawerLayout
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Set drawer toggle icon
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }

        // Set NavigationItemSelectedListener
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                // Do action by menu item id
                switch (menuItem.getItemId()){
                    case R.id.nav_inicio:
                        Intent intent = new Intent(MenuPrincipal.this, NeumaticoListActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.man_camion:
                        Intent intent2 = new Intent(MenuPrincipal.this, MenuCamionActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.intro_medidas:
                        Intent intent3 = new Intent(MenuPrincipal.this, BuscarNeumaticoxCamionActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.cambio_y_rota:
                        break;
                    case R.id.auditoria:
                        Intent intent5 = new Intent(MenuPrincipal.this, MenuAuditoriaActivity.class);
                        intent5.putExtra("tipogestion" , "auditor");
                        startActivity(intent5);
                        break;
                    case R.id.nav_logout:
                        new AlertDialog.Builder(MenuPrincipal.this)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("Cerrar Sesión")
                                .setMessage("¿Está seguro de cerrar sesión?")
                                .setPositiveButton("Si", new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(MenuPrincipal.this , MainActivityIngreso.class);
                                        UsuarioRepository.logout();
                                        finish();
                                        startActivity(intent);
                                    }

                                })
                                .setNegativeButton("No", null)
                                .show();
                        break;
                }

                // Close drawer
                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: // Option open drawer
                if(!drawerLayout.isDrawerOpen(GravityCompat.START))
                    drawerLayout.openDrawer(GravityCompat.START);   // Open drawer
                else
                    drawerLayout.closeDrawer(GravityCompat.START);    // Close drawer
                return true;
        }
        return super.onOptionsItemSelected(item);
    }




    public void GestionCamion(View paramView){

        Intent intent = new Intent(this, MenuCamionActivity.class);
        startActivity(intent);

    }

    public void GestionLlanta(View paramView){
        Intent intent = new Intent(this, NeumaticoListActivity.class);
        startActivity(intent);

    }
    public void Medida(View paramView){
        Intent intent = new Intent(this, BuscarNeumaticoxCamionActivity.class);
        startActivity(intent);

    }

    public void Salir(View paramView){

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Cerrar Sesión")
                .setMessage("¿Está seguro de cerrar sesión?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MenuPrincipal.this , MainActivityIngreso.class);
                        UsuarioRepository.logout();
                        finish();
                        startActivity(intent);
                    }

                })
                .setNegativeButton("No", null)
                .show();





    }

    public void GestionAuditoria(View view){
        Intent intent = new Intent(this, MenuAuditoriaActivity.class);
        intent.putExtra("tipogestion" , "auditor");
        startActivity(intent);
    }

}
