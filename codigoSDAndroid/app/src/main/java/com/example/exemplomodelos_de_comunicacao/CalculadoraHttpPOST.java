package com.example.exemplomodelos_de_comunicacao;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class CalculadoraHttpPOST extends AsyncTask<Void, Void, String> {

    TextView tv;
    String oper1, oper2;
    PrecisaCalcular pc;
    int op;

    public CalculadoraHttpPOST(TextView tv, PrecisaCalcular pc, String oper1, String oper2, int op) {
        this.tv = tv;
        this.oper1 = oper1;
        this.oper2 = oper2;
        this.pc = pc;
        this.op = op; //1-somar 2-subtrair 3-multiplicar 4-dividir
    }
    @Override
    protected String doInBackground(Void... voids) {
        String result="";
        try {

           URL url = new URL("https://double-nirvana-273602.appspot.com/?hl=pt-BR");
           HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true) ;

            //ENVIO DOS PARAMETROS
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, StandardCharsets.UTF_8));
            writer.write("oper1="+oper1+"&oper2="+oper2+"&operacao="+op);
            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                //RECBIMENTO DOS PARAMETROS
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                result = response.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPreExecute() {
        //Codigo
    }

    @Override
    protected void onPostExecute(String result) {
        if(this.tv!=null) {
            this.tv.setText(result);
        }else {
            this.pc.result_calculoRemoto(result);
        }
    }

}