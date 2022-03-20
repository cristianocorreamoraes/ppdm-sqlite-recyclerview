package com.cristianomoraes.libri;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cristianomoraes.libri.database.SQLHelper;
import com.cristianomoraes.libri.helpers.DateFormat;
import com.cristianomoraes.libri.helpers.Login;

public class activity_cadastro_livro extends AppCompatActivity {

    private EditText txtTitulo;
    private EditText txtDescricao;
    private EditText txtFoto;
    private Button btnCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_livro);

        txtTitulo = findViewById(R.id.txtTitulo);
        txtDescricao = findViewById(R.id.txtLivroDescricao);
        txtFoto = findViewById(R.id.txtFoto);
        btnCadastrar = findViewById(R.id.btnCadastrarLivro);

        btnCadastrar.setOnClickListener(view->{

            if(!validate()){
                Toast.makeText(activity_cadastro_livro.this, R.string.fields_message, Toast.LENGTH_LONG).show();
                return;
            }

            AlertDialog dialog = new AlertDialog.Builder(activity_cadastro_livro.this)
                    .setTitle("CADASTRO DE LIVROS")
                    .setMessage("VOCÊ ESTÁ CADASTRO UM LIVRO NO APP LIBRI")
                    .setPositiveButton(R.string.save, (dialog1, witch)->{
                        String sTitulo = this.txtTitulo.getText().toString();
                        String sDescricao = this.txtDescricao.getText().toString();
                        String sFoto = "TESTE DE FOTO";
                        DateFormat sdata = new DateFormat();

                        int cod_livro = SQLHelper.getInstance(activity_cadastro_livro.this)
                                .addBook(1, sTitulo, sDescricao, sFoto, sdata.getDateFormat());

                        if (cod_livro > 0)
                            Toast.makeText(activity_cadastro_livro.this, R.string.saved, Toast.LENGTH_LONG).show();

                        Intent intent  = new Intent(activity_cadastro_livro.this, activity_feed.class);
                        //intent.putExtra("cod_usuario", cod_livro);
                        startActivity(intent);

                    })
                    .setNegativeButton(R.string.cancel, (dialog1, witch)->{

                    }).create();

            dialog.show();

        });

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

    private boolean validate(){

        return(
                !txtTitulo.getText().toString().isEmpty() &&
                        !txtDescricao.getText().toString().isEmpty() &&
                        !txtFoto.getText().toString().isEmpty()
        );
    }
}