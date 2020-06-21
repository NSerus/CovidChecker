package com.boom.covidchecker;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;


public class ItemAddFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    public static final int ID_CURSOR_LOADER_CATEGORIAS = 0;
    private EditText editTextConteudo;
    private Spinner spinnerCategoria;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MainActivity activity = (MainActivity) getActivity();
        activity.setFragmentActual(this);
        activity.setMenuActual(R.menu.menu_add_item);

        LoaderManager.getInstance(this).initLoader(ID_CURSOR_LOADER_CATEGORIAS, null, this);

        return inflater.inflate(R.layout.fragment_item_add, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = getContext();

        MainActivity activity = (MainActivity) getActivity();
        activity.setFragmentActual(this);
        activity.setMenuActual(R.menu.menu_add_item);

        editTextConteudo = (EditText) view.findViewById(R.id.editTextTitulo);
        spinnerCategoria = (Spinner) view.findViewById(R.id.spinnerCategoria);

        mostraDadosSpinnerCategorias(null);

        LoaderManager.getInstance(this).initLoader(ID_CURSOR_LOADER_CATEGORIAS, null, this);
    }

    public void guardar() {
        String conteudo = editTextConteudo.getText().toString();

        if (conteudo.length() == 0) {
            editTextConteudo.setError("Preencha o item");
            editTextConteudo.requestFocus();
            return;
        }

        long idCategoria = spinnerCategoria.getSelectedItemId();

        Item item = new Item();
        item.setConteudo(conteudo);
        item.setIdCategoria(idCategoria);

        try {
            getActivity().getContentResolver().insert(ItemsContentProvider.ENDERECO_ITEMS, Converte.itemToContentValues(item));
            Toast.makeText(getContext(), "Item adicionado com sucesso", Toast.LENGTH_SHORT).show();
            NavController navController = NavHostFragment.findNavController(ItemAddFragment.this);
            navController.navigate(R.id.action_itemAddFragment_to_checklistFragment2);
        } catch (Exception e) {
            Snackbar.make(editTextConteudo, "Erro: Não foi possível criar o item", Snackbar.LENGTH_INDEFINITE).show();
        }
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(getContext(), ItemsContentProvider.ENDERECO_CATEGORIAS, BdTableCategorias.TODOS_CAMPOS, null, null, BdTableCategorias.CAMPO_DESCRICAO);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mostraDadosSpinnerCategorias(data);
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