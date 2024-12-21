package com.nicholson.client_reservation_vol.présentation.bienvenue

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.nicholson.client_reservation_vol.R
import com.nicholson.client_reservation_vol.présentation.VueAuthentifié
import com.nicholson.client_reservation_vol.présentation.bienvenue.ContratVuePrésentateurBienvenue.*

class BienvenueVue : VueAuthentifié(), IBienvenueVue {
    var présentateur : IBienvenuePrésentateur? = BienvenuePrésentateur( this )
    lateinit var btnGoMesVoyages : ConstraintLayout
    lateinit var btnGoRechercherUnVol : ConstraintLayout
    lateinit var bouttonDéconnexion : FloatingActionButton
    lateinit var navController : NavController

    override fun onCreate( savedInstanceState: Bundle? ) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate( R.layout.fragment_bienvenue_vue, container, false )
    }

    override fun onViewCreated( vue: View, savedInstanceState: Bundle? ) {
        super.onViewCreated( vue, savedInstanceState )
        navController = Navigation.findNavController( vue )
        bouttonDéconnexion = vue.findViewById( R.id.bouttonDéconnexion )
        attacherÉcouteurRedirectionListeReservations( vue )
        attacherÉcouteurRedirectionRechercherUnVol( vue )
        bouttonDéconnexion.setOnClickListener {
            présentateur?.traiterDéconnexion()
        }
        présentateur?.traiterDémarage()
    }

    override fun redirigerAListeReservation() {
        navController.navigate( R.id.action_bienvenueVue_vers_listeRéservationsVue )
    }

    override fun redirigerARechercherUnVol() {
        navController.navigate( R.id.action_bienvenueVue_vers_rechercherUnVolVue )
    }

    override fun afficherMessageErreur() {
        val dialogErreur = MaterialAlertDialogBuilder( requireContext() )
        dialogErreur.setMessage( getString( R.string.une_erreur_r_seau_c_est_produite ) )
        dialogErreur.setPositiveButton( "OK" ) { dialog, _ ->
            dialog.dismiss()
        }
        dialogErreur.show()
    }

    override fun montrerDéconnexion() {
        bouttonDéconnexion.isClickable = true
        bouttonDéconnexion.visibility = View.VISIBLE
    }

    override fun cacherDéconnexion() {
        bouttonDéconnexion.isClickable = false
        bouttonDéconnexion.visibility = View.GONE
    }

    override fun obtenirToken(): String? {
        return préférences.getString( "token", null )
    }

    private fun attacherÉcouteurRedirectionListeReservations( vue : View ){
        btnGoMesVoyages = vue.findViewById( R.id.constraintLayoutBtnMesVoyages )
        btnGoMesVoyages.setOnClickListener {
            présentateur?.traiterDemandeRedirectionListeReservations()
        }
    }

    private fun attacherÉcouteurRedirectionRechercherUnVol( vue : View ){
        btnGoRechercherUnVol = vue.findViewById( R.id.constraintLayoutBtnRechercheVols )
        btnGoRechercherUnVol.setOnClickListener {
            présentateur?.traiterDemandeRedirectionRechercherUnVol()
        }
    }
}