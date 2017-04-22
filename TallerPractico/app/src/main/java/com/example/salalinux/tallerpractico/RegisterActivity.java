package com.example.salalinux.tallerpractico;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RegisterActivity extends AppCompatActivity {

    private EditText reg_nom;
    private EditText reg_pais;
    private EditText reg_ciudad;
    private EditText reg_deport;

    private EditText reg_email;
    private EditText reg_pass;
    private EditText reg_passc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        reg_nom = (EditText) findViewById(R.id.reg_nom);
        reg_pais = (EditText) findViewById(R.id.reg_pais);
        reg_ciudad = (EditText) findViewById(R.id.reg_ciudad);
        reg_deport = (EditText) findViewById(R.id.reg_deport);

        reg_email = (EditText) findViewById(R.id.reg_email);
        reg_pass = (EditText) findViewById(R.id.reg_pass);
        reg_passc = (EditText) findViewById(R.id.reg_passc);
    }

    public void registrar(View v) {
        SharedPreferences preferencias = getSharedPreferences("datos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString("nombre", reg_nom.getText().toString());
        editor.putString("pais", reg_pais.getText().toString());
        editor.putString("ciudad", reg_ciudad.getText().toString());
        editor.putString("deporte", reg_deport.getText().toString());

        if (reg_pass.getText().toString().equals(reg_passc.getText().toString()))
        {
            alta(); //envia a la base de datos
            editor.commit(); //envia por el shared preference
            finish();
        } else {
            reportError();
        }
    }

    private void reportError() {
        Toast.makeText(this, "Las contraseñas debe coincidir", Toast.LENGTH_SHORT).show();
    }

    public boolean alta() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String user = reg_email.getText().toString();
        String pass = encrypt(reg_pass.getText().toString());
        ContentValues registro=new ContentValues();
        registro.put("email",user);
        registro.put("password",pass);
        bd.insert("usuarios",null,registro);
        bd.close();
        Toast.makeText(this,"Se guardaron los datos del usuario",Toast.LENGTH_SHORT).show();
        return true;
    }

    private String encrypt(String pass) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.reset();
        md.update(pass.getBytes());
        byte[] digest = md.digest();
        BigInteger bigInt = new BigInteger(1,digest);
        String hashtext = bigInt.toString(16);
        // Now we need to zero pad it if you actually want the full 32 chars.
        while(hashtext.length() < 32 ){
            hashtext = "0"+hashtext;
        }
        return hashtext;
    }
}
