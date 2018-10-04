package com.workspaceapp.skamper.calling;

import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallListener;
import com.workspaceapp.skamper.R;
import com.workspaceapp.skamper.SkamperApplication;
import com.workspaceapp.skamper.main.MainActivity;

import java.util.List;

public class CallingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calling);

        Button buttonHangUp = findViewById(R.id.buttonHangUp);
        Button buttonAnswer = findViewById(R.id.buttonAnswer);
        SkamperApplication.call.addCallListener(new SinchCallListener());

        buttonHangUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SkamperApplication.call.hangup();
            }
        });
buttonAnswer.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        SkamperApplication.call.answer();

    }
});
    }
    class SinchCallListener implements CallListener {
        @Override
        public void onCallEnded(Call endedCall) {
            SkamperApplication.call = null;
            //button.setText("Call");
            Toast toast = Toast.makeText(getApplicationContext()," polaczenie zakonczone", Toast.LENGTH_SHORT);
            toast.show();
            setVolumeControlStream(AudioManager.USE_DEFAULT_STREAM_TYPE);
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
        @Override
        public void onCallEstablished(Call establishedCall) {
            Toast toast = Toast.makeText(getApplicationContext(),"Nawiazano polaczenie", Toast.LENGTH_SHORT);
            toast.show();
            setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
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
