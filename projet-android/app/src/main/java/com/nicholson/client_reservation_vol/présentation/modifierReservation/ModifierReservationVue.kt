package com.nicholson.client_reservation_vol.présentation.modifierReservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.nicholson.client_reservation_vol.R
import com.nicholson.client_reservation_vol.présentation.OTD.ClientOTD
import com.nicholson.client_reservation_vol.présentation.VueAuthentifié

class ModifierReservationVue : VueAuthentifié(), ContratVueModifierReservation.IModifierReservationVue {

    private var présentateur: ContratVueModifierReservation.IModifierReservationPrésentateur? = ModifierReservationPresentateur(this)
    private lateinit var navController: NavController
    private lateinit var textViewModifierNomReservation: TextView
    private lateinit var textViewModifierPrenomReservation: TextView
    private lateinit var textViewModifiernuméroPasseportReservation: TextView
    private lateinit var textViewModifierEmailReservation: TextView
    private lateinit var textViewModifierTelephoneReservation: TextView
    private lateinit var layoutBarChargement : ConstraintLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_modifier_reservation_vue, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         textViewModifierNomReservation = view.findViewById(R.id.textViewModifierNomReservation)
         textViewModifierPrenomReservation = view.findViewById(R.id.textViewModifierPrenomReservation)
        textViewModifiernuméroPasseportReservation = view.findViewById(R.id.textViewModifiernuméroPasseportReservation)
        textViewModifierEmailReservation = view.findViewById(R.id.textViewModifierEmailReservation)
        textViewModifierTelephoneReservation = view.findViewById(R.id.textViewModifierTelephoneReservation)
        layoutBarChargement = view.findViewById(R.id.barDeChargement)
        navController = Navigation.findNavController(view)

        présentateur?.traiterDémarage()

    }

    override fun miseEnPlace(clientOTD: ClientOTD) {
        textViewModifierNomReservation.text = clientOTD.nom
        textViewModifierPrenomReservation.text = clientOTD.prénom
        textViewModifiernuméroPasseportReservation.text = clientOTD.numéroPasseport
        textViewModifierEmailReservation.text = clientOTD.email
        textViewModifierTelephoneReservation.text = clientOTD.téléphone
    }

    override fun redirigerBienvenueErreur() {
        navController.navigate(R.id.action_modifierReservationVue_vers_bienvenueVue)
    }

    override fun montrerChargement() {
        layoutBarChargement.visibility = View.VISIBLE
    }

    override fun masquerChargement() {
        layoutBarChargement.visibility = View.GONE
    }



}
