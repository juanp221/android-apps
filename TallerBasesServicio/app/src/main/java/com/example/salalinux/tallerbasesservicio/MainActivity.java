package com.example.salalinux.tallerbasesservicio;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText nameInput;
    EditText ageInput;
    RadioButton masculinoRB;
    RadioButton femeninoRB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nameInput=(EditText)findViewById(R.id.nameInput);
        ageInput=(EditText)findViewById(R.id.ageInput);
        masculinoRB=(RadioButton)findViewById(R.id.masculinoRB);
        femeninoRB=(RadioButton)findViewById(R.id.femeninoRB);
        startService( new Intent(MainActivity.this,myService.class));
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
    public void insertarDato(View v){
        AdminSQLiteOpenHelper admin= new AdminSQLiteOpenHelper(this,"pruebas",null,1);
        SQLiteDatabase bd=admin.getWritableDatabase();
        String name=nameInput.getText().toString();
        int age =Integer.parseInt(ageInput.getText().toString());
        String sexo="";
        if(masculinoRB.isChecked()){
            sexo="M";
        }
        else if(femeninoRB.isChecked()){
            sexo="F";
        }
        ContentValues registro=new ContentValues();
        registro.put("nombre",name);
        registro.put("edad",age);
        registro.put("genero",sexo);
        bd.insert("pruebas",null,registro);
        bd.close();
        nameInput.setText("");
        ageInput.setText("");
        masculinoRB.setChecked(false);
        femeninoRB.setChecked(false);
        Toast.makeText(this,"Se cargaron los datos",Toast.LENGTH_SHORT).show();
        //Toast.makeText(this,nameInput.getText().toString()+"  "+ageInput.getText().toString(),Toast.LENGTH_SHORT).show();
    }
}
