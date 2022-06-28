package com.cristianomoraes.libri.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.cristianomoraes.libri.helpers.DateFormat;
import com.cristianomoraes.libri.model.Item;
import com.cristianomoraes.libri.model.Livro;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SQLHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "libri.db";
    private static final int DB_VERSION = 1;
    private static SQLHelper INSTANCE;

    public static SQLHelper getInstance(Context context) {

        if (INSTANCE == null) {
            INSTANCE = new SQLHelper(context);
        }

        return INSTANCE;

    }

    public SQLHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE tbl_usuario" +
                "(cod_usuario INTEGER PRIMARY KEY, " +
                "nome TEXT, sobrenome TEXT, email TEXT, " +
                "login TEXT, senha TEXT, created_date DATETIME);");

        sqLiteDatabase.execSQL("CREATE TABLE tbl_livros " +
                "(cod_livro INTEGER PRIMARY KEY, " +
                "cod_usuario INTEGER, " +
                "titulo TEXT, " +
                "descricao TEXT, " +
                "foto TEXT," +
                "created_date DATETIME," +
                " FOREIGN KEY (cod_usuario) REFERENCES tbl_usuario(cod_usuario));");

        Log.d("SQLITE", "BANCO CRIADO COM SUCESSO! - VERSÃO: " + DB_VERSION);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        Log.d("SQLITE", "BANCO ALTERADO COM SUCESSO! - VERSÃO: " + DB_VERSION);

    }

    /**
     * INSERÇÃO DE DADOS  DE USUÁRIO
     **/
    public int addUser(String nome, String sobrenome, String email, String login, String senha,
                       String created_date) {

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        int cod_usuario = 0;

        try {

            sqLiteDatabase.beginTransaction();

            ContentValues values = new ContentValues();
            values.put("nome", nome);
            values.put("sobrenome", sobrenome);
            values.put("email", email);
            values.put("login", login);
            values.put("senha", senha);

            DateFormat df = new DateFormat();
            values.put("created_date", df.getDateFormat());

            cod_usuario = (int) sqLiteDatabase.insertOrThrow("tbl_usuario", null, values);
            sqLiteDatabase.setTransactionSuccessful();

        } catch (Exception e) {

            Log.d("SQLITE", e.getMessage());

        } finally {

            if (sqLiteDatabase.isOpen()) {
                sqLiteDatabase.endTransaction();
            }

            return cod_usuario;

        }

    }

    /**
     * INSERÇÃO DE DADOS  DE LIVROS
     **/
    public int addBook(int cod_usuario, String titulo, String descricao, String foto,
                       String created_date) {

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        int cod_livro = 0;

        try {

            sqLiteDatabase.beginTransaction();

            ContentValues values = new ContentValues();

            values.put("cod_usuario", cod_usuario);
            values.put("titulo", titulo);
            values.put("descricao", descricao);
            values.put("foto", foto);

            DateFormat df = new DateFormat();
            values.put("created_date", df.getDateFormat());

            cod_livro = (int) sqLiteDatabase.insertOrThrow("tbl_livros", null, values);
            sqLiteDatabase.setTransactionSuccessful();

        } catch (Exception e) {

            Log.d("SQLITE", e.getMessage());

        } finally {

            if (sqLiteDatabase.isOpen()) {
                sqLiteDatabase.endTransaction();
            }

            return cod_livro;
        }

    }

    /**
     * INSERÇÃO DE DADOS  DE USUÁRIO
     **/
    public int login(String login, String senha) {

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM tbl_usuario WHERE login = ? AND senha = ?",
                new String[]{login, senha});

        int cod_usuario = 0;
        try {

            if (cursor.moveToFirst()) {

                Log.d("COUNT-", String.valueOf(cursor.getCount()));
                cod_usuario = cursor.getInt(cursor.getColumnIndex("cod_usuario"));
                return cod_usuario;

            } else {

                Log.d("COUNT-", String.valueOf(cursor.getCount()));

            }

        } catch (Exception e) {

            Log.d("SQLite", e.getMessage());

        } finally {

            if (cursor != null && !cursor.isClosed()) {

                cursor.close();

            }

        }

        return 0;

    }

    /**
     * LISTAGEM DE LIVROS POR CÓDIGO DE USUÁRIOS
     **/
    public List<Item> listBooks(int cod_usuario) {

        List<Item> items = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tbl_livros WHERE cod_usuario = ?",
                new String[]{String.valueOf(cod_usuario)});

        try {

            if (cursor.moveToFirst()) {
                do {

                    Livro livro = new Livro(
                            cursor.getInt(cursor.getColumnIndex("cod_livro")),
                            cursor.getString(cursor.getColumnIndex("titulo")),
                            cursor.getString(cursor.getColumnIndex("descricao"))
                    );

                    items.add(new Item(0, livro));

                } while (cursor.moveToNext());

            }

        } catch (Exception e) {

            Log.e("SQLite", e.getMessage());

        } finally {

            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }

        }

        return items;

    }

    /**
     * LISTAGEM DE LIVROS POR CÓDIGO DE LIVROS
     **/
    public Livro listBookID(int cod_livro) {

        Livro livro = null;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tbl_livros WHERE cod_livro = ?",
                new String[]{String.valueOf(cod_livro)});


        try {

            if (cursor.moveToFirst()) {

                do {

                    livro = new Livro(
                            cursor.getInt(cursor.getColumnIndex("cod_livro")),
                            cursor.getString(cursor.getColumnIndex("titulo")),
                            cursor.getString(cursor.getColumnIndex("descricao"))
                    );

                } while (cursor.moveToNext());

            }

        } catch (Exception e) {

            Log.e("SQLite", e.getMessage());

        } finally {

            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }

        }

        return livro;

    }

    /**
     * ATUALIZAÇÃO DE LIVROS
     **/
    public boolean updateBooks(int cod_livro, String titulo, String descricao){

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.beginTransaction();

        try{

            ContentValues values = new ContentValues();

            values.put("titulo", titulo);
            values.put("descricao", descricao);

            sqLiteDatabase.update("tbl_livros", values, "cod_livro = ?", new String[]{String.valueOf(cod_livro)});
            sqLiteDatabase.setTransactionSuccessful();

            return true;

        }catch (Exception e){

            Log.e("SQLite", e.getMessage());
            return false;

        }finally {

            if (sqLiteDatabase.isOpen()) {
                sqLiteDatabase.endTransaction();
            }

        }

    }

    /**
     * EXCLUSÃO DE LIVROS
     **/
    public boolean deleteBooks(int cod_livro){

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.beginTransaction();

        try{

            ContentValues values = new ContentValues();
            values.put("cod_livro", cod_livro);

            sqLiteDatabase.delete("tbl_livros", "cod_livro = ?", new String[]{String.valueOf(cod_livro)});
            sqLiteDatabase.setTransactionSuccessful();

            return true;

        }catch(Exception e){

            Log.e("SQLite", e.getMessage());
            return false;

        }finally {

            if (sqLiteDatabase.isOpen()) {
                sqLiteDatabase.endTransaction();
            }

        }

    }

}
