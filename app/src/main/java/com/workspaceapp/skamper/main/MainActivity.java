package com.workspaceapp.skamper.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallListener;
import com.workspaceapp.skamper.R;
import com.workspaceapp.skamper.SkamperApplication;
import com.workspaceapp.skamper.calling.CallingActivity;

import java.util.List;

import static com.workspaceapp.skamper.SkamperApplication.call;
import static com.workspaceapp.skamper.SkamperApplication.sinchClient;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mContactsRecyclerView;
    private RecyclerView.Adapter mContactsAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String username = user.getEmail();
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
        sinchClient = Sinch.getSinchClientBuilder()
                .context(this)
                .userId(user.getEmail())
                .applicationKey("ae90c73b-4fcd-4a40-8a5b-ab8a0adb642a")
                .applicationSecret("0li5kxQXtUyF+D9ruk7MHg==")
                .environmentHost("sandbox.sinch.com")
                .build();
        sinchClient.setSupportCalling(true);
        sinchClient.start();
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


