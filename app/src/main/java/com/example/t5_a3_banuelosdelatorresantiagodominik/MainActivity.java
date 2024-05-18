package com.example.t5_a3_banuelosdelatorresantiagodominik;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button[] numeros = new Button[10];
    Button btnSuma, btnResta, btnMult, btnDiv, btnRacional, btnSqr, btnRoot, btnMod, btnPunto, btnRes, btnC, btnCE, btnBorrar, btnInvertir;
    TextView cajaNum, cajaOp;
    ContextoCalculadora cc = new ContextoCalculadora();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        int i = R.id.btn0, k = 0;
        //agarrar los 10 numeros de la ui
        for (; i <= R.id.btn9; i++, k++) {
            numeros[k] = findViewById(i);
            numeros[k].setText(""+k);
        }
        btnInvertir = findViewById(R.id.btnInvertir);
        btnPunto = findViewById(R.id.btnPunto);
        btnRes = findViewById(R.id.btnRes);

        btnDiv = findViewById(R.id.btnDiv);
        btnMult = findViewById(R.id.btnMult);
        btnResta = findViewById(R.id.btnResta);
        btnSuma = findViewById(R.id.btnSuma);

        btnMod = findViewById(R.id.btnMod);
        btnSqr = findViewById(R.id.btnSqr);
        btnRoot = findViewById(R.id.btnRoot);
        btnRacional = findViewById(R.id.btnRacional);

        cajaNum = findViewById(R.id.caja_num);
        cajaNum.setText("0");
        cajaNum.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
        cajaOp = findViewById(R.id.caja_op);
        cajaOp.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);



    }
    double obtenerNumero(){
        try {
            double nm = Double.parseDouble(cajaNum.getText().toString());
            return nm;
        }catch (NumberFormatException e){
            Toast.makeText(this, "Formato del numero incorrecto", Toast.LENGTH_LONG).show();
            return 0;
        }
    }
    public void crecerNumero(String n){
        Log.i("operador", ""+cc.operador);
        if(cc.operador || cajaNum.getText().toString().equals(("0"))) cajaNum.setText("");
        //if(cc.operador || obtenerNumero() == 0 && !n.equals(".") && !cajaNum.getText().toString().equals("0."))
        String p = cajaNum.getText().toString();

        cajaNum.setText(p+n);
        cc.operador = false;
    }
    public void mostrarNumero(View v){
        //if (v.getId() == R.id.btnResta);
        int idx = v.getId() - R.id.btn0;

        crecerNumero(""+idx);

    }

    public void mostrarPunto(View v){
        String c = cajaNum.getText().toString();
        for(int i = 0; i < c.length(); i++){
            if(c.charAt(i) == '.') return;
        }
        if(cajaNum.getText().toString().isEmpty()) crecerNumero("0");
        crecerNumero(".");
    }
    public void prepararOp(){
        if(cc.operacionCompleta) cc.operacion = "";
        cc.operacionCompleta = false;
    }
    public void agregarOperador(View v){
        String op = ((Button)v).getText().toString();
        cc.agregarNumero(obtenerNumero());

        prepararOp();
        cc.nuevoOperador(op.charAt(0));
        cajaNum.setText(""+cc.n1);

        cajaOp.setText(cc.operacion);
    }
    public void realizarOperacion(View v){
        cc.agregarNumero(obtenerNumero());

        prepararOp();
        double res = cc.operarOrdinario();

        cajaOp.setText(cc.operacion);
        cajaNum.setText(""+res);
    }
    public void invertir(View v){
        prepararOp();
        double r = cc.invertir();
        cajaNum.setText(""+r);
    }
    public void operacionEspecial(View v){
        char orop = cc.op;
        String op = ((Button)v).getText().toString();
        cc.op = op.charAt(0);

        prepararOp();
        double res = cc.operarExtraordinario(obtenerNumero());
        cc.n1 = res;
        cc.op = orop;
        cajaOp.setText(cc.operacion);
        cajaNum.setText(""+res);

    }
    public void modular(View v){
        double n1 = cc.n1;
        double n2 = cc.n2;
        prepararOp();
        cc.agregarNumero(obtenerNumero());
        double res = cc.modulo();
        Log.i("MODULO", ""+res);
        cajaNum.setText(""+res);
        prepararOp();
        cajaOp.setText(cc.operacion);
        cc.n2 = n2;
        cc.n1 = n1;
    }
    public void racionalizar(View v){
        prepararOp();
        double r = cc.racionalizar(obtenerNumero());
        cajaOp.setText(cc.operacion);
        cajaNum.setText(""+r);
    }
    public void MS(View v){
        cc.MS(obtenerNumero());
    }
    public void MR(View v){
        cc.MOperar('R', obtenerNumero());
    }
    public void Mp(View v){
        cc.MOperar('+', obtenerNumero());
    }
    public void Mm(View v){
        cc.MOperar('-', obtenerNumero());
    }
    public void MC(View v){
        cc.MOperar('C', obtenerNumero());
    }
    public void borrar(View v){
        int lim = cajaNum.getText().toString().length()-1;
        if(lim <=0) cajaNum.setText("0");
        else cajaNum.setText(cajaNum.getText().subSequence(0, lim).toString());
    }
    public void CE(View v){
        cc.CE();
        cajaNum.setText(""+cc.n1);
    }
    public void C(View v){
        cc.C();
        cajaNum.setText(""+0);
        prepararOp();
        cajaOp.setText(cc.operacion);
    }
    public void memoria(View v){
        String op = ((Button)v).getText().toString();
        char memop = op.charAt(1);

        if(memop == 'R'){
            cajaNum.setText(""+cc.mem);
        }else cc.MOperar(memop, obtenerNumero());
    }
}