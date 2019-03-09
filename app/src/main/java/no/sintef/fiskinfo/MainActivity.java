package no.sintef.fiskinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import no.sintef.fiskinfo.ui.overview.OverviewFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavHostFragment host = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController controller = host.getNavController();

        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        AppBarConfiguration configuration = new AppBarConfiguration.Builder(controller.getGraph()).setDrawerLayout(drawerLayout).build();
        NavigationUI.setupActionBarWithNavController(this, controller, configuration);
/*
        AppBarConfiguration configuration = new AppBarConfiguration(
                controller.getGraph(), drawerLayout);

                (controller.getGraph());
*/
        setupNavigationMenu(controller);

/*        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, OverviewFragment.newInstance())
                    .commitNow();
        }*/
    }


    protected void setupNavigationMenu(NavController navController) {
        NavigationView view = findViewById(R.id.navigationView);
        NavigationUI.setupWithNavController(view, navController);

    }

}
