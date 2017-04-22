package com.example.salalinux.tallerpractico;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private EditText log_user;
    private EditText log_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        log_user = (EditText) findViewById(R.id.log_user);
        log_pass = (EditText) findViewById(R.id.log_pass);
    }

    public void oauth (View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getReadableDatabase();
        String user = log_user.getText().toString();
        String pass = log_pass.getText().toString();

        Cursor fila = bd.rawQuery("SELECT password FROM usuarios WHERE email='"+user+"'", null);

        if(fila.moveToFirst()) {
            if (comparePassword(pass, fila.getString(0)))
            {
                Intent mainActivity = new Intent(this, MainActivity.class);
                startActivity(mainActivity);
            } else {
                reportError();
            }
        } else {
            reportError();
        }
    }
    
    private void reportError() {
        Toast.makeText(this, "Usuario o Contraseña no corresponde", Toast.LENGTH_LONG).show();
    }

    private boolean comparePassword(String pass, String bd_pass) {
        return encrypt(pass).equals(bd_pass);
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

    public void  registrar (View v) {
        Intent registrarActivity = new Intent(this, RegisterActivity.class);
        startActivity(registrarActivity);
    }
}
