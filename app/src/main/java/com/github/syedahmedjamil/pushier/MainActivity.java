package com.github.syedahmedjamil.pushier;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.github.syedahmedjamil.pushier.R;
import com.github.syedahmedjamil.pushier.databinding.ActivityInstanceBinding;
import com.pusher.pushnotifications.PushNotifications;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private SharedPreferences dataSP;
    private SharedPreferences localSP;
    private SharedPreferences globalSP;
    private Uri selectedImageUri;
    private InterestAdapter interestAdapter;
    private List<InterestItem> interestItems;
    private ActivityInstanceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInstanceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        globalSP = getSharedPreferences("global", MODE_PRIVATE);
        localSP = getSharedPreferences("local", MODE_PRIVATE);
        //Load stored interests from shared preferences
        interestItems = new ArrayList<>();
        dataSP = getSharedPreferences("data", MODE_PRIVATE);
        Map<String, ?> allSharedPreferences = dataSP.getAll();
        for (Map.Entry<String, ?> entry : allSharedPreferences.entrySet()) {
            if (entry.getValue() instanceof String) {
                interestItems.add(new InterestItem(entry.getKey(), entry.getValue().toString()));
            }
        }

        //initialize recyclerView with loaded interests
        RecyclerView recyclerView = findViewById(R.id.interests_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        interestAdapter = new InterestAdapter(this, interestItems);
        interestAdapter.setRemoveItemClickListener(this::onRemoveInterestClick);
        recyclerView.setAdapter(interestAdapter);

        //misc
        String al = localSP.getString("calias", null);
        String inid = localSP.getString("cinstanceId", null);
        if (al != null && inid != null) {
            binding.instanceId.getEditText().setText(inid);
            binding.alias.getEditText().setText(al);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100) {
            assert data != null;
            selectedImageUri = data.getData();
//          binding.interestImage.setImageURI(selectedImageUri);
            getContentResolver().takePersistableUriPermission(selectedImageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
    }

    public void onConnectClick(View view) {
        String instanceId = binding.instanceId.getEditText().getText().toString();
        String alias = binding.alias.getEditText().getText().toString();

        if (instanceId.equals("") || alias.equals("")) {
            Toast.makeText(this, "Please enter alias and instance ID", Toast.LENGTH_SHORT).show();
            return;
        }

        PushNotifications.start(getApplicationContext(), instanceId);
        PushNotifications.clearDeviceInterests();
        for (InterestItem interestItem : interestItems) {
            PushNotifications.addDeviceInterest(interestItem.interestName);
        }


        globalSP.edit().putString("alias", alias).apply();
        globalSP.edit().putString("instanceId", instanceId).apply();
        localSP.edit().putString("calias", alias).apply();
        localSP.edit().putString("cinstanceId", instanceId).apply();
        Intent intent = new Intent(this, InterestsActivity.class);
        startActivity(intent);
        finish();
    }

    public void onAddIconClick(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        startActivityForResult(Intent.createChooser(intent, "Select Icon Image"), 100);
    }

    public void onAddInterestClick(View view) {
        String interestName = binding.interestName.getEditText().getText().toString();
        if (interestName.equals("")) {
            Toast.makeText(this, "Please enter interest name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (interestName.contains(" ")) {
            Toast.makeText(this, "Please enter interest name without spaces", Toast.LENGTH_SHORT).show();
            return;
        }
//        Bitmap icon = null;
//        try {
//            icon = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImageUri));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        icon.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] b = baos.toByteArray();
//        String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
//        dataSP.edit().putString(interestName, encodedImage).apply();
        if (dataSP.contains(interestName)) {
            Toast.makeText(this, "Interest already added", Toast.LENGTH_SHORT).show();
            return;
        }

        dataSP.edit().putInt(interestName + "-count", 0).apply();
        dataSP.edit().putInt(interestName + "-startIndex", -1).apply();
        dataSP.edit().putInt(interestName + "-endIndex", -1).apply();
        dataSP.edit().putString(interestName, interestName).apply();
        interestItems.add(new InterestItem(interestName, interestName));
        interestAdapter.notifyItemInserted(interestItems.size() - 1);

        binding.interestName.getEditText().getText().clear();
        binding.interestName.getEditText().clearFocus();
//      binding.interestImage.setImageResource(R.drawable.ic_twotone_add_photo_alternate_24);
        selectedImageUri = null;
    }

    public void onRemoveInterestClick(View view, int position) {
        dataSP.edit().remove(interestItems.get(position).interestName).apply();
        dataSP.edit().remove(interestItems.get(position).interestName + "-count").apply();
        dataSP.edit().remove(interestItems.get(position).interestName + "-startIndex").apply();
        dataSP.edit().remove(interestItems.get(position).interestName + "-endIndex").apply();
        interestItems.remove(position);
        interestAdapter.notifyItemRemoved(position);
    }

}