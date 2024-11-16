package com.nicholson.client_reservation_vol.présentation.vue

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.nicholson.client_reservation_vol.R
import com.nicholson.client_reservation_vol.donnée.fictive.SourceDonnéesFictive

class modifierReservationVue : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_modifier_reservation_vue, container, false)

        val textViewModifierNomReservation: TextView = view.findViewById(R.id.textViewModifierNomReservation)
        val textViewModifierPrenomReservation: TextView = view.findViewById(R.id.textViewModifierPrenomReservation)
        val textViewModifiernuméroPasseportReservation: TextView = view.findViewById(R.id.textViewModifiernuméroPasseportReservation)
        val textViewModifierEmailReservation: TextView = view.findViewById(R.id.textViewModifierEmailReservation)
        val textViewModifierTelephoneReservation: TextView = view.findViewById(R.id.textViewModifierTelephoneReservation)
        val réservation = SourceDonnéesFictive.listeRéservation[0]

        val client = réservation.clients[0]

        textViewModifierNomReservation.text = client.nom
        textViewModifierPrenomReservation.text = client.prénom
        textViewModifiernuméroPasseportReservation.text = client.numéroPasseport
        textViewModifierEmailReservation.text = client.email
        textViewModifierTelephoneReservation.text = client.numéroTéléphone


        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val BtnModifierSiege: ImageButton = view.findViewById(R.id.BtnModifierSiege)
        BtnModifierSiege.setOnClickListener {
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_modifierReservationVue_to_choisirSiegeVue)
        }
        val BtnModifierInformation: ImageButton = view.findViewById(R.id.BtnModifierInformation)
        BtnModifierInformation.setOnClickListener {
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_modifierReservationVue_to_choisirInfoVue)
        }
        val BtnModifierClasse: ImageButton = view.findViewById(R.id.BtnModifierClasse)
        BtnModifierClasse.setOnClickListener {
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_modifierReservationVue_to_choisirClasseVue)
        }
    }

}