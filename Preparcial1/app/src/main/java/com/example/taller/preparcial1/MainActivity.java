package com.example.taller.preparcial1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    EditText nombre, email, telefono;
    SharedPreferences preferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nombre = (EditText)findViewById(R.id.nombre);
        email = (EditText)findViewById(R.id.email);
        telefono = (EditText)findViewById(R.id.telefono);
        preferencias = getSharedPreferences("datos",Context.MODE_PRIVATE);



    }

    public void confirmar(View view){

        String n = nombre.getText().toString().trim();
        String e = email.getText().toString().trim();
        String t = telefono.getText().toString().trim();

        //Validar que ningún campo no sea vacío
        if(n.isEmpty()){
            showError(nombre, "El nombre no debe ser vacío.");
        }
        else if(e.isEmpty()){
            showError(email, "El email no debe ser vacío.");
        }
        else if(t.isEmpty()){
            showError(email, "El télefono no debe ser vacío.");
        }
        else{
            if(isValidEmail(e)){
                SharedPreferences.Editor editor = preferencias.edit();
                editor.putString("nombre",n);
                editor.putString("email",e);
                editor.putString("telefono",t);
                editor.commit();

                nombre.setText("");
                email.setText("");
                telefono.setText("");

                Toast.makeText(this, "Información almacenada con éxito.", Toast.LENGTH_LONG).show();
                startService(new Intent(MainActivity.this, MyService.class));
            }
            else{
                showError(email, "Debe ingresar un email válido.");
            }
        }

    }

    private void showError(EditText field, String msg){
        field.requestFocus();
        field.setError(msg);
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        stopService(new Intent(MainActivity.this, MyService.class));
    }

}
