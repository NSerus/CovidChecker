package com.boom.covidchecker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

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
        item.setIdCategoria(idCatProg);
        tabelaitems.insert(Converte.itemToContentValues(item));

        item = new Item();
        item.setConteudo("Limpar carburador");
        item.setIdCategoria(idCatMota);
        tabelaitems.insert(Converte.itemToContentValues(item));

        item = new Item();
        item.setConteudo("treinar posição de tiro");
        item.setIdCategoria(idCatArco);
        tabelaitems.insert(Converte.itemToContentValues(item));

        item = new Item();
        item.setConteudo("arquitetar");
        item.setIdCategoria(idCatArquitetura);
        tabelaitems.insert(Converte.itemToContentValues(item));
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     *
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
