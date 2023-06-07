package com.example.mybaby6;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mybaby6.ui.dashboard.DashboardFragment;
import com.example.mybaby6.ui.dashboard.ListViewAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class inShop extends AppCompatActivity implements MyInterface {
    int price = 0;
    Button button;
    static ListView list;
    static ListViewAdapter adapter;
    static TextView textView;
    private static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.in_shop);
        Intent intent = getIntent();
        String num = intent.getStringExtra("num");
        button = findViewById(R.id.link);
        ImageButton imageButton = findViewById(R.id.imageButton);
        context = getApplicationContext();
        //go back to homefragment
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Main_Home_Page.class);
                startActivity(intent);
            }
        };
        imageButton.setOnClickListener(onClickListener);
        list = findViewById(R.id.list);
        final ListView list2 = findViewById(R.id.zameny);
        DashboardFragment.inShoP(num);
        ArrayList<String> zamen = zamena(inShop);
        adapter = new ListViewAdapter(getApplicationContext(), getList(inShop));
        list.setAdapter(adapter);
        final ArrayAdapter[] adapter2 = {new ArrayAdapter(inShop.this, R.layout.list_item_layout, getList(zamen))};
        list2.setAdapter(adapter2[0]);
        textView = findViewById(R.id.textView5);
        textView.setText("Цена: \n" + x.get(x.size() - 1).get(shops.indexOf(num))  + " р.");


        list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                price = 0;
                String s = (String) adapter2[0].getItem(position);
                String typeNow = s.split(" ")[1];
                String s1 = "";
                for (String in: inShop){
                    if (in.split(" ")[1].equals(typeNow)) s1 = in;
                }
                s1 = s;
                zamen.remove(s1);
                inShop.add(s1);

                list.setAdapter(adapter);

                adapter2[0] = new ArrayAdapter(inShop.this, R.layout.list_item_layout, zamen);
                list2.setAdapter(adapter2[0]);
                for (String ourBasket: inShop) {
                    String[] m = ourBasket.split(" ");
                    price += Integer.parseInt(m[m.length - 3]) * Integer.parseInt(m[m.length - 2]);
                }
                textView.setText("Цена: " + Integer.toString(price) + " р.");
            }
        });
        Button button = findViewById(R.id.link);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //setLink();
            }
        });
    }
    public ArrayList<String> zamena(ArrayList<String> inShop){
        ArrayList<String> a = new ArrayList<>(), b = new ArrayList<>(), dif = new ArrayList<>();
        String shop = "";
        for (String givenProd: inShop) {
            String[] m = givenProd.split(" ");
            shop = m[0];
            for (ArrayList<String> prod : allProductsPrice) {
                if (m[0].equals(prod.get(0)) &&
                        m[1].equals(prod.get(1).split(" ")[0])
                        && !a.contains(String.join(" ", prod) + " 1") && !inShop.contains(String.join(" ", prod) + " 1")) {
                    a.add(String.join(" ", prod) + " 1");
                }
            }
        }
        //если товара нет в этом магазине, то все равно предложи альтераниву на него
        for (int i = 0; i < basket.size(); i++){
            if (!inShop.contains(basket.get(i))){
                dif.add(basket.get(i));
                String givenProd = basket.get(i);
                String[] m = givenProd.split(" ");
                for (ArrayList<String> prod : allProductsPrice) {
                    if (shop.equals(prod.get(0)) &&
                            m[0].equals(prod.get(1).split(" ")[0])
                            && !a.contains(String.join(" ", prod) + " 1") && !inShop.contains(String.join(" ", prod) + " 1")) {
                        a.add(String.join(" ", prod) + " 1");
                    }
                }
            }
        }

        return a;
    }
    public ArrayList<String> quant(ArrayList<String> inShopppp){
        ArrayList<String> quanty = new ArrayList<>();

        for (String prodInBasket: DashboardFragment.a) {
            for (String prod : inShopppp) {
                if (prod.contains(String.join(" ", Arrays.copyOfRange(prodInBasket.split(" "), 0,prodInBasket.split(" ").length - 1)))){
                    quanty.add(prod + " " + prodInBasket.split(" ")[prodInBasket.split(" ").length - 1] + " шт.");
                }
            }
        }
        return quanty;
    }
    public static String removeItemShop(int position){
        //k is left quantity
        String product = inShop.get(position);
        String k1;
        int k = Integer.parseInt(product.split(" ")[product.split(" ").length - 2]);
        if (k == 1){
            inShop.remove(position);
             k1 = "0";
        }
        else {
            String[] smth = product.split(" ");
            inShop.set(position, String.join(" ", Arrays.copyOfRange(smth, 0, product.split(" ").length - 2)) + " " + (k -1) + " шт.");
             k1 = Integer.toString(k - 1);
        }
        adapter = new ListViewAdapter(context, inShop);
        list.setAdapter(adapter);


        int price = 0;
        for (String ourBasket: inShop) {
            String[] m = ourBasket.split(" ");
            price += Integer.parseInt(m[m.length - 3]) * Integer.parseInt(m[m.length - 2]);
        }
        textView.setText("Цена: " + price + " р.");
        return k1;
    }
     ArrayList<String > getList(ArrayList<String> li){
        for (int i = 0; i <li.size(); i++){
            li.set(i, li.get(i) + " шт.");
        }
        //видимо потому что мы передаем статчные объекты у нас меняется изначальный лист, поэтому считываем цены индекс -3
        return li;
    }
}
