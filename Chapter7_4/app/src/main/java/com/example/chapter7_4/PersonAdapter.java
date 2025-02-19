package com.example.chapter7_4;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {
    ArrayList<Person> items = new ArrayList<Person>();

    @NonNull
    @Override
    // 자동호출 (뷰홀더 객체가 만들어질 때)
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        // 각 아이템을 위해 정의한 XML 레이아웃을 이용해 뷰 객체를 만들어준다.
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.person_item, viewGroup, false);

        return new ViewHolder(itemView); // 뷰 객체를 뷰홀더 객체에 담아 반환한다.
    }

    @Override
    // 자동호출 (뷰홀더 객체가 재사용될 때)
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        // 기존의 뷰 객체를 사용하고 데이터만 바꿔준다.
        Person item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() { // 아이템의 개수를 반환
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView_name;
        TextView textView_mobile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView_name = itemView.findViewById(R.id.textView);
            textView_mobile = itemView.findViewById(R.id.textView2);
        }

        public void setItem(Person item){
            textView_name.setText(item.getName());
            textView_mobile.setText(item.getMobile());
        }
    }

    public void addItem(Person item){
        items.add(item);
    }
    public void setItems(ArrayList<Person> items){
        this.items = items;
    }
    public Person getItem(int position){
        return items.get(position);
    }
    public void setItem(int position, Person item){
        items.set(position, item);
    }

}
