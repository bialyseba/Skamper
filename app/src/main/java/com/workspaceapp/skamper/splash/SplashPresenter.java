package com.workspaceapp.skamper.start;

import android.support.annotation.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;

public class StartPresenter implements StartContract.Presenter{

    private final StartContract.View mStartView;

    public StartPresenter(@NonNull StartContract.View startView) {

        mStartView = checkNotNull(startView, "tasksView cannot be null!");

        mStartView.setPresenter(this);
    }
    @Override
    public void start() {

    }

    private void loadProfile(){

    }
}
