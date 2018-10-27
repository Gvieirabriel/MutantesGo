package com.mutantes.otala.mutantesgo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mutantes.otala.mutantesgo.bd.SimpleBDWrapper;

public class DashboardActivity extends AppCompatActivity {

    Button button1;
    Button button2;
    Button button3;
    Button button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_dashboard);

        button1 = (Button) findViewById(R.id.btn1);
        button2 = (Button) findViewById(R.id.btn2);
        button3 = (Button) findViewById(R.id.btn3);
        button4 = (Button) findViewById(R.id.btn4);

        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                nextActivity(RegisterActivity.class);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                nextActivity(MutantListActivity.class);
            }
        });


        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                nextActivity(SearchActivity.class);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sair();
            }
        });
    }

    public void sair(){
        this.finishAffinity();
    }

    public void nextActivity(Class c) {
        Intent register = new Intent(this, c);
        startActivity(register);
    }
}
