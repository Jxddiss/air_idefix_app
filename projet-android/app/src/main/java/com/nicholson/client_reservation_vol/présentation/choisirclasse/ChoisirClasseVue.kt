package com.nicholson.client_reservation_vol.présentation.choisirclasse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.nicholson.client_reservation_vol.R
import com.nicholson.client_reservation_vol.présentation.OTD.VolChoixClassOTD
import com.nicholson.client_reservation_vol.présentation.choisirclasse.ContratVuePrésentateurChoisirClasse.*

class ChoisirClasseVue: Fragment(), IChoisirClasseVue {
    var présentateur : IChoisirClassePrésentateur? = ChoisirClassePrésentateur( this )
    lateinit var imageViewVillechoisirInformation : ImageView
    lateinit var textViewPrixÉconomique : TextView
    lateinit var textViewPrixAffaire : TextView
    lateinit var textViewPrixPremière : TextView
    lateinit var textViewHeureDépart : TextView
    lateinit var textViewNomVilleDépart : TextView
    lateinit var textViewCodeAéroportDépart : TextView
    lateinit var textViewHeureArrivée : TextView
    lateinit var textViewNomVilleArrivée : TextView
    lateinit var textViewCodeAéroportArrivée : TextView
    lateinit var textViewDurée : TextView
    lateinit var textViewNomDestination : TextView
    lateinit var textViewDateVolPrécedent : TextView
    lateinit var textViewPrixÉconoVolPrécedent : TextView
    lateinit var textViewDateDépartSlider : TextView
    lateinit var textViewPrixÉconomiqueSlider : TextView
    lateinit var textViewDateVolSuivant : TextView
    lateinit var textViewPrixÉconoVolSuivant : TextView
    lateinit var imageButtonVolPrecedent : ImageButton
    lateinit var imageButtonVolSuivant : ImageButton
    lateinit var btnContinuerRéservation : Button
    lateinit var radioGroupChoixClass : RadioGroup
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_choisir_classe_vue, container, false)
        return view
    }

    override fun onViewCreated(vue: View, savedInstanceState: Bundle?) {
        super.onViewCreated( vue, savedInstanceState )
        imageViewVillechoisirInformation = vue.findViewById( R.id.imageViewVillechoisirInformation )
        textViewPrixÉconomique = vue.findViewById( R.id.textViewPrixÉconomique )
        textViewPrixAffaire = vue.findViewById( R.id.textViewPrixAffaire )
        textViewPrixPremière = vue.findViewById( R.id.textViewPrixPremiere )
        textViewHeureDépart = vue.findViewById( R.id.textViewHeureDépart )
        textViewNomVilleDépart = vue.findViewById( R.id.textViewNomVilleDépart )
        textViewCodeAéroportDépart = vue.findViewById( R.id.textViewCodeAéroportDépart )
        textViewHeureArrivée = vue.findViewById( R.id.textViewHeureArrivée )
        textViewNomVilleArrivée = vue.findViewById( R.id.textViewNomVilleArrivée )
        textViewCodeAéroportArrivée = vue.findViewById( R.id.textViewCodeAéroportArrivée )
        textViewDurée = vue.findViewById( R.id.textViewDurée )
        textViewNomDestination = vue.findViewById( R.id.textViewNomDestination )
        textViewDateVolPrécedent = vue.findViewById( R.id.textViewDateVolPrécedent )
        textViewPrixÉconoVolPrécedent = vue.findViewById( R.id.textViewPrixÉconoVolPrécedent )
        textViewDateDépartSlider = vue.findViewById( R.id.textViewDateDépartSlider )
        textViewPrixÉconomiqueSlider = vue.findViewById( R.id.textViewPrixÉconomiqueSlider )
        textViewDateVolSuivant = vue.findViewById( R.id.textViewDateVolSuivant )
        textViewPrixÉconoVolSuivant = vue.findViewById( R.id.textViewPrixÉconoVolSuivant )

        imageButtonVolPrecedent = vue.findViewById( R.id.imageButtonVolPrecedent )
        imageButtonVolPrecedent.setOnClickListener {
            présentateur?.traiterDemandeVolPrécédant()
        }

        imageButtonVolSuivant = vue.findViewById( R.id.imageButtonVolSuivant )
        imageButtonVolSuivant.setOnClickListener {
            présentateur?.traiterDemandeVolSuivant()
        }

        btnContinuerRéservation = vue.findViewById( R.id.btnContinuerRéservation )
        btnContinuerRéservation.setOnClickListener {
            présentateur?.traiterContinuer()
        }

        radioGroupChoixClass = vue.findViewById( R.id.radioGroupChoixClass )
        radioGroupChoixClass.setOnCheckedChangeListener{ rg, checkedId ->
            when(checkedId){
                R.id.radioButtonÉconomique -> présentateur?.traterRadioÉconomiqueCliqué()
                R.id.radioButtonAffaire -> présentateur?.traterRadioAffaireCliqué()
                R.id.radioButtonPremière -> présentateur?.traterRadioPremièreCliqué()
            }
        }

        navController = Navigation.findNavController( vue )
        présentateur?.traiterDémarage()
    }

    override fun miseEnPlace( volChoixClassOTD: VolChoixClassOTD ) {
        textViewPrixÉconomique.text = volChoixClassOTD.prixÉconomique
        textViewPrixAffaire.text = volChoixClassOTD.prixAffaire
        textViewPrixPremière.text = volChoixClassOTD.prixPremière
        textViewHeureDépart.text = volChoixClassOTD.heureDépart
        textViewNomVilleDépart.text = volChoixClassOTD.nomVilleDépart
        textViewCodeAéroportDépart.text = volChoixClassOTD.codeAéroportDépart
        textViewHeureArrivée.text = volChoixClassOTD.heureArrivée
        textViewNomVilleArrivée.text = volChoixClassOTD.nomVilleArrivée
        textViewCodeAéroportArrivée.text = volChoixClassOTD.codeAéroportArrivée
        textViewDurée.text = volChoixClassOTD.durée
        textViewNomDestination.text =
            getString( R.string.vol_de, volChoixClassOTD.nomVilleDépart,
                volChoixClassOTD.nomVilleArrivée )

        textViewDateDépartSlider.text = volChoixClassOTD.dateDépart
        textViewPrixÉconomiqueSlider.text = volChoixClassOTD.prixÉconomique

        Glide.with(requireContext())
            .load( volChoixClassOTD.urlPhotoVilleArrivé )
            .into( imageViewVillechoisirInformation )
    }

    override fun obtenirChoixClasse(): String {
        TODO("Not yet implemented")
    }

    override fun redirigerChoixInfo() {
        navController.navigate( R.id.action_choisirClasseVue_vers_choisirInfoVue )
    }

    override fun placerVolPrécédent(date: String, prixÉconomique: String) {
        textViewDateVolPrécedent.text = date
        textViewPrixÉconoVolPrécedent.text = prixÉconomique
    }

    override fun placerVolSuivant(date: String, prixÉconomique: String) {
        textViewDateVolSuivant.text = date
        textViewPrixÉconoVolSuivant.text = prixÉconomique
    }
}