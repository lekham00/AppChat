package com.example.lekham.appchat.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lekham.appchat.R;
import com.example.lekham.appchat.activity.MainActivity;
import com.example.lekham.appchat.activity.RegisterActivity;
import com.example.lekham.appchat.core.login.LoginContract;
import com.example.lekham.appchat.core.login.LoginPresenter;
import com.example.lekham.appchat.utils.Toaster;

/**
 */
public class LoginFragment extends Fragment implements View.OnClickListener, LoginContract.View {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    Button btnLogin, btnMoveRegister;
    View view;
    LoginPresenter mLoginPresenter;
    private EditText editTextEmail, editTextPassword;
    ProgressDialog mProgressDialog;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);
        // Inflate the layout for this fragment
        init();
        return view;
    }

    private void init() {
        editTextEmail = (EditText) view.findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) view.findViewById(R.id.editTxtPassword);
        btnMoveRegister = (Button) view.findViewById(R.id.btnMoveRegister);
        btnMoveRegister.setOnClickListener(this);
        btnLogin = (Button) view.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        mLoginPresenter = new LoginPresenter(this);
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setTitle(getString(R.string.loading));
        mProgressDialog.setMessage(getString(R.string.please_wait));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnMoveRegister:
                RegisterActivity.startIntent(getActivity());
                break;
            case R.id.btnLogin:
                handelLogin();
                break;
        }
    }

    private void handelLogin() {

        String email = editTextEmail.getText().toString();
        String pass = editTextPassword.getText().toString();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {
            Toaster.error(getActivity(), getString(R.string.error_login), Toast.LENGTH_SHORT).show();
            return;
        }
        if (mLoginPresenter != null) {
            mProgressDialog.show();
            mLoginPresenter.login(getActivity(), editTextEmail.getText().toString(), editTextPassword.getText().toString());
        }
    }

    @Override
    public void onLoginSuccess(String message) {
        mProgressDialog.dismiss();
       // Toaster.error(getActivity(), message.toString(), Toast.LENGTH_SHORT).show();
        MainActivity.startIntent(getActivity(), Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    @Override
    public void onLoginFailure(String message) {
        mProgressDialog.dismiss();
        Toaster.success(getActivity(), message.toString(), Toast.LENGTH_SHORT).show();
    }
}
