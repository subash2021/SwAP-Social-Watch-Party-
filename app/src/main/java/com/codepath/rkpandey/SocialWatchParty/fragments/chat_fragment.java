package com.codepath.rkpandey.SocialWatchParty.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codepath.rkpandey.SocialWatchParty.R;
import com.codepath.rkpandey.SocialWatchParty.data.model.Users;
import com.codepath.rkpandey.SocialWatchParty.data.model.UsersAdapter;
import com.codepath.rkpandey.SocialWatchParty.databinding.FragmentChatFragmentBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class chat_fragment extends Fragment {



    public chat_fragment() {
        // Required empty public constructor
    }


    FragmentChatFragmentBinding binding;

    ArrayList<Users> list = new ArrayList<>();
    FirebaseDatabase database;
    //RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentChatFragmentBinding.inflate(inflater, container, false);
        database = FirebaseDatabase.getInstance();
        UsersAdapter adapter = new UsersAdapter(list,getContext());
        binding.chatsrecyclerview.setAdapter(adapter);

        //binding.recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.chatsrecyclerview.setLayoutManager(layoutManager);
        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Users users = dataSnapshot.getValue(Users.class);
                    users.setUserId(dataSnapshot.getKey());
                    list.add(users);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //getActivity().getSupportFragmentManager().beginTransaction().remove().commit();

                getActivity().onBackPressed();
                //Toast toast = Toast.makeText(getActivity(),"ads",Toast.LENGTH_SHORT);
                //toast.setGravity(Gravity.TOP| Gravity.CENTER_HORIZONTAL, 0, 0);
                //toast.show();
               // FragmentTransaction = frag;





            }
        });



        return binding.getRoot();

    }
}