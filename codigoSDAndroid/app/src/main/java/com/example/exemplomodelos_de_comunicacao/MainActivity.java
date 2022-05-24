package com.example.exemplomodelos_de_comunicacao;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tvResSocket;
    TextView tvResHttp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bt = findViewById(R.id.button);
        Button bt2 = findViewById(R.id.button2);
        Button bt3 = findViewById(R.id.button3);
        Button bt4 = findViewById(R.id.button4);
        tvResHttp = findViewById(R.id.tvResHttp);

        Button bt5 = findViewById(R.id.button5);
        Button bt6 = findViewById(R.id.button6);
        Button bt7 = findViewById(R.id.button7);
        Button bt8 = findViewById(R.id.button8);
        tvResSocket = findViewById(R.id.tvResSocket);

        // botões calculadora HTTP
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               PrecisaCalcular shc = new PrecisaCalcular(tvResHttp);
               //shc.calculoRemoto("soma");
               shc.calculoRemotoHTTP("soma");
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrecisaCalcular shc = new PrecisaCalcular(tvResHttp);
                shc.calculoRemotoHTTP("sub");
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrecisaCalcular shc = new PrecisaCalcular(tvResHttp);
                shc.calculoRemotoHTTP("mult");
            }
        });
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrecisaCalcular shc = new PrecisaCalcular(tvResHttp);
                shc.calculoRemotoHTTP("div");
            }
        });

        // botões calculadora socket
        bt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrecisaCalcular shc = new PrecisaCalcular(tvResSocket);
                shc.calculoRemotoSocket("soma");
            }
        });
        bt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrecisaCalcular shc = new PrecisaCalcular(tvResSocket);
                shc.calculoRemotoSocket("sub");
            }
        });
        bt7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrecisaCalcular shc = new PrecisaCalcular(tvResSocket);
                shc.calculoRemotoSocket("mult");
            }
        });
        bt8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrecisaCalcular shc = new PrecisaCalcular(tvResSocket);
                shc.calculoRemotoSocket("div");
            }
        });
    }
}
