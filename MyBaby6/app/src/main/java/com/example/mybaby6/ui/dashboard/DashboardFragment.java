package com.example.mybaby6.ui.dashboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mybaby6.MyInterface;
import com.example.mybaby6.databinding.FragmentDashboardBinding;
import com.example.mybaby6.inShop;
import com.example.mybaby6.ui.home.HomeFragment;

import java.util.ArrayList;
import java.util.Arrays;

public class DashboardFragment extends Fragment implements MyInterface {
    static ImageView imageView2;
    static View root;
    //public static ArrayList<String> basket = new ArrayList<>();
    boolean ifContainsAll = true;
    public static ArrayList<String> basketFinal = new ArrayList<>(), afinal = new ArrayList<>();
    public static ArrayList<ArrayList<String>> pricesToDisplay = new ArrayList<>();
    //basketFinal contains "shop" "product name" "price"
    //
    public static ArrayList<String> a = new ArrayList<>();
    static ListView list, list2;
    private FragmentDashboardBinding binding;
    public static ArrayList<ArrayList<String>> allProductsPrice;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        list = binding.ListOfBasket;
        list2 = binding.ShopLists;
        allProductsPrice = HomeFragment.allProductsPrice;



        if (basketFinal.size() == 0 || basketFinal.size() / shops.size() != basket.size()){
            //forming a list with quantities
            basketFinal = getPrice(basket);
            HomeFragment.FormArrayA();
            getResult(basketFinal);
            ArrayList<String> m = new ArrayList<>();
            for (int i = 0; i < shops.size(); i++) {
                ArrayList<String> b = checkInShop(shops.get(i));
                if (b.size() == basket.size()) {
                   m.add("Магазин: " + shops.get(i) + ".\nЦена: " + x.get(x.size() - 1).get(i) + ".");
                }
                else{
                    m.add("Магазин: " + shops.get(i) + ".\nЦена: " + x.get(x.size() - 1).get(i) + ".\n" + "Не все выбранные продукты");
                }
            }
            pricesToDisplay.add(m);
        }
        ListViewAdapter adapter = new ListViewAdapter(getActivity(), basket);
        ListViewAdapterShops adapterShops = new ListViewAdapterShops(getActivity(), pricesToDisplay.get(pricesToDisplay.size() - 1));
        list2.setAdapter(adapterShops);
        list.setAdapter(adapter);
        list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                String s = (String) adapterShops.getItem(position);
                Intent intent = new Intent(getActivity(), inShop.class);
                intent.putExtra("num", HomeFragment.shops.get(position));
                startActivity(intent);
            }
        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                removeItem(i);
                return false;
            }
        });
        return root;
    }
    // function to remove an item given its index in the grocery list.
    public static String removeItem(int i) {
        String k;
        String [] m1 = a.get(i).split(" ");
        //search basketfinal product
        if (Integer.parseInt(m1[m1.length - 1]) == 1){
            basket.remove(i);
            a.remove(i);
            k = "0";
        }
        else {
            a.set(i, String.join(" ", Arrays.copyOfRange(m1, 0, m1.length - 1)) + " " + (-1 + Integer.parseInt(m1[m1.length - 1])));
            k = a.get(i).split(" ")[m1.length - 1];
        }
        addingFinal(m1, -1);
        getResult(basketFinal);
        ArrayList<String> m = new ArrayList<>();
        for (int j = 0; j < shops.size(); j++) {
            ArrayList<String> b = checkInShop(shops.get(i));
            if (b.size() == basket.size()) {
                m.add("Магазин: " + shops.get(j) + ".\nЦена: " + x.get(x.size() - 1).get(j) + ".");
            }
            else{
                m.add("Магазин: " + shops.get(j) + ".\nЦена: " + x.get(x.size() - 1).get(j) + ".\n" + "Не все выбранные продукты");
            }
        }
        pricesToDisplay.add(m);

        ListViewAdapter adapter = new ListViewAdapter((Activity) root.getContext(), basket);
        ListViewAdapterShops adapterShops = new ListViewAdapterShops((Activity) root.getContext(), pricesToDisplay.get(pricesToDisplay.size() - 1));
        list.setAdapter(adapter);
        list2.setAdapter(adapterShops);

        return k;
    }
    //form a basket with prices
    public static ArrayList<String> getPrice(ArrayList<String> basket) {
        basketFinal = new ArrayList<String>();
        afinal = new ArrayList<>();
        for (String shop: shops) {
            for (String product : basket) {
                String k = "";
                ArrayList<String> prodforshops = new ArrayList<>();
                ArrayList<String> prodforshopsQuantities = new ArrayList<>();
                int mi = 9999999;
                for (ArrayList<String> priceWprod : allProductsPrice) {
                    if (!basketFinal.contains(String.join(" ", priceWprod)) && (calculate(priceWprod.get(1), product) < mi && priceWprod.contains(shop))) {
                        mi = calculate(priceWprod.get(1), product);
                        k = String.join(" ", priceWprod);
                    }
                }
                basketFinal.add(k);
                afinal.add(k + " 1");
            }
        }
        return basketFinal;
    }

    //forms an arraylist with prices in each shop
    public static void getResult(ArrayList<String> basketFinal){
        if (a == null) return;
        ArrayList<String> prices = new ArrayList<>();
        for (String shop: HomeFragment.shops){
            int price = 0;
            for (String ourBasket: basketFinal) {
                if (ourBasket.contains(shop)) {
                    int m = afinal.get(basketFinal.indexOf(ourBasket)).split(" ").length;
                    int k = Integer.parseInt(afinal.get(basketFinal.indexOf(ourBasket)).split(" ")[m - 1]);
                    price += Integer.parseInt(afinal.get(basketFinal.indexOf(ourBasket)).split(" ")[m - 2]) * k;
                }
            }
            prices.add(String.valueOf(price));
        }
        if (prices.size()!= 0) x.add(prices);
    }
    //функция создает список с продуктами и их ценами при нажатии на название магазина
    public static void inShoP(String num){
        inShop.clear();
        for (String i: afinal) {
            if (i.contains(num)) {
                inShop.add(i);
            }
        }
    }
    public static ArrayList<String> checkInShop(String shopName){
        ArrayList<String> likeInShop = new ArrayList<>();
        for (String i: afinal) {
            if (i.contains(shopName)) {
                likeInShop.add(i);
            }
        }
        return likeInShop;
    }
    // метод изменяет параметры страницы (пересчитывает и показывает стоимости) когда пользователь выбирает еще товар
    public static String adding(int i){
        String [] m1 = a.get(i).split(" ");
        a.set(i, String.join(" ", Arrays.copyOfRange(m1, 0, m1.length - 1)) + " " + (1 + Integer.parseInt(m1[m1.length - 1])));
        addingFinal(m1, 1);
        getResult(basketFinal);
        ArrayList<String> m = new ArrayList<>();
        for (int j = 0; j < shops.size(); j++) {
            ArrayList<String> b = checkInShop(shops.get(i));
            if (b.size() == basket.size()) {
                m.add("Магазин: " + shops.get(j) + ".\nЦена: " + x.get(x.size() - 1).get(j) + ".");
            }
            else{
                m.add("Магазин: " + shops.get(j) + ".\nЦена: " + x.get(x.size() - 1).get(j) + ".\n" + "Не все выбранные продукты");
            }
        }
        pricesToDisplay.add(m);

        ListViewAdapter adapter = new ListViewAdapter((Activity) root.getContext(), basket);
        ListViewAdapterShops adapterShops = new ListViewAdapterShops((Activity) root.getContext(), pricesToDisplay.get(pricesToDisplay.size() - 1));
        list.setAdapter(adapter);
        list2.setAdapter(adapterShops);

        String k = a.get(i).split(" ")[m1.length - 1];
        return k;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    static int calculate(String x, String y) {
        int[][] dp = new int[x.length() + 1][y.length() + 1];

        for (int i = 0; i <= x.length(); i++) {
            for (int j = 0; j <= y.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                }
                else if (j == 0) {
                    dp[i][j] = i;
                }
                else {
                    dp[i][j] = min(dp[i - 1][j - 1]
                                    + costOfSubstitution(x.charAt(i - 1), y.charAt(j - 1)),
                            dp[i - 1][j] + 1,
                            dp[i][j - 1] + 1);
                }
            }
        }

        return dp[x.length()][y.length()];
    }
    public static void addingFinal(String[] product, int amount){
        product[product.length - 1] = String.valueOf(Integer.parseInt(product[product.length - 1]) + amount);
        int k = Integer.parseInt(product[product.length - 1]);
        String prod = String.join(" ", Arrays.copyOfRange(product, 0, product.length - 1));


        //search levenstein
        int mi = 999999999;
        String li = "";
        for (String shop: shops) {
            for (ArrayList<String> priceWprod : allProductsPrice) {
                if (basketFinal.contains(String.join(" ", priceWprod)) && ((calculate(priceWprod.get(1), prod) < mi) || !li.contains(shop)) && priceWprod.contains(shop)) {
                    mi = calculate(priceWprod.get(1), prod);
                    li = String.join(" ", priceWprod);
                }
            }
            if (k > 0){
                afinal.set(basketFinal.indexOf(li), li + " " +  k);
            }
            else{
                afinal.remove(basketFinal.indexOf(li));
                basketFinal.remove(li);
            }
        }
    }
    public static int costOfSubstitution(char a, char b) {
        return a == b ? 0 : 1;
    }

    public static int min(int... numbers) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Arrays.stream(numbers).min().orElse(Integer.MAX_VALUE);
        }
        return 0;
    }
    public static boolean checkShop(String a, String shop){
        for (String k: basketFinal){
            if (k.contains(a) && k.contains(shop)){
                return false;
            }
        }
        return true;
    }

}