package com.example.chapter7_4_2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> implements OnPersonItemClickListener {
    ArrayList<Person> items = new ArrayList<Person>();
    OnPersonItemClickListener listener;

    @NonNull
    @Override
    // 자동호출 (뷰홀더 객체가 만들어질 때)
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        // 각 아이템을 위해 정의한 XML 레이아웃을 이용해 뷰 객체를 만들어준다.
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.person_item, viewGroup, false);

        return new ViewHolder(itemView, this); // 뷰 객체를 뷰홀더 객체에 담아 반환한다.
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

    // 뷰홀더 클래스
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView_name;
        TextView textView_mobile;

        public ViewHolder(@NonNull View itemView, final OnPersonItemClickListener listener) {
            super(itemView);

            textView_name = itemView.findViewById(R.id.textView);
            textView_mobile = itemView.findViewById(R.id.textView2);

            // 아이템뷰에 OnClickListener 설정하기
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition(); // 아이템이 어댑터에서 몇 번째인지 인덱스 정보를 반환
                    if(listener!=null) // 아이템뷰 클릭 시 미리 정의한 다른 리스너의 메서드 호출하기
                        listener.onItemClick(ViewHolder.this, view, position);
                }
            });
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

    public void setOnItemClickListener(OnPersonItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if(listener!=null){
            listener.onItemClick(holder, view, position);
        }
    }
}
