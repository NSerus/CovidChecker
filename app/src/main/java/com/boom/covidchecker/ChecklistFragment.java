package com.boom.covidchecker;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ChecklistFragment extends Fragment  implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final int ID_CURSOR_LOADER_ITEMS = 0;

    private AdaptadorItems adaptadorItems;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_checklist, container, false);
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = getContext();

        RecyclerView recyclerViewItems = (RecyclerView) view.findViewById(R.id.RecyclerViewItems);
        adaptadorItems = new AdaptadorItems(context);
        recyclerViewItems.setAdapter(adaptadorItems);
        recyclerViewItems.setLayoutManager(new LinearLayoutManager(context));

        adaptadorItems.setCursor(null);

        LoaderManager.getInstance(this).initLoader(ID_CURSOR_LOADER_ITEMS, null, this);
    }

    public void alteraItem() {
        NavController navController = NavHostFragment.findNavController(ChecklistFragment.this);
        navController.navigate(R.id.action_update_item);
    }

    public void novoItem() {
        NavController navController = NavHostFragment.findNavController(ChecklistFragment.this);
        navController.navigate(R.id.action_add_item);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {

        return new CursorLoader(getContext(), ItemsContentProvider.ENDERECO_ITEMS, BdTableItems.TODOS_CAMPOS, null, null, BdTableItems.CAMPO_CONTEUDO);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        adaptadorItems.setCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        adaptadorItems.setCursor(null);
    }
}
