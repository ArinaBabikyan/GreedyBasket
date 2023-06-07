package com.example.mybaby6.ui.dashboard;

import static com.example.mybaby6.inShop.removeItemShop;
import static com.example.mybaby6.ui.dashboard.DashboardFragment.root;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mybaby6.MyInterface;
import com.example.mybaby6.R;

import java.util.ArrayList;

public class ListViewAdapter extends ArrayAdapter<String> implements MyInterface {
    ArrayList<String> lista;
    Context context;
    public static int cnt = 0;

    // The ListViewAdapter Constructor
    // @param context: the Context from the MainActivity
    // @param items: The list of items in our Grocery List
    public ListViewAdapter(Context context, ArrayList<String> items) {
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
            convertView = mInflater.inflate(R.layout.list_row, null);
            TextView name = convertView.findViewById(R.id.name);
            ImageView add = convertView.findViewById(R.id.add);
            ImageView remove = convertView.findViewById(R.id.remove);
            name.setText(lista.get(position));
            // Listeners for duplicating and removing an item.
            // They use the static removeItem and addItem methods created in MainActivity.
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String k;
                    if (context == root.getContext()) {
                        k = DashboardFragment.removeItem(position);
                    }
                    else {
                        k = removeItemShop(position);
                    }
                    if (k.equals("0")) {
                        Toast.makeText(context, "Товар удален из корзины.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Выбрано " + k + " шт.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String k = DashboardFragment.adding(position);
                    Toast.makeText(context, "Выбрано " + k + " шт.", Toast.LENGTH_SHORT).show();
                }
            });
        }
        return convertView;
    }
}