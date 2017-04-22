package com.example.ejercicio2.enviodemensajes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private EditText phoneNumber, smsBody;
    private Button smsManagerBtn,smsSendToBtn,smsViewBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        smsBody = (EditText) findViewById(R.id.smsBody);
        smsManagerBtn = (Button) findViewById(R.id.smsManager);
        smsSendToBtn = (Button) findViewById(R.id.smsSIntent);
        smsViewBtn = (Button) findViewById(R.id.smsVIntent);

        smsManagerBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                sendSmsByManager();
            }

        });

        smsSendToBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                sendSmsBySIntent();
            }

        });

        smsViewBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                sendSmsByVIntent();
            }

        });

    }

    public void sendSmsByManager(){ // MANDA EL MENSAJE SIN ABRIR LA APLICACION NATIVA, ES EL QUE SE MANDA POR DEBAJO ES EL MAS ADECUADO
        try{
            String sent = "android.telephony.SmsManager.STATUS_ON_ICC_SENT";
            PendingIntent piSent = PendingIntent.getBroadcast(MainActivity.this, 0,new Intent(sent),0);

            //get the default instance of the SmsManager
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage("12345678901",null,"hello!",piSent,null);
            Toast.makeText(getApplicationContext(),"Your sms has successfully sent!",Toast.LENGTH_LONG).show();

        }catch (Exception ex){
            Toast.makeText(getApplicationContext(),"Your sms has failed...",Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    public void sendSmsBySIntent(){
        //add the phone number in the data
        Uri uri = Uri.parse("smsto:" + phoneNumber.getText().toString());

        Intent smsSIntent = new Intent(Intent.ACTION_SENDTO,uri);
        //add the message at the sms body extra field
        smsSIntent.putExtra("sms_body", smsBody.getText().toString());
        try{
            startActivity(smsSIntent);
        }catch (Exception ex){
            Toast.makeText(MainActivity.this,"Your sms has failed...",Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }


    public void sendSmsByVIntent(){

        Intent smsVIntent = new Intent(Intent.ACTION_VIEW);
        // prompsts only sms-sms clients
        smsVIntent.setType("vnd.android-dir/mms-sms");

        //extra fields for number and message respectively
        smsVIntent.putExtra("address", phoneNumber.getText().toString());
        smsVIntent.putExtra("sms_body", smsBody.getText().toString());
        try{
            startActivity(smsVIntent);
        }catch (Exception ex){
            Toast.makeText(MainActivity.this,"Your sms has failed...",Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }
}
