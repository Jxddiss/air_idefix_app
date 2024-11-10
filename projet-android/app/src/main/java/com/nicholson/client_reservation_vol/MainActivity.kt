package com.nicholson.client_reservation_vol

import android.content.Context
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Locale

class  MainActivity : AppCompatActivity() {

    lateinit var navController: NavController
    lateinit var buttonMesRéservationNav : Button
    lateinit var floatingButtonHomeNav : FloatingActionButton
    lateinit var buttonPréfrérencesNav : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appliquerLangueDePreferences()
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val navHostFragment = supportFragmentManager
            .findFragmentById( R.id.fragmentContainerView ) as NavHostFragment
        navController = navHostFragment.navController
        buttonMesRéservationNav = findViewById( R.id.buttonMesVoyagesNav )
        floatingButtonHomeNav  = findViewById( R.id.floatingButtonHomeNav )
        buttonPréfrérencesNav = findViewById( R.id.buttonPréférencesNav )

        miseEnPlaceDeLaBarreDeNavigation()
    }

    private fun setLanguage(languageCode: String) {
        val locale = when (languageCode) {
            "fr" -> Locale.FRENCH
            "en" -> Locale.ENGLISH
            "es" -> Locale("es")
            else -> Locale.getDefault()
        }
        Locale.setDefault(locale)

        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
        refreshUI()
    }

    private fun refreshUI() {
        val fragmentActuel = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
        fragmentActuel?.let {
            supportFragmentManager.beginTransaction().detach(it).attach(it).commit()
        }
    }

    private fun appliquerLangueDePreferences() {
        val preferences = getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        val langueSauvegardée = preferences.getString("language", "fr") ?: "fr"
        setLanguage(langueSauvegardée)
    }

    fun surChangementDeLangue(languageCode: String) {
        // Save the selected language in SharedPreferences
        val editor = getSharedPreferences("app_preferences", Context.MODE_PRIVATE).edit()
        editor.putString("language", languageCode)
        editor.apply()

        setLanguage(languageCode)
    }


    private fun miseEnPlaceDeLaBarreDeNavigation(){
        val navOptions = NavOptions.Builder()
            .setLaunchSingleTop( true )
            .setEnterAnim( com.google.android.material.R.anim.abc_fade_in )
            .setExitAnim( com.google.android.material.R.anim.abc_fade_out )
            .build()

        buttonMesRéservationNav.setOnClickListener {
            navController.navigate(
                resId = R.id.listeRéservationsVue,
                args = null,
                navOptions = navOptions
            )
        }

        floatingButtonHomeNav.setOnClickListener {
            navController.navigate(
                resId = R.id.bienvenueVue,
                args = null,
                navOptions = navOptions
            )
        }

        buttonPréfrérencesNav.setOnClickListener {
            navController.navigate(
                resId = R.id.preferences_vue,
                args = null,
                navOptions = navOptions
            )
        }
    }
}