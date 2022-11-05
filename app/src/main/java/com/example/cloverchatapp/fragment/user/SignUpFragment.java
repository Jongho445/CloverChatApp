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
import com.example.cloverchatapp.web.client.HttpClient;
import com.example.cloverchatapp.fragment.FragmentEnum;
import com.example.cloverchatapp.web.domain.user.UserCreateForm;

public class SignUpFragment extends Fragment {

    MainActivity activity;
    ViewGroup rootView;

    EditText editId;
    EditText editPassword;
    EditText editNickname;

    HttpClient httpClient;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_sign_up, container, false);
        httpClient = new HttpClient(activity.authStorage);

        initEditTexts();
        setSignUpBtnListener();
        setNavBtnListeners();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (rootView != null) {
            clearInputs();
        }
    }

    private void setSignUpBtnListener() {
        Button signUpBtn = rootView.findViewById(R.id.signUpBtn);
        signUpBtn.setOnClickListener(view -> {
            UserCreateForm userCreateForm = new UserCreateForm(editId.getText().toString(), editPassword.getText().toString(), editNickname.getText().toString());
            httpClient.register(userCreateForm, res -> {
                activity.navigate(FragmentEnum.SIGN_IN);
            });
        });
    }

    private void setNavBtnListeners() {
        Button signUpToSignInBtn = rootView.findViewById(R.id.signUpToSignInBtn);
        signUpToSignInBtn.setOnClickListener((View v) -> {
            activity.navigate(FragmentEnum.SIGN_IN);
        });
    }

    private void initEditTexts() {
        editId = rootView.findViewById(R.id.signUpId);
        editPassword = rootView.findViewById(R.id.signUpPassword);
        editNickname = rootView.findViewById(R.id.signUpNickname);
    }

    private void clearInputs() {
        editId.setText(null);
        editPassword.setText(null);
    }
}
