package com.boom.covidchecker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.text.TextUtils;

import java.util.Arrays;

public class BdTableCovid implements BaseColumns {
    public static final String NOME_TABELA = "covid";



    public static final String CAMPO_PROGRESSO = "progresso";
    public static final String CAMPO_TOGGLE = "toggle";

    public static final String CAMPO_ID_COMPLETO = NOME_TABELA + "." + _ID;
    public static final String CAMPO_PROGRESSO_COMPLETO = NOME_TABELA + "." + CAMPO_PROGRESSO;
    public static final String CAMPO_TOGGLE_COMPLETO = NOME_TABELA + "." + CAMPO_TOGGLE;

    public static final String[] TODOS_CAMPOS = {CAMPO_ID_COMPLETO, CAMPO_PROGRESSO_COMPLETO, CAMPO_TOGGLE_COMPLETO };

    private SQLiteDatabase db;

    public BdTableCovid(SQLiteDatabase db) {
        this.db = db;
    }

    public void cria() {
        db.execSQL("CREATE TABLE " + NOME_TABELA + "(" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                CAMPO_PROGRESSO + " INTEGER NOT NULL," +
                CAMPO_TOGGLE + " INTEGER NOT NULL " +
                ")");
    }


    public long insert(ContentValues values) {
        return db.insert(NOME_TABELA, null, values);
    }


    public Cursor query(String[] columns, String selection,
                        String[] selectionArgs, String groupBy, String having,
                        String orderBy) {
        if(!Arrays.asList(columns).contains(_ID)) {
            return db.query(NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy);
        }

        String campos = TextUtils.join(" , ", columns);

        String sql = "SELECT " + campos;
        sql += " FROM " + NOME_TABELA + " INNER JOIN " + BdTableCategorias.NOME_TABELA;

        if (selection != null) {
            sql += " WHERE " + selection;
        }

        if (groupBy != null) {
            sql += " GROUP BY " + groupBy;

            if (having != null) {
                sql += " HAVING " + having;
            }
        }

        if (orderBy != null) {
            sql += " ORDER BY " + orderBy;
        }

        return db.rawQuery(sql, selectionArgs);
    }


    public int update(ContentValues values, String whereClause, String[] whereArgs) {
        return db.update(NOME_TABELA, values, whereClause, whereArgs);
    }


    public int delete(String whereClause, String[] whereArgs) {
        return db.delete(NOME_TABELA, whereClause, whereArgs);
    }
}
