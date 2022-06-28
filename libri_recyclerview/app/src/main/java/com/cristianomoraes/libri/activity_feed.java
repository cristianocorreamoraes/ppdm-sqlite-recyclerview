package com.cristianomoraes.libri;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cristianomoraes.libri.database.SQLHelper;
import com.cristianomoraes.libri.helpers.Login;
import com.cristianomoraes.libri.model.Item;
import com.cristianomoraes.libri.model.Livro;

import java.util.List;

public class activity_feed extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        List<Item> items = SQLHelper.getInstance(this).listBooks(Login.getCod_usuario());

        recyclerView.setAdapter(new LivroAdapter(items));

        Log.d("LOGIN-", String.valueOf(Login.getCod_usuario()));

    }

    /** INFLATE DO MENU **/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
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

    /********** RECYCLERVIEW **********/
    private class LivroAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        List<Item> items;

        public LivroAdapter(List<Item> items) {
            this.items = items;
        }

        /***** INICIO DOS MÉTODOS DO ADAPTER *****/
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            if(viewType == 0){

                return new LivroAdapter.LivroViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container_livro,
                        parent,
                        false
                ));

            }

            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            if(getItemViewType(position) == 0){
                Livro livro = (Livro) items.get(position).getObject();
                ((LivroAdapter.LivroViewHolder) holder).setLivroData(livro);
            }

        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        @Override
        public int getItemViewType(int position) {
            return items.get(position).getType();
        }

        /***** FIM DOS MÉTODOS DO ADAPTER *****/

        class LivroViewHolder extends RecyclerView.ViewHolder{

//            private ImageView livroImagem;
            private TextView textLivroTitulo, textLivroDescricao;
            private int cod_livro;

            public LivroViewHolder(@NonNull View itemView) {
                super(itemView);

                textLivroTitulo = itemView.findViewById(R.id.textLivroTitulo);
                textLivroDescricao = itemView.findViewById(R.id.textLivroDescricao);
//            livroImagem = itemView.findViewById(R.id.LivroImagem);

                itemView.setOnClickListener(view -> {

//                    Log.d("CLICK-", String.valueOf(cod_livro));
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity_feed.this)
                            .setMessage("ESCOLHA A AÇÃO QUE DESEJA EXECUTAR")
                            .setNegativeButton("ALTERAR", (dialog1, witch)->{
//                                Log.d("CLICK-", "ALTERAR");
                                Intent intent = new Intent(activity_feed.this, activity_altera_livro.class);
                                intent.putExtra("cod_livro", cod_livro);
                                startActivity(intent);
                            })
                            .setPositiveButton("EXCLUIR", (dialog1, witch)->{
//                                Log.d("CLICK-", "EXCLUIR");
                                if(SQLHelper.getInstance(activity_feed.this).deleteBooks(cod_livro)){
                                    Toast.makeText(activity_feed.this, "LIVRO EXCLUÍDO COM SUCESSO!", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(activity_feed.this, activity_feed.class);
                                    startActivity(intent);

                                }else{
                                    Toast.makeText(activity_feed.this, "HOUVE UM ERRO AO EXCLUIR O LIVRO!", Toast.LENGTH_SHORT).show();
                                }
                            });

                    alertDialog.show();

                });

            }

            void setLivroData(Livro livro){
                textLivroTitulo.setText(livro.getTitulo());
                textLivroDescricao.setText(livro.getDescricao());
                cod_livro = livro.getCod_livro();
//            livroImagem.setImageResource(livro.getImagem());
            }
        }

    }

}