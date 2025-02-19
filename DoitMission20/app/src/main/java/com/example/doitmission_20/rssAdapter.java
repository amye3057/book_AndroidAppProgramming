package com.example.doitmission_20;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class rssAdapter extends RecyclerView.Adapter<rssAdapter.ViewHolder> {
    ArrayList<RSS> items = new ArrayList<RSS>();

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView_title, textView_descrption;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_title = itemView.findViewById(R.id.textView2);
            textView_descrption = itemView.findViewById(R.id.textView3);
        }

        public void setItem(RSS item){
            textView_title.setText(item.title);
            textView_descrption.setText(item.description);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.rss_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull rssAdapter.ViewHolder holder, int position) {
        RSS item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    void addItem(RSS item){
        items.add(item);
    }

    public void setItems(ArrayList<RSS> items){
        this.items = items;
    }

    public RSS getItem(int position){
        return items.get(position);
    }
}
