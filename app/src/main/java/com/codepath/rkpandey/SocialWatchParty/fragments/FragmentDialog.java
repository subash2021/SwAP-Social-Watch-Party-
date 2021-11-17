package com.codepath.rkpandey.SocialWatchParty.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.rkpandey.SocialWatchParty.R;
import com.codepath.rkpandey.SocialWatchParty.data.model.Users;
import com.codepath.rkpandey.SocialWatchParty.data.model.UsersAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentDialog extends DialogFragment {
    ArrayList<Users> list = new ArrayList<>();
    FirebaseDatabase database;
    RecyclerView recyclerView;
    @NonNull
    @Override

    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.dialog_layout,null);
        ImageButton back_btn = dialogLayout.findViewById(R.id.back_bt);
        ImageButton msg_btn = dialogLayout.findViewById(R.id.mesg_bt);
        TextView text = dialogLayout.findViewById(R.id.text_vi);
        AlertDialog.Builder builder =new AlertDialog.Builder(getActivity());
        builder.setTitle("CHATS");
        builder.setView(dialogLayout);
        recyclerView = (RecyclerView) dialogLayout.findViewById(R.id.dialog_recycler);
        recyclerView.hasFixedSize();
        database = FirebaseDatabase.getInstance();
        UsersAdapter adapter = new UsersAdapter(list,getContext());
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
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

        return builder.create();
    }
}
