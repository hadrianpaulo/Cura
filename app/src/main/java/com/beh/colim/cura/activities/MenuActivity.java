package com.beh.colim.cura.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.beh.colim.cura.R;

public class MenuActivity extends AppCompatActivity {
    private Toolbar _toolbar;
    private CuraApplication _app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        _toolbar = (Toolbar) findViewById(R.id.toolbar);
        _toolbar.setTitle("Menu");
        setSupportActionBar(_toolbar);

        _app = (CuraApplication) getApplication();
        Toast.makeText(MenuActivity.this, _app.getUsername(), Toast.LENGTH_SHORT).show();
    }

    public void addReport(View view){
        Intent intent = new Intent(MenuActivity.this, AddReportActivity.class);
        startActivity(intent);
    }

    public void searchDrugstore(View view){
        Intent intent = new Intent(MenuActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.logout_menu_item) {
            Intent intent = new Intent(MenuActivity.this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
