package com.yudong80.rxandroid2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.yudong80.rxandroid2.chapter01.GugudanFragment;
import com.yudong80.rxandroid2.chapter02.OpenWeatherMapFragment;

import static android.R.attr.id;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //moveToFragment(MainFragment.newInstance());
        //moveToFragment(GugudanFragment.newInstance());
        moveToFragment(OpenWeatherMapFragment.newInstance());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.action_gugudan:
                moveToFragment(GugudanFragment.newInstance());
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void moveToFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, fragment)
                .commit();
    }
}
