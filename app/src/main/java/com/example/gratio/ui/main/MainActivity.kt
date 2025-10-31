// ui/main/MainActivity.kt

package com.example.gratio.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.gratio.R
import com.example.gratio.data.AppDatabase
import com.example.gratio.data.AppRepository
import com.example.gratio.model.User
import com.example.gratio.repository.UserDao
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNav.setupWithNavController(navController)

        // Проверяем, есть ли активный пользователь
        checkUserSession()
    }

    private fun checkUserSession() {
        val db = AppDatabase.getDatabase(this)
        val userDao = db.userDao()

        CoroutineScope(Dispatchers.IO).launch {
            val currentUser = userDao.getUserById("current_user_id") // Здесь должен быть ID текущего пользователя
            // В реальном приложении вы бы хранили ID текущего пользователя в SharedPreferences или в ViewModel

            withContext(Dispatchers.Main) {
                if (currentUser == null) {
                    // Если нет пользователя, показываем экран входа
                    findNavController(R.id.nav_host_fragment).navigate(R.id.action_mainFragment_to_loginFragment)
                }
            }
        }
    }
}