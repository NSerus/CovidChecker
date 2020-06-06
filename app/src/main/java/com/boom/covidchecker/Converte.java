package com.boom.covidchecker;

import android.content.ContentValues;
import android.database.Cursor;

public class Converte {
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
        valores.put(BdTableItems.CAMPO_CATEGORIA, item.getIdCategoria());

        return valores;
    }

    public static Item contentValuesToitem(ContentValues valores) {
        Item item = new Item();

        item.setId(valores.getAsLong(BdTableItems._ID));
        item.setConteudo(valores.getAsString(BdTableItems.CAMPO_CONTEUDO));
        item.setIdCategoria(valores.getAsLong(BdTableItems.CAMPO_CATEGORIA));

        return item;
    }

    public static Item cursorToitem(Cursor cursor) {
        Item item = new Item();

        item.setId(cursor.getLong(cursor.getColumnIndex(BdTableItems._ID)));
        item.setConteudo(cursor.getString(cursor.getColumnIndex(BdTableItems.CAMPO_CONTEUDO)));
        item.setIdCategoria(cursor.getLong(cursor.getColumnIndex(BdTableItems.CAMPO_CATEGORIA)));

        return item;
    }
}
