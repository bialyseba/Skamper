package com.workspaceapp.skamper.main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.workspaceapp.skamper.R;
import com.workspaceapp.skamper.Service.ConnectionService;
import com.workspaceapp.skamper.addfriend.AddFriendActivity;
import com.workspaceapp.skamper.data.AppDataManager;
import com.workspaceapp.skamper.data.DataManager;
import com.workspaceapp.skamper.data.model.Contact;
import com.workspaceapp.skamper.login.LoginActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mContactsRecyclerView;
    private RecyclerView.Adapter mContactsAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton mFloatingActionButton;

    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private ArrayList<Contact> mContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mFloatingActionButton = findViewById(R.id.fab);
        mFloatingActionButton.setOnClickListener(view -> startActivity(new Intent(this, AddFriendActivity.class)));

        mContactsRecyclerView = (RecyclerView) findViewById(R.id.contactsRecyclerView);
        mContactsRecyclerView.setHasFixedSize(true);
        mContactsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //if (newState == RecyclerView.SCROLL_STATE_IDLE){
                  //  mFloatingActionButton.show();
                //}
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy<0 && !mFloatingActionButton.isShown())
                    mFloatingActionButton.show();
                else if(dy>0 && mFloatingActionButton.isShown())
                    mFloatingActionButton.hide();
            }
        });

        mLayoutManager = new LinearLayoutManager(this);
        mContactsRecyclerView.setLayoutManager(mLayoutManager);

        updateUserInfo();

        ImageButton logoutButton = findViewById(R.id.logoutImageButton);
        logoutButton.setOnClickListener(view -> {
            clickLogout();
        });

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mAuthListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if(user!=null){
                updateUserInfo();
            }
        };
        mAuth.addAuthStateListener(mAuthListener);

        mDatabase.getReference().child("Users").orderByChild("email").equalTo(AppDataManager.getInstance().getCurrentUser().getEmail()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mContacts = new ArrayList<>();
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    Log.d("MainActivity", d.getKey());
                    for(DataSnapshot ds : d.getChildren()){
                        if(ds.getKey().equals("contacts")){
                            for(DataSnapshot dsn : ds.getChildren()){
                                Log.d("MainActivity", dsn.getKey());
                                    String email = (String) dsn.child("email").getValue();
                                    String username = (String) dsn.child("username").getValue();
                                    Contact contact = new Contact(email, username);
                                    mContacts.add(contact);
                            }
                        }
                    }
                }
                mContactsAdapter = new ContactsAdapter(MainActivity.this, mContacts);
                mContactsRecyclerView.setAdapter(mContactsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                showDialogBox(databaseError.getMessage());
            }
        });

        startService(new Intent(this, ConnectionService.class));
    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void updateUserInfo(){
        FirebaseUser user = AppDataManager.getInstance().getCurrentUser();
        if(user != null){
            String username = user.getDisplayName();
            String email = user.getEmail();

            TextView textView = findViewById(R.id.testTextview);
            textView.setText(username);

            TextView emailTextView = findViewById(R.id.emailTextView);
            emailTextView.setText(email);

            if(user.getPhotoUrl() != null){
                ImageView userImageView = findViewById(R.id.userImageView);
                Glide.with(this)
                        .load(user.getPhotoUrl().toString())
                        .apply(RequestOptions.circleCropTransform())
                        .into(userImageView);
            }

        }
    }

    private void clickLogout(){
        AppDataManager.getInstance().setCurrentUserLoggedInMode(this, DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT);
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
    }

    private void showDialogBox(String message){
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("MainActivity", "Sending atomic bombs to Jupiter");
                    }
                })
                .show();
    }
}


