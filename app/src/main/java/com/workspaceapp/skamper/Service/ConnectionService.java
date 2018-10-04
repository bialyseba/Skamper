package com.workspaceapp.skamper.Service;

import android.content.Intent;
import android.os.IBinder;

import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallClient;
import com.sinch.android.rtc.calling.CallClientListener;
import com.workspaceapp.skamper.SkamperApplication;
import com.workspaceapp.skamper.calling.CallingActivity;

public class ConnectionService extends android.app.Service {

    @Override
    public void onCreate() {
        super.onCreate();
        SkamperApplication.sinchClient.getCallClient().addCallClientListener(new SinchCallClientListener());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    class SinchCallClientListener implements CallClientListener {
        @Override
        public void onIncomingCall(CallClient callClient, Call incomingCall) {

            SkamperApplication.call = incomingCall;
            Intent intent = new Intent(getApplicationContext(),CallingActivity.class);
            startActivity(intent);

            /*SkamperApplication.call.addCallListener(new SinchCallListener());*/
        }
    }

}
