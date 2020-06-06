package com.boom.covidchecker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BdTableCategorias implements BaseColumns {
    public static final String NOME_TABELA = "categorias";

    public static final String CAMPO_DESCRICAO = "descricao";
    public static final String[] TODOS_CAMPOS = {_ID, CAMPO_DESCRICAO};

    private SQLiteDatabase db;

    public BdTableCategorias(SQLiteDatabase db) {
        this.db = db;
    }

    public void cria() {
        db.execSQL(
                "CREATE TABLE " + NOME_TABELA + " (" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        CAMPO_DESCRICAO + " TEXT NOT NULL" +
                        ")");
    }

    //INSERE
    public long insert(ContentValues values) {
        return db.insert(NOME_TABELA, null, values);
    }

    //PROCURA
    public Cursor query(String[] columns, String selection,
                        String[] selectionArgs, String groupBy, String having,
                        String orderBy) {
        return db.query(NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy);
    }

   //UPDATA
    public int update(ContentValues values, String whereClause, String[] whereArgs) {
        return db.update(NOME_TABELA, values, whereClause, whereArgs);
    }

    //DELETA
    public int delete(String whereClause, String[] whereArgs) {
        return db.delete(NOME_TABELA, whereClause, whereArgs);
    }
}
