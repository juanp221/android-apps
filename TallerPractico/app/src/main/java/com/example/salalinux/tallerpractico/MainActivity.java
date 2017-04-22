package com.example.salalinux.tallerpractico;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        int id = item.getItemId();
        Intent perf = new Intent(this, ProfileActivity.class);
        if (id==R.id.m_perf) {
            startActivity(perf);
        }
        if (id==R.id.m_exit) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
