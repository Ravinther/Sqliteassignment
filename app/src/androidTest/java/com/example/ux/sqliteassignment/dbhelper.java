package com.example.ux.sqliteassignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.view.View;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ravi on 6/23/2016.
 */
public class dbhelper extends SQLiteOpenHelper {

    public static final String dbname="sqllite.db";
    public static final String dbtablename="imageinsert";
    public static final String db_name="name";
    public static final String dbage="age";
    public static final String dbimage="image";
    public static final String db_Id="id";
    private HashMap hp;

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(
                    "create table imageinsert " +
                            "id integer primary key,name text,age integer,image Blob NOT NULL"
            );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists imageinsert");
        onCreate(db);

    }
    public dbhelper(Context context)
    {
        super(context, dbname, null, 1);
    }
    public boolean insertmethod(String name,String age,byte[] image)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("name",name);
        contentValues.put("age",age);
        contentValues.put("image", image);
        db.insert(dbtablename, null, contentValues);
        return true;
    }
    public boolean updatemethod(Integer id,String name,String age,byte[] image)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("name",name);
        contentValues.put("age",age);
        contentValues.put("image", image);
        db.update(dbtablename, contentValues, "id=?", new String[]{Integer.toString(id)});
        return  true;
    }
    public Cursor numofround(Integer id)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from imageinsert where id="+id+"",null);
        return cursor;
    }
    public Integer numofrows()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        int num=(int) DatabaseUtils.queryNumEntries(db,dbtablename);
        return num;
    }
    public Integer deletemethod(Integer id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(dbtablename,"id=?",new String[]{Integer.toString(id)});
    }

    public ArrayList<String> getallcontacts()
    {
        ArrayList<String>arrayList=new ArrayList<>();
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from imageinsert",null);
        cursor.moveToFirst();
        while (cursor.moveToLast()==false)
        {
            arrayList.add(cursor.getString(cursor.getColumnIndex(db_name)));
            cursor.moveToNext();
        }
        return arrayList;
    }

}
