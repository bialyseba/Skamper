package com.workspaceapp.skamper.main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.workspaceapp.skamper.R;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mContactsRecyclerView;
    private RecyclerView.Adapter mContactsAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //String username = user.getEmail();
        //TextView textView = findViewById(R.id.testTextview);
        //textView.setText(username);

        mContactsRecyclerView = (RecyclerView) findViewById(R.id.contactsRecyclerView);
        mContactsRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mContactsRecyclerView.setLayoutManager(mLayoutManager);

        String[] contactsDataSet = {"Drzyzga", "Jurek", "Kadziu", "Kubi", "Kurek" ,"Lepa", "Nowakowski", "Zatorski" };
        mContactsAdapter = new ContactsAdapter(this, contactsDataSet );
        mContactsRecyclerView.setAdapter(mContactsAdapter);
    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }
}
