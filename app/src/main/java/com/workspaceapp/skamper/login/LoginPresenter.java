package com.workspaceapp.skamper.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.workspaceapp.skamper.R;
import com.workspaceapp.skamper.data.AppDataManager;
import com.workspaceapp.skamper.data.DataManager;
import com.workspaceapp.skamper.utils.HashHelper;

import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.concurrent.Executor;

import static com.google.common.base.Preconditions.checkNotNull;

public class LoginPresenter implements LoginContract.Presenter {

    private final LoginContract.View mLoginView;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    public static final int RC_SIGN_IN = 9001;


    public LoginPresenter(@NonNull LoginContract.View loginView) {

        mLoginView = checkNotNull(loginView, "tasksView cannot be null!");

        mLoginView.setPresenter(this);
    }

    @Override
    public void start(Context context) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void registerUser(String username, String email, String password) {
        try {
            AppDataManager.getInstance().createUserWithEmailAndPassword(email, HashHelper.hashString(password), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        AppDataManager.getInstance().addUserToDb(username, email, "EMAIL");
                        mLoginView.startMainActivity();
                    }else{
                        mLoginView.showDialogBox(Objects.requireNonNull(task.getException()).getMessage());
                    }
                }
            });
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loginUser(String email, String password) {
        try {
            AppDataManager.getInstance().signInWithEmailAndPassword(email, HashHelper.hashString(password), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        mLoginView.startMainActivity();
                    }else{
                        mLoginView.showDialogBox(Objects.requireNonNull(task.getException()).getMessage());
                    }
                }
            });
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void signInGoogle(Activity activity) {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        activity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void firebaseAuthWithGoogle(GoogleSignInAccount acct, Context context) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("LoginPresenter", "signInWithCredential:success");
                            String email = mAuth.getCurrentUser().getEmail();
                            AppDataManager.getInstance().existsEmailOnDb(email, new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            /*AppDataManager.getInstance().setCurrentUserLoggedInMode(context, DataManager.LoggedInMode.LOGGED_IN_MODE_GOOGLE);
                            AppDataManager.getInstance().clearEmail();
                            AppDataManager.getInstance().clearHashedPassword();
                            mLoginView.startMainActivity();*/
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("LoginPresenter", "signInWithCredential:failure", task.getException());
                            //Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }


}
