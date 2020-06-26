package com.boom.covidchecker;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.NativeActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;


public class EliminateFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private TextView textViewTitulo;
    private TextView textViewCategoria;
    private Item item;






    @Override
    public void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MainActivity activity = (MainActivity) getActivity();
        activity.setFragmentActual(this);


        return inflater.inflate(R.layout.fragment_eliminate, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = getContext();

        MainActivity activity = (MainActivity) getActivity();
        activity.setFragmentActual(this);

        activity.setMenuActual(R.menu.menu_none);

        textViewTitulo = (TextView) view.findViewById(R.id.textViewTitulo);
        textViewCategoria = (TextView) view.findViewById(R.id.textViewCategoria);

        item = activity.getItem();
        textViewTitulo.setText(item.getConteudo());
        textViewCategoria.setText(item.getCategoria());

        Button buttonEliminar = (Button) view.findViewById(R.id.buttonEliminar);
        buttonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminar();
            }
        });


    }


    public void eliminar() {
        try {
            Uri enderecoLivro = Uri.withAppendedPath(ItemsContentProvider.ENDERECO_ITEMS, String.valueOf(item.getId()));

            int apagados = getActivity().getContentResolver().delete(enderecoLivro, null, null);

            if (apagados == 1) {
                Toast.makeText(getContext(), "Item eliminado com sucesso", Toast.LENGTH_SHORT).show();

                return;
            }
        } catch (Exception e) {
        }

        Snackbar.make(textViewTitulo, "Erro: Não foi possível eliminar o livro", Snackbar.LENGTH_INDEFINITE).show();


    }



    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}