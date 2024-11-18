package com.nicholson.client_reservation_vol.présentation.modifierReservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.nicholson.client_reservation_vol.R

class ModifierReservationVue : Fragment(), ContratVueModifierReservation.IModifierReservationVue {

    var présentateur: ContratVueModifierReservation.IModifierReservationPrésentateur? = ModifierReservationPresentateur(this)
    lateinit var navController: NavController

    lateinit var btnModifierSiege: ImageButton
    lateinit var btnModifierClasse: ImageButton
    lateinit var btnModifierInfo: ImageButton

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

        navController = Navigation.findNavController(view)

        // Attacher les écouteurs aux boutons
        btnModifierSiege = view.findViewById(R.id.BtnModifierSiege)
        btnModifierClasse = view.findViewById(R.id.BtnModifierClasse)
        btnModifierInfo = view.findViewById(R.id.BtnModifierInformation)

        attacherÉcouteurRedirectionChoisirSiege()
        attacherÉcouteurRedirectionChoisirClasse()
        attacherÉcouteurRedirectionChoisirInformation()
    }

    override fun redirigerAChoisirSiege() {
        navController.navigate(R.id.action_modifierReservationVue_to_choisirSiegeVue)
    }

    override fun redirigerAChoisirClasse() {
        navController.navigate(R.id.action_modifierReservationVue_to_choisirClasseVue)
    }

    override fun redirigerAChoisirInformation() {
        navController.navigate(R.id.action_modifierReservationVue_to_choisirInfoVue)
    }

    private fun attacherÉcouteurRedirectionChoisirSiege() {
        btnModifierSiege.setOnClickListener {
            présentateur?.traiterDemandeRedirectionChoisirSiege()
        }
    }

    private fun attacherÉcouteurRedirectionChoisirClasse() {
        btnModifierClasse.setOnClickListener {
            présentateur?.traiterDemandeRedirectionChoisirClasse()
        }
    }

    private fun attacherÉcouteurRedirectionChoisirInformation() {
        btnModifierInfo.setOnClickListener {
            présentateur?.traiterDemandeRedirectionChoisirInformation()
        }
    }
}
