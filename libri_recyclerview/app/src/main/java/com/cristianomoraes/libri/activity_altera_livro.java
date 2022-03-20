package com.cristianomoraes.libri;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cristianomoraes.libri.database.SQLHelper;
import com.cristianomoraes.libri.helpers.Login;
import com.cristianomoraes.libri.model.Livro;

public class activity_altera_livro extends AppCompatActivity {

    private int cod_livro;
    private EditText txtTitulo;
    private EditText txtDescricao;
    private EditText txtFoto;
    private Button btnEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_altera_livro);

        cod_livro = getIntent().getExtras().getInt("cod_livro");

        txtTitulo = findViewById(R.id.txtTitulo);
        txtDescricao = findViewById(R.id.txtLivroDescricao);
        txtFoto = findViewById(R.id.txtFoto);
        btnEditar = findViewById(R.id.btnEditarLivro);

        if(cod_livro > 0){

            Log.d("COD_LIVRO", String.valueOf(cod_livro));

            try{

                Livro livro = SQLHelper.getInstance(this).listBookID(cod_livro);
                //Log.d("DADO-", livro.getTitulo());
                //Log.d("DADO-", livro.getDescricao());

                txtTitulo.setText(livro.getTitulo());
                txtDescricao.setText(livro.getDescricao());
                txtFoto.setText("TESTE DE FOTO");

            }catch(Exception e){

                Log.d("ERRO", e.getMessage());

            }

            btnEditar.setOnClickListener(view -> {

                String sTitulo = txtTitulo.getText().toString();
                String sDescricao = txtDescricao.getText().toString();

                if(SQLHelper.getInstance(this).updateBooks(cod_livro, sTitulo, sDescricao)){

                    Toast.makeText(activity_altera_livro.this, "DADOS ALTERADOS COM SUCESSO!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(activity_altera_livro.this, activity_feed.class);
                    //intent.putExtra("cod_usuario", Login.getCod_usuario());
                    startActivity(intent);

                }else{

                    Toast.makeText(activity_altera_livro.this, "HOUVE UM ERRO AO TENTAR ALTERAR OS DADOS!", Toast.LENGTH_SHORT).show();

                }

            });

        }

    }

    /** INFLATE DO MENU **/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.menu_cadastrar_livro:
                startActivity(new Intent(this, activity_cadastro_livro.class));
                break;

            case R.id.menu_feed_livros:
                startActivity(new Intent(this, activity_feed.class));
                break;

            case R.id.menu_sair:
                Login.setCod_usuario(0);
                startActivity(new Intent(this, MainActivity.class));
                break;

            default:
                return super.onOptionsItemSelected(item);

        }

        return super.onOptionsItemSelected(item);
    }
}