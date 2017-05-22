package com.apps.kasper.reshelf;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.lucasr.twowayview.TwoWayView;

import java.util.HashMap;

public class ReshelfActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    MaterialSearchView searchView;
    Downloader d;
    TwoWayView lv;
    ImageView accountView;
    private SessionManager session;
    private CoordinatorLayout coordinatorLayout;
    private SQLiteHandler db;
    private TextView navUsername;
    private TextView navUsermail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reshelf);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final Intent intent = new Intent(this,CameraActivity.class);
        final Intent login = new Intent(this,LoginActivity.class);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if(checkCameraHardware(this)){
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(intent);
                }
            });
        }
        else{
            Toast.makeText(getApplicationContext(),
                    "Are you sure you have a camera?", Toast.LENGTH_LONG)
                    .show();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        session = new SessionManager(getApplicationContext());
        db = new SQLiteHandler(getApplicationContext());
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        navUsername = (TextView) headerView.findViewById(R.id.navUsername);
        navUsermail = (TextView) headerView.findViewById(R.id.navUsermail);





        if (session.isLoggedIn()) {
            HashMap<String, String> user = db.getUserDetails();
            String name = user.get("name");
            String email = user.get("email");
            navUsername.setText(name);
            navUsermail.setText(email);

            Snackbar.make(coordinatorLayout,"Hello "+name+" ("+email+")",Snackbar.LENGTH_LONG).setAction("LOGOUT", new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    session.setLogin(false);
                    db.deleteUsers();
                    navUsername.setText("LOGIN");
                    navUsermail.setText("OR REGISTER");
                }

            }).show();
        }


        accountView = (ImageView) headerView.findViewById(R.id.accountView);
        accountView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if (session.isLoggedIn()) {
                    //Magic stuff
                }
                else{
                    startActivity(login);
                    finish();
                }
            }
        });

        lv= (TwoWayView) findViewById(R.id.bookView);


        searchView = (MaterialSearchView) findViewById(R.id.search_view);

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (searchView.isSearchOpen()){
            searchView.closeSearch();
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.reshelf, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_messages) {

        } else if (id == R.id.nav_watch) {

        } else if (id == R.id.nav_buy) {

        } else if (id == R.id.nav_sell) {

        } else if (id == R.id.nav_display) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_about) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onResume(){
        super.onResume();
        refresh();
    }

    public void refresh(){
        //lv.deferNotifyDataSetChanged();
        d=new Downloader(this,AppConfig.URL_REFRESH,lv);
        d.execute();
    }

    /** Check if this device has a camera */
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }
}
