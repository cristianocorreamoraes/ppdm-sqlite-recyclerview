package com.cristianomoraes.libri.model;

public class Livro {
    private int cod_livro;
//    private int imagem;
    private String titulo, descricao;

    public Livro(int cod_livro, String titulo, String descricao) {
//        this.imagem = imagem;
        this.cod_livro = cod_livro;
        this.titulo = titulo;
        this.descricao = descricao;
    }

//    public int getImagem() {
//        return imagem;
//    }

    public int getCod_livro() { return cod_livro; }
    public String getTitulo() {
        return titulo;
    }
    public String getDescricao() {
        return descricao;
    }
}
