package com.nicholson.client_reservation_vol.présentation.bienvenue

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nicholson.client_reservation_vol.R
import com.nicholson.client_reservation_vol.présentation.bienvenue.ContratVuePrésentateurBienvenue.*

class BienvenueVue : Fragment(), IBienvenueVue {

    var présentateur : IBienvenuePrésentateur? = BienvenuePrésentateur( this )
    lateinit var btnGoMesVoyages : ImageButton
    lateinit var btnGoRechercherUnVol : ImageButton
    lateinit var navController : NavController

    override fun onCreate( savedInstanceState: Bundle? ) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_bienvenue_vue, container, false )
    }

    override fun onViewCreated( vue: View, savedInstanceState: Bundle? ) {
        super.onViewCreated( vue, savedInstanceState )
        navController = Navigation.findNavController( vue )
        attacherÉcouteurRedirectionListeReservations( vue )
        attacherÉcouteurRedirectionRechercherUnVol( vue )
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

    private fun attacherÉcouteurRedirectionListeReservations( vue : View ){
        btnGoMesVoyages = vue.findViewById( R.id.imageButtonMesVoyages )
        btnGoMesVoyages.setOnClickListener {
            présentateur?.traiterDemandeRedirectionListeReservations()
        }
    }

    private fun attacherÉcouteurRedirectionRechercherUnVol( vue : View ){
        btnGoRechercherUnVol = vue.findViewById( R.id.imageButtonRechercheVols )
        btnGoRechercherUnVol.setOnClickListener {
            présentateur?.traiterDemandeRedirectionRechercherUnVol()
        }
    }
}