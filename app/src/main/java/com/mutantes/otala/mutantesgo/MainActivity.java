package com.mutantes.otala.mutantesgo;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements Runnable {

    private final int DELAY = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        Toast.makeText(this,"Aguarde o carregamento do aplicativo...",Toast.LENGTH_SHORT).show();
        Handler h = new Handler();
        h.postDelayed(this,DELAY);
    }

    @Override
    public void run() {
        startActivity(new Intent(this, DashboardActivity.class));
        finish();
    }
}
