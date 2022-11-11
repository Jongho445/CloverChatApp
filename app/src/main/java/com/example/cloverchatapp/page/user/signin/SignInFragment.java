package com.example.cloverchatapp.page.user.signin;

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
import com.example.cloverchatapp.page.FragmentEnum;

public class SignInFragment extends Fragment {

    private MainActivity activity;
    private ViewGroup rootView;

    private EditText editId;
    private EditText editPassword;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        initFields(inflater, container);

        setNavBtnListeners();
        setRemoveBtnListener();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (rootView != null) {
            clearInputs();
        }
    }

    private void initFields(LayoutInflater inflater, ViewGroup container) {
        this.activity = (MainActivity) getActivity();
        this.rootView = (ViewGroup) inflater.inflate(R.layout.fragment_sign_in, container, false);

        this.editId = rootView.findViewById(R.id.signInId);
        this.editPassword = rootView.findViewById(R.id.signInPassword);
    }

    private void clearInputs() {
        editId.setText(null);
        editPassword.setText(null);
    }

    private void setNavBtnListeners() {
        Button signInToSignUpBtn = rootView.findViewById(R.id.signInToSignUpBtn);
        signInToSignUpBtn.setOnClickListener((View v) -> {
            activity.navigator.navigate(FragmentEnum.SIGN_UP);
        });
    }

    private void setRemoveBtnListener() {
        Button signIpButton = rootView.findViewById(R.id.signInBtn);

        signIpButton.setOnClickListener(new SignInButtonOnClickListener(activity, editId, editPassword));
    }
}
