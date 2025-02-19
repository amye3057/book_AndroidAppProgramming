package com.example.chapter5_2;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ViewerFragment extends Fragment {
    ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_viewer, container, false);

        imageView = rootView.findViewById(R.id.imageView);
        return rootView;
    }

    public void setImage(int resId){
        imageView.setImageResource(resId);
    }
}