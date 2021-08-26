package com.codepath.rkpandey.swap.fragments;

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

import com.codepath.rkpandey.swap.HomeActivity;
import com.codepath.rkpandey.swap.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpFragment extends Fragment {
    private FirebaseAuth mAuth;
    private EditText FullName;
    private EditText SignUpEmail;
    private EditText signUpPassword;
    private EditText SignUpPasswordConfirm;
    private Button SignUpButton;
    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        mAuth = FirebaseAuth.getInstance();
        FullName = view.findViewById(R.id.signUpName);
        SignUpEmail = view.findViewById(R.id.signUpEmail);
        signUpPassword = view.findViewById(R.id.signUpPassword);
        SignUpPasswordConfirm = view.findViewById(R.id.confirmPassword);
        SignUpButton = view.findViewById(R.id.signUpButton);

        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = SignUpEmail.getText().toString();
                String Password = signUpPassword.getText().toString();
                mAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
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