package com.boom.covidchecker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BdTableItems implements BaseColumns {
    public static final String NOME_TABELA = "items";

    public static final String CAMPO_CONTEUDO = "conteudo";
    public static final String CAMPO_CATEGORIA = "categoria";
    public static final String[] TODOS_CAMPOS = {_ID, CAMPO_CONTEUDO, CAMPO_CATEGORIA};

    private SQLiteDatabase db;

    public BdTableItems(SQLiteDatabase db) {
        this.db = db;
    }

    public void cria() {
        db.execSQL("CREATE TABLE " + NOME_TABELA + "(" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                CAMPO_CONTEUDO + " TEXT NOT NULL," +
                CAMPO_CATEGORIA + " INTEGER NOT NULL," +
                "FOREIGN KEY (" + CAMPO_CATEGORIA + ") REFERENCES " +
                BdTableCategorias.NOME_TABELA + "("+ BdTableCategorias._ID + ")" +
                ")");
    }


    public long insert(ContentValues values) {
        return db.insert(NOME_TABELA, null, values);
    }


    public Cursor query(String[] columns, String selection,
                        String[] selectionArgs, String groupBy, String having,
                        String orderBy) {
        return db.query(NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy);
    }


    public int update(ContentValues values, String whereClause, String[] whereArgs) {
        return db.update(NOME_TABELA, values, whereClause, whereArgs);
    }


    public int delete(String whereClause, String[] whereArgs) {
        return db.delete(NOME_TABELA, whereClause, whereArgs);
    }
}
