package com.codepath.rkpandey.SocialWatchParty;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends FragmentActivity implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;

    String Fullname, Email, Phone;
    String searchInput, mRecyclerView, btnSearch;
    Button YoutubeButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        DrawerLayout drawerLayout = findViewById(R.id.drawerlayout);
        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START );
            }
        });

        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        NavController navController = Navigation.findNavController(this, R.id.navHostFragment );
        Navigation.setViewNavController(navigationView, navController );

        ImageView youtubeImage = findViewById(R.id.youtubeImage);

//       Button SignOutButton = findViewById(R.id.signOutButton);
       //Button YouTubeButton = findViewById(R.id.youtubebtn);
//        Button ProfileButton = findViewById(R.id.button_profile);
//       SignOutButton.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//               FirebaseAuth.getInstance().signOut();
//               startActivity(new Intent(HomeActivity.this,MainActivity.class));
//               finish();
//           }
//       });
//       YouTubeButton.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//               startActivity(new Intent(HomeActivity.this, YouTubeMainActivity.class));
//               finish();
//           }
//       });
//       ProfileButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
//                finish();
//            }
//        });
        youtubeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, YouTubeMainActivity.class));
                finish();
            }
        });
   }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuProfile:
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                intent.putExtra("Fullname", Fullname);
                intent.putExtra("Email",Email);
                intent.putExtra("Phone",Phone);
                startActivity(intent);
                break;
        }

        switch (item.getItemId()){
            case R.id.menuYoutubee:
                Intent intent = new Intent(HomeActivity.this, YouTubeMainActivity.class);
                intent.putExtra("searchInput", searchInput);
                intent.putExtra("mRecyclerView", mRecyclerView);
                intent.putExtra("btnSearch",btnSearch);
                startActivity(intent);
                break;
        }

        switch (item.getItemId()){
            case R.id.menulogout:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }
        return true;

    }
}
