package com.example.a15039840.demodatabasecrud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by 15039840 on 18/5/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "simplenotes.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NOTE = "note";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NOTE_CONTENT = "note_content";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createNoteTableSql = "CREATE TABLE " + TABLE_NOTE + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NOTE_CONTENT + " TEXT ) ";
        db.execSQL(createNoteTableSql);
        Log.i("info", "created tables");

        //Dummy records, to be inserted when the database is created
        for (int i = 0; i< 4; i++) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NOTE_CONTENT, "Data number " + i);
            db.insert(TABLE_NOTE, null, values);
        }
        Log.i("info", "dummy records inserted");


    }
    public long insertNote(String noteContent) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTE_CONTENT, noteContent);
        long result = db.insert(TABLE_NOTE, null, values);
        if (result == -1){
            Log.d("DBHelper", "Insert failed");
        }

        db.close();
        Log.d("SQL Insert",""+ result); //id returned, shouldn’t be -1
        return result;
    }
    public int updateNote(Note data){
        SQLiteDatabase db = this.getWritableDatabase();
        //SET note_content = "idk some data" in DATABASE
        // ITS SIMILAR!!!
        // SET THE NEW CONTENT TO UPDATE.
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTE_CONTENT, data.getNoteContent());
        //WHERE_ID=1
        //^ IS WHAT IN DATABASE
        // GET THE ID OF THE THINGS TO UPDATE THEN UPDATE IT!!!!!!!!!!!!
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};
        // UNTIL HERE, MAKE It HAPPEN
        int result = db.update(TABLE_NOTE, values, condition, args);
        if (result < 1){
            Log.d("DBHelper", "Update failed");
        }

        db.close();

        return result;
    }
    public int deleteNote(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        //WHERE id=1 ** THIS IS AN EXAMPPLE **
        // ^ IT IS WHAT IT IS IN DATABASE
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        //UNTIL HERE
        int result = db.delete(TABLE_NOTE, condition, args);
        db.close();
        return result;
    }



    public ArrayList<String> getAllNotes() {
        ArrayList<String> notes = new ArrayList<String>();

        String selectQuery = "SELECT " + COLUMN_ID + ","
                + COLUMN_NOTE_CONTENT + " FROM " + TABLE_NOTE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String content = cursor.getString(1);
                notes.add("ID:" + id + ", " + content);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return notes;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE);
        onCreate(db);
    }


    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

}
