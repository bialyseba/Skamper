package com.workspaceapp.skamper.calling;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.workspaceapp.skamper.R;
import com.workspaceapp.skamper.SkamperApplication;

public class CallingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calling);

        Button button = findViewById(R.id.buttonHangUp);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SkamperApplication.call.hangup();
            }
        });

    }
}
