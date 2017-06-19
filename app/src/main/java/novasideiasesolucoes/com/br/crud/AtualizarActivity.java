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

public class AtualizarActivity extends AppCompatActivity {
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizar);


        final Intent it = getIntent();

        final EditText nome = (EditText) findViewById(R.id.txtNomeUP);
        final EditText email = (EditText) findViewById(R.id.txtEmailUP);
        Button btVoltar = (Button) findViewById(R.id.btnVoltarUP);
        Button btAtualizar = (Button) findViewById(R.id.btnAtualizarUP);
        Button btApagar= (Button) findViewById(R.id.btnApagarUP);

        nome.setText(it.getStringExtra("nome"));
        email.setText(it.getStringExtra("email"));

        db = openOrCreateDatabase("crud.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);


        //apagar
        btApagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(db.delete("clientes","_id = ?", new String[]{String.valueOf(it.getIntExtra("_id",0))}) > 0);
                Toast.makeText(getBaseContext(),"Sucesso", Toast.LENGTH_SHORT);
                finish();

            }
        });


        //editar
        btAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ContentValues ctv = new ContentValues();

                ctv.put("nome", nome.getText().toString());
                ctv.put("email", email.getText().toString());

                if(db.update("clientes",ctv, "_id = ?", new String[]{String.valueOf(it.getIntExtra("_id",0))}) > 0);
                Toast.makeText(getBaseContext(),"Sucesso", Toast.LENGTH_SHORT);
                finish();
            }
        });




        //voltar a main
        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // abre activity
                startActivity(new Intent(getBaseContext(), MainActivity.class));
            }
        });



    }
}
