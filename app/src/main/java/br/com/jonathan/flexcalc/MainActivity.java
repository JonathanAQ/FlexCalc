package br.com.jonathan.flexcalc;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    private EditText precoAlcool;
    private EditText precoGasolina;
    private Button botaoVerificar;
    private Button botaoListar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Localixar elementos na activity
        precoAlcool = findViewById(R.id.precoAlcoolId);
        precoGasolina = findViewById(R.id.precoGasolinaId);
        botaoVerificar = findViewById(R.id.botaoVerificarId);
        botaoListar = findViewById(R.id.botaoListaId);

        //Evento Botao Listar
        botaoListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, quartaActivity.class);
                startActivity(intent);
            }
        });


        //Evento do botão Calcular
        botaoVerificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                PendingIntent p = PendingIntent.getActivity(getApplicationContext(), 0, new Intent(getApplicationContext(), terceiraActivity.class), 0);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
                builder.setTicker("FlexCalc");
                builder.setContentTitle("Dica do dia:");
                builder.setContentText("Dicas para economia de combustivél");
                builder.setSmallIcon(R.drawable.ic_flex_calc_logo);
                builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_flex_calc_logo));
                builder.setContentIntent(p);

                Notification n = builder.build();
                n.vibrate = new long[]{150, 300, 150, 600};
                nm.notify(R.drawable.ic_flex_calc_logo, n);

                try {
                    Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    Ringtone toque = RingtoneManager.getRingtone(getApplicationContext(), som);
                    toque.play();
                } catch (Exception e) {

                }


                //Toast mostrando pequena mensagem de click no botão
                Toast.makeText(getApplicationContext(), "Calculando...", Toast.LENGTH_SHORT).show();
                //Recuperando os dados
                String textoPrecoAlcool = precoAlcool.getText().toString();
                String textoPrecoGasolina = precoGasolina.getText().toString();

                //Convertendo os textos em numeros para realizar os calculo
                Double valorAlcool = Double.parseDouble(textoPrecoAlcool);
                Double valorGasolina = Double.parseDouble(textoPrecoGasolina);

                //Realizando o calculo algoritimo (preco do alcool / preco da gasolina se > que 0.7 melhor gasolina se não melhor alcool)
                double resultado = valorAlcool / valorGasolina;

                if (resultado >= 0.7) {
                    //Gasolina - Passando o resultado para outra activity
                    Intent intent = new Intent(MainActivity.this, segundaActivity.class);
                    intent.putExtra("resultado", "É MELHOR USAR GASOLINA!");
                    startActivity(intent);

                } else {
                    //Alcool - Passando o resultado para outra activity
                    Intent intent = new Intent(MainActivity.this, segundaActivity.class);
                    intent.putExtra("resultado", "É MELHOR USAR ALCOOL!");
                    startActivity(intent);
                }
            }
        });


    }
}
