package com.example.test5;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends ArrayAdapter<Contact> {
    private int resourceid;
    public MyAdapter(Context context, int textViewResourceId, List<Contact> objects) {
        super(context, textViewResourceId, objects);
        resourceid = textViewResourceId;
    }

    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        Contact contact = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceid,parent,false);
        TextView contact_name = view.findViewById(R.id.contact_name);
        TextView contact_phone = view.findViewById(R.id.contact_phone);
        contact_name.setText(contact.getName());
        contact_phone.setText(contact.getPhone());
        return view;
    }
}
