package com.example.mybaby6.ui.dashboard;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mybaby6.MyInterface;
import com.example.mybaby6.R;

import java.util.ArrayList;

class ListViewAdapterShops extends ArrayAdapter<String> implements MyInterface {
    ArrayList<String> lista;
    Context context;
    public static int cnt = 0;

    // The ListViewAdapter Constructor
    // @param context: the Context from the MainActivity
    // @param items: The list of items in our Grocery List
    public ListViewAdapterShops(Context context, ArrayList<String> items) {
        super(context, R.layout.list_row, items);
        this.context = context;
        lista = items;
    }

    // The method we override to provide our own layout for each View (row) in the ListView
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_row_shops, null);
            TextView name = convertView.findViewById(R.id.name);
            name.setText(lista.get(position));
            // Listeners for duplicating and removing an item.
            // They use the static removeItem and addItem methods created in MainActivity.
        }
        return convertView;
    }
}