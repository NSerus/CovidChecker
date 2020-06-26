package com.boom.covidchecker;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class BdItemsTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.boom.covidchecker", appContext.getPackageName());
    }
    @Before
    public void apagaBaseDados() {
        getTargetContext().deleteDatabase(BdItemsOpenHelper.NOME_BASE_DADOS);
    }

    @Test
    public void consegueAbrirBaseDados() {
        // Context of the app under test.
        Context appContext = getTargetContext();

        BdItemsOpenHelper openHelper = new BdItemsOpenHelper(appContext);
        SQLiteDatabase bditems = openHelper.getReadableDatabase();
        assertTrue(bditems.isOpen());
        bditems.close();
    }

    private Context getTargetContext() {
        return InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    private long insereCategoria(BdTableCategorias tabelaCategorias, Categoria categoria) {
        long id = tabelaCategorias.insert(Converte.categoriaToContentValues(categoria));
        assertNotEquals(-1, id);

        return id;
    }

    private long insereCategoria(BdTableCategorias tabelaCategorias, String descricao) {
        Categoria categoria = new Categoria();
        categoria.setDescricao(descricao);

        return insereCategoria(tabelaCategorias, categoria);
    }



    private long insereitem(SQLiteDatabase bditems, String titulo,String data, String descCategoria) {
        BdTableCategorias tabelaCategorias = new BdTableCategorias(bditems);

        long idCategoria = insereCategoria(tabelaCategorias, descCategoria);

        Item item = new Item();
        item.setConteudo(titulo);
        item.setData(data);
        item.setIdCategoria(idCategoria);

        BdTableItems tabelaitems = new BdTableItems(bditems);
        long id = tabelaitems.insert(Converte.itemToContentValues(item));
        assertNotEquals(-1, id);

        return  id;
    }

    private long insereitemsolidao(SQLiteDatabase bditemsolidao, String titulo,String data) {
        Solidao solidao = new Solidao();
        solidao.setConteudo(titulo);
        solidao.setDataSolidao(data);

        BdTableSolidao tabelaSolidao = new BdTableSolidao(bditemsolidao);
        long id = tabelaSolidao.insert(Converte.SolidaoToContentValues(solidao));
        assertNotEquals(-1, id);

        return  id;
    }

    private long insereitemcovid(SQLiteDatabase bditemcovid, Integer progresso,Integer Toggle) {
        Covid covid = new Covid();
        covid.setProgresso(progresso);
        covid.setToggle(Toggle);

        BdTableCovid tabelaCovid = new BdTableCovid(bditemcovid);
        long id = tabelaCovid.insert(Converte.CovidToContentValues(covid));
        assertNotEquals(-1, id);

        return  id;
    }

    @Test
    public void consegueInserirCategorias() {
        Context appContext = getTargetContext();

        BdItemsOpenHelper openHelper = new BdItemsOpenHelper(appContext);
        SQLiteDatabase bditems = openHelper.getWritableDatabase();

        BdTableCategorias tabelaCategorias = new BdTableCategorias(bditems);

        insereCategoria(tabelaCategorias, "Ação");

        bditems.close();
    }

    @Test
    public void consegueLerCategorias() {
        Context appContext = getTargetContext();

        BdItemsOpenHelper openHelper = new BdItemsOpenHelper(appContext);
        SQLiteDatabase bditems = openHelper.getWritableDatabase();

        BdTableCategorias tabelaCategorias = new BdTableCategorias(bditems);

        Cursor cursor = tabelaCategorias.query(BdTableCategorias.TODOS_CAMPOS, null, null, null, null, null);
        int registos = cursor.getCount();
        cursor.close();

        insereCategoria(tabelaCategorias, "Sci-fi");

        cursor = tabelaCategorias.query(BdTableCategorias.TODOS_CAMPOS, null, null, null, null, null);
        assertEquals(registos + 1, cursor.getCount());
        cursor.close();

        bditems.close();
    }

    @Test
    public void consegueAlterarCategorias() {
        Context appContext = getTargetContext();

        BdItemsOpenHelper openHelper = new BdItemsOpenHelper(appContext);
        SQLiteDatabase bditems = openHelper.getWritableDatabase();

        BdTableCategorias tabelaCategorias = new BdTableCategorias(bditems);

        Categoria categoria = new Categoria();
        categoria.setDescricao("Est");

        long id = insereCategoria(tabelaCategorias, categoria);

        categoria.setDescricao("Estudo");
        int registosAfetados = tabelaCategorias.update(Converte.categoriaToContentValues(categoria), BdTableCategorias._ID + "=?", new String[]{String.valueOf(id)});
        assertEquals(1, registosAfetados);

        bditems.close();
    }

    @Test
    public void consegueEliminarCategorias() {
        Context appContext = getTargetContext();

        BdItemsOpenHelper openHelper = new BdItemsOpenHelper(appContext);
        SQLiteDatabase bditems = openHelper.getWritableDatabase();

        BdTableCategorias tabelaCategorias = new BdTableCategorias(bditems);

        long id = insereCategoria(tabelaCategorias, "TESTE");

        int registosEliminados = tabelaCategorias.delete(BdTableCategorias._ID + "=?", new String[]{String.valueOf(id)});
        assertEquals(1, registosEliminados);

        bditems.close();
    }

    @Test
    public void consegueInserirItemsSolidao() {
        Context appContext = getTargetContext();

        BdItemsOpenHelper openHelper = new BdItemsOpenHelper(appContext);
        SQLiteDatabase bditemsolidao = openHelper.getWritableDatabase();

        insereitemsolidao(bditemsolidao, "entrada diario nº1","2015/12/06");

        bditemsolidao.close();
    }

    @Test
    public void consegueLerItemsSolidao() {
        Context appContext = getTargetContext();

        BdItemsOpenHelper openHelper = new BdItemsOpenHelper(appContext);
        SQLiteDatabase bditemsolidao = openHelper.getWritableDatabase();

        BdTableSolidao tabelaitemsolidao = new BdTableSolidao(bditemsolidao);

        Cursor cursor = tabelaitemsolidao.query(BdTableSolidao.TODOS_CAMPOS, null, null, null, null, null);
        int registos = cursor.getCount();
        cursor.close();

        insereitemsolidao(bditemsolidao, "Rocking","2015/05/22");

        cursor = tabelaitemsolidao.query(BdTableSolidao.TODOS_CAMPOS, null, null, null, null, null);
        assertEquals(registos + 1, cursor.getCount());
        cursor.close();

        bditemsolidao.close();
    }

    @Test
    public void consegueAlteraritemsSolidao() {
        Context appContext = getTargetContext();

        BdItemsOpenHelper openHelper = new BdItemsOpenHelper(appContext);
        SQLiteDatabase bditemsolidao = openHelper.getWritableDatabase();

        long iditem = insereitemsolidao(bditemsolidao, "shootin","2015/05/22");

        BdTableSolidao tabelaitems = new BdTableSolidao(bditemsolidao);

        Cursor cursor = tabelaitems.query(BdTableSolidao.TODOS_CAMPOS, BdTableSolidao.CAMPO_ID_COMPLETO + "=?", new String[]{ String.valueOf(iditem) }, null, null, null);
        assertEquals(1, cursor.getCount());

        assertTrue(cursor.moveToNext());
        Solidao item = Converte.cursorToSolidao(cursor);
        cursor.close();

        assertEquals("shootin", item.getConteudo());

        item.setConteudo("Shooting");
        int registosAfetados = tabelaitems.update(Converte.SolidaoToContentValues(item), BdTableItems._ID + "=?", new String[]{String.valueOf(item.getId())});
        assertEquals(1, registosAfetados);

        bditemsolidao.close();
    }

    @Test
    public void consegueEliminaritemsSolidao() {
        Context appContext = getTargetContext();

        BdItemsOpenHelper openHelper = new BdItemsOpenHelper(appContext);
        SQLiteDatabase bditemsolidao = openHelper.getWritableDatabase();

        long id = insereitemsolidao(bditemsolidao, "shootin","2015/05/22");

        BdTableSolidao tabelaitems = new BdTableSolidao(bditemsolidao);
        int registosEliminados = tabelaitems.delete(BdTableSolidao._ID + "=?", new String[]{String.valueOf(id)});
        assertEquals(1, registosEliminados);

        bditemsolidao.close();
    }

    public void consegueInserirItemsCovid() {
        Context appContext = getTargetContext();

        BdItemsOpenHelper openHelper = new BdItemsOpenHelper(appContext);
        SQLiteDatabase bditemscovid = openHelper.getWritableDatabase();

        insereitemcovid(bditemscovid, 45,1);

        bditemscovid.close();
    }

    @Test
    public void consegueLerItemsCovid() {
        Context appContext = getTargetContext();

        BdItemsOpenHelper openHelper = new BdItemsOpenHelper(appContext);
        SQLiteDatabase bditemcovid = openHelper.getWritableDatabase();

        BdTableCovid tabelaitemscovid = new BdTableCovid(bditemcovid);

        Cursor cursor = tabelaitemscovid.query(BdTableCovid.TODOS_CAMPOS, null, null, null, null, null);
        int registos = cursor.getCount();
        cursor.close();

        insereitemcovid(bditemcovid, 45,1);

        cursor = tabelaitemscovid.query(BdTableCovid.TODOS_CAMPOS, null, null, null, null, null);
        assertEquals(registos + 1, cursor.getCount());
        cursor.close();

        bditemcovid.close();
    }

    @Test
    public void consegueAlteraritemsCovid() {
        Context appContext = getTargetContext();

        BdItemsOpenHelper openHelper = new BdItemsOpenHelper(appContext);
        SQLiteDatabase bditemcovid = openHelper.getWritableDatabase();

        long iditem = insereitemcovid(bditemcovid, 45,1);

        BdTableCovid tabelaitems = new BdTableCovid(bditemcovid);

        Cursor cursor = tabelaitems.query(BdTableCovid.TODOS_CAMPOS, BdTableCovid.CAMPO_ID_COMPLETO + "=?", new String[]{ String.valueOf(iditem) }, null, null, null);
        assertEquals(1, cursor.getCount());

        assertTrue(cursor.moveToNext());
        Covid item = Converte.cursorToCovid(cursor);
        cursor.close();

        assertEquals("45", String.valueOf(item.getProgresso()));

        item.setProgresso(45);
        int registosAfetados = tabelaitems.update(Converte.CovidToContentValues(item), BdTableCovid._ID + "=?", new String[]{String.valueOf(item.getId())});
        assertEquals(1, registosAfetados);

        bditemcovid.close();
    }

    @Test
    public void consegueEliminaritemsCovid() {
        Context appContext = getTargetContext();

        BdItemsOpenHelper openHelper = new BdItemsOpenHelper(appContext);
        SQLiteDatabase bditemcovid = openHelper.getWritableDatabase();

        long id = insereitemcovid(bditemcovid, 45,1);

        BdTableCovid tabelaitems = new BdTableCovid(bditemcovid);
        int registosEliminados = tabelaitems.delete(BdTableCovid._ID + "=?", new String[]{String.valueOf(id)});
        assertEquals(1, registosEliminados);

        bditemcovid.close();
    }

    @Test
    public void consegueInserirItems() {
        Context appContext = getTargetContext();

        BdItemsOpenHelper openHelper = new BdItemsOpenHelper(appContext);
        SQLiteDatabase bditems = openHelper.getWritableDatabase();

        insereitem(bditems, "O Intruso","2015/05/22", "Terror");

        bditems.close();
    }

    @Test
    public void consegueLerItems() {
        Context appContext = getTargetContext();

        BdItemsOpenHelper openHelper = new BdItemsOpenHelper(appContext);
        SQLiteDatabase bditems = openHelper.getWritableDatabase();

        BdTableItems tabelaitems = new BdTableItems(bditems);

        Cursor cursor = tabelaitems.query(BdTableItems.TODOS_CAMPOS, null, null, null, null, null);
        int registos = cursor.getCount();
        cursor.close();

        insereitem(bditems, "Rocking","2015/05/22", "Rock");

        cursor = tabelaitems.query(BdTableItems.TODOS_CAMPOS, null, null, null, null, null);
        assertEquals(registos + 1, cursor.getCount());
        cursor.close();

        bditems.close();
    }

    @Test
    public void     consegueAlteraritems() {
        Context appContext = getTargetContext();

        BdItemsOpenHelper openHelper = new BdItemsOpenHelper(appContext);
        SQLiteDatabase bditems = openHelper.getWritableDatabase();

        long iditem = insereitem(bditems, "shootin","2015/05/22", "Guns");

        BdTableItems tabelaitems = new BdTableItems(bditems);

        Cursor cursor = tabelaitems.query(BdTableItems.TODOS_CAMPOS, BdTableItems.CAMPO_ID_COMPLETO + "=?", new String[]{ String.valueOf(iditem) }, null, null, null);
        assertEquals(1, cursor.getCount());

        assertTrue(cursor.moveToNext());
        Item item = Converte.cursorToItem(cursor);
        cursor.close();

        assertEquals("shootin", item.getConteudo());

        item.setConteudo("Shooting");
        int registosAfetados = tabelaitems.update(Converte.itemToContentValues(item), BdTableItems._ID + "=?", new String[]{String.valueOf(item.getId())});
        assertEquals(1, registosAfetados);

        bditems.close();
    }

    @Test
    public void consegueEliminaritems() {
        Context appContext = getTargetContext();

        BdItemsOpenHelper openHelper = new BdItemsOpenHelper(appContext);
        SQLiteDatabase bditems = openHelper.getWritableDatabase();

        long id = insereitem(bditems, "shootin","2015/05/22", "Guns");

        BdTableItems tabelaitems = new BdTableItems(bditems);
        int registosEliminados = tabelaitems.delete(BdTableItems._ID + "=?", new String[]{String.valueOf(id)});
        assertEquals(1, registosEliminados);

        bditems.close();
    }
}
