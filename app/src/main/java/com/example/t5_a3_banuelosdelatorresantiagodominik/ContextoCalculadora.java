package com.example.t5_a3_banuelosdelatorresantiagodominik;

import android.util.Log;

public class ContextoCalculadora {
    double n1 = 0, n2 = 0, mem = 0;
    char op = ' ';

    String operacion = "";
    boolean operador = false;
    boolean operable = false;

    boolean operacionCompleta = false;
    void agregarNumero(double n){
        n2 = n1;
        n1 = n;

    }
    void nuevoOperador(char op){
        if(operable && !operador) {//resolver y agregar resultado
            agregarNumero(operarOrdinario());
        }
        this.op = op;
        operador = true;
        operable = true;
        operacionCompleta = false;
        operacion = ""+n1+op;
    }
    //operaciones aritmeticas comunes
    double operarOrdinario (){
        double r = n1;
        switch (op){
          case '+': r= n1+n2; break;
          case '-': r= n2-n1; break;
          case 'x': r= n1*n2; break;
          case '/': r= n2/n1; break;
        }
        operador = operacionCompleta = true;
        operable = false;
        operacion = ""+n2+op+n1+"=";
        return r;
    }
    //un solo operador requerido (racional, raiz, potencia)
    double operarExtraordinario(double n){
        double r = n;
        switch (op){
            //case '_': r = 1.0/r; break;
            case '^': r = r*r; break;
            case '√': r = Math.sqrt(r); break;
        }
        operacion += op+"("+n+")=";
        operable=false;
        operador = operacionCompleta = true;
        return r;
    }
    //caso especial
    double modulo(){

        double r = 0.0;
        switch (op){
            case '-':
            case '+': {
                r = n2 * n1 / 100.0;

            } break;
            case '/':
            case 'x':
                //Log.i("modpor", ""+n1);
                r = (n1/100.0);
                break;
        }
        operador = true;
        operacion+= ""+r;
        return r;
    }
    double invertir(){
        return -n1;
    }
    double racionalizar(double n){
        operacion += "1/("+n+")";
        operacionCompleta = true;
        return 1/n;
    }
    public void CE(){
        n1 = 0;

    }
    public void C(){
        n1 = n2 = 0;
        operador = operable = operacionCompleta = false;
        operacion = "";
        op = ' ';
    }
    public void MS(double n) {
        mem = n;
    }
    public void MOperar(char mop, double n){
        switch (mop){
            //case 'S': MS(); break;
            case '+': mem+=n; break;
            case '-': mem -= n; break;
            case 'C': mem = 0; break;
            default: break;
        }
    }
}
