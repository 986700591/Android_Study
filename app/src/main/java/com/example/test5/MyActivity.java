package com.example.test5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyActivity extends AppCompatActivity {
    ArrayList<Contact> contacts = new ArrayList<Contact>();
    MyAdapter myAdapter = null;
    private String newId = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        Button addData = findViewById(R.id.addData);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加数据
                Uri uri = Uri.parse("content://com.example.test5.provider/Contact");//将uri字符串解析成 Uri 对象才并作为参数传入
                ContentValues values = new ContentValues();
                values.put("name","郑英健");
                values.put("phone","13680049591");
                Uri newUri = getContentResolver().insert(uri,values);//返回值是新数据行的uri
                newId = newUri.getPathSegments().get(1);//新行的id
                if(newId!=null){
                    Toast.makeText(MyActivity.this,"添加成功",Toast.LENGTH_LONG).show();
                }
            }
        });

        Button queryData = findViewById(R.id.queryData);
        queryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAdapter = new MyAdapter(MyActivity.this,R.layout.activity_contact,contacts);
                Uri uri = Uri.parse("content://com.example.test5.provider/Contact");
                Cursor cursor = getContentResolver().query(uri,null,null,null,null);
                //通过Context 中的getContentResolver()方法获取到该类的实例，然后调用query方法将数据从 Cursor 对象中逐个读取出来了
                if(cursor!=null){
                    while(cursor.moveToNext()){
                        Contact contact = new Contact();
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String phone = cursor.getString(cursor.getColumnIndex("phone"));
                        contact.setName(name);
                        contact.setPhone(phone);
                        contacts.add(contact);
                    }
                    //cursor.close();
                }
                ListView listView = findViewById(R.id.listview2);
                listView.setAdapter(myAdapter);
                Toast.makeText(MyActivity.this,"查询成功",Toast.LENGTH_LONG).show();
            }
        });
        Button updataData = findViewById(R.id.updataData);
        updataData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://com.example.test5.provider/Contact/"+newId);//将uri字符串解析成 Uri 对象才并作为参数传入
                ContentValues values = new ContentValues();
                values.put("name","小郑");
                values.put("phone","13610599754");
                getContentResolver().update(uri,values,null,null);
            }
        });

        Button deleteData = findViewById(R.id.deleteData);
        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://com.example.test5.provider/Contact"+newId);
                getContentResolver().delete(uri, null, null);
            }
        });
    }
}
