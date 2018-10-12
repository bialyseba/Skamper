package com.workspaceapp.skamper.conversation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.sinch.android.rtc.messaging.WritableMessage;
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

    private MessagesAdapter messagesAdapter;
    private ArrayList<Message> messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

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
            call = sinchClient.getCallClient().callUser(recipientId);
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

}
