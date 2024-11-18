package com.nicholson.client_reservation_vol.présentation.modifierReservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.nicholson.client_reservation_vol.R
import com.nicholson.client_reservation_vol.présentation.OTD.ClientOTD

class ModifierReservationVue : Fragment(), ContratVueModifierReservation.IModifierReservationVue {

    var présentateur: ContratVueModifierReservation.IModifierReservationPrésentateur? = ModifierReservationPresentateur(this)
    lateinit var navController: NavController
    lateinit var textViewModifierNomReservation: TextView
    lateinit var textViewModifierPrenomReservation: TextView
    lateinit var textViewModifiernuméroPasseportReservation: TextView
    lateinit var textViewModifierEmailReservation: TextView
    lateinit var textViewModifierTelephoneReservation: TextView


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


}
