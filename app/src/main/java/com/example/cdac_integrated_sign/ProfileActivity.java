package com.example.cdac_integrated_sign;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class ProfileActivity extends AppCompatActivity {
    GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClient;

    TextView id,email,name;
    ImageView profilepic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        id = findViewById(R.id.id);
        email = findViewById(R.id.email);
        name = findViewById(R.id.name);
        profilepic = findViewById(R.id.profilepic);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        //get last sign in account details
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account==null) Toast.makeText(this, "You are not sign up yet, error", Toast.LENGTH_LONG).show();

        id.setText( account.getId() );
        email.setText( account.getEmail() );
        name.setText( account.getDisplayName() );

        Uri photo = account.getPhotoUrl();
        Glide.with(this)
                .load(photo)
                .centerCrop()
                .override(200,200)
                .into(profilepic);
    }

    public void signout(View view) {
        mGoogleSignInClient.signOut().addOnCompleteListener(ProfileActivity.this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getApplicationContext(), "successfully signout",Toast.LENGTH_LONG).show();
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}
