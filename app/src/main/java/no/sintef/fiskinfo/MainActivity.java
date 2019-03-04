package no.sintef.fiskinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.navigation.NavController;
import androidx.navigation.ui.NavigationUI;
import no.sintef.fiskinfo.ui.overview.OverviewFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, OverviewFragment.newInstance())
                    .commitNow();
        }
    }


    protected void setupNavigationMenu(NavController navController) {
        NavigationView view = findViewById(R.id.navigationView);
        NavigationUI.setupWithNavController(view, navController);

    }

}
