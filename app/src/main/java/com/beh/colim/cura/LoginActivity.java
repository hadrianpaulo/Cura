package com.beh.colim.cura;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import com.firebase.client.*;

public class LoginActivity extends AppCompatActivity {

    private Toolbar m_t_toolbar;
    private EditText m_et_uname;
    private EditText m_et_pw;

    private Firebase m_firebase_ref;
    private ProgressDialog m_pd_auth;
    private AuthData m_authdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        m_t_toolbar = (Toolbar) findViewById(R.id.toolbar);
        m_t_toolbar.setTitle("Login");

        setSupportActionBar(m_t_toolbar);

        // Edit texts
        m_et_uname = (EditText) findViewById(R.id.et_login_uname);
        m_et_pw = (EditText) findViewById(R.id.et_login_pw);


        // Firebase
        Firebase.setAndroidContext(this);
        m_firebase_ref = new Firebase("https://cura.firebaseio.com/");


        m_pd_auth = new ProgressDialog(this);
        m_pd_auth.setTitle("Loading..");
        m_pd_auth.setMessage("Authenticating with Firebase.");
        //m_pd_auth.show();
    }

    public void login(View view){
        final String s_uname = m_et_uname.getText().toString();
        final String s_pw = m_et_pw.getText().toString();

        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
        startActivity(intent);
    }

    public void login_google(View view){
        // Insert connection to google here
        // Future reference: https://github.com/firebase/firebase-login-demo-android
        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
        startActivity(intent);
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
}

