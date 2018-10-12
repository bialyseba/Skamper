package com.workspaceapp.skamper.conversation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.sinch.android.rtc.messaging.WritableMessage;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.workspaceapp.skamper.R;
import com.workspaceapp.skamper.SkamperApplication;
import com.workspaceapp.skamper.calling.CallingActivity;
import com.workspaceapp.skamper.data.AppDataManager;
import com.workspaceapp.skamper.data.model.Contact;
import com.workspaceapp.skamper.data.model.Message;

import java.util.ArrayList;

import static com.workspaceapp.skamper.SkamperApplication.call;
import static com.workspaceapp.skamper.SkamperApplication.sinchClient;

public class ConversationActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText messageEditText;
    private ImageButton sendButton;
    private ListView listView;
    private BroadcastReceiver updateUIReciver;
    private ImageView contactImageView;

    private MessagesAdapter messagesAdapter;
    private ArrayList<Message> messages;

    AdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        adView = (AdView) findViewById(R.id.adBannerView);
        //AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build(); //dla emulatora
        AdRequest adRequest = new AdRequest.Builder().build();   //DLA URZADZENIA
        adView.loadAd(adRequest);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        toolbar = findViewById(R.id.conversationToolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        String username = intent.getStringExtra("username");
        Contact contact = new Contact(email, username);

        setContactInfo(contact);

        ImageButton callButton = toolbar.findViewById(R.id.phoneButton);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callUser(contact.getEmail());
            }
        });

        messageEditText = findViewById(R.id.messageEditText);

        sendButton = findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!messageEditText.getText().toString().equals("")){
                    WritableMessage message = new WritableMessage(
                            contact.getEmail(),
                            messageEditText.getText().toString());
                    SkamperApplication.messageClient.send(message);
                    messagesAdapter.add(new Message(System.currentTimeMillis(), messageEditText.getText().toString(), "dsfdsg", AppDataManager.getInstance().getCurrentUser().getEmail()));
                    listView.setSelection(messagesAdapter.getCount() -1);
                    messageEditText.setText("");
                }
            }
        });
        listView = findViewById(R.id.messagesListView);
        messages = new ArrayList<>();
        messages = SkamperApplication.messagesDatabaseHelper.getMessagesForConversationWith(contact.getEmail());
        //messages.add(new Message(System.currentTimeMillis(), "Bssdf", "sebs9302@gmail.com", "dfsdgdsg"));
        messagesAdapter = new MessagesAdapter(this, messages);
        listView.setAdapter(messagesAdapter);
        listView.setSelection(messagesAdapter.getCount() -1);

        IntentFilter filter = new IntentFilter();
        filter.addAction("service.to.activity.transfer");
        updateUIReciver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent != null){
                    String body = intent.getStringExtra("body");
                    String recipient  = intent.getStringExtra("recipient");
                    String sender = intent.getStringExtra("sender");
                    long time = intent.getLongExtra("time", System.currentTimeMillis());
                    if(sender.equals(contact.getEmail())){
                        Message myMessage = new Message(time, body, recipient, sender);
                        messages.add(myMessage);
                        messagesAdapter.notifyDataSetChanged();
                        listView.setSelection(messagesAdapter.getCount() -1);
                    }
                }
            }
        };

        contactImageView = toolbar.findViewById(R.id.contactImageView);
        try{
            AppDataManager.getInstance().getPhotoUriOfSpecifiedUser(contact.getEmail(), new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot d : dataSnapshot.getChildren()){
                        String uri = (String) d.child("photoUri").getValue();
                        if(uri != null && !uri.equals("")){
                            Glide.with(ConversationActivity.this)
                                    .load(uri)
                                    .apply(RequestOptions.circleCropTransform())
                                    .into(contactImageView);
                        }else{
                            Glide.with(ConversationActivity.this)
                                    .clear(contactImageView);
                            contactImageView.setImageDrawable(ContextCompat.getDrawable(ConversationActivity.this, R.drawable.ic_user_round));
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(updateUIReciver, new IntentFilter("service.to.activity.transfer"));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(updateUIReciver);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void callUser(String recipientId){
        if (call == null) {
            call = sinchClient.getCallClient().callUserVideo(recipientId);
            Intent intent = new Intent(getApplicationContext(),CallingActivity.class);
            startActivity(intent);
        } else {
            call.hangup();
        }
    }

    private void setContactInfo(Contact contact){
        TextView contactTextView = toolbar.findViewById(R.id.contactTextView);
        contactTextView.setText(contact.getUsername());
    }
    public void callUserVideo(String recipientId){
        if (call == null) {
            call = sinchClient.getCallClient().callUserVideo(recipientId);
            Intent intent = new Intent(getApplicationContext(),CallingActivity.class);
            startActivity(intent);
        } else {
            call.hangup();
        }
    }
}
