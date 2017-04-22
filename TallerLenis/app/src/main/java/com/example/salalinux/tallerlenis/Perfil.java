package com.example.salalinux.tallerlenis;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Perfil extends AppCompatActivity {
    TextView name;
    TextView country;
    TextView city;
    TextView sport;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        name=(TextView)findViewById(R.id.name);
        country=(TextView)findViewById(R.id.country);
        city=(TextView)findViewById(R.id.city);
        sport=(TextView)findViewById(R.id.sport);
        SharedPreferences prefe=getSharedPreferences("datos", Context.MODE_PRIVATE);
        name.setText("nombre: "+prefe.getString("name",""));
        country.setText("pais: "+prefe.getString("country",""));
        city.setText("ciudad: "+prefe.getString("city",""));
        sport.setText("sport: "+prefe.getString("sport",""));
    }

    public void inicio(View v){
        Intent i =new Intent(this,MainActivity.class);
        startActivity(i);
        finish();
    }

}
