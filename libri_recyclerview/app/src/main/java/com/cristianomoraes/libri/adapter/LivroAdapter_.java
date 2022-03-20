package com.cristianomoraes.libri.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.cristianomoraes.libri.R;
import com.cristianomoraes.libri.activity_feed;
import com.cristianomoraes.libri.model.Item;
import com.cristianomoraes.libri.model.Livro;

import java.util.List;

public class LivroAdapter_ extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    List<Item> items;

    public LivroAdapter_(List<Item> items) {
        this.items = items;
    }

    /***** INICIO DOS MÉTODOS DO ADAPTER *****/
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == 0){

            return new LivroViewHolder(LayoutInflater.from(parent.getContext()).inflate(
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
            ((LivroViewHolder) holder).setLivroData(livro);
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

    public static class LivroViewHolder extends RecyclerView.ViewHolder{

        //        private ImageView livroImagem;
        private TextView textLivroTitulo, textLivroDescricao;
        private int cod_livro;

        public LivroViewHolder(@NonNull View itemView) {
            super(itemView);

            textLivroTitulo = itemView.findViewById(R.id.textLivroTitulo);
            textLivroDescricao = itemView.findViewById(R.id.textLivroDescricao);
//            livroImagem = itemView.findViewById(R.id.LivroImagem);

            itemView.setOnClickListener(view -> {
                //Toast.makeText(null, "TESTE", Toast.LENGTH_LONG).show();
                Log.d("CLICK-", "AAAAAA");

//                AlertDialog alertDialog = new AlertDialog.Builder(R.class.ac)
//                        .setMessage("ESCOLHA A AÇÃO QUE DESEJA EXECUTAR")
//                        .setNegativeButton("ALTERAR", (dialog1, witch)->{
//                            Log.d("CLICK-", "ALTERAR");
//                        })
//                        .setPositiveButton("EXCLUIR", (dialog1, witch)->{
//                            Log.d("CLICK-", "EXCLUIR");
//                        });

            });

        }

        public void setLivroData(Livro livro){
            textLivroTitulo.setText(livro.getTitulo());
            textLivroDescricao.setText(livro.getDescricao());
            cod_livro = livro.getCod_livro();
//            livroImagem.setImageResource(livro.getImagem());
        }
    }

}
