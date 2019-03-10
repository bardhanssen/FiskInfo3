package no.sintef.fiskinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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



//        Arrays.asList(new Integer[][R.id.mapFragment, R.id.overviewFragment, R.id.toolsFragment])

        appBarConfiguration = new AppBarConfiguration.Builder(controller.getGraph())
                .setDrawerLayout(drawerLayout)
                .build();
        NavigationUI.setupActionBarWithNavController(this, controller, appBarConfiguration);
        setupNavigationMenu(controller);

/*        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, OverviewFragment.newInstance())
                    .commitNow();
        }*/
    }

    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(this, R.id.nav_host_fragment).navigateUp();
    }

    protected void setupNavigationMenu(NavController navController) {
        NavigationView view = findViewById(R.id.navigation_view);
        NavigationUI.setupWithNavController(view, navController);

    }

}
