package com.example.salalinux.tallerbasesservicio;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MostrarDatos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_datos);
        //String[] lista={"lol1","lol2","lol3"};
        ArrayAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        ListView listView = (ListView) findViewById(R.id.datosList);
        listView.setAdapter(adapter);
        AdminSQLiteOpenHelper admin=new AdminSQLiteOpenHelper(this,"pruebas",null,1);

        SQLiteDatabase bd=admin.getWritableDatabase();

        Cursor fila=bd.rawQuery("select * from pruebas",null);
        while(fila.moveToNext()){
            adapter.insert(fila.getString(1)+" "+fila.getString(2)+"  "+fila.getString(3),0);
        }
        //adapter.insert("lol",0);
        bd.close();
        /*
        if(fila.moveToFirst()){
            //Intent i=new Intent(this,Bienvenido.class);
            //startActivity(i);
            //bd.close();
            finish();

        }
        else{
            Toast.makeText(this,"Usuario o Contraseña incorrecta", Toast.LENGTH_SHORT).show();
        }
        */

    }
    @Override public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        if(id==R.id.insertar){
            Intent i=new Intent(this,MainActivity.class);
            startActivity(i);
            finish();
            //Toast.makeText(this,"Se seleccionó la primer opción",Toast.LENGTH_LONG).show();
        }
        if(id==R.id.mostrar){
            Intent i=new Intent(this,MostrarDatos.class);
            startActivity(i);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
