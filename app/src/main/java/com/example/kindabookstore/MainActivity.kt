package com.example.kindabookstore

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.kindabookstore.data.local.prefs.Prefs
import com.example.kindabookstore.databinding.ActivityMainBinding
import com.example.kindabookstore.ui.login.LoginFragment
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val prefs: Prefs by lazy {
        Prefs(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_home, R.id.navigation_contact, R.id.navigation_cart
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.onboardingFragment -> navView.visibility = View.GONE
                R.id.loginFragment -> navView.visibility = View.GONE
                else -> navView.visibility = View.VISIBLE
            }
        }
        if (prefs.isFirstLaunch()) {
            navController.navigate(R.id.onboardingFragment)
            prefs.makeFirstLaunch()
        }
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null && !prefs.isFirstLaunch()) {
            // Пользователь не авторизован, показать экран регистрации
            navController.navigate(R.id.loginFragment)
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.container, LoginFragment())
//                .commit()
        } else {
            // Пользователь авторизован, показать основной экран
            navController.navigate(R.id.navigation_home)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}