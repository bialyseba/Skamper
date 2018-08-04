package com.workspaceapp.skamper.start;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.workspaceapp.skamper.R;

import static com.google.common.base.Preconditions.checkNotNull;

public class StartFragment extends Fragment implements StartContract.View {

    private StartContract.Presenter mPresenter;


    public StartFragment() {
        // Requires empty public constructor
    }

    public static StartFragment newInstance() {
        return new StartFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_start, container, false);

        return root;
    }

    @Override
    public void setPresenter(StartContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
}
