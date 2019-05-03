package no.sintef.fiskinfo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import no.sintef.fiskinfo.model.Echogram;
import no.sintef.fiskinfo.ui.snap.EchogramFragment;
import no.sintef.fiskinfo.ui.snap.dummy.DummyContent;

import com.google.android.material.navigation.NavigationView;

import java.util.HashSet;

import static androidx.navigation.ui.NavigationUI.setupActionBarWithNavController;

public class MainActivity extends AppCompatActivity implements EchogramFragment.OnEchogramInteractionListener {

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
        topLevelDests.add(R.id.map_fragment);
        topLevelDests.add(R.id.overview_fragment);
        topLevelDests.add(R.id.tools_fragment);

//        appBarConfiguration = new AppBarConfiguration.Builder(topLevelDests) //controller.getGraph())
        appBarConfiguration = new AppBarConfiguration.Builder(controller.getGraph())
                .setDrawerLayout(drawerLayout)
                .build();

        setupActionBarWithNavController(this, controller, drawerLayout);
        //setupActionBarWithNavController(this, controller, appBarConfiguration);

//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawerLayout, toolbar, R.string.proto_open_drawer, R.string.proto_close_drawer);
//        toggle.syncState();

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
        boolean retVal = super.onCreateOptionsMenu(menu);
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
        return NavigationUI.navigateUp(controller, drawerLayout);
        //return Navigation.findNavController(this, R.id.nav_host_fragment).navigateUp();
    }

    protected void setupNavigationMenu(NavController navController) {
        NavigationView view = findViewById(R.id.navigation_view);
        NavigationUI.setupWithNavController(view, navController);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nav_drawer, menu);
        return true;
    }

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


    @Override
    public void onViewEchogramClicked(Echogram echogram) {

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(echogram.echogramURL);
        startActivity(i);

//        Toast toast = Toast.makeText(this,"Show Echogram " + echogram.source + " " + echogram.timestamp.toString(),Toast.LENGTH_LONG);
//        toast.show();
    }

    @Override
    public void onShareEchogramClicked(Echogram echogram) {
        Toast toast = Toast.makeText(this,"Share Echogram " + echogram.source + " " + echogram.timestamp.toString(),Toast.LENGTH_LONG);
        toast.show();
    }
}
