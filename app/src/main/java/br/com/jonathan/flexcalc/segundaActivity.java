package br.com.jonathan.flexcalc;

import android.*;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class segundaActivity extends Activity {

    private TextView texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda);

        texto = (TextView) findViewById(R.id.resultadoId);

        //Recuperando as informações
        Bundle extra = getIntent().getExtras();

        //Testando os dados passados
        if(extra != null){
            String resultadoPassado = (String) extra.getString("resultado");
            texto.setText(resultadoPassado);
        }
    }

}
