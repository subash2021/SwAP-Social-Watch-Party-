package com.codepath.rkpandey.SocialWatchParty.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.rkpandey.SocialWatchParty.HomeActivity;
import com.codepath.rkpandey.SocialWatchParty.R;
import com.codepath.rkpandey.SocialWatchParty.data.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class    SignUpFragment extends Fragment {
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference reference;

    private EditText FullName;
    private EditText SignUpEmail;
    private EditText signUpPassword;
    private EditText SignUpPasswordConfirm;
    private Button SignUpButton;
    private ProgressDialog progressDialogue;
    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        FullName = view.findViewById(R.id.signUpName);
        SignUpEmail = view.findViewById(R.id.signUpEmail);
        signUpPassword = view.findViewById(R.id.signUpPassword);
        SignUpPasswordConfirm = view.findViewById(R.id.confirmPassword);
        SignUpButton = view.findViewById(R.id.signUpButton);
        progressDialogue = new ProgressDialog(getContext());
        progressDialogue.setTitle("Signing Up");
        progressDialogue.setMessage("Please wait signing up");

        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Uname =FullName.getText().toString();
                String Email = SignUpEmail.getText().toString();
                String Password = signUpPassword.getText().toString();
                String Confirm_pw = SignUpPasswordConfirm.getText().toString();
                if(TextUtils.isEmpty(Uname)){
                    FullName.setError("Please enter fullname");
                    FullName.requestFocus();
                    return;
                }
                else if(TextUtils.isEmpty(Email)){
                    SignUpEmail.setError("Please enter Email");
                    SignUpEmail.requestFocus();
                    return;
                }
                else if(TextUtils.isEmpty(Password) || Password.length()<6){
                    signUpPassword.setError("Please re-enter password");
                    signUpPassword.requestFocus();
                    return;
                }
               /* else if(Password != Confirm_pw)
                {
                    SignUpPasswordConfirm.setError("Password did not match. Please re-enter");
                    SignUpPasswordConfirm.requestFocus();
                    return;
                }*/
                progressDialogue.show();
                mAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialogue.dismiss();
                        if(task.isSuccessful()){
                            Users user = new Users(Uname,Email,Password);
                            String id = task.getResult().getUser().getUid();
                            database.getReference().child("Users").child(id).setValue(user);
                            Toast.makeText(getContext(), "Account Creation Successful", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getContext(), HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            getActivity().finish();
                        }
                        else{
                            Toast.makeText(getContext(), ""+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        return view;
    }
}