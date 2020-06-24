package com.boom.covidchecker;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class ItemUpdateFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    public static final int ID_CURSOR_LOADER_CATEGORIAS = 0;

    private EditText editTextConteudo;
    private Spinner spinnerCategoria;
    private Item item;
    private boolean categoriasCarregadas = false;
    private boolean categoriaAtualizada = false;

    public ItemUpdateFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item_update, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = getContext();

        MainActivity activity = (MainActivity) getActivity();
        activity.setFragmentActual(this);

        activity.setMenuActual(R.menu.menu_alterar_item);

        editTextConteudo = (EditText) view.findViewById(R.id.editTextTitulo);
        spinnerCategoria = (Spinner) view.findViewById(R.id.spinnerCategoria);

        mostraDadosSpinnerCategorias(null);

        item = activity.getItem();
        editTextConteudo.setText(item.getConteudo());

        LoaderManager.getInstance(this).initLoader(ID_CURSOR_LOADER_CATEGORIAS, null, this);

        actualizaCategoriaSelecionada();
    }

    private void actualizaCategoriaSelecionada() {
        if (!categoriasCarregadas) return;
        if (categoriaAtualizada) return;

        long idCategoria = item.getIdCategoria();

        for (int i= 0; i < spinnerCategoria.getCount(); i++) {
            if (spinnerCategoria.getItemIdAtPosition(i) == idCategoria) {
                spinnerCategoria.setSelection(i, true);
                break;
            }
        }

        categoriaAtualizada = true;
    }

    public void guardar() {
        String titulo = editTextConteudo.getText().toString();

        if (titulo.length() == 0) {
            editTextConteudo.setError("Preencha o título");
            editTextConteudo.requestFocus();
            return;
        }

        long idCategoria = spinnerCategoria.getSelectedItemId();

        MainActivity activity = (MainActivity) getActivity();

        Item item = activity.getItem();

        item.setConteudo(titulo);
        item.setIdCategoria(idCategoria);

        try {
            Uri enderecoItem = Uri.withAppendedPath(ItemsContentProvider.ENDERECO_ITEMS, String.valueOf(item.getId()));

            int registos = getActivity().getContentResolver().update(enderecoItem, Converte.itemToContentValues(item), null, null);

            if (registos == 1) {
                Toast.makeText(getContext(), "Item guardado com sucesso", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (Exception e) {
        }

        Snackbar.make(editTextConteudo, "Erro: Não foi possível alterar o item", Snackbar.LENGTH_INDEFINITE).show();
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(getContext(), ItemsContentProvider.ENDERECO_CATEGORIAS, BdTableCategorias.TODOS_CAMPOS, null, null, BdTableCategorias.CAMPO_DESCRICAO);

    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mostraDadosSpinnerCategorias(data);
        categoriasCarregadas = true;
        actualizaCategoriaSelecionada();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mostraDadosSpinnerCategorias(null);
    }
    private void mostraDadosSpinnerCategorias(Cursor data) {
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                getContext(),
                android.R.layout.simple_list_item_1,
                data,
                new String[]{BdTableCategorias.CAMPO_DESCRICAO},
                new int[]{android.R.id.text1}
        );

        spinnerCategoria.setAdapter(adapter);
    }
}