package com.example.salalinux.servicios;

import android.app.Service;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.IBinder;
import android.widget.Toast;

import java.util.Date;

public class MyService extends Service {
    MyTask myTask;
    public MyService() {
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
                    Thread.sleep(2000);
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

            Toast.makeText(getApplicationContext(),"Hora actual:"+values[0],Toast.LENGTH_SHORT).show();
        }
        @Override
        protected void onCancelled(){
            super.onCancelled();
            cent=false;
        }
    }

}

