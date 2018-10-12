package com.workspaceapp.skamper.addfriend;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.workspaceapp.skamper.R;
import com.workspaceapp.skamper.SkamperApplication;
import com.workspaceapp.skamper.conversation.ConversationActivity;
import com.workspaceapp.skamper.data.AppDataManager;
import com.workspaceapp.skamper.data.model.Contact;
import com.workspaceapp.skamper.main.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NewContactsAdapter extends RecyclerView.Adapter<NewContactsAdapter.MyViewHolder> {
    public ArrayList<Contact> mDataset;
    private Context mContext;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView mTextView;
        public ImageButton addButton;
        public ImageView mImageView;
        private Context mContext;
        public MyViewHolder(View view, Context context) {
            super(view);
            mContext = context;
            mTextView = view.findViewById(R.id.contactTextView);
            addButton = view.findViewById(R.id.messageButton);
            mImageView = view.findViewById(R.id.newcontactImageView);

            /*boolean itemExistsBasedOnProp = SkamperApplication.contacts.contains(mDataset.get(pos));
            if(itemExistsBasedOnProp){

            }*/
        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();

            if(pos!= RecyclerView.NO_POSITION){
                Contact contact = mDataset.get(pos);

            }
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public NewContactsAdapter(Context context, ArrayList<Contact> myDataset) {
        mDataset = myDataset;
        mContext = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public NewContactsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                                         int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.new_contact, parent, false);

        return new NewContactsAdapter.MyViewHolder(itemView, mContext);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(NewContactsAdapter.MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(mDataset.get(position).getUsername());
        if(contactIsAdded(mDataset.get(position))){
            holder.addButton.setVisibility(View.GONE);
        }
        holder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogBox(mDataset.get(position));
            }
        });
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

    private boolean contactIsAdded(Contact contact){
        for(Contact c : SkamperApplication.contacts){
            if(c.getEmail().equals(contact.getEmail())){
                return true;
            }
        }
        return false;
    }

    private void showDialogBox(Contact contact){
        new AlertDialog.Builder(mContext)
                .setMessage("Do you want to add user "+ contact.getUsername() + "?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppDataManager.getInstance().addContact(contact.getUsername(), contact.getEmail(), new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot d : dataSnapshot.getChildren()){
                                    //if(d.child("contacts").exists()){
                                        Map<String, Object> userValues = contact.toMap();
                                        Map<String, Object> childUpdates = new HashMap<>();
                                        String key = d.child("contacts").getRef().push().getKey();
                                        childUpdates.put("", userValues);
                                        d.child("contacts/" + key).getRef().updateChildren(userValues);
                                        Intent intent= new Intent(mContext, MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                        mContext.startActivity(intent);
                                    //}
                                }
                                Log.d("NewContactsAdapter", dataSnapshot.getKey());

                                //childUpdates.put("/Users/" + key, userValues);
                                //dataSnapshot.child("contacts").getRef().
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        dialog.cancel();
                    }
                })
                .setNegativeButton("NO", null)
                .show();
    }
}
