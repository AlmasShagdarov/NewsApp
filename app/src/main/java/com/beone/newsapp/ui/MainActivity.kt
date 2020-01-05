package com.beone.newsapp.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.beone.newsapp.R
import com.beone.newsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController
    lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        setupNavigation(binding)

    }

    private fun setupNavigation(binding: ActivityMainBinding) {
        navController = this.findNavController(R.id.nav_host_fragment)
        drawerLayout = binding.drawerLayout
        navController.addOnDestinationChangedListener { nc: NavController, nd: NavDestination, _ ->
            if (nd.id == nc.graph.startDestination) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }
        with(binding.navView) {
            setupWithNavController(navController)
            setNavigationItemSelectedListener { menuItem ->
                drawerLayout.closeDrawers()
                menuItem.isChecked = true
                when (menuItem.itemId) {
                    R.id.homeViewPageFragment -> navController.navigate(R.id.homeViewPageFragment)
                    R.id.favoritesFragment -> navController.navigate(R.id.favoritesFragment)
                }
                true
            }
        }
    }

    fun getDrawer() = findViewById<View>(R.id.drawerLayout) as DrawerLayout

}

