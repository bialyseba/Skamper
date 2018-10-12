package com.workspaceapp.skamper.Service;

import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallClient;
import com.sinch.android.rtc.calling.CallClientListener;
import com.sinch.android.rtc.messaging.Message;
import com.sinch.android.rtc.messaging.MessageClient;
import com.sinch.android.rtc.messaging.MessageClientListener;
import com.sinch.android.rtc.messaging.MessageDeliveryInfo;
import com.sinch.android.rtc.messaging.MessageFailureInfo;
import com.workspaceapp.skamper.SkamperApplication;
import com.workspaceapp.skamper.calling.CallingActivity;
import com.workspaceapp.skamper.data.AppDataManager;

import java.util.List;

import static com.workspaceapp.skamper.SkamperApplication.messageClient;
import static com.workspaceapp.skamper.SkamperApplication.sinchClient;

public class ConnectionService extends android.app.Service {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String username = user.getEmail();
        sinchClient = Sinch.getSinchClientBuilder()
                .context(this)
                .userId(user.getEmail())
                .applicationKey("ae90c73b-4fcd-4a40-8a5b-ab8a0adb642a")
                .applicationSecret("0li5kxQXtUyF+D9ruk7MHg==")
                .environmentHost("sandbox.sinch.com")
                .build();
        sinchClient.setSupportCalling(true);
        sinchClient.setSupportMessaging(true);
        sinchClient.startListeningOnActiveConnection();
        sinchClient.setSupportActiveConnectionInBackground(true);

        sinchClient.start();
        SkamperApplication.sinchClient.getCallClient().addCallClientListener(new SinchCallClientListener());

        SkamperApplication.messageClient = sinchClient.getMessageClient();
        messageClient.addMessageClientListener(new MyMessageClientListener());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    class SinchCallClientListener implements CallClientListener {
        @Override
        public void onIncomingCall(CallClient callClient, Call incomingCall) {
            if (incomingCall.getDetails().isVideoOffered())
            {

                SkamperApplication.videoInitiator = true;
                SkamperApplication.call = incomingCall;



                Toast.makeText(getApplicationContext(), "Za chwilę nastąpi połączenie ",
                        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(),CallingActivity.class);
                startActivity(intent);

            }
else {
                SkamperApplication.call = incomingCall;
                Intent intent = new Intent(getApplicationContext(), CallingActivity.class);
                startActivity(intent);
            }

        }
    }

    class MyMessageClientListener implements MessageClientListener{

        @Override
        public void onIncomingMessage(MessageClient messageClient, Message message) {
            Log.d("ConnectionService", "Message from "+ message.getSenderId() + ": " + message.getTextBody());
            com.workspaceapp.skamper.data.model.Message myMessage = new com.workspaceapp.skamper.data.model.Message(
                    System.currentTimeMillis(),
                    message.getTextBody(),
                    AppDataManager.getInstance().getCurrentUser().getEmail(),
                    message.getSenderId());
            SkamperApplication.messagesDatabaseHelper.insertMessage(myMessage);
            sendBroadcastToActivity(myMessage);
        }

        @Override
        public void onMessageSent(MessageClient messageClient, Message message, String s) {
            Log.d("ConnectionService", "Message to "+ message.getRecipientIds().get(0) + ": " + message.getTextBody());
            com.workspaceapp.skamper.data.model.Message myMessage = new com.workspaceapp.skamper.data.model.Message(
                    System.currentTimeMillis(),
                    message.getTextBody(),
                    message.getRecipientIds().get(0),
                    AppDataManager.getInstance().getCurrentUser().getEmail());
            SkamperApplication.messagesDatabaseHelper.insertMessage(myMessage);
        }

        @Override
        public void onMessageFailed(MessageClient messageClient, Message message, MessageFailureInfo messageFailureInfo) {

        }

        @Override
        public void onMessageDelivered(MessageClient messageClient, MessageDeliveryInfo messageDeliveryInfo) {

        }

        @Override
        public void onShouldSendPushData(MessageClient messageClient, Message message, List<PushPair> list) {

        }
    }

    private void sendBroadcastToActivity(com.workspaceapp.skamper.data.model.Message message){
        Intent local = new Intent();
        local.setAction("service.to.activity.transfer");
        local.putExtra("sender",message.getSender());
        local.putExtra("recipient", message.getRecipient());
        local.putExtra("body", message.getBody());
        local.putExtra("time", message.getTimeInMillis());
        sendBroadcast(local);
    }
}
