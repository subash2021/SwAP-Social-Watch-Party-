package com.codepath.rkpandey.SocialWatchParty.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.rkpandey.SocialWatchParty.HomeActivity;
import com.codepath.rkpandey.SocialWatchParty.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpFragment extends Fragment {
    public static final String TAG = "tag";
    private FirebaseAuth mAuth;
    private EditText FullName;
    private EditText SignUpEmail;
    private EditText signUpPassword;
    private EditText SignUpPasswordConfirm;
    private Button SignUpButton;
    FirebaseFirestore fstore;
    String userID;
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
        fstore = FirebaseFirestore.getInstance();

        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = SignUpEmail.getText().toString();
                String Password = signUpPassword.getText().toString();
                String fullname = FullName.getText().toString();

                if(Email.isEmpty() & Password.isEmpty() & fullname.isEmpty())
                {
                    Toast.makeText(getContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(fullname.isEmpty())
                {
                    Toast.makeText(getContext(), "Fullname cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(Email.isEmpty())
                {
                    Toast.makeText(getContext(), "Email cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches())
                {
                    Toast.makeText(getContext(), "Invalid email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Password.isEmpty())
                {
                    Toast.makeText(getContext(), "Password cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Password.length() < 6)
                {
                    Toast.makeText(getContext(), "Password cannot be less than 6 characters", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (signUpPassword != SignUpPasswordConfirm)
                {
                    Toast.makeText(getContext(), "Password does't match", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if(task.isSuccessful()){
                            Toast.makeText(getContext(), "Account is Created Sucessfully", Toast.LENGTH_SHORT).show();
                            userID = mAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fstore.collection("users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("fName", fullname);
                            user.put("Email", Email);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(@NonNull Void unused) {
                                    Log.d(TAG, "Onsuccess: User Profile is Created for "+userID);
                                }
                            });
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