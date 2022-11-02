package com.example.cloverchatapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.R;

public class TestFragment extends Fragment {

    MainActivity activity;
    TextView textView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_test, container, false);

        textView = rootView.findViewById(R.id.testResult);

        Button testToIndexBtn = rootView.findViewById(R.id.testToIndexBtn);
        testToIndexBtn.setOnClickListener((View v) -> {
            activity.navigate(FragmentEnum.INDEX);
        });

        Button RetTestBtn = rootView.findViewById(R.id.RetTestBtn);
        RetTestBtn.setOnClickListener((View v) -> {

        });

        return rootView;
    }

}
