package com.example.firebasetesst;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterUser extends AppCompatActivity implements View.OnClickListener{

    //create the edittext for register page form
    private EditText editTextFullName, editTextPhone,editTextEmail,editTextPassword;
    private ProgressBar progressBar;
    private TextView banner, registerUser;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();

        //back to home route textView
        banner =(TextView) findViewById(R.id.banner);
        banner.setOnClickListener(this);


        registerUser=(Button) findViewById(R.id.Register);
        registerUser.setOnClickListener(this);

        editTextFullName=(EditText) findViewById(R.id.fullName);
        editTextEmail=(EditText) findViewById(R.id.RegisterEmail);
        editTextPassword=(EditText) findViewById(R.id.RegisterPassword);
        editTextPhone=(EditText) findViewById(R.id.phone);





    }

//    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.banner:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.Register:
                registerUser();

                break;

        }
    }

    private void registerUser() {
        String email=editTextEmail.getText().toString().trim();
        String password=editTextPassword.getText().toString().trim();
        String fullName=editTextFullName.getText().toString().trim();
        String phone=editTextPhone.getText().toString().trim();

        //return error
        if(fullName.isEmpty()){
            editTextFullName.setError("Full name is required!");
            editTextFullName.requestFocus();
            return;
        }

        if(phone.isEmpty()){
            editTextPhone.setError("Phone number required!");
            editTextPhone.requestFocus();
            return;
        }

        if(email.isEmpty()){
            editTextEmail.setError("Email is requred!");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please dont make joke to us!");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("you dont want a password????");
            editTextPassword.requestFocus();
            return;
        }

        //firebase
        //check if user register
//        mAuth.createUserWithEmailAndPassword(email,password)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        //if there are get meeage from editText
//                        if(task.isSuccessful()){ //wrong here
//                            User user=new User(fullName,phone,email);
//
//                            //send user to real time database
//                            FirebaseDatabase.getInstance().getReference("Users")
//                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())//return id for the user
//                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    //see if user get into firebase
//                                    if(task.isSuccessful()){
//                                        Toast.makeText(RegisterUser.this,
//                                                "User is already added!",Toast.LENGTH_LONG)
//                                                .show();
//                                    }else{
//                                        //the user have not be add,
//                                        Toast.makeText(RegisterUser.this,
//                                                "User is not be added!",Toast.LENGTH_LONG)
//                                                .show();
//                                    }
//                                }
//                            });
//
//                        }else{
//                            Toast.makeText(RegisterUser.this,
//                                    "User is not be added22!",Toast.LENGTH_LONG)
//                                    .show();
//                        }
//
//                    }
//                });

        //second try
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(fullName,phone,email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterUser.this,"User has been registered successful!",Toast.LENGTH_LONG).show();
                                    }else{
                                        Toast.makeText(RegisterUser.this,"User not registered successful!",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(RegisterUser.this,"User not registered successful2!",Toast.LENGTH_LONG).show();
                        }
                    }
                });


    }




}