package com.codepath.rkpandey.swap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

       Button SignOutButton = findViewById(R.id.signOutButton);
       Button YouTubeButton = findViewById(R.id.youtubebtn);
       SignOutButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               FirebaseAuth.getInstance().signOut();
               startActivity(new Intent(HomeActivity.this,MainActivity.class));
               finish();
           }
       });
       YouTubeButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(HomeActivity.this, YouTubeMainActivity.class));
               finish();
           }
       });
    }
}