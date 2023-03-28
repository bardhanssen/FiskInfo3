/**
 * Copyright (C) 2020 SINTEF
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package no.sintef.fiskinfo

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.preference.PreferenceManager
import com.google.android.material.navigation.NavigationView
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.AndroidEntryPoint
import no.sintef.fiskinfo.dal.sprice.SpriceDatabase
import no.sintef.fiskinfo.dal.sprice.SpriceDbRepository
import no.sintef.fiskinfo.databinding.MainActivityBinding
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint // Dagger-Hilt requirement
class MainActivity : AppCompatActivity() { //, NavigationView.OnNavigationItemSelectedListener {
    @Inject lateinit var repository: SpriceDbRepository

    private lateinit var mFirebaseAnalytics: FirebaseAnalytics
    private lateinit var drawerLayout : DrawerLayout
    private lateinit var controller : NavController
    private lateinit var appBarConfiguration : AppBarConfiguration
    private lateinit var binding: MainActivityBinding
    private lateinit var spriceDatabase: SpriceDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_launcher_background)

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)

        val host = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        controller = host!!.navController


        drawerLayout = findViewById(R.id.drawer_layout)

        val topLevelLinks = HashSet<Int>()
        topLevelLinks.add(R.id.fragment_map)
        topLevelLinks.add(R.id.fragment_overview)
        topLevelLinks.add(R.id.fragment_tools)

        appBarConfiguration = AppBarConfiguration.Builder(controller.graph)
            .setOpenableLayout(drawerLayout)
            .build()

        setupActionBarWithNavController(controller, drawerLayout)

        initSpriceDatabase()

        setupNavigationMenu(controller)
    }

    private fun initSpriceDatabase() {
        spriceDatabase = SpriceDatabase.getInstance(this)
    }

    private fun setupNavigationMenu(navController: NavController) {
        val view = findViewById<NavigationView>(R.id.navigation_view)
        NavigationUI.setupWithNavController(view, navController)

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)

        val snapItem = view.menu.findItem(R.id.fragment_snap)
        snapItem.isVisible = prefs.getBoolean(getString(R.string.pref_snap_enable_service), false)

        val spriceItem = view.menu.findItem(R.id.fragment_sprice)
        spriceItem.isVisible = prefs.getBoolean(getString(R.string.pref_sprice_enable_service_key), false)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(controller, drawerLayout)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        //val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.getOnBackPressedDispatcher().onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return NavigationUI.onNavDestinationSelected(item, controller) || super.onOptionsItemSelected(
            item
        )
    }

    companion object {
//        const val MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 0x001
        const val MY_PERMISSIONS_REQUEST_FINE_LOCATION = 0x002
    }
}
