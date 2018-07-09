package br.com.jonathan.flexcalc;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class quartaActivity extends AppCompatActivity {

    private EditText textArea;
    private Button botaoAdc;
    private ListView listaManutencao;
    private SQLiteDatabase bancoDados;

    //Criando um adptador para mostrar no ListView
    private ArrayAdapter<String> itensAdaptador;
    private ArrayList<String> descricoes;
    private  ArrayList<Integer> ids;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quarta);

        try {
            //Recuperando os componentes
            textArea = (EditText) findViewById(R.id.textoId);
            botaoAdc = (Button) findViewById(R.id.botaoAdcId);

            //Lista de descriçoes
            listaManutencao = (ListView) findViewById(R.id.listId);

            //Criando Banco de dados
            bancoDados = openOrCreateDatabase("appflex", MODE_PRIVATE, null);

            //Criando a tabela
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS list(id INTEGER PRIMARY KEY AUTOINCREMENT, desc VARCHAR)");


            //Evento botao adicionar
            botaoAdc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String textoDigitado = textArea.getText().toString();
                    salvarDescr(textoDigitado);
                }
            });

            //Evento do clique para excluir
            listaManutencao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    deleteReg(ids.get(position));
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    // Metodo para salvar no banco
    private void salvarDescr(String texto) {

        try {

            if (texto.equals("")) {
                Toast.makeText(quartaActivity.this, "Digite uma descricao", Toast.LENGTH_SHORT).show();
            } else {
                bancoDados.execSQL(" INSERT INTO list (desc) VALUES (' " + texto + " ')");
                Toast.makeText(quartaActivity.this, "Registro Salvo", Toast.LENGTH_SHORT).show();
                selectDescr();
                textArea.setText("");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Metodo para selecionar dados
    private void selectDescr() {

        try {

            //Recuperar os dados
            @SuppressLint("Recycle") Cursor cursor = bancoDados.rawQuery("SELECT * FROM list ORDER BY id DESC", null);

            //Recuperar Id's
            int indiceColunaId = cursor.getColumnIndex("ID");
            int indiceColunaDescr = cursor.getColumnIndex("Descricao");



            //Criação de adaptador para visualização
            descricoes = new ArrayList<String>();
            ids = new ArrayList<Integer>();
            itensAdaptador = new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_list_item_2,
                    android.R.id.text2,
                    descricoes){

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {

                    View view = super.getView(position, convertView, parent);
                    TextView text = (TextView) view.findViewById(android.R.id.text2);
                    text.setTextColor(Color.BLACK);
                    return view;
                }
            };

            listaManutencao.setAdapter( itensAdaptador );

            //Listar as tarefas
            cursor.moveToFirst();

            while (cursor != null) {
                Log.i("Resultado - ", "Id : " + cursor.getString( indiceColunaId ) + " Descricao: " + cursor.getString( indiceColunaDescr ) );
                descricoes.add(cursor.getString(indiceColunaDescr));
                ids.add(Integer.parseInt(cursor.getString(indiceColunaId)) );

                cursor.moveToNext();
            }


        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    //Metodo para excluir registros
    private void deleteReg (Integer id){

        try{

            bancoDados.execSQL("DELETE FROM list WHERE id"+id);
            Toast.makeText(quartaActivity.this, "Registro Excluido", Toast.LENGTH_SHORT).show();
            selectDescr();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
