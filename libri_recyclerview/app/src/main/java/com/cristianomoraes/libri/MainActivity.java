package com.cristianomoraes.libri;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cristianomoraes.libri.database.SQLHelper;
import com.cristianomoraes.libri.helpers.Login;

public class MainActivity extends AppCompatActivity {

    Button btnCadastrar;
    Button btnLogar;
    EditText txtLogin;
    EditText txtSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /***** BOTÃO DE CADASTRAR *****/
        this.btnCadastrar = findViewById(R.id.btnCadastrarUsuario);

        this.btnCadastrar.setOnClickListener(view->{

            startActivity(new Intent(MainActivity.this, activity_cadastro_usuario.class));
        });

        /***** BOTÃO DE LOGIN *****/
        this.btnLogar = findViewById(R.id.btnLogar);
        this.txtLogin = findViewById(R.id.txtLogin);
        this.txtSenha = findViewById(R.id.txtSenha);

        this.btnLogar.setOnClickListener(view->{

            String sLogin = txtLogin.getText().toString();
            String sSenha = txtSenha.getText().toString();

            int cod_usuario = SQLHelper.getInstance(MainActivity.this).login(sLogin, sSenha);

            Log.d("COUNT-", "COD " + String.valueOf(cod_usuario));
            if(cod_usuario > 0){

                Login.setCod_usuario(cod_usuario);
                Intent intent  = new Intent(MainActivity.this, activity_feed.class);
                //intent.putExtra("cod_usuario", cod_usuario);
                startActivity(intent);

            }else{

                Toast.makeText(MainActivity.this, R.string.login_fail, Toast.LENGTH_LONG).show();

            }

        });

    }
}