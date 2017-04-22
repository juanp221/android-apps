package com.example.salalinux.tallerlenis;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Registrarse extends AppCompatActivity {
    EditText nameInput;
    EditText countryInput;
    EditText cityInput;
    EditText sportInput;
    EditText emailInput;
    EditText passwordInput;
    EditText password2Input;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
        nameInput=(EditText)findViewById(R.id.nameInput);
        countryInput=(EditText)findViewById(R.id.countryInput);
        cityInput=(EditText)findViewById(R.id.cityInput);
        sportInput=(EditText)findViewById(R.id.sportInput);
        emailInput=(EditText)findViewById(R.id.emailInput);
        passwordInput=(EditText)findViewById(R.id.passwordInput);
        password2Input=(EditText)findViewById(R.id.password2Input);
    }

    public void guardar(View v){



        if(passwordInput.getText().toString().equals(password2Input.getText().toString())){
            SharedPreferences preferences=getSharedPreferences("datos", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=preferences.edit();
            editor.putString("name",nameInput.getText().toString());
            editor.putString("country",countryInput.getText().toString());
            editor.putString("city",cityInput.getText().toString());
            editor.putString("sport",sportInput.getText().toString());
            //Toast.makeText(this,"Datos guardados",Toast.LENGTH_LONG).show();
            editor.commit();
            AdminSQLiteOpenHelper admin= new AdminSQLiteOpenHelper(this,"prueba",null,1);
            SQLiteDatabase bd=admin.getWritableDatabase();
            String email=emailInput.getText().toString();
            String password=passwordInput.getText().toString();
            ContentValues registro=new ContentValues();
            registro.put("email",email);
            registro.put("password",SHA1(password));
            bd.insert("prueba",null,registro);
            bd.close();
            //Toast.makeText(this,"Se cargaron los datos del artículo",Toast.LENGTH_SHORT).show();

            Intent i =new Intent(this,MainActivity.class);
            Toast.makeText(this,SHA1("hola")+"",Toast.LENGTH_LONG).show();
            startActivity(i);
            finish();
        }
        else{
            Toast.makeText(this,"La constraseñas no coinciden, por favor ingrese en ambas campos la misma",Toast.LENGTH_LONG).show();
        }

        //Toast.makeText(this,SHA1("hola")+"",Toast.LENGTH_LONG).show();
    }
    //tomado de: http://stackoverflow.com/questions/5980658/how-to-sha1-hash-a-string-in-android
    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex( byte[] bytes )
    //tomado de: http://stackoverflow.com/questions/5980658/how-to-sha1-hash-a-string-in-android
    {
        char[] hexChars = new char[ bytes.length * 2 ];
        for( int j = 0; j < bytes.length; j++ )
        {
            int v = bytes[ j ] & 0xFF;
            hexChars[ j * 2 ] = hexArray[ v >>> 4 ];
            hexChars[ j * 2 + 1 ] = hexArray[ v & 0x0F ];
        }
        return new String( hexChars );
    }

    String SHA1( String toHash )
            //tomado de: http://stackoverflow.com/questions/5980658/how-to-sha1-hash-a-string-in-android
    {
        String hash = null;
        try
        {
            MessageDigest digest = MessageDigest.getInstance( "SHA-1" );
            byte[] bytes = toHash.getBytes("UTF-8");
            digest.update(bytes, 0, bytes.length);
            bytes = digest.digest();

            // This is ~55x faster than looping and String.formating()
            hash = bytesToHex( bytes );
        }
        catch( NoSuchAlgorithmException e )
        {
            e.printStackTrace();
        }
        catch( UnsupportedEncodingException e )
        {
            e.printStackTrace();
        }
        return hash;
    }
}
