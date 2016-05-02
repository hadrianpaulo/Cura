package com.beh.colim.cura.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.beh.colim.cura.R;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.ui.auth.core.AuthProviderType;
import com.firebase.ui.auth.core.FirebaseLoginBaseActivity;
import com.firebase.ui.auth.core.FirebaseLoginError;

import static com.firebase.client.Firebase.setAndroidContext;

public class MainActivity extends FirebaseLoginBaseActivity{
    private static final String TAG = "Login";
    private CuraApplication _app;
    private Toolbar _toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _toolbar = (Toolbar) findViewById(R.id.toolbar);
        _toolbar.setTitle("Login");
        setSupportActionBar(_toolbar);

        _app = (CuraApplication) getApplication();
        if(_app.getFirebaseRef() != null){
            _app.getFirebaseRef().unauth();
        }

        setAndroidContext(this);
        _app.setFirebaseRef(new Firebase("https://cura.firebaseio.com/"));
    }

    @Override
    protected void onStart(){
        super.onStart();

        setEnabledAuthProvider(AuthProviderType.GOOGLE);
        setEnabledAuthProvider(AuthProviderType.PASSWORD);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.login_menu_item).setVisible(getAuth() == null);
        menu.findItem(R.id.logout_menu_item).setVisible(getAuth() != null);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.login_menu_item) {
            this.showFirebaseLoginPrompt();
            return true;
        }

        else if(id == R.id.logout_menu_item) {
            this.logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFirebaseLoggedIn(AuthData authData){
        Log.i(TAG, "Logged in to " + authData.getProvider().toString());

        switch (authData.getProvider()) {
            case "password":
                _app.setUsername((String) authData.getProviderData().get("email"));

                break;
            default:
                _app.setUsername((String) authData.getProviderData().get("displayName"));
                break;
        }

        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
        startActivity(intent);
        Toast.makeText(MainActivity.this, _app.getUsername(), Toast.LENGTH_SHORT).show();
        invalidateOptionsMenu();
    }

    @Override
    public void onFirebaseLoggedOut(){
        Log.i(TAG, "Logged out");
        _app.setUsername("");
        invalidateOptionsMenu();
    }

    @Override
    protected Firebase getFirebaseRef() {
        return _app.getFirebaseRef();
    }

    @Override
    protected void onFirebaseLoginProviderError(FirebaseLoginError firebaseLoginError) {
        Log.e(TAG, "Login provider error: "+firebaseLoginError.toString());
        Toast.makeText(MainActivity.this, "Login provider error " + firebaseLoginError.toString(),
                Toast.LENGTH_SHORT).show();
        resetFirebaseLoginPrompt();
    }

    @Override
    protected void onFirebaseLoginUserError(FirebaseLoginError firebaseLoginError) {
        Log.e(TAG, "Login user error: "+firebaseLoginError.toString());
        Toast.makeText(MainActivity.this, "Login user error " + firebaseLoginError.toString(),
                Toast.LENGTH_SHORT).show();
        resetFirebaseLoginPrompt();
    }
}
