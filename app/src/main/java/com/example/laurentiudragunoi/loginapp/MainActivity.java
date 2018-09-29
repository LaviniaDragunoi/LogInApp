package com.example.laurentiudragunoi.loginapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final String ANONYMOUS = "anonymous";
    public static final String USER_NAME = "userName";
    String userName;
    private static final int RC_SIGN_IN = 2;
    @BindView(R.id.browse)
    Button browseFurtherActivity;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mFirebaseAuth = FirebaseAuth.getInstance();

     mAuthStateListener = new FirebaseAuth.AuthStateListener() {
         @Override
         public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
             FirebaseUser user = firebaseAuth.getCurrentUser();
             if(user != null){
                 //user is signed in
                 onSignedInInitialize(user.getDisplayName());
             }else {
                 //user is signed out
                 onSignedOutCleanUp();
                 // Choose authentication providers
                 List<AuthUI.IdpConfig> providers = Arrays.asList(
                         new AuthUI.IdpConfig.EmailBuilder().build(),
                         new AuthUI.IdpConfig.GoogleBuilder().build());

// Create and launch sign-in intent
                 startActivityForResult(
                         AuthUI.getInstance()
                                 .createSignInIntentBuilder()
                                 .setIsSmartLockEnabled(false)
                                 .setAvailableProviders(providers)
                                 .build(),
                         RC_SIGN_IN);
             }

         }
     };

    }

    //verifies if the SingedIn process result is ok and if is not it will finish and leave the app
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == RC_SIGN_IN){
            if(resultCode == RESULT_OK){
                Toast.makeText(this, "Signed in!", Toast.LENGTH_LONG).show();
            }else if(resultCode == RESULT_CANCELED){
                Toast.makeText(this, "Signed in canceled!", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    private void onSignedInInitialize(String displayName) {
        userName = displayName;
        setTitle(userName);
        attachBrowseEmployeeList();
    }

    private void attachBrowseEmployeeList() {
        browseFurtherActivity.setVisibility(View.VISIBLE);
        browseFurtherActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserActivity.class);
                intent.putExtra(USER_NAME, userName);
                startActivity(intent);
            }
        });

    }

    private void onSignedOutCleanUp() {
        userName = ANONYMOUS;
        detachBrowseEmployeeList();
    }

    private void detachBrowseEmployeeList() {
        browseFurtherActivity.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
        detachBrowseEmployeeList();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.user_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sign_out:
                AuthUI.getInstance().signOut(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
