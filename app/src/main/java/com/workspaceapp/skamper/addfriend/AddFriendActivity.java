package com.workspaceapp.skamper.addfriend;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.workspaceapp.skamper.R;
import com.workspaceapp.skamper.data.AppDataManager;
import com.workspaceapp.skamper.data.model.Contact;
import com.workspaceapp.skamper.main.ContactsAdapter;
import com.workspaceapp.skamper.main.MainActivity;

import java.util.ArrayList;

public class AddFriendActivity extends AppCompatActivity {

    private RecyclerView mNewContactsRecyclerView;
    private RecyclerView.Adapter mNewContactsAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button searchButton;
    private EditText searchEditText;
    private TextView foundTextView;
    private TextView notfoundTextView;

    private FirebaseDatabase mDatabase;
    private ArrayList<Contact> mNewContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mDatabase = FirebaseDatabase.getInstance();

        mNewContactsRecyclerView = (RecyclerView) findViewById(R.id.addFriendsRecyclerView);
        mNewContactsRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mNewContactsRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<Contact> mContacts = new ArrayList<>();
        mContacts.add(new Contact("ssfs@wp.pl", "user jakis"));
        mContacts.add(new Contact("ssdfdsf@eep.pl", "user dwa"));
        mContacts.add(new Contact("ssdfdsf@eep.pl", "user trzy"));

        //mNewContactsAdapter = new NewContactsAdapter(this, mContacts);
        //mNewContactsRecyclerView.setAdapter(mNewContactsAdapter);

        searchButton = findViewById(R.id.addUserSearchButton);
        searchEditText = findViewById(R.id.adduserEditText);
        foundTextView = findViewById(R.id.foundUsersLabel);
        notfoundTextView = findViewById(R.id.notFoundInfoTextView);
        setSearchButtonActive(false);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() != 0){
                    if(!searchButton.isEnabled()){
                        setSearchButtonActive(true);
                    }
                }else{
                    setSearchButtonActive(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickSearchButton(searchEditText.getText().toString());
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void setSearchButtonActive(boolean isActive){
        if(isActive){
            searchButton.setBackgroundResource(R.drawable.button_rounded);
            searchButton.setEnabled(true);
        }else{
            searchButton.setBackgroundResource(R.drawable.button_rounded_inactive);
            searchButton.setEnabled(false);
        }
    }

    private void clickSearchButton(String string){
        mDatabase.getReference().child("Users").orderByChild("username").startAt(string).endAt(string + "\uf8ff").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mNewContacts = new ArrayList<>();
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    Log.d("AddFriendActivity", d.getKey());
                    Contact contact = new Contact();
                    for(DataSnapshot ds : d.getChildren()){
                        Log.d("AddFriendActivity", ds.getKey());
                        if(ds.getKey().equals("email")){
                            contact.setEmail((String) ds.getValue());
                        }
                        if(ds.getKey().equals("username")){
                            contact.setUsername((String) ds.getValue());
                        }
                    }
                    if(!contact.getEmail().equals(AppDataManager.getInstance().getCurrentUser().getEmail())){
                        mNewContacts.add(contact);
                    }
                }
                if(mNewContacts.size() > 0){
                    showFoundUsers();
                    mNewContactsAdapter = new NewContactsAdapter(AddFriendActivity.this, mNewContacts);
                    mNewContactsRecyclerView.setAdapter(mNewContactsAdapter);
                }else{
                    showNotFoundUsers();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void showFoundUsers(){
        notfoundTextView.setVisibility(View.GONE);
        foundTextView.setVisibility(View.VISIBLE);
        mNewContactsRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showNotFoundUsers(){
        foundTextView.setVisibility(View.GONE);
        mNewContactsRecyclerView.setVisibility(View.GONE);
        notfoundTextView.setVisibility(View.VISIBLE);
    }
}
