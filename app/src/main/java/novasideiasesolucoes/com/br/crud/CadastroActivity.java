package novasideiasesolucoes.com.br.crud;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CadastroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        Button btnCadastro = (Button) findViewById(R.id.btnCadastrar);
        Button btnVoltar = (Button) findViewById(R.id.btnVoltar);


        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txtNome = (EditText) findViewById(R.id.txtNome);
                EditText txtEmail = (EditText) findViewById(R.id.txtEmail);


                /* //Maneira mais manual de inserir dados
                String sql = "INSERT INTO clientes (nome, email) VALUES('"+txtNome.getText().toString()+"', '"+txtEmail.getText().toString()+"')";

                //cria ou abre banco de dados
                SQLiteDatabase db = openOrCreateDatabase("crud.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);

                //executa sql string
                db.execSQL(sql);
                */

                //Maneira mais fácil de inserir dados
                //cria ou abre banco de dados
                SQLiteDatabase db = openOrCreateDatabase("crud.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);


                ContentValues ctv = new ContentValues();
                ctv.put("nome", txtNome.getText().toString());
                ctv.put("email", txtEmail.getText().toString());

                db.insert("clientes", "id", ctv);
                Toast.makeText(getBaseContext(),"Sucesso", Toast.LENGTH_SHORT);
                finish();

            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // abre activity
                startActivity(new Intent(getBaseContext(), MainActivity.class));

            }
        });



    }
}
