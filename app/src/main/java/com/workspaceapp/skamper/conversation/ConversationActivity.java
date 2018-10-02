package com.workspaceapp.skamper.conversation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallListener;
import com.workspaceapp.skamper.R;
import com.workspaceapp.skamper.SkamperApplication;
import com.workspaceapp.skamper.calling.CallingActivity;
import com.workspaceapp.skamper.main.MainActivity;

import java.util.List;

import static com.workspaceapp.skamper.SkamperApplication.call;
import static com.workspaceapp.skamper.SkamperApplication.sinchClient;

public class ConversationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Toolbar toolbar = (Toolbar) findViewById(R.id.conversationToolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ImageButton callButton = toolbar.findViewById(R.id.phoneButton);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callUser();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void callUser(){
        if (call == null) {
            call = sinchClient.getCallClient().callUser("call-recipient-id");
        } else {
            call.hangup();

        }
        call.addCallListener(new SinchCallListener());
    }
    class SinchCallListener implements CallListener {
        @Override
        public void onCallEnded(Call endedCall) {
            SkamperApplication.call = null;
            //button.setText("Call");
            Toast toast = Toast.makeText(getApplicationContext()," polaczenie zakonczone", Toast.LENGTH_SHORT);
            toast.show();
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
        @Override
        public void onCallEstablished(Call establishedCall) {
            Toast toast = Toast.makeText(getApplicationContext(),"Nawiazano polaczenie", Toast.LENGTH_SHORT);
            toast.show();
        }
        @Override
        public void onCallProgressing(Call progressingCall) {
            Toast toast = Toast.makeText(getApplicationContext(),"Dryndam", Toast.LENGTH_SHORT);
            toast.show();
            Intent intent = new Intent(getApplicationContext(),CallingActivity.class);
            startActivity(intent);
        }
        @Override
        public void onShouldSendPushNotification(Call call, List<PushPair> pushPairs) {
            //don't worry about this right now
        }
    }
}
