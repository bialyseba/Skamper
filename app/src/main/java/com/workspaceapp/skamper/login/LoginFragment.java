package com.workspaceapp.skamper.login;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static com.google.common.base.Preconditions.checkNotNull;

public class LoginFragment extends Fragment implements LoginContract.View {

    private LoginContract.Presenter mPresenter;
    private static int mResource;


    public LoginFragment() {
        // Requires empty public constructor
    }

    public static LoginFragment newInstance(@LayoutRes int resource) {
        mResource = resource;
        return new LoginFragment();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(mResource, container, false);

        return root;
    }
}
