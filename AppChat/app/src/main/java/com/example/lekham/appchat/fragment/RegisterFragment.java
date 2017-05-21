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
import com.example.lekham.appchat.activity.LoginActivity;
import com.example.lekham.appchat.core.register.RegisterContract;
import com.example.lekham.appchat.core.register.RegisterPresenter;
import com.example.lekham.appchat.core.users.add.AddUsersContract;
import com.example.lekham.appchat.core.users.add.AddUsersPresenter;
import com.example.lekham.appchat.utils.Toaster;
import com.google.firebase.auth.FirebaseUser;

public class RegisterFragment extends Fragment implements View.OnClickListener, RegisterContract.View, AddUsersContract.View {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private EditText editTextEmail, editTextPassword;
    private Button btnRegister;
    private View view;
    private ProgressDialog mProgressDialog;
    private RegisterPresenter mRegisterPresenter;
    private AddUsersPresenter mAddUsersPresenter;

    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
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
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_register, container, false);
        init();
        return view;
    }

    private void init() {
        editTextEmail = (EditText) view.findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) view.findViewById(R.id.editTxtPassword);
        btnRegister = (Button) view.findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setTitle(getString(R.string.loading));
        mProgressDialog.setMessage(getString(R.string.please_wait));
        mProgressDialog.setIndeterminate(true);
        mRegisterPresenter = new RegisterPresenter(this);
        mAddUsersPresenter = new AddUsersPresenter(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRegister:
                handelRegister();
                break;
        }
    }

    @Override
    public void onRegistrationSuccess(FirebaseUser firebaseUser) {
        mProgressDialog.setMessage(getString(R.string.adding_user_to_db));
        Toaster.success(getActivity(), "Register Success", Toast.LENGTH_SHORT).show();
        mAddUsersPresenter.addUser(getActivity(), firebaseUser);
    }

    @Override
    public void onRegistrationFailure(String message) {
        mProgressDialog.dismiss();
        Toaster.error(getActivity(),message.toString(), Toast.LENGTH_LONG).show();
    }
    private void handelRegister() {

        String email = editTextEmail.getText().toString();
        String pass = editTextPassword.getText().toString();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {
            Toaster.error(getActivity(), getString(R.string.error_login), Toast.LENGTH_SHORT).show();
            return;
        }
        if (mRegisterPresenter != null) {
            mProgressDialog.show();
            mRegisterPresenter.register(getActivity(), editTextEmail.getText().toString(), editTextPassword.getText().toString());

        }
    }
    @Override
    public void onAddUserSuccess(String message) {
        mProgressDialog.dismiss();
        Toaster.success(getActivity(), message.toString(), Toast.LENGTH_LONG).show();
        LoginActivity.startIntent(getActivity(), Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    @Override
    public void onAddUserFailure(String message) {
        mProgressDialog.dismiss();
        Toaster.error(getActivity(), message.toString(), Toast.LENGTH_LONG).show();
    }
}
