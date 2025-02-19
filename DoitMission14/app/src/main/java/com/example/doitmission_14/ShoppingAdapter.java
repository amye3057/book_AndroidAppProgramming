package com.example.doitmission_14;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ViewHolder>
                             implements OnShoppingItemClickListener{
    ArrayList<ShoppingItem> items = new ArrayList<ShoppingItem>();
    OnShoppingItemClickListener listener;

    public void setOnItemClickListener(OnShoppingItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if(listener!=null)
            listener.onItemClick(holder, view, position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, description;
        ImageView image;
        public ViewHolder(@NonNull View itemView, final OnShoppingItemClickListener listener) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView);
            name = itemView.findViewById(R.id.textView);
            price = itemView.findViewById(R.id.textView2);
            description = itemView.findViewById(R.id.textView3);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener!=null){
                        listener.onItemClick(ViewHolder.this, view, position);
                    }
                }
            });
        }

        public void setItem(ShoppingItem item){
            image.setImageResource(item.getImage());
            name.setText(item.getName());
            price.setText(item.getPrice());
            description.setText(item.getDescrpiton());
        }
    }

    @NonNull
    @Override
    public ShoppingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.shopping_item, parent, false);
        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingAdapter.ViewHolder holder, int position) {
        ShoppingItem item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(ShoppingItem item){
        items.add(item);
    }
    public void setItems(ArrayList<ShoppingItem> items){
        this.items = items;
    }
    public ShoppingItem getItem(int position){
        return items.get(position);
    }
    public void setItem(int position, ShoppingItem item){
        items.set(position, item);
    }

}
