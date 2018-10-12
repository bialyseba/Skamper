package com.workspaceapp.skamper.conversation;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.text.format.DateUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.workspaceapp.skamper.R;
import com.workspaceapp.skamper.data.AppDataManager;
import com.workspaceapp.skamper.data.model.Message;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MessagesAdapter extends BaseAdapter {
    private ArrayList<Message> messageArrayList;
    Context context;

    public MessagesAdapter(Context context,ArrayList<Message> messageArrayList){
        this.context = context;
        this.messageArrayList = messageArrayList;
    }

    @Override
    public int getCount() {
        return messageArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return messageArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class MessageViewHolder {
        TextView bodyView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MessageViewHolder holder = new MessageViewHolder();
        View row = convertView;
        LayoutInflater messageInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if(messageArrayList.get(position).getRecipient().equals(AppDataManager.getInstance().getCurrentUser().getEmail())){
            row = messageInflater.inflate(R.layout.incoming_message, null);
        }else{
            row = messageInflater.inflate(R.layout.sent_message, null);
        }
        holder.bodyView =  row.findViewById(R.id.messageBody);
        holder.bodyView.setText(messageArrayList.get(position).getBody());
        return row;
    }

    /*public static int getAttributeColor(
            Context context,
            int attributeId) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(attributeId, typedValue, true);
        int colorRes = typedValue.resourceId;
        int color = -1;
        try {
            color = context.getResources().getColor(colorRes);
        } catch (Resources.NotFoundException e) {
            Log.w("sdsfd", "Not found color resource by id: " + colorRes);
        }
        return color;
    }*/


    public void add(Message Message){
        this.messageArrayList.add(Message);
        notifyDataSetChanged();
    }

    /*public void swap(ArrayList<Message> chatMessages){
        this.messageArrayList.clear();
        this.messageArrayList.addAll(chatMessages);
        notifyDataSetChanged();
    }*/
    /*private boolean isSameDay(long timestamp1, long timestamp2){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date date1 = new Date(timestamp1);
        Date date2 = new Date(timestamp2);
        String dateString1 = simpleDateFormat.format(date1);
        String dateString2 = simpleDateFormat.format(date2);
        if(dateString1.equals(dateString2)){
            return true;
        }else{
            return false;
        }
    }*/

    /*private static String getStringTime(Context context, long time)
    {
        Date date=new Date(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy, HH:mm");
        String dateString = simpleDateFormat.format(date);

        Calendar c1 = Calendar.getInstance(); // today
        c1.add(Calendar.DAY_OF_YEAR, -1);

        Calendar c2 = Calendar.getInstance();
        c2.setTime(date);

        if(DateUtils.isToday(time)){
            simpleDateFormat = new SimpleDateFormat("HH:mm");
            dateString = context.getString(R.string.today)+", "+ simpleDateFormat.format(date);
        }
        if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR)){
            simpleDateFormat = new SimpleDateFormat("HH:mm");
            dateString = context.getString(R.string.yesterday)+", "+simpleDateFormat.format(date);
        }
        return dateString;
    }*/
}

