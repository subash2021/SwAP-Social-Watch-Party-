package com.codepath.rkpandey.SocialWatchParty.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.rkpandey.SocialWatchParty.HomeActivity;
import com.codepath.rkpandey.SocialWatchParty.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInFragment extends Fragment {
    private EditText signInEmail;
    private EditText signInPassword;
    private Button SignInButton;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialogue;
    public SignInFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view  = inflater.inflate(R.layout.fragment_sign_in, container, false);

       //
        signInEmail = view.findViewById(R.id.SigninEmail);
        signInPassword = view.findViewById(R.id.SignInPassword);
        SignInButton = view.findViewById(R.id.signInButton);
        mAuth = FirebaseAuth.getInstance();
        progressDialogue = new ProgressDialog(getContext());
        progressDialogue.setTitle("Signing In");
        progressDialogue.setMessage("Please wait signing in");
        //
        SignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = signInEmail.getText().toString();
                String Password = signInPassword.getText().toString();
                if(!Email.isEmpty() && !Password.isEmpty()){
                    progressDialogue.show();
                    mAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                progressDialogue.dismiss();
                                Intent intent = new Intent(getContext(), HomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                getActivity().finish();
                            }else{
                                progressDialogue.dismiss();
                                Toast.makeText(getContext(), "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialogue.dismiss();
                            e.printStackTrace();
                        }
                    });

                }
            }
        });


       return view;
    }

}