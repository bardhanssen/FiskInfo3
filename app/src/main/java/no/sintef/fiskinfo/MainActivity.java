package no.sintef.fiskinfo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import java.util.HashSet;

import static androidx.navigation.ui.NavigationUI.setupActionBarWithNavController;

public class MainActivity extends AppCompatActivity  {

    AppBarConfiguration appBarConfiguration = null;
    NavController controller;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_launcher_background);



        NavHostFragment host = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        controller = host.getNavController();

        drawerLayout = findViewById(R.id.drawer_layout);

        HashSet<Integer> topLevelDests = new HashSet<>();
        topLevelDests.add(R.id.fragment_map);
        topLevelDests.add(R.id.fragment_overview);
        topLevelDests.add(R.id.fragment_tools);

        appBarConfiguration = new AppBarConfiguration.Builder(controller.getGraph())
                .setDrawerLayout(drawerLayout)
                .build();

        setupActionBarWithNavController(this, controller, drawerLayout);

        setupNavigationMenu(controller);

    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(controller, drawerLayout);
        //return Navigation.findNavController(this, R.id.nav_host_fragment).navigateUp();
    }

    protected void setupNavigationMenu(NavController navController) {
        NavigationView view = findViewById(R.id.navigation_view);
        NavigationUI.setupWithNavController(view, navController);

    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nav_drawer, menu);
        return true;
    }
*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return NavigationUI.onNavDestinationSelected(item, controller)
                || super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
