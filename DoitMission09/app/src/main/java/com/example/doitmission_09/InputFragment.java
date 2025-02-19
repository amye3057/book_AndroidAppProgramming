package com.example.doitmission_09;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class InputFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View f1 = inflater.inflate(R.layout.fragment_input, container, false);

        EditText text_name = f1.findViewById(R.id.editTextText);
        EditText text_age = f1.findViewById(R.id.editTextText2);

        Button button_birth = f1.findViewById(R.id.button_birth);
        Button button_save = f1.findViewById(R.id.button_save);

        button_birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button_birth.setText("1999.05.10");
            }
        });

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = text_name.getText().toString();
                String age = text_age.getText().toString();
                Toast.makeText(getActivity(), "이름 : "+name+", 나이 : "+age, Toast.LENGTH_LONG).show();
            }
        });

        return f1;
    }
}