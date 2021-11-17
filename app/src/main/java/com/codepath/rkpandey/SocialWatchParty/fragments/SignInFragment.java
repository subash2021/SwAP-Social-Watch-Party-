package com.codepath.rkpandey.SocialWatchParty.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.rkpandey.SocialWatchParty.HomeActivity;
import com.codepath.rkpandey.SocialWatchParty.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class SignInFragment extends Fragment {
    private EditText signInEmail;
    private EditText signInPassword;
    private Button SignInButton;
    private TextView forgotTextLink;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialogue;
    private static final Pattern password_pattern = Pattern.compile("(?=.*[@#$%^&+=])") ;

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
        forgotTextLink = view.findViewById(R.id.forgotpassword);
        progressDialogue = new ProgressDialog(getContext());
        progressDialogue.setTitle("Signing In");
        progressDialogue.setMessage("Please wait signing in");
        //
        SignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = signInEmail.getText().toString();
                String Password = signInPassword.getText().toString();

                if(Email.isEmpty() & Password.isEmpty())
                {
                    Toast.makeText(getContext(), "Email and Password cannot be empty", Toast.LENGTH_SHORT).show();
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

        forgotTextLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText resetMail = new EditText(view.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
                passwordResetDialog.setTitle("Reset Password ?");
                passwordResetDialog.setMessage("Enter your Email.");
                passwordResetDialog.setView((resetMail));

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String mail = resetMail.getText().toString();
                        mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(@NonNull Void unused) {
                                Toast.makeText(getContext(), "Reset link sent to your Email", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Error!! Link is not sent", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                passwordResetDialog.create().show();
            }
        });


       return view;
    }

}