package com.my.first.taller_sharedpreference;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText edtusuario, edtpassword;
    Button btnIniciarSesion;
    //CheckBox checkGuardarSesion;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String llave="Sesion";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializarElementos();
        if (resvisarSesion()){
            AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
            alerta.setMessage("Desea continuar con la Sesión Guarda?")
                    .setCancelable(false)
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //startActivity(new Intent(this,ActivityPrincipal.class));
                            edtusuario.setText(preferences.getString("usuario",""));
                            edtpassword.setText(preferences.getString("password",""));
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            AlertDialog titulo = alerta.create();
            titulo.setTitle("Continuar Sesión");
            titulo.show();

        }else{
            String mensaje = "Iniciar Sesion";
            Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
        }

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtusuario.getText().toString().equals("admin") && edtpassword.getText().toString().equals("admin")){
                    AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
                    alerta.setMessage("Desea Guardar la Sesión?")
                            .setCancelable(false)
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(MainActivity.this, "Bienvenido Usuario: "+edtusuario.getText().toString(),Toast.LENGTH_SHORT).show();
                                    guardarSesion(true);
                                    Intent intent = new Intent(getApplicationContext(),ActivityPrincipal.class);
                                    intent.putExtra("usuario",edtusuario.getText().toString());
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(MainActivity.this, "Bienvenido Usuario: "+edtusuario.getText().toString(),Toast.LENGTH_SHORT).show();
                                    editor.putBoolean(llave,false);
                                    Intent intent = new Intent(getApplicationContext(),ActivityPrincipal.class);
                                    intent.putExtra("usuario",edtusuario.getText().toString());
                                    startActivity(intent);
                                    finish();
                                }
                            });
                    AlertDialog titulo = alerta.create();
                    titulo.setTitle("Guardar Sesión");
                    titulo.show();
                }else{
                    Toast.makeText(MainActivity.this,"Usuario Incorrecto",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean resvisarSesion(){
        return this.preferences.getBoolean(llave,false);
    }

    private void guardarSesion(boolean checked){
        editor.putBoolean(llave,checked);
        editor.putString("usuario",edtusuario.getText().toString());
        editor.putString("password",edtpassword.getText().toString());
        editor.apply();
    }

    private void inicializarElementos(){

        preferences = getSharedPreferences("sesiones",Context.MODE_PRIVATE);
        editor = preferences.edit();

        edtusuario = findViewById(R.id.edtUsuario);
        edtpassword =findViewById(R.id.edtPassword);
        btnIniciarSesion = findViewById(R.id.btnLogin);
        //checkGuardarSesion = findViewById(R.id.checkBoxGuardarSesion);
    }
}