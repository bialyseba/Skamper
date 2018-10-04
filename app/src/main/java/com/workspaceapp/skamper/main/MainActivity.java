package com.workspaceapp.skamper.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;

import com.workspaceapp.skamper.R;
import com.workspaceapp.skamper.Service.ConnectionService;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mContactsRecyclerView;
    private RecyclerView.Adapter mContactsAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TextView textView = findViewById(R.id.testTextview);
        //textView.setText(username);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mContactsRecyclerView = (RecyclerView) findViewById(R.id.contactsRecyclerView);
        mContactsRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mContactsRecyclerView.setLayoutManager(mLayoutManager);

        String[] contactsDataSet = {"Drzyzga", "Jurek", "Kadziu", "Kubi", "Kurek" ,"Lepa", "Nowakowski", "Zatorski" };
        mContactsAdapter = new ContactsAdapter(this, contactsDataSet );
        mContactsRecyclerView.setAdapter(mContactsAdapter);

        //Button button = (Button) findViewById(R.id.button);

        startService(new Intent(this, ConnectionService.class));
        //sinchClient.getCallClient().addCallClientListener(new SinchCallClientListener());

        /*button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //sinchClient.getCallClient().callUser("call-recipient-id");
                //TODO dodac nazwe usera koncowego
                if (call == null) {
                    call = sinchClient.getCallClient().callUser("call-recipient-id");
                    button.setText("Hang Up");
                } else {
                    call.hangup();

                }
                call.addCallListener(new SinchCallListener());
            }
        });*/

    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }



}


