package com.example.exemplomodelos_de_comunicacao;

import android.widget.TextView;

public class PrecisaCalcular {
    TextView tv;
    public PrecisaCalcular(TextView tv) {
        this.tv=tv;
    }
    public String calculoLocalSoma() {
        Calculadora calc = new Calculadora();
        String result= calc.soma(20.0, 20.0)+"";
        return result;
    }

    public String calculoLocalSub() {
        Calculadora calc = new Calculadora();
        String result= calc.subtracao(20.0, 20.0)+"";
        return result;
    }

    public void calculoRemotoSocket(String op) {
        CalculadoraSocket shs = null;

        switch (op){
            case "soma":
                shs = new CalculadoraSocket(tv, "15", "15", 1);
                break;
            case "sub":
                shs = new CalculadoraSocket(tv, "15", "15", 2);
                break;
            case "mult":
                shs = new CalculadoraSocket(tv, "15", "15", 4);
                break;
            case "div":
                shs = new CalculadoraSocket(tv, "15", "15", 3);
                break;
        }

        if(shs != null) shs.execute();
        else result_calculoRemoto("Operação inválida");

    }
    public void calculoRemotoHTTP(String op) {
        CalculadoraHttpPOST shs = null;
        switch (op){
            case "soma":
                shs = new CalculadoraHttpPOST(this.tv, this, "15", "15", 1);
                break;
            case "sub":
                shs = new CalculadoraHttpPOST(this.tv, this, "15", "15", 2);
                break;
            case "mult":
                shs = new CalculadoraHttpPOST(this.tv, this, "15", "15", 3);
                break;
            case "div":
                shs = new CalculadoraHttpPOST(this.tv, this, "15", "15", 4);
                break;
        }

        if(shs != null) shs.execute();
        else result_calculoRemoto("Operação inválida");
    }

    public void result_calculoRemoto(String result){
        tv.setText(result);
    }

}
