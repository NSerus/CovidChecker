package com.boom.covidchecker;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

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
        View itemItem = LayoutInflater.from(context).inflate(R.layout.item_only, parent, false);

        return new ViewHolderItem(itemItem);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolderItem holder, int position) {
        cursor.moveToPosition(position);
        Item item = Converte.cursorToItem(cursor);
        holder.setItem(item);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        if(cursor == null) {
            return 0;
        }

        return cursor.getCount();
    }

    public class ViewHolderItem extends RecyclerView.ViewHolder {
        private Item item = null;

        private TextView editTextConteudo;
        private TextView textViewCategoria;

        public ViewHolderItem(@NonNull View itemView) {
            super(itemView);

            editTextConteudo = (TextView)itemView.findViewById(R.id.editTextConteudo);
            textViewCategoria = (TextView)itemView.findViewById(R.id.TextViewCategoria);
        }

        public void setItem(Item item) {
            this.item = item;

            editTextConteudo.setText(item.getConteudo());
            textViewCategoria.setText(String.valueOf(item.getCategoria()));
        }
    }
}
