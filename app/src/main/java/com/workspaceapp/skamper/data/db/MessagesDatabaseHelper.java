package com.workspaceapp.skamper.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.workspaceapp.skamper.data.AppDataManager;
import com.workspaceapp.skamper.data.model.Message;

import java.util.ArrayList;

public class MessagesDatabaseHelper extends SQLiteOpenHelper {
    public MessagesDatabaseHelper(Context context, String name) {
        super(context, name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Messages(body VARCHAR, time INTEGER, sender VARCHAR, recipient VARCHAR);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertMessage(Message message){
        SQLiteDatabase db = getWritableDatabase();
        try{
            db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put("body",message.getBody());
            values.put("time", message.getTimeInMillis());
            values.put("sender", message.getSender());
            values.put("recipient", message.getRecipient());
            db.insertOrThrow("Messages", null, values);
            db.setTransactionSuccessful();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
    }

    public ArrayList<Message> getMessagesForConversationWith(String friendEmail){
        ArrayList<Message> messages  = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        try{
            db.beginTransaction();
            //String qu = "SELECT * FROM Messages group by send;";
            String qu = "SELECT * FROM Messages WHERE (sender LIKE ? AND recipient LIKE ?) OR (recipient LIKE ? AND sender LIKE ?);";// OR (sender = ? AND recipient = ?);";
            String userEmail = AppDataManager.getInstance().getCurrentUser().getEmail();
            //Cursor resultSet = db.rawQuery(qu, null);
            Cursor resultSet = db.rawQuery(qu, new String[]{userEmail, friendEmail, userEmail, friendEmail});
            resultSet.moveToFirst();
            while (!resultSet.isAfterLast()){
                String body = resultSet.getString(0);
                long time = resultSet.getLong(1);
                String sender = resultSet.getString(2);
                String recipient = resultSet.getString(3);

                Message message = new Message(time, body, recipient, sender);
                messages.add(message);
                resultSet.moveToNext();
            }
            resultSet.close();
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
        return messages;
    }
}
