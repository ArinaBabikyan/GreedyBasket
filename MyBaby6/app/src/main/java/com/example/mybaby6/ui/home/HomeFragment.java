package com.example.mybaby6.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mybaby6.MyInterface;
import com.example.mybaby6.R;
import com.example.mybaby6.databinding.FragmentHomeBinding;
import com.example.mybaby6.ui.dashboard.DashboardFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class HomeFragment extends Fragment implements MyInterface {
    public ArrayAdapter adapter;
    private FragmentHomeBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final ListView list = binding.theList;
        final EditText thefilter = binding.searchFilter;
        final Button button = binding.button3;
        //search a product
        getData();
        adapter = new ArrayAdapter(getActivity(), R.layout.list_item_layout, basket);
        list.setAdapter(adapter);
        //choose a product
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = thefilter.getText().toString();
                if (!basket.contains(s)) {
                    basket.add(s);
                    //FormArrayA();
                    list.setAdapter(adapter);
                }
                thefilter.setText("");
            }
        });
        return root;
    }
    public static void FormArrayA(){
        for (String i: basket) {
            if (!DashboardFragment.a.contains(i + " 1")) DashboardFragment.a.add(i + " 1");
        }
    }

    public void getData(){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        DataSnapshot dataSnapshot = task.getResult();
                        for (Object shop: ((HashMap<String, HashMap>) dataSnapshot.getValue()).keySet().toArray()){
                            for (Object type:((HashMap<String, HashMap>) dataSnapshot.child((String) shop).getValue()).keySet().toArray()) {
                                for (Object brand : ((HashMap<String, HashMap>) dataSnapshot.child((String) shop).child((String) type).getValue()).keySet().toArray()) {
                                    allProductsPrice.add(new ArrayList<String>(Arrays.asList((String) shop,
                                            (String) type + " " + (String) brand,
                                            String.valueOf(dataSnapshot.child((String) shop).child((String) type).child((String) brand).child("Цена").getValue()))));
                                    allProductsLink.add(new ArrayList<String>(Arrays.asList((String) shop,
                                            String.valueOf(dataSnapshot.child((String) shop).child((String) type).child((String) brand).child("Ссылка").getValue()))));
                                    if (!allProducts.contains((String) type + " " + (String) brand)) {
                                        allProducts.add((String) type + " " + (String) brand);
                                    }
                                }
                                if (!categories.contains((String) type)){
                                    categories.add((String) type);
                                }
                            }
                            if (!shops.contains(shop)) shops.add((String) shop);
                        }
                    }
                }
                else {
                    Toast.makeText(getActivity(), "Sorry, service is unavailable", Toast.LENGTH_SHORT).show();
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                }
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}