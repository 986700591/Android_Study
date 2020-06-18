package com.example.test5;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_Contact = "create table Contact ("
            + "id integer primary key autoincrement,"
            + "name text,"
            + "phone text)";

    private Context mContext;
    public MyDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_Contact);
        db.execSQL("INSERT INTO Contact(`name`,`phone`) VALUES('郑英健','13610599754')");
        db.execSQL("INSERT INTO Contact(`name`,`phone`) VALUES('郑英健','13427047440')");
        db.execSQL("INSERT INTO Contact(`name`,`phone`) VALUES('郑舒敏','13025354191')");
        Toast.makeText(mContext,"数据库创建成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
