package com.github.syedahmedjamil.pushier;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.TextView;

import com.github.syedahmedjamil.pushier.R;
import com.github.syedahmedjamil.pushier.databinding.ActivityInterestsBinding;
import com.google.gson.Gson;
import com.pusher.pushnotifications.PushNotifications;

import java.util.ArrayList;
import java.util.List;

public class InterestsActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences globalSP;
    private SharedPreferences dataSP;
    private NotificationAdapter notificationAdapter;
    private List<NotificationItem> notificationItems;
    private ActivityInterestsBinding binding;
    private RecyclerView recyclerView;
    MenuItem selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        binding = ActivityInterestsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        globalSP = getSharedPreferences("global", MODE_PRIVATE);
        dataSP = getSharedPreferences("data", MODE_PRIVATE);
        String alias = globalSP.getString("alias", null);
        String instanceId = globalSP.getString("instanceId", null);
        if (instanceId == null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        PushNotifications.start(getApplicationContext(), instanceId);

        TextView aliasTextView = binding.navView.getHeaderView(0).findViewById(R.id.navView_alias);
        TextView instanceIdTextView = binding.navView.getHeaderView(0).findViewById(R.id.navView_instanceId);
        aliasTextView.setText(alias);
        instanceIdTextView.setText(instanceId);

        SubMenu subMenu = binding.navView.getMenu().getItem(0).getSubMenu();
        int id = 1;
        for (String interest : PushNotifications.getDeviceInterests()) {
            MenuItem item = subMenu.add(R.id.interest,id,Menu.NONE,interest);
            id++;

        }

        notificationItems = new ArrayList<>();

        binding.appBar.materialToolbar.setNavigationOnClickListener(view -> {
            binding.drawerLayout.open();
        });
        binding.appBar.materialToolbar.setOnMenuItemClickListener(item -> {

            if(item.getItemId()== R.id.menu_refresh)
            {
                notificationItems.clear();
                Gson gson = new Gson();
                sharedPreferences = getSharedPreferences(selectedItem.getTitle().toString(), MODE_PRIVATE);
                int startIndex = dataSP.getInt(selectedItem.getTitle().toString() + "-startIndex", -1);
                int endIndex = dataSP.getInt(selectedItem.getTitle().toString() + "-endIndex", -1);
                if(startIndex == -1 || endIndex == -1){
                    notificationAdapter.notifyDataSetChanged();
                    return true;
                }

                for (int i = startIndex; i != endIndex; ) {
                    String json = sharedPreferences.getString(String.valueOf(i), null);
                    NotificationItem notificationItem = gson.fromJson(json, NotificationItem.class);
                    notificationItems.add(notificationItem);
                    i = (i+1) % Global.MAX_NOTIFICATIONS;
                }
                String json = sharedPreferences.getString(String.valueOf(endIndex), null);
                NotificationItem notificationItem = gson.fromJson(json, NotificationItem.class);
                notificationItems.add(notificationItem);
                notificationAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(notificationItems.size()-1);
            }
            if(item.getItemId()== R.id.menu_clear)
            {
                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setTitle("Hold On !")
                        .setMessage("Are you sure you want to delete all notifications ?")
                        .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                sharedPreferences = getSharedPreferences(selectedItem.getTitle().toString(), MODE_PRIVATE);
                                sharedPreferences.edit().clear().apply();
                                dataSP.edit().putInt(selectedItem.getTitle().toString() + "-startIndex",-1).apply();
                                dataSP.edit().putInt(selectedItem.getTitle().toString() + "-endIndex",-1).apply();
                                notificationItems.clear();
                                notificationAdapter.notifyDataSetChanged();
                            }
                        })
                        .create();
                alertDialog.show();

            }
            return true;
        });
        binding.navView.setNavigationItemSelectedListener(item -> {
            if (item.getTitle().equals("Disconnect")) {
                PushNotifications.stop();
                globalSP.edit().clear().apply();
                dataSP.edit().remove("selectedItem").apply();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            }
            selectedItem = item;
            dataSP.edit().putInt("selectedItem", selectedItem.getItemId()).apply();
            selectedItem.setCheckable(true);
            binding.navView.setCheckedItem(selectedItem);
            binding.drawerLayout.close();
            notificationItems.clear();
            Gson gson = new Gson();
            sharedPreferences = getSharedPreferences(selectedItem.getTitle().toString(), MODE_PRIVATE);
            int startIndex = dataSP.getInt(selectedItem.getTitle().toString() + "-startIndex", -1);
            int endIndex = dataSP.getInt(selectedItem.getTitle().toString() + "-endIndex", -1);
            if(startIndex == -1 || endIndex == -1){
                notificationAdapter.notifyDataSetChanged();
                return true;
            }

            for (int i = startIndex; i != endIndex; ) {
                String json = sharedPreferences.getString(String.valueOf(i), null);
                NotificationItem notificationItem = gson.fromJson(json, NotificationItem.class);
                notificationItems.add(notificationItem);
                i = (i+1) % Global.MAX_NOTIFICATIONS;
            }
            String json = sharedPreferences.getString(String.valueOf(endIndex), null);
            NotificationItem notificationItem = gson.fromJson(json, NotificationItem.class);
            notificationItems.add(notificationItem);
            notificationAdapter.notifyDataSetChanged();
            recyclerView.smoothScrollToPosition(notificationItems.size()-1);
            return true;
        });

        recyclerView = findViewById(R.id.notifications_recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        notificationAdapter = new NotificationAdapter(this, notificationItems);
        notificationAdapter.setClickListener((view, position) ->
        {
            Uri webpage = Uri.parse(notificationItems.get(position).link);
            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Intent chooser = Intent.createChooser(intent, "Select Browser");
            startActivity(chooser);
        });
        recyclerView.setAdapter(notificationAdapter);

        int index = dataSP.getInt("selectedItem", -1);
        if(index == -1) index = 1;
        selectedItem = binding.navView.getMenu().getItem(0).getSubMenu().findItem(index);
        if(selectedItem != null){
            binding.appBar.materialToolbar.getMenu().getItem(0).setVisible(true);
            binding.appBar.materialToolbar.getMenu().getItem(1).setVisible(true);
            selectedItem.setCheckable(true);
            binding.navView.setCheckedItem(selectedItem);
            notificationItems.clear();
            Gson gson = new Gson();
            sharedPreferences = getSharedPreferences(selectedItem.getTitle().toString(), MODE_PRIVATE);
            int startIndex = dataSP.getInt(selectedItem.getTitle().toString() + "-startIndex", -2);
            int endIndex = dataSP.getInt(selectedItem.getTitle().toString() + "-endIndex", -2);
            if(startIndex == -1 || endIndex == -1){
                notificationAdapter.notifyDataSetChanged();
            }
            else {
                for (int i = startIndex; i != endIndex; ) {
                    String json = sharedPreferences.getString(String.valueOf(i), null);
                    NotificationItem notificationItem = gson.fromJson(json, NotificationItem.class);
                    notificationItems.add(notificationItem);
                    i = (i + 1) % Global.MAX_NOTIFICATIONS;
                }
                String json = sharedPreferences.getString(String.valueOf(endIndex), null);
                NotificationItem notificationItem = gson.fromJson(json, NotificationItem.class);
                notificationItems.add(notificationItem);
                notificationAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(notificationItems.size() - 1);
            }
        }

    }

}