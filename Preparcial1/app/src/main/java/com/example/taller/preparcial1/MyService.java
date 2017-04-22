package com.example.taller.preparcial1;


import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;
import java.util.Date;

public class MyService extends Service {
    MyTask myTask;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate(){
        super.onCreate();
        Toast.makeText(this, "¡Servicio creado! :D ", Toast.LENGTH_SHORT).show();
        myTask = new MyTask();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        myTask.execute();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Toast.makeText(this, "¡Servicio destruido! D:", Toast.LENGTH_SHORT).show();
        myTask.cancel(true);
    }


    private class MyTask extends AsyncTask<String, String, String > {

        private DateFormat dateFormat;
        private String date;
        private boolean cent;

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected String doInBackground(String... params) {

            while(cent){
                date = dateFormat.getDateTimeInstance().format(new Date());

                SharedPreferences prefe = getSharedPreferences("datos", Context.MODE_PRIVATE);
                String nombre = prefe.getString("nombre","");
                String telefono = prefe.getString("telefono","");
                Log.i("Telefono: ", telefono);

                String cadena = "El mensaje a "+ nombre +" fue enviado exitosamente a las "+date+".";
                try{

                    String sent = "android.telephony.SmsManager.STATUS_ON_ICC_SENT";
                    PendingIntent piSent = PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent(sent), 0);
                    //Get the default instance of the Sms Manager
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(telefono, null, "Mensaje para: "+nombre+"\nHora de envío: "+date, piSent, null);
                    publishProgress(cadena);
                    //Stop 5s
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    Toast.makeText(getApplicationContext(), "Your sms has failed!",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

            return null;
        }

        @TargetApi(Build.VERSION_CODES.N)
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            dateFormat = new SimpleDateFormat("HH:mm:ss");
            cent = true;
        }

        @Override
        protected void onProgressUpdate(String... values){
            Toast.makeText(getApplicationContext(), values[0], Toast.LENGTH_LONG).show();
        }

        protected void onCancelled(){
            super.onCancelled();
            cent = false;
        }

    }

}
