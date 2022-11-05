package com.example.cloverchatapp.fragment.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.R;
import com.example.cloverchatapp.component.button.SignInButton;
import com.example.cloverchatapp.fragment.FragmentEnum;

public class SignInFragment extends Fragment {

    MainActivity activity;
    ViewGroup rootView;

    EditText editId;
    EditText editPassword;

    SignInButton signInButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_sign_in, container, false);

        initEditTexts();
        setNavBtnListeners();

        signInButton = new SignInButton(activity, rootView, editId, editPassword);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (rootView != null) {
            clearInputs();
        }
    }

    private void setNavBtnListeners() {
        Button signInToSignUpBtn = rootView.findViewById(R.id.signInToSignUpBtn);
        signInToSignUpBtn.setOnClickListener((View v) -> {
            activity.navigator.navigate(FragmentEnum.SIGN_UP);
        });
    }

    private void initEditTexts() {
        editId = rootView.findViewById(R.id.signInId);
        editPassword = rootView.findViewById(R.id.signInPassword);
    }

    private void clearInputs() {
        editId.setText(null);
        editPassword.setText(null);
    }
}
