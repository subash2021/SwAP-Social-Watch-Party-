package com.codepath.rkpandey.swap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.codepath.rkpandey.swap.fragments.SignInFragment;
import com.codepath.rkpandey.swap.fragments.SignUpFragment;
import com.codepath.rkpandey.swap.viewPageAdapter.ViewPageAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewpager;
    private TabLayout mTabs;
    private ViewPageAdapter adapter;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewpager = findViewById(R.id.viewPager);
        mTabs = findViewById(R.id.tabLayout);
        adapter = new ViewPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new SignInFragment(),"Sign In");
        adapter.addFragment(new SignUpFragment(),"Sign Up");
        mViewpager.setAdapter(adapter);
        mTabs.setupWithViewPager(mViewpager);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()!=null){
            Intent intent = new Intent(MainActivity.this,HomeActivity.class);
            startActivity(intent);
        }
    }
}