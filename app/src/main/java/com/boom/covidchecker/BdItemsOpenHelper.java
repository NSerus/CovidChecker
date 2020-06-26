package com.boom.covidchecker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.sql.Date;

public class BdItemsOpenHelper extends SQLiteOpenHelper {
    public static final String NOME_BASE_DADOS = "items.db";
    private static final int VERSAO_BASE_DADOS = 1;
    private static final boolean DESENVOLVIMENTO = true;


    public BdItemsOpenHelper(@Nullable Context context) {
        super(context, NOME_BASE_DADOS, null, VERSAO_BASE_DADOS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        BdTableCategorias tabelaCategorias = new BdTableCategorias(db);
        tabelaCategorias.cria();

        BdTableItems tabelaitems = new BdTableItems(db);
        tabelaitems.cria();

        BdTableSolidao tabelaSolidao = new BdTableSolidao(db);
        tabelaSolidao.cria();

        BdTableCovid tabelaCovid = new BdTableCovid(db);
        tabelaCovid.cria();

        if (DESENVOLVIMENTO) {
            seedData(db);
        }
    }

    private void seedData(SQLiteDatabase db) {
        BdTableCategorias tabelaCategorias = new BdTableCategorias(db);

        Categoria categoria = new Categoria();
        categoria.setDescricao("Prog. avançada");
        long idCatProg = tabelaCategorias.insert(Converte.categoriaToContentValues(categoria));

        categoria = new Categoria();
        categoria.setDescricao("Mota");
        long idCatMota = tabelaCategorias.insert(Converte.categoriaToContentValues(categoria));

        categoria = new Categoria();
        categoria.setDescricao("Arco");
        long idCatArco = tabelaCategorias.insert(Converte.categoriaToContentValues(categoria));

        categoria = new Categoria();
        categoria.setDescricao("Arquitetura");
        long idCatArquitetura = tabelaCategorias.insert(Converte.categoriaToContentValues(categoria));

        BdTableItems tabelaitems = new BdTableItems(db);

        Item item = new Item();
        item.setConteudo("arranjar base dados");
        item.setData(String.valueOf(Date.valueOf("2015-12-06")));
        item.setIdCategoria(idCatProg);
        tabelaitems.insert(Converte.itemToContentValues(item));

        item = new Item();
        item.setConteudo("Limpar carburador");
        item.setData(String.valueOf(Date.valueOf("2011-11-01")));
        item.setIdCategoria(idCatMota);
        tabelaitems.insert(Converte.itemToContentValues(item));

        item = new Item();
        item.setConteudo("treinar posição de tiro");
        item.setData(String.valueOf(Date.valueOf("2013-02-07")));
        item.setIdCategoria(idCatArco);
        tabelaitems.insert(Converte.itemToContentValues(item));

        item = new Item();
        item.setConteudo("arquitetar");
        item.setData(String.valueOf(Date.valueOf("2015-12-06") ));
        item.setIdCategoria(idCatArquitetura);
        tabelaitems.insert(Converte.itemToContentValues(item));

        BdTableSolidao tabelaSolidao = new BdTableSolidao(db);

        Solidao solidao = new Solidao();
        solidao.setConteudo("entrada diario nº1");
        solidao.setDataSolidao(String.valueOf(Date.valueOf("2015-12-06")));
        tabelaSolidao.insert(Converte.SolidaoToContentValues(solidao));

        BdTableCovid tabelaCovid = new BdTableCovid(db);

        Covid covid = new Covid();
        covid.setProgresso(45);
        covid.setToggle(1);
        tabelaCovid.insert(Converte.SolidaoToContentValues(solidao));
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
