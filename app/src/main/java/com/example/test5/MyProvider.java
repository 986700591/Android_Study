package com.example.test5;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.CancellationSignal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyProvider extends ContentProvider {
    private MyDatabaseHelper dbHelper;
    private static final  int CONTACT_DIR = 0;
    private static final  int CONTACT_ITEM = 1;
    public static final String AUTHORITY = "com.example.test5.provider";//权限
    private static UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY,"Contact",CONTACT_DIR);
        uriMatcher.addURI(AUTHORITY,"Contact/#",CONTACT_ITEM);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new MyDatabaseHelper(getContext(),"Contact.db",null,1);
        dbHelper.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        switch (uriMatcher.match(uri)){
            case CONTACT_DIR:
                cursor = db.query("Contact",projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case CONTACT_ITEM:
                String contactsId=uri.getPathSegments().get(1);
                cursor = db.query("Contact",projection,"id = ?",new String[]{contactsId},null,null,sortOrder);
                break;
        }
        return cursor;
    }

    //根据传入的内容 URI 来返回相应的 MIME 类型
    public String getType( Uri uri) {
        switch (uriMatcher.match(uri)){
            case CONTACT_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.test5.provider.Contact";
            case CONTACT_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.test5.provider.Contact";
        }
        return null;
    }


    //向内容提供器中添加一条数据。使用 uri 参数来确定要添加到的表
    public Uri insert( Uri uri,  ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Uri uri1 = null;
        switch (uriMatcher.match(uri)){
            case CONTACT_DIR:
            case CONTACT_ITEM:
                Long newContactsId = db.insert("Contract",null,values);
                uri1 = Uri.parse("content://"+AUTHORITY+"/contact/"+newContactsId);
                break;
            default:
                break;
        }
        return uri1;
    }

    //更新内容提供器中已有的数据。使用 uri 参数来确定更新哪一张表中的数据
    public int update( Uri uri,  ContentValues values,  String selection,  String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int updatedRows = 0;
        switch (uriMatcher.match(uri)){
            case CONTACT_DIR:
                updatedRows = db.update("Contact",values,selection,selectionArgs);
                break;
            case CONTACT_ITEM:
                String contactsId=uri.getPathSegments().get(1);
                updatedRows = db.update("Contact",values,"id = ?",new String[]{contactsId});
                break;
            default:
                break;
        }
        return updatedRows;
    }


    //从内容提供器中删除数据。使用 uri 参数来确定删除哪一张表中的数据
    public int delete( Uri uri,  String selection,  String[] selectionArgs) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int deletedRows = 0;
        switch (uriMatcher.match(uri)){
            case CONTACT_DIR:
                deletedRows = db.delete("Contact",selection,selectionArgs);
                break;
            case CONTACT_ITEM:
                String contactsId=uri.getPathSegments().get(1);
                deletedRows = db.delete("Contact","id = ?",new String[]{contactsId});
                break;
            default:
                break;
        }
        return deletedRows;
    }
}
