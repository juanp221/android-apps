package com.example.salalinux.tallerlenis;

import android.app.Activity;
import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {
    EditText userInput;
    EditText passwordInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userInput=(EditText)findViewById(R.id.userInput);
        passwordInput=(EditText)findViewById(R.id.passwordInput);
    }
    public void ingresar(View v){
        AdminSQLiteOpenHelper admin=new AdminSQLiteOpenHelper(this,"prueba",null,1);

        SQLiteDatabase bd=admin.getWritableDatabase();
        String email=userInput.getText().toString();
        Cursor fila=bd.rawQuery("select * from prueba where email='"+email+"' and password='"+SHA1(passwordInput.getText().toString())+"'",null);
        if(fila.moveToFirst()){
            Intent i=new Intent(this,Bienvenido.class);
            startActivity(i);
            bd.close();
            finish();

        }
        else{
            Toast.makeText(this,"Usuario o Contraseña incorrecta", Toast.LENGTH_SHORT).show();
        }


    }
    public  void registrarse(View v){
        Intent i=new Intent(this,Registrarse.class);
        startActivity(i);
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
