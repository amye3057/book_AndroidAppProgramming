package com.example.doitmission_21;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    ArrayList<BookItem> items = new ArrayList<BookItem>();

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView_title, textView_writer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_title = itemView.findViewById(R.id.textView3);
            textView_writer = itemView.findViewById(R.id.textView4);
        }

        public void setItem(BookItem item){
            textView_title.setText(item.book_title);
            textView_writer.setText(item.book_wirter);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.book_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookItem item = getItem(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(BookItem item){
        items.add(item);
    }

    public void setItems(){
        this.items = items;
    }

    public BookItem getItem(int position){
        return items.get(position);
    }
}
