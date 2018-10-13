package com.workspaceapp.skamper.calling;

import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.sinch.android.rtc.AudioController;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.video.VideoCallListener;
import com.sinch.android.rtc.video.VideoController;
import com.workspaceapp.skamper.R;
import com.workspaceapp.skamper.SkamperApplication;
import com.workspaceapp.skamper.main.MainActivity;

import java.util.List;

public class CallingActivity extends AppCompatActivity {
    VideoController vc;
    RelativeLayout localView;
    LinearLayout viewRemoteVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calling);


        Button buttonHangUp = findViewById(R.id.buttonHangUp);
        Button buttonAnswer = findViewById(R.id.buttonAnswer);
        Button buttonVideo = findViewById(R.id.buttonVideo);
        SkamperApplication.call.addCallListener(new SinchCallListener());

        /*if(SkamperApplication.videoInitiator == true)
        {
            SkamperApplication.call.answer();
            SkamperApplication.videoInitiator = false;
            onVideoTrackAdded();

        }*/

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
buttonVideo.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        //SkamperApplication.videoRecipientId = call.getCallId();
        //SkamperApplication.call.hangup();
        //SkamperApplication.videoInitiator = true;
        //SkamperApplication.call = sinchClient.getCallClient().callUserVideo(videoRecipientId);
        //onVideoTrackAdded();
        //call.resumeVideo();

        String  recipientId = SkamperApplication.call.getRemoteUserId();

        //vc = SkamperApplication.sinchClient.getVideoController();


    }
});
    }
    class SinchCallListener implements VideoCallListener {
        @Override
        public void onCallEnded(Call endedCall) {
            Toast toast = Toast.makeText(getApplicationContext(),"Call ended", Toast.LENGTH_SHORT);
            toast.show();
localView.removeView(vc.getLocalView());
viewRemoteVideo.removeView(vc.getRemoteView());

            setVolumeControlStream(AudioManager.USE_DEFAULT_STREAM_TYPE);
            SkamperApplication.call = null;
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
        @Override
        public void onCallEstablished(Call establishedCall) {
            //call.pauseVideo();
            Toast toast = Toast.makeText(getApplicationContext(),"Call established", Toast.LENGTH_SHORT);
            toast.show();
            setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
            AudioController audioController = SkamperApplication.sinchClient.getAudioController();
            audioController.enableSpeaker();


        }
        @Override
        public void onCallProgressing(Call progressingCall) {
            Toast toast = Toast.makeText(getApplicationContext(),"Call is progressing...", Toast.LENGTH_SHORT);
            toast.show();
            /*Intent intent = new Intent(getApplicationContext(),CallingActivity.class);
            startActivity(intent);*/
        }
        @Override
        public void onShouldSendPushNotification(Call call, List<PushPair> pushPairs) {
            //don't worry about this right now
        }
        public void onVideoTrackAdded(Call call) {

            vc = SkamperApplication.sinchClient.getVideoController();

            localView = (RelativeLayout) findViewById(R.id.localVideo);
            localView.addView(vc.getLocalView());
            viewRemoteVideo = (LinearLayout) findViewById(R.id.remoteVideo);
            viewRemoteVideo.addView(vc.getRemoteView());

            //View myPreview = vc.getLocalView();
            //View remoteView = vc.getRemoteView();
            // Add the views to your view hierarchy
        //...
        }

        @Override
        public void onVideoTrackPaused(Call call) {

        }

        @Override
        public void onVideoTrackResumed(Call call) {
            String  recipientId = SkamperApplication.call.getRemoteUserId();
            /*vc = SkamperApplication.sinchClient.getVideoController();
            LinearLayout viewRemoteVideo = (LinearLayout) findViewById(R.id.remoteVideo);
            viewRemoteVideo.addView(vc.getRemoteView());
            RelativeLayout localView = (RelativeLayout) findViewById(R.id.localVideo);
            localView.addView(vc.getLocalView());*/
        }


    }
    public void onVideoTrackAdded(){
        String  recipientId = SkamperApplication.call.getRemoteUserId();
        //SkamperApplication.call.hangup();

        vc = SkamperApplication.sinchClient.getVideoController();


        SkamperApplication.sinchClient.getAudioController();



    }
}
