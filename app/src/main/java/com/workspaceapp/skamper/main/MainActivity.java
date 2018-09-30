package com.workspaceapp.skamper.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.calling.Call;
import com.workspaceapp.skamper.R;

public class MainActivity extends AppCompatActivity {
    private Call call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String username = user.getEmail();
        TextView textView = findViewById(R.id.testTextview);
        textView.setText(username);
        Button button = (Button) findViewById(R.id.button);
        SinchClient sinchClient = Sinch.getSinchClientBuilder()
                .context(this)
                .userId(user.getEmail())
                .applicationKey("ae90c73b-4fcd-4a40-8a5b-ab8a0adb642a")
                .applicationSecret("0li5kxQXtUyF+D9ruk7MHg==")
                .environmentHost("sandbox.sinch.com")
                .build();
        sinchClient.setSupportCalling(true);
        sinchClient.start();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sinchClient.getCallClient().callUser("call-recipient-id");
                //TODO dodac nazwe usera koncowego
            }
        });

    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }
}
