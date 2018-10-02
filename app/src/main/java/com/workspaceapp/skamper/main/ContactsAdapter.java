package com.workspaceapp.skamper.main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallListener;
import com.workspaceapp.skamper.R;
import com.workspaceapp.skamper.SkamperApplication;
import com.workspaceapp.skamper.calling.CallingActivity;
import com.workspaceapp.skamper.conversation.ConversationActivity;

import java.util.List;

import static com.workspaceapp.skamper.SkamperApplication.call;
import static com.workspaceapp.skamper.SkamperApplication.sinchClient;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder> {
    private String[] mDataset;
    private Context mContext;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public ImageButton messagesImageButton;
        private Context mContext;
        public MyViewHolder(View view, Context context) {
            super(view);
            mContext = context;
            mTextView = (TextView) view.findViewById(R.id.contactTextView);
            messagesImageButton =  view.findViewById(R.id.messageButton);

            messagesImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openConversationActivity();
                    //sinchClient.getCallClient().callUser("call-recipient-id");
                    //TODO dodac nazwe usera koncowego
                    //MainActivity.callUser();
                    //call.addCallListener(new MainActivity.SinchCallListener());
                }
            });

        }
        private void openConversationActivity(){
            mContext.startActivity(new Intent(mContext, ConversationActivity.class));
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ContactsAdapter(Context context, String[] myDataset) {
        mDataset = myDataset;
        mContext = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ContactsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact, parent, false);

        return new MyViewHolder(itemView, mContext);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(mDataset[position]);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }


}