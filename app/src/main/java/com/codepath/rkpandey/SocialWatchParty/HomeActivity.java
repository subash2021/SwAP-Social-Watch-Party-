package com.codepath.rkpandey.SocialWatchParty;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.codepath.rkpandey.SocialWatchParty.databinding.ActivityHomeBinding;
import com.codepath.rkpandey.SocialWatchParty.fragments.FragmentDialog;
import com.codepath.rkpandey.SocialWatchParty.fragments.chat_fragment;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

        ActivityHomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //getSupportActionBar().hide();



       binding.signOutButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               FirebaseAuth.getInstance().signOut();
               startActivity(new Intent(HomeActivity.this,MainActivity.class));
               finish();
           }
       });
       binding.youtubebtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(HomeActivity.this, YouTubeMainActivity.class));
               finish();
           }
       });
       binding.chat.setOnClickListener(new View.OnClickListener(){

           @Override
           public void onClick(View view) {
              //adding a fragment
               getSupportFragmentManager().beginTransaction().replace(R.id.container_home,new chat_fragment()).commit();

           }
       });


    }
}
