package com.boom.covidchecker;

import android.content.ContentValues;
import android.database.Cursor;

public class Converte {
    //Converte os valores das classes para guardar valores de forma que o ContentResolver consiga processar.
    //E o ContentResolver provide acesso de Conteudo para o modelo de conteudo usado.
    public static ContentValues categoriaToContentValues(Categoria categoria) {
        ContentValues valores = new ContentValues();

        valores.put(BdTableCategorias.CAMPO_DESCRICAO, categoria.getDescricao());

        return valores;
    }

    public static Categoria contentValuesToCategoria(ContentValues valores) {
        Categoria categoria = new Categoria();

        categoria.setId(valores.getAsLong(BdTableCategorias._ID));
        categoria.setDescricao(valores.getAsString(BdTableCategorias.CAMPO_DESCRICAO));

        return categoria;
    }

    public static ContentValues itemToContentValues(Item item) {
        ContentValues valores = new ContentValues();

        valores.put(BdTableItems.CAMPO_CONTEUDO, item.getConteudo());
        valores.put(BdTableItems.CAMPO_DATA, item.getData());
        valores.put(BdTableItems.CAMPO_ID_CATEGORIA, item.getIdCategoria());

        return valores;
    }

    public static Item contentValuesToitem(ContentValues valores) {
        Item item = new Item();

        item.setId(valores.getAsLong(BdTableItems._ID));
        item.setConteudo(valores.getAsString(BdTableItems.CAMPO_CONTEUDO));
        item.setData(valores.getAsString(BdTableItems.CAMPO_DATA));
        item.setIdCategoria(valores.getAsLong(BdTableItems.CAMPO_ID_CATEGORIA));

        return item;
    }

    public static Item cursorToItem(Cursor cursor) {
        Item item = new Item();

        item.setId(cursor.getLong(cursor.getColumnIndex(BdTableItems._ID)));
        item.setConteudo(cursor.getString(cursor.getColumnIndex(BdTableItems.CAMPO_CONTEUDO)));
        item.setData(cursor.getString(cursor.getColumnIndex(BdTableItems.CAMPO_DATA)));
        item.setIdCategoria(cursor.getLong(cursor.getColumnIndex(BdTableItems.CAMPO_ID_CATEGORIA)));
        item.setCategoria(cursor.getString(cursor.getColumnIndex(BdTableItems.CAMPO_CATEGORIA)));

        return item;
    }

    public static ContentValues SolidaoToContentValues(Solidao solidao) {
        ContentValues valores = new ContentValues();

        valores.put(BdTableSolidao.CAMPO_CONTEUDO, solidao.getConteudo());
        valores.put(BdTableSolidao.CAMPO_DATA, solidao.getDataSolidao());

        return valores;
    }
    public static Solidao contentValuesToSolidao(ContentValues valores) {
        Solidao solidao = new Solidao();

        solidao.setId(valores.getAsLong(BdTableSolidao._ID));
        solidao.setConteudo(valores.getAsString(BdTableSolidao.CAMPO_CONTEUDO));
        solidao.setDataSolidao(valores.getAsString(BdTableSolidao.CAMPO_DATA));

        return solidao;
    }
    public static Solidao cursorToSolidao(Cursor cursor) {
        Solidao solidao = new Solidao();

        solidao.setId(cursor.getLong(cursor.getColumnIndex(BdTableSolidao._ID)));
        solidao.setConteudo(cursor.getString(cursor.getColumnIndex(BdTableSolidao.CAMPO_CONTEUDO)));
        solidao.setDataSolidao(cursor.getString(cursor.getColumnIndex(BdTableSolidao.CAMPO_DATA)));

        return solidao;
    }

    public static ContentValues CovidToContentValues(Covid covid) {
        ContentValues valores = new ContentValues();

        valores.put(BdTableCovid.CAMPO_PROGRESSO, covid.getProgresso());
        valores.put(BdTableCovid.CAMPO_TOGGLE, covid.getToggle());

        return valores;
    }
    public static Covid contentValuesToCovid(ContentValues valores) {
        Covid covid = new Covid();

        covid.setId(valores.getAsLong(BdTableCovid._ID));
        covid.setProgresso(valores.getAsInteger(BdTableCovid.CAMPO_PROGRESSO));
        covid.setToggle(valores.getAsInteger(BdTableCovid.CAMPO_PROGRESSO));

        return covid;
    }
    public static Covid cursorToCovid(Cursor cursor) {
        Covid covid = new Covid();

        covid.setId(cursor.getLong(cursor.getColumnIndex(BdTableCovid._ID)));
        covid.setProgresso(cursor.getInt(cursor.getColumnIndex(BdTableCovid.CAMPO_PROGRESSO)));
        covid.setToggle(cursor.getInt(cursor.getColumnIndex(BdTableCovid.CAMPO_TOGGLE)));

        return covid;
    }
}
