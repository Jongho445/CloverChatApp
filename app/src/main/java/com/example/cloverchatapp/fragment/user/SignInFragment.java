package com.example.cloverchatapp.fragment.user;

import android.app.AlertDialog;
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
import com.example.cloverchatapp.client.AppClient;
import com.example.cloverchatapp.fragment.FragmentEnum;
import com.example.cloverchatapp.web.user.RequestLoginForm;

public class SignInFragment extends Fragment {

    MainActivity activity;
    ViewGroup rootView;

    EditText editId;
    EditText editPassword;

    AppClient httpClient;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        httpClient = new AppClient(activity.authStorage);
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_sign_in, container, false);

        initEditTexts();
        setSignInBtnListener();
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

    private void setSignInBtnListener() {
        Button signInBtn = rootView.findViewById(R.id.signInBtn);
        signInBtn.setOnClickListener(view -> {
            login();
        });
    }

    private void login() {
        RequestLoginForm requestLoginForm = new RequestLoginForm(editId.getText().toString(), editPassword.getText().toString());
        httpClient.login(requestLoginForm, res -> {
            if (!res.isSuccessful()) {
                showAlertDialog("회원 정보가 잘못되었습니다.");
                return;
            }

            activity.authStorage.storeData(res);
            activity.navigate(FragmentEnum.INDEX);
        });
    }

    private void setNavBtnListeners() {
        Button signInToSignUpBtn = rootView.findViewById(R.id.signInToSignUpBtn);
        signInToSignUpBtn.setOnClickListener((View v) -> {
            activity.navigate(FragmentEnum.SIGN_UP);
        });
    }

    private void showAlertDialog(String msg) {
        new AlertDialog.Builder(activity)
                .setTitle("알림")
                .setMessage(msg)
                .setPositiveButton("확인", null)
                .show();
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
