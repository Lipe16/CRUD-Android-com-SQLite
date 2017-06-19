package novasideiasesolucoes.com.br.crud;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {
    //cria e segura banco de dados
    private SQLiteDatabase db = null;
    private  SimpleCursorAdapter adt = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //cria e segura banco de dados
        db = openOrCreateDatabase("crud.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);

        //comando string sql
        String sql = "CREATE TABLE IF NOT EXISTS clientes (_id INTEGER PRIMARY KEY autoincrement, " +
                "nome VARCHAR(50), email VARCHAR(80))";
        //executa sql string
        db.execSQL(sql);



        //chama activity cadastrar
        Button btnCadastrar = (Button) findViewById(R.id.btnTelaCadastrar);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // abre activity
                startActivity(new Intent(getBaseContext(), CadastroActivity.class));

            }
        });




        // parte do CRUD que trata de buscas no BD
        final EditText txtBusca = (EditText) findViewById(R.id.txtBusca);

        txtBusca.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                String[] busca = {"%"+txtBusca.getText().toString()+"%"};

                // listar dados
                Cursor cursor = db.query("clientes", new String[]{"_id", "nome", "email"}, "nome LIKE ?", busca, null, null, null, null);
                adt.changeCursor(cursor);

                return false;
            }
        });

    }


    @Override
    public void onResume(){
        super.onResume();

        // listar dados e adptalos aparecer na tela
        Cursor cursor = db.query("clientes", new String[]{"_id", "nome", "email"}, null, null, null, null, null, null);

        //configurando os campos que vão aparecer
        String[] campos = {"_id", "nome", "email"};
        int[] ids = {R.id.idView, R.id.nomeView, R.id.emailView};// setando campos no model (listView.xml)

        adt = new SimpleCursorAdapter(getBaseContext(), R.layout.mod_listview, cursor, campos, ids, 0);

        ListView listView = (ListView) findViewById(R.id.listaNaTela);
        listView.setAdapter(adt);




        // parte do CRUD que trata de edições no BD o código continua na activity indicada
        ListView listaNaTela = (ListView) findViewById(R.id.listaNaTela);

        listaNaTela.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int posicao, long id) {

                Cursor cursorRetorno = (Cursor) adt.getItem(posicao);

                Intent intencao = new Intent(getBaseContext(), AtualizarActivity.class);

                intencao.putExtra("_id",cursorRetorno.getInt(cursorRetorno.getColumnIndex("_id")));
                intencao.putExtra("nome",cursorRetorno.getString(cursorRetorno.getColumnIndex("nome")));
                intencao.putExtra("email",cursorRetorno.getString(cursorRetorno.getColumnIndex("email")));

                startActivity(intencao);
            }
        });

    }

}
