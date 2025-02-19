package com.example.chapter10_6;

import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    ArrayList<MyMovie> items = new ArrayList<MyMovie>();

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView_name, textView_audi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView_name = itemView.findViewById(R.id.textView);
            textView_audi = itemView.findViewById(R.id.textView2);
        }

        public void setItem(MyMovie item){ // Movie 클래스에 있는 속성 사용
            textView_name.setText(item.movieNm); // 영화 이름
            textView_audi.setText(item.audiCnt+"명 "); // 관객 수
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.movie_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyMovie item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(MyMovie item){
        items.add(item);
    }

    public void setItems(ArrayList<MyMovie> items){
        this.items = items;
    }

    public MyMovie getItem(int position){
        return items.get(position);
    }

}
