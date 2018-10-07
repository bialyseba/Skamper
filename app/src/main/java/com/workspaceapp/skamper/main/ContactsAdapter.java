package com.workspaceapp.skamper.main;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallListener;
import com.workspaceapp.skamper.R;
import com.workspaceapp.skamper.SkamperApplication;
import com.workspaceapp.skamper.calling.CallingActivity;
import com.workspaceapp.skamper.conversation.ConversationActivity;
import com.workspaceapp.skamper.data.AppDataManager;
import com.workspaceapp.skamper.data.model.Contact;

import java.util.ArrayList;
import java.util.List;

import static com.workspaceapp.skamper.SkamperApplication.call;
import static com.workspaceapp.skamper.SkamperApplication.sinchClient;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder> {
    public ArrayList<Contact> mDataset;
    private Context mContext;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView mTextView;
        public ImageView mImageView;
        private Context mContext;
        public MyViewHolder(View view, Context context) {
            super(view);
            mContext = context;
            mTextView = view.findViewById(R.id.contactTextView);
            mImageView = view.findViewById(R.id.contactImageView);
            view.setOnClickListener(this);
        }

        private void openConversationActivity(Contact contact){
            mContext.startActivity(new Intent(mContext, ConversationActivity.class)
                    .putExtra("email", contact.getEmail())
                    .putExtra("username", contact.getUsername()));
        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();

            if(pos!= RecyclerView.NO_POSITION){
                Contact contact = mDataset.get(pos);
                openConversationActivity(contact);
            }
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ContactsAdapter(Context context, ArrayList<Contact> myDataset) {
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
        holder.mTextView.setText(mDataset.get(position).getUsername());
        try{
            AppDataManager.getInstance().getPhotoUriOfSpecifiedUser(mDataset.get(position).getEmail(), new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot d : dataSnapshot.getChildren()){
                        String uri = (String) d.child("photoUri").getValue();
                        Log.d("ContactsAdapter",mDataset.get(position).getUsername() + " " + uri);
                        if(uri != null && !uri.equals("")){
                            Glide.with(mContext)
                                    .load(uri)
                                    .apply(RequestOptions.circleCropTransform())
                                    .into(holder.mImageView);
                        }else{
                            Glide.with(mContext)
                                    .clear(holder.mImageView);
                            holder.mImageView.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_user_round));
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

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}