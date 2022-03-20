package com.cristianomoraes.libri.helpers;

import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cristianomoraes.libri.MainActivity;
import com.cristianomoraes.libri.R;
import com.cristianomoraes.libri.activity_cadastro_livro;
import com.cristianomoraes.libri.activity_feed;

public class SwitchMenu extends AppCompatActivity {

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
