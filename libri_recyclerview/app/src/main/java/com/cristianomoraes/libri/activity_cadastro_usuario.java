package com.cristianomoraes.libri;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import com.cristianomoraes.libri.helpers.DateFormat;
import com.cristianomoraes.libri.helpers.Login;

public class activity_cadastro_usuario extends AppCompatActivity {

    private EditText txtNome;
    private EditText txtSobrenome;
    private EditText txtEmail;
    private EditText txtLogin;
    private EditText txtSenha;
    private Button btnCadastrarUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        this.txtNome = findViewById(R.id.txtNome);
        this.txtSobrenome = findViewById(R.id.txtSobrenome);
        this.txtEmail = findViewById(R.id.txtEmail);
        this.txtLogin = findViewById(R.id.txtLogin);
        this.txtSenha = findViewById(R.id.txtSenha);
        this.btnCadastrarUsuario = findViewById(R.id.btnCadastrarUsuario);

        this.btnCadastrarUsuario.setOnClickListener(view->{

            if(!validate()){
                Toast.makeText(activity_cadastro_usuario.this, R.string.fields_message, Toast.LENGTH_LONG).show();
                return;
            }

            AlertDialog dialog = new AlertDialog.Builder(activity_cadastro_usuario.this)
                    .setTitle(getString(R.string.create_user_message_title))
                    .setMessage(R.string.create_user_message)
                    .setPositiveButton(R.string.save, (dialog1, which)->{

                        String snome = this.txtNome.getText().toString();
                        String ssobreNome = this.txtSobrenome.getText().toString();
                        String semail = this.txtEmail.getText().toString();
                        String slogin = this.txtLogin.getText().toString();
                        String ssenha = this.txtSenha.getText().toString();
                        DateFormat sdata = new DateFormat();

                        DateFormat df = new DateFormat();
                        Log.d("TESTE", df.getDateFormat());

                        int cod_usuario = SQLHelper.getInstance(activity_cadastro_usuario.this)
                                .addUser(snome, ssobreNome, semail, slogin, ssenha, sdata.getDateFormat());

                        if (cod_usuario > 0)
                            Toast.makeText(activity_cadastro_usuario.this, R.string.saved, Toast.LENGTH_LONG).show();

                            Login.setCod_usuario(cod_usuario);
                            Intent intent  = new Intent(activity_cadastro_usuario.this, activity_feed.class);
                            //intent.putExtra("cod_usuario", cod_usuario);
                            startActivity(intent);

                    })
                    .setNegativeButton(R.string.cancel, (dialog1, which)->{

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
                !txtNome.getText().toString().isEmpty() &&
                !txtSobrenome.getText().toString().isEmpty() &&
                !txtEmail.getText().toString().isEmpty() &&
                !txtLogin.getText().toString().isEmpty() &&
                !txtSenha.getText().toString().isEmpty()
                );
    }

}