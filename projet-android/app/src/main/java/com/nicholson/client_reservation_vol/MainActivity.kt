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
                resId = R.id.action_bienvenueVue_to_historiqueRechercheVue,
                args = null,
                navOptions = navOptions
            )
        }
    }
}