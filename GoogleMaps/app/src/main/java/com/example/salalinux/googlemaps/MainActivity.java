package com.example.salalinux.googlemaps;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button bt1;
    EditText et1;
    EditText et2;
    TextView tv1;
    ObtenerWebService hConexion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt1=(Button)findViewById(R.id.bt1);
        et1=(EditText)findViewById(R.id.et1);
        et2=(EditText)findViewById(R.id.et2);
        tv1=(TextView)findViewById(R.id.tv1);
        bt1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bt1:
                hConexion=new ObtenerWebService();
                hConexion.execute(et1.getText().toString(),et2.getText().toString());
                break;
            default:
                break;
        }
    }
    public class ObtenerWebService extends AsyncTask<String,Integer,String>{
        @Override
        protected String doInBackground(String... params){
            String cadena="maps.googleapis.com/maps/api/geocode/json?latlng=";
            cadena+=params[0];
            cadena+=",";
            cadena+=params[1];
            cadena+="&sensor=false";
            String devuelve="oo";
            URL url=null;
            try{
                url=new URL(cadena);
                HttpURLConnection connection=(HttpsURLConnection) url.openConnection();
                connection.setRequestProperty("user-Agent","Mozilla/5.0.+"+"(Linux: Android 1.5; es-ES) Ejemplo HTTP));");
                int respuesta=connection.getResponseCode();
                StringBuilder res1=new StringBuilder();
                if(respuesta==HttpsURLConnection.HTTP_OK) {
                    InputStream in = new BufferedInputStream(connection.getInputStream());
                    BufferedReader leer = new BufferedReader(new InputStreamReader(in));

                    String linea=leer.readLine();

                    while (linea!= null) {
                        res1.append(linea);
                        linea=leer.readLine();
                    }
                    JSONObject resJson = new JSONObject(res1.toString());
                    JSONArray ansJason = resJson.getJSONArray("results");
                    String direccion = "Sin datos para esa longitud y latitud";
                    if (ansJason.length() > 0) {
                        direccion = ansJason.getJSONObject(0).getString("formatted_address");
                    }
                    devuelve = "Direccion" + direccion;

                }
            }catch(MalformedURLException e){
                e.printStackTrace();
                devuelve="oo";
            }catch(JSONException e){
                e.printStackTrace();
                devuelve="ool";
            }catch(IOException e){
                e.printStackTrace();
                devuelve="ooll";
            }
            return devuelve;

        }
        @Override
        protected void onCancelled(String aVoid){
            super.onCancelled();
        }
        @Override
        protected  void onPostExecute(String aVoid){
            tv1.setText(aVoid);
        }
        @Override
        protected  void onPreExecute(){
            super.onCancelled();
        }
        @Override
        protected void onProgressUpdate(Integer... values){
            super.onProgressUpdate(values);
        }
    }
}
