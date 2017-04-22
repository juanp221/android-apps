package com.example.salalinux.tallerpractico;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ProfileActivity extends AppCompatActivity {

    private EditText per_nom;
    private EditText per_pais;
    private EditText per_ciudad;
    private EditText per_deport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        per_nom = (EditText) findViewById(R.id.per_nom);
        per_pais = (EditText) findViewById(R.id.per_pais);
        per_ciudad = (EditText) findViewById(R.id.per_ciudad);
        per_deport = (EditText) findViewById(R.id.per_deport);

        SharedPreferences prefe = getSharedPreferences("datos", Context.MODE_PRIVATE);

        per_nom.setText(prefe.getString("nombre", ""));
        per_pais.setText(prefe.getString("pais", ""));
        per_ciudad.setText(prefe.getString("ciudad", ""));
        per_deport.setText(prefe.getString("deporte", ""));
    }

    public void inicio (View v) {
        finish();
    }
}
