package no.sintef.fiskinfo;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import no.sintef.fiskinfo.ui.overview.OverviewFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.Arrays;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    AppBarConfiguration appBarConfiguration = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavHostFragment host = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController controller = host.getNavController();

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);

        HashSet<Integer> topLevelDests = new HashSet<>();
        topLevelDests.add(R.id.map_fragment);
        topLevelDests.add(R.id.overview_fragment);
        topLevelDests.add(R.id.tools_fragment);

        appBarConfiguration = new AppBarConfiguration.Builder(topLevelDests) //controller.getGraph())
                .setDrawerLayout(drawerLayout)
                .build();

        NavigationUI.setupActionBarWithNavController(this, controller, appBarConfiguration);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.proto_open_drawer, R.string.proto_close_drawer);
        toggle.syncState();

        setupNavigationMenu(controller);

/*        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, OverviewFragment.newInstance())
                    .commitNow();
        }*/
    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_nav_drawer, menu);
        return true;
*        boolean retVal = super.onCreateOptionsMenu(menu);
        NavigationView navView = findViewById(R.id.navigation_view);
        if (navView == null) {
            getMenuInflater().inflate(R.menu.menu_nav_drawer, menu);
            return true;
        }
        return retVal;*
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return NavigationUI.onNavDestinationSelected(item, Navigation.findNavController(this, R.id.nav_host_fragment));
    }
*/
    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(this, R.id.nav_host_fragment).navigateUp();
    }

    protected void setupNavigationMenu(NavController navController) {
        NavigationView view = findViewById(R.id.navigation_view);
        NavigationUI.setupWithNavController(view, navController);

    }

}
