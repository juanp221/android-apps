package com.example.salalinux.servicios;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.buttonOn).setOnClickListener(mClickListener);
        findViewById(R.id.buttonOff).setOnClickListener(mClickListener);
    }

    View.OnClickListener mClickListener=new View.OnClickListener(){
        @Override
        public void onClick(View v){
         switch (v.getId()){
             case R.id.buttonOn:
                 //Start service
                 startService( new Intent(MainActivity.this,MyService.class));
                 break;
             case R.id.buttonOff:
                 //Stop service
                 stopService(new Intent(MainActivity.this,MyService.class));
                 break;
         }
        }
    };
}
