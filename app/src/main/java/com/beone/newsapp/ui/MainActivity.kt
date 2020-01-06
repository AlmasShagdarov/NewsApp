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
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.beone.newsapp.R
import com.beone.newsapp.databinding.ActivityMainBinding
import com.beone.newsapp.work.RefreshDataWorker
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        setupNavigation(binding)
        oneTimeRefreshWorkStart()

    }

    private fun oneTimeRefreshWorkStart() {
        GlobalScope.launch {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .build()

            val worker =
                OneTimeWorkRequestBuilder<RefreshDataWorker>().setConstraints(constraints).build()
            WorkManager.getInstance().enqueue(worker)
        }
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
                when (menuItem.itemId) {
                    R.id.homeViewPageFragment -> {
                        binding.navView.setCheckedItem(R.id.homeViewPageFragment)
                        navController.navigate(R.id.homeViewPageFragment)
                    }
                    R.id.favoritesFragment -> {
                        binding.navView.setCheckedItem(R.id.favoritesFragment)
                        navController.navigate(R.id.favoritesFragment)
                    }
                }
                drawerLayout.closeDrawers()
                true
            }
        }
    }

    fun getDrawer() = findViewById<View>(R.id.drawerLayout) as DrawerLayout

}

