package com.workspaceapp.skamper.conversation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.workspaceapp.skamper.R;
import com.workspaceapp.skamper.calling.CallingActivity;
import com.workspaceapp.skamper.data.model.Contact;

import static com.workspaceapp.skamper.SkamperApplication.call;
import static com.workspaceapp.skamper.SkamperApplication.sinchClient;

public class ConversationActivity extends AppCompatActivity {
    private Toolbar toolbar;

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
