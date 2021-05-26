package fr.umontpellier.localisermonenfant.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;

import fr.umontpellier.localisermonenfant.R;
import fr.umontpellier.localisermonenfant.fragments.AddChildFragment;
import fr.umontpellier.localisermonenfant.fragments.BlankZoneMenu;
import fr.umontpellier.localisermonenfant.fragments.ChildFragment;
import fr.umontpellier.localisermonenfant.fragments.MapsControlFragment;
import fr.umontpellier.localisermonenfant.fragments.UploadPhotoFragment;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toolbar = (Toolbar) findViewById(R.id.topAppBar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //navigationView.setCheckedItem(R.id.);

        FragmentManager fragmentManager = getSupportFragmentManager();
        //MapsControlFragment fragment = new MapsControlFragment();
        UploadPhotoFragment fragment = new UploadPhotoFragment();
        fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit();

        FragmentManager fragmentManager2 = getSupportFragmentManager();
        ChildFragment childFragment = new ChildFragment();
        fragmentManager2.beginTransaction().replace(R.id.frame_container_childs, childFragment).commit();
    }

    private void setupDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = new MapsControlFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (item.getItemId()) {
            case R.id.nav_home:
                fragment = new MapsControlFragment();
                break;
            case R.id.nav_zone_parameter:
                fragment = new BlankZoneMenu();
                break;
            case R.id.nav_add_new_child:
                fragment = new AddChildFragment();
                break;
        }
        fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit();
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.autre:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}