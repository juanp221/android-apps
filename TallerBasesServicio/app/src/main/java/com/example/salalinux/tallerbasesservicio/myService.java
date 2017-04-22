package com.example.salalinux.tallerbasesservicio;

/**
 * Created by salalinux on 24/03/2017.
 */

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.IBinder;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Date;

public class myService extends Service {
    MyTask myTask;
    public myService() {
    }
    @Override
    public void onCreate(){
        super.onCreate();
        Toast.makeText(this,"Servicio creado!",Toast.LENGTH_SHORT).show();
        myTask=new MyTask();
    }

    @Override
    public  int onStartCommand(Intent intent,int flags,int startId){
        myTask.execute();
        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Toast.makeText(this,"Servicio destruido!",Toast.LENGTH_SHORT).show();
        myTask.cancel(true);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    private class MyTask extends AsyncTask<String,String,String> {
        private DateFormat dateFormat;
        private String date;
        private boolean cent;

        @Override
        protected String doInBackground(String... params) {
            while(cent){
                date=dateFormat.format(new Date());
                try{
                    publishProgress(date);
                    //Stop 5s
                    Thread.sleep(20000);

                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected  void onPreExecute(){
            super.onPreExecute();
            dateFormat=new SimpleDateFormat("HH:mm:ss");
            cent=true;
        }
        @Override
        protected void onProgressUpdate(String...values){
            generarPublicidad();
            //Toast.makeText(getApplicationContext(),"Hora actual:"+values[0],Toast.LENGTH_SHORT).show();
        }
        @Override
        protected void onCancelled(){
            super.onCancelled();
            cent=false;
        }
        private void generarPublicidad(){

            AdminSQLiteOpenHelper admin=new AdminSQLiteOpenHelper(getApplicationContext(),"pruebas",null,1);

            SQLiteDatabase bd=admin.getWritableDatabase();

            Cursor fila=bd.rawQuery("select * from pruebas",null);
            while(fila.moveToNext()){
                if(fila.getString(3).equals("M")){
                    if(Integer.parseInt(fila.getString(2))>30){
                        Toast.makeText(getApplicationContext(),"BMW a $70.000.000",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"ingenieria de sistemas en la Javeriana entra",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    if(Integer.parseInt(fila.getString(2))>30){
                        Toast.makeText(getApplicationContext(),"Viaje a las islas del caribe a USD 700",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Capacitacion en ofimatica",Toast.LENGTH_SHORT).show();
                    }
                }
            }
            //adapter.insert("lol",0);
            bd.close();
        }
    }

}
