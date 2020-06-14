package com.boom.covidchecker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BdTableCategorias implements BaseColumns {
    public static final String NOME_TABELA = "categorias";

    public static final String CAMPO_DESCRICAO = "descricao";

    public static final String CAMPO_ID_COMPLETO = NOME_TABELA + "." + _ID;
    public static final String CAMPO_DESCRICAO_COMPLETO = NOME_TABELA + "." +CAMPO_DESCRICAO;

    public static final String[] TODOS_CAMPOS = {_ID, CAMPO_DESCRICAO};

    private SQLiteDatabase db;


    public BdTableCategorias(SQLiteDatabase db) {
        this.db = db;
    }

    //Cria tabela de categoria c todas os campos
    public void cria() {
        db.execSQL(
                "CREATE TABLE " + NOME_TABELA + " (" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        CAMPO_DESCRICAO + " TEXT NOT NULL" +
                        ")");
    }

    //INSERE valores na BD
    public long insert(ContentValues values) {
        return db.insert(NOME_TABELA, null, values);
    }

    //PESQUISA na bd por varios parametros
    public Cursor query(String[] columns, String selection,
                        String[] selectionArgs, String groupBy, String having,
                        String orderBy) {
        return db.query(NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy);
    }

   //UPDATE todos os parametros
    public int update(ContentValues values, String whereClause, String[] whereArgs) {
        return db.update(NOME_TABELA, values, whereClause, whereArgs);
    }

    //DELETE de tabela
    public int delete(String whereClause, String[] whereArgs) {
        return db.delete(NOME_TABELA, whereClause, whereArgs);
    }
}
