package com.nicholson.client_reservation_vol.présentation.navbar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.nicholson.client_reservation_vol.R
import com.nicholson.client_reservation_vol.présentation.navbar.ContratVuePrésentateurNavBar.*

class NavBarVue : Fragment(), INavbarVue {
    var navController: NavController? = null
    lateinit var buttonMesRéservationNav : Button
    lateinit var floatingButtonHomeNav : FloatingActionButton
    lateinit var buttonPréfrérencesNav : Button
    lateinit var navOptions : NavOptions
    var présentateur : INavBarPrésentateur? = NavBarPrésentateur(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nav_bar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHostFragment = activity?.supportFragmentManager
            ?.findFragmentById( R.id.fragmentContainerView ) as NavHostFragment?
        navController = navHostFragment?.navController
        buttonMesRéservationNav = view.findViewById( R.id.buttonMesVoyagesNav )
        floatingButtonHomeNav  = view.findViewById( R.id.floatingButtonHomeNav )
        buttonPréfrérencesNav = view.findViewById( R.id.buttonPréférencesNav )
        présentateur?.traiterDémarage()
    }

    override fun miseEnPlace() {
        navOptions = NavOptions.Builder()
            .setLaunchSingleTop( true )
            .setEnterAnim( com.google.android.material.R.anim.abc_fade_in )
            .setExitAnim( com.google.android.material.R.anim.abc_fade_out )
            .build()

        buttonMesRéservationNav.setOnClickListener {
            présentateur?.traiterRedirigerÀMesRéservation()
        }

        floatingButtonHomeNav.setOnClickListener {
            présentateur?.traiterRedirigerÀBienvenue()
        }

        buttonPréfrérencesNav.setOnClickListener {
            présentateur?.traiterRedirigerÀHistorique()
        }
    }

    override fun redirigerÀMesRéservation() {
        navController?.navigate(
            resId = R.id.listeRéservationsVue,
            args = null,
            navOptions = navOptions
        )
    }

    override fun redirigerÀBienvenue() {
        navController?.navigate(
            resId = R.id.bienvenueVue,
            args = null,
            navOptions = navOptions
        )
    }

    override fun redirigerÀHistorique() {
        navController?.navigate(
            resId = R.id.historiqueRechercheVue,
            args = null,
            navOptions = navOptions
        )
    }
}