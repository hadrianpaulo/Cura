package com.beh.colim.cura;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.firebase.client.*;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.io.IOException;

import static com.firebase.client.Firebase.*;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener
{
    private static final String TAG = "LoginActivity";
    private static final int RC_SIGN_IN = 9001;

    private Toolbar toolbar;
    private EditText m_et_uname;
    private EditText m_et_pw;

    private Firebase mFirebaseRef;
    private ProgressDialog mPdAuth;
    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInOptions mGso;

    private CuraApplication app;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Login");
        setSupportActionBar(toolbar);

        // application
        app = (CuraApplication) getApplication();

        // edit texts
        m_et_uname = (EditText) findViewById(R.id.et_login_uname);
        m_et_pw = (EditText) findViewById(R.id.et_login_pw);

        // firebase setup
        setAndroidContext(this);
        mFirebaseRef = new Firebase("https://cura.firebaseio.com/");

        // signing in variables
        mPdAuth = new ProgressDialog(this);

        mGso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, mGso)
                .build();
    }

    private void progressDialog(boolean show, String message){
        if(show) {
            mPdAuth.setTitle("Loading..");
            mPdAuth.setMessage(message);
            mPdAuth.show();
        }
        else {
            mPdAuth.hide();
        }
    }

    public void login_uname_pw(View view){
        final String s_uname = m_et_uname.getText().toString();
        final String s_pw = m_et_pw.getText().toString();

        progressDialog(true, "Logging in using email and password..");

        mFirebaseRef.authWithPassword(s_uname, s_pw, new AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                progressDialog(false, "");
                Log.d(TAG, "Successful login");
                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                startActivity(intent);
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                progressDialog(false, "");
                Toast.makeText(LoginActivity.this, "Enter registered user credentials",
                        Toast.LENGTH_SHORT).show();
                Log.e(TAG, String.valueOf(firebaseError));
            }
        });
    }

    public void login_google(View view){
        progressDialog(true, "Loading google accounts...");
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        progressDialog(false, "");
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        progressDialog(true, "Signing in...");
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            Toast.makeText(LoginActivity.this, acct.getDisplayName(), Toast.LENGTH_SHORT).show();
            app.setM_s_username(acct.getDisplayName());

            final String email = acct.getEmail();
            AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
                String errorMessage = null;
                @Override
                protected String doInBackground(Void... voids) {
                    String scopes = "oauth2:profile email";
                    String token = null;
                    try {
                        token = GoogleAuthUtil.getToken(getApplicationContext(), email, scopes);
                    } catch (IOException e) {
                        errorMessage = "Network error: " + e.getMessage();
                        e.printStackTrace();
                    } catch (GoogleAuthException e) {
                        errorMessage = "Authentication error: " + e.getMessage();
                        e.printStackTrace();
                    }
                    return token;
                }
                @Override
                protected void onPostExecute(String token){
                    if (token != null) {
                    /* Successfully got OAuth token, now login with Google */
                        mFirebaseRef.authWithOAuthToken("google", token, new AuthResultHandler() {
                            @Override
                            public void onAuthenticated(AuthData authData) {
                                progressDialog(false, "");
                                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                                startActivity(intent);
                            }

                            @Override
                            public void onAuthenticationError(FirebaseError firebaseError) {
                                Toast.makeText(LoginActivity.this, "Authentication error with Firebase",
                                        Toast.LENGTH_SHORT).show();
                                progressDialog(false, "");
                            }
                        });
                    } else {
                        Toast.makeText(LoginActivity.this, "No token received: " + errorMessage,
                                Toast.LENGTH_SHORT).show();
                    }
                }
            };
            task.execute();

        } else {
            Toast.makeText(LoginActivity.this, "Signing in failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(mGoogleApiClient.isConnected()){
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(@NonNull Status status) {
                            Log.d(TAG, "User logged out");
                        }
                    }
            );
            mGoogleApiClient.disconnect();
        }
    }
}

