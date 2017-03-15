package com.mobile.hyerim.mobiletermproject.activities;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import com.mobile.hyerim.mobiletermproject.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

public class LoginActivity extends BaseActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    private final static String TAG = LoginActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 9001;

    /* View Component */
    private RelativeLayout mLoginGoogleLayout;

    /* Firebase Auth */
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    /* Firebase Database */
    private DatabaseReference mUserReference;
    private ValueEventListener mUserListener;

    /* Google Login */
    private GoogleApiClient mGoogleApiClient;

    public LoginActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        initFirebase();
        initGoogle();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void init() {
        mLoginGoogleLayout = (RelativeLayout) findViewById(R.id.login_google_layout);
        mLoginGoogleLayout.setOnClickListener(this);
    }

    private void initFirebase() {
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    ToastUtil.makeShortToast(LoginActivity.this, "로그인중입니다");

                    saveUserProfileToFirebase(user);
                    saveUserInLocalDevice(user);

                    callMainActivity();
                }
            }
        };
    }

    private void initGoogle() {
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        ToastUtil.makeShortToast(this, "Google Play Services error.");
    }

    private void callMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.fade, R.anim.hold);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_google_layout:
                onClickLoginGoogleLayout();
                break;
            default:
                break;
        }
    }

    private void onClickLoginGoogleLayout() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {

            Log.d(TAG, "onActivityResult data : " + data);

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            Log.d(TAG, "onActivityResult result : " + result.isSuccess());

            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                handleGoogleAccessToken(account);
            } else {
                // Google Sign In failed, update UI appropriately
                saveUserInLocalDevice(null);
            }
        }
    }

    private void handleGoogleAccessToken(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        // TODO : show Progress

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            ToastUtil.makeShortToast(LoginActivity.this, "Authentication failed");
                        }
                        // TODO : hide Progress
                    }
                });
    }

    private void saveUserProfileToFirebase(FirebaseUser user) {
        if (user != null) {
            String firebaseUid = user.getUid();
            String userEmail = user.getEmail();
            String userName = user.getDisplayName();
            String profilePictureUrl = user.getPhotoUrl().toString();
            UserController.createUser(firebaseUid, userEmail, userName, profilePictureUrl);
        }
    }

    private void saveUserInLocalDevice(FirebaseUser user) {
        if (user != null) {
            String firebaseUid = user.getUid();
            String userEmail = user.getEmail();
            String userName = user.getDisplayName();
            String profilePictureUrl = user.getPhotoUrl().toString();

            UserUtil.saveUserFirebaseUid(firebaseUid);
            UserUtil.saveUserEmail(userEmail);
            UserUtil.saveUserName(userName);
            UserUtil.saveUserProfilePictureUrl(profilePictureUrl);
        }
        else {
            UserUtil.saveUserFirebaseUid(null);
        }
    }
}
