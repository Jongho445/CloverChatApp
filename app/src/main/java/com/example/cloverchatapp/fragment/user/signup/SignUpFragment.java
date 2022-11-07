package com.example.cloverchatapp.fragment.user.signup;

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
import com.example.cloverchatapp.fragment.FragmentEnum;
import com.example.cloverchatapp.global.GlobalContext;
import com.example.cloverchatapp.web.domain.user.RequestUserCreateForm;
import com.example.cloverchatapp.web.http.user.UserHttpClient;

public class SignUpFragment extends Fragment {

    private MainActivity activity;
    private GlobalContext global;
    private ViewGroup rootView;

    private EditText editId;
    private EditText editPassword;
    private EditText editNickname;

    private UserHttpClient userHttpClient;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.activity = (MainActivity) getActivity();
        this.rootView = (ViewGroup) inflater.inflate(R.layout.fragment_sign_up, container, false);
        this.global = activity.global;
        this.userHttpClient = new UserHttpClient(global.auth);

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
            RequestUserCreateForm requestUserCreateForm = new RequestUserCreateForm(editId.getText().toString(), editPassword.getText().toString(), editNickname.getText().toString());
            userHttpClient.register(requestUserCreateForm, res -> {
                activity.navigator.navigate(FragmentEnum.SIGN_IN);
            });
        });
    }

    private void setNavBtnListeners() {
        Button signUpToSignInBtn = rootView.findViewById(R.id.signUpToSignInBtn);
        signUpToSignInBtn.setOnClickListener((View v) -> {
            activity.navigator.navigate(FragmentEnum.SIGN_IN);
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
