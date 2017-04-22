package com.example.salalinux.taller013;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText et1,et2,et3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1=(EditText)findViewById(R.id.et1);
        et2=(EditText)findViewById(R.id.et2);
        et3=(EditText)findViewById(R.id.et3);
    }

    public void alta(View v){
        AdminSQLiteOpenHelper admin= new AdminSQLiteOpenHelper(this,"administracion",null,1);
        SQLiteDatabase bd=admin.getWritableDatabase();
        String cod=et1.getText().toString();
        String descri=et2.getText().toString();
        String pre=et3.getText().toString();
        ContentValues registro=new ContentValues();
        registro.put("codigo",cod);
        registro.put("descripcion",descri);
        registro.put("precio",pre);
        bd.insert("articulos",null,registro);
        bd.close();
        et1.setText("");
        et2.setText("");
        et3.setText("");
        Toast.makeText(this,"Se cargaron los datos del artículo",Toast.LENGTH_SHORT).show();
    }
    public void consultarporcodigo(View v){
        AdminSQLiteOpenHelper admin=new AdminSQLiteOpenHelper(this,"administracion",null,1);
        SQLiteDatabase bd=admin.getWritableDatabase();
        String cod=et1.getText().toString();
        Cursor fila=bd.rawQuery("select descripcion,precio from articulos where codigo="+cod,null);
        if(fila.moveToFirst()){
            et2.setText(fila.getString(0));
            et3.setText(fila.getString(1));
        }
        else{
            Toast.makeText(this,"No existe un artículo con dicho código", Toast.LENGTH_SHORT).show();
        }
        bd.close();
    }
    public void bajaporcodigo(View v){
        AdminSQLiteOpenHelper admin=new AdminSQLiteOpenHelper(this,"administracion",null,1);
        SQLiteDatabase bd=admin.getWritableDatabase();
        String cod=et1.getText().toString();
        int cant=bd.delete("articulos","codigo="+cod,null);
        bd.close();
        et1.setText("");
        et2.setText("");
        et3.setText("");
        if (cant==1){
            Toast.makeText(this,"EL articulo a sido eliminado",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this,"No existe el articulo con dicho codigo",Toast.LENGTH_SHORT).show();
        }



    }
    public void modificacion(View v){
        AdminSQLiteOpenHelper admin=new AdminSQLiteOpenHelper(this,"administracion",null,1);
        SQLiteDatabase bd=admin.getWritableDatabase();
        String cod=et1.getText().toString();
        String descri=et2.getText().toString();
        String pre=et3.getText().toString();
        ContentValues registro=new ContentValues();
        registro.put("codigo",cod);
        registro.put("descripcion",descri);
        registro.put("precio",pre);
        int cant=bd.update("articulos",registro,"codigo="+cod,null);
        bd.close();
        if(cant==1){
            Toast.makeText(this,"se modificaron los",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this,"no existe un artíco con el código ingresado",Toast.LENGTH_SHORT).show();
        }
    }
}
