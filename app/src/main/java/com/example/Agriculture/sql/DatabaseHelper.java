package com.example.Agriculture.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.Agriculture.model.State;
import com.example.Agriculture.model.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "UserManager.db";

    // User table name
    private static final String TABLE_USER = "crop_table";
    // State table name
    private static final String TABLE_STATE = "state_table";

    // User Table Columns names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_CROP_ID = "crop_id";
    private static final String COLUMN_CROP = "crop";
    private static final String COLUMN_CROP_VARIETY = "variety";
    private static final String COLUMN_CROP_STATEID = "state_id";

    // State Table Columns names
    private static final String COLUMN_USER_STATE_ID = "user_state_id";
    private static final String COLUMN_STATE_ID = "state_id";
    private static final String COLUMN_STATE_NAME = "state_name";

    // create table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_CROP_ID + " TEXT,"
            + COLUMN_CROP + " TEXT," + COLUMN_CROP_VARIETY + " TEXT,"
            /* + "FOREIGN KEY(" */ + COLUMN_CROP_STATEID + " TEXT"/*") REFERENCES "
            + TABLE_STATE + "(state_name) "*/ + ")";

    // create state table sql query
    private String CREATE_STATE_TABLE = "CREATE TABLE " + TABLE_STATE + "("
            + COLUMN_USER_STATE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_STATE_ID + " TEXT,"
            + COLUMN_STATE_NAME + " TEXT" + ")";

    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    // drop state table sql query
    private String DROP_STATE_TABLE = "DROP TABLE IF EXISTS " + TABLE_STATE;

    /**
     * Constructor
     *
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_STATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);

        // Create tables again
        onCreate(db);
    }

    /**
     * This method is to create user record
     *
     * @param user
     */
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_CROP_ID, user.getCropId());
        values.put(COLUMN_CROP, user.getCrop());
        values.put(COLUMN_CROP_VARIETY, user.getCropVariety());
        values.put(COLUMN_CROP_STATEID, user.getCropStateId());

        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public void addState(State state) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_STATE_ID, state.getStateId());
        values.put(COLUMN_STATE_NAME, state.getStateName());

        // Inserting Row
        db.insert(TABLE_STATE, null, values);
        db.close();
    }

    public List<User> getALLCropByState() {
        String query = "Select COLUMN_STATE_ID.* , COLUMN_CROP_STATEID.* from " + TABLE_USER + " as COLUMN_STATE_ID left join " + TABLE_STATE + " as COLUMN_CROP_STATEID on COLUMN_STATE_ID." + COLUMN_CROP_STATEID + "= COLUMN_CROP_STATEID." + COLUMN_STATE_ID;


        List<User> list = new ArrayList<User>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
//            db.beginTransaction();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    User user = new User();
                    user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                    user.setCropId(cursor.getString(cursor.getColumnIndex(COLUMN_CROP_ID)));
                    user.setCrop(cursor.getString(cursor.getColumnIndex(COLUMN_CROP)));
                    user.setCropVariety(cursor.getString(cursor.getColumnIndex(COLUMN_CROP_VARIETY)));
                    user.setCropStateId(cursor.getString(cursor.getColumnIndex(COLUMN_CROP_STATEID)));
                    user.setCropJoinStateName(cursor.getString(cursor.getColumnIndex(COLUMN_STATE_NAME)));
                    // Adding user record to list
                    list.add(user);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();

        } catch (Exception e) {
            Log.e("db", e.toString());
        }
        return list;
    }

    public List<User> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_CROP_ID,
                COLUMN_CROP,
                COLUMN_CROP_VARIETY,
                COLUMN_CROP_STATEID
        };
        List<User> userList = new ArrayList<User>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                null); //The sort order

        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setCropId(cursor.getString(cursor.getColumnIndex(COLUMN_CROP_ID)));
                user.setCrop(cursor.getString(cursor.getColumnIndex(COLUMN_CROP)));
                user.setCropVariety(cursor.getString(cursor.getColumnIndex(COLUMN_CROP_VARIETY)));
                user.setCropStateId(cursor.getString(cursor.getColumnIndex(COLUMN_CROP_STATEID)));
                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }

    public List<State> getAllState() {
        // array of columns to fetch
        String[] columns1 = {
                COLUMN_USER_STATE_ID,
                COLUMN_STATE_ID,
                COLUMN_STATE_NAME
        };

        // sorting orders
//        String sortOrder = COLUMN_CROP_STATEID + " ASC";
        List<State> stateList = new ArrayList<State>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_STATE, //Table to query
                columns1,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                null); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                State state = new State();
                state.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_STATE_ID))));
                state.setStateId(cursor.getString(cursor.getColumnIndex(COLUMN_STATE_ID)));
                state.setStateName(cursor.getString(cursor.getColumnIndex(COLUMN_STATE_NAME)));
                // Adding user record to list
                stateList.add(state);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return stateList;
    }

    /**
     * This method to update user record
     *
     * @param user
     */

    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_CROP_ID, user.getCropId());
        values.put(COLUMN_CROP, user.getCrop());
        values.put(COLUMN_CROP_VARIETY, user.getCropVariety());
        values.put(COLUMN_CROP_VARIETY, user.getCropStateId());

        // updating row
        db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * This method is to delete user record
     *
     * @param user
     */
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    public void deleteState(State state) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_STATE, COLUMN_USER_STATE_ID + " = ?",
                new String[]{String.valueOf(state.getId())});
        db.close();
    }


    public boolean checkUser(User user) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_CROP + " = ?";

        // selection argument
        String[] selectionArgs = {user.getCropStateId()};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'srbh@gmail.com';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    public boolean checkUserState(State state) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_CROP + " = ?" + " AND " + COLUMN_CROP_VARIETY + " = ?";

        // selection arguments
        String[] selectionArgs = {state.getStateId()};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'srbh@gmail.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }
}
