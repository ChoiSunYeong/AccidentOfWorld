package com.example.cheonyujung.accidentofworld.data.query.BoardQuery;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cheonyujung.accidentofworld.data.DBQuery;
import com.example.cheonyujung.accidentofworld.data.struct.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by ohyongtaek on 16. 5. 16..
 */
public class Comment extends DBQuery {
    public Comment(Context context) {
        super(context);
    }

    public void insert(com.example.cheonyujung.accidentofworld.data.struct.Post post, String content, String user){
        SimpleDateFormat date = new SimpleDateFormat("yyyy/mm/dd");
        ContentValues values = new ContentValues();
        values.put("post_id",post.get_id());
        values.put("content",content);
        values.put("user_id",user);
        values.put("comment_date", date+"");
        writeDB().insert("comment", null, values);
    }
    public void update(int id,String content){
        ContentValues values = new ContentValues();
        values.put("content",content);
        writeDB().update("comment", values, "_id=?", new String[]{String.valueOf(id)});
    }
    public void delete(int id){
        writeDB().delete("comment", "_id=?", new String[]{String.valueOf(id)});
    }

    public ArrayList<com.example.cheonyujung.accidentofworld.data.struct.Comment> getCommentByPost_id(com.example.cheonyujung.accidentofworld.data.struct.Post post) {
        if(post != null) {
            String[] whereArgs = new String[]{post.get_id() + ""};
            SQLiteDatabase db = readDB();
            Cursor cursor = db.rawQuery("select * from comment where post_id = ?;", whereArgs);
            ArrayList<com.example.cheonyujung.accidentofworld.data.struct.Comment> comments = new ArrayList<>();

            while (cursor.moveToNext()) {
                com.example.cheonyujung.accidentofworld.data.struct.Comment comment = new com.example.cheonyujung.accidentofworld.data.struct.Comment();
                comment.set_id(cursor.getInt(0));
                comment.setPost(post);
                comment.setContent(cursor.getString(2));
                comment.setUserID(cursor.getString(3));
                comment.setComment_date(cursor.getString(4));
                comments.add(comment);
            }
            return comments;
        }
        return null;
    }
}
