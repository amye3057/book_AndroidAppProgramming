package com.example.doitmission_13;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {
    ArrayList<Person> items = new ArrayList<Person>();

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView_name;
        TextView textView_birth;
        TextView textView_mobile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_name = itemView.findViewById(R.id.textView_name);
            textView_birth = itemView.findViewById(R.id.textView_birth);
            textView_mobile = itemView.findViewById(R.id.textView_mobile);
        }

        public void setItem(Person item){
            textView_name.setText(item.getName());
            textView_birth.setText(item.getBirth());
            textView_mobile.setText(item.getMobile());
        }
    }

    @NonNull
    @Override
    // 저번 예제에서는 그냥 .ViewHolder로 되어있는데 이번엔 PersonAdapter.가 추가되어있다. 근데 달라도 큰 상관없는듯
    public PersonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.person_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonAdapter.ViewHolder holder, int position) {
        Person item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }



    // 귀찮아서 복붙한 거

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
