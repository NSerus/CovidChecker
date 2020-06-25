package com.boom.covidchecker;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdaptadorItems extends RecyclerView.Adapter<AdaptadorItems.ViewHolderItem> {
    private final Context context;
    private Cursor cursor = null;

    public void setCursor(Cursor cursor) {
        if (cursor != this.cursor) {
            this.cursor = cursor;
            notifyDataSetChanged();
        }
    }

    public AdaptadorItems(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemIteme = LayoutInflater.from(context).inflate(R.layout.item_only, parent, false);

        return new ViewHolderItem(itemIteme);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderItem holder, int position) {
        cursor.moveToPosition(position);
        Item item = Converte.cursorToItem(cursor);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        if(cursor == null) {
            return 0;
        }

        return cursor.getCount();
    }



    private ViewHolderItem viewHolderItemSelecionado = null;



    public class ViewHolderItem extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Item item = null;

        private TextView TextViewData;
        private TextView TextViewConteudo;
        private TextView textViewCategoria;

        public ViewHolderItem(@NonNull View itemView) {
            super(itemView);

            TextViewData = (TextView) itemView.findViewById(R.id.TextViewData);
            TextViewConteudo = (TextView) itemView.findViewById(R.id.TextViewConteudo);
            textViewCategoria = (TextView)itemView.findViewById(R.id.TextViewCategoria);


            itemView.setOnClickListener(this);
        }




        public void setItem(Item item) {
            this.item = item;

            TextViewData.setText(item.getData());
            TextViewConteudo.setText(item.getConteudo());
            textViewCategoria.setText(String.valueOf(item.getCategoria()));
        }

        @Override
        public void onClick(View v) {




               if (viewHolderItemSelecionado == this) {
                   return;

               }

            if (viewHolderItemSelecionado != null) {
                viewHolderItemSelecionado.desSeleciona();

            }

            viewHolderItemSelecionado = this;
            seleciona();

            MainActivity activity = (MainActivity) AdaptadorItems.this.context;
            activity.itemAlterado(item);
        }

        private void seleciona() {
            itemView.setBackgroundResource(R.color.colorAccent);
        }

        private void desSeleciona() {
            itemView.setBackgroundResource(android.R.color.white);
        }
        }
    }

