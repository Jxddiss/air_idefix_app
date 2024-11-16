package com.nicholson.client_reservation_vol.présentation.choisirclasse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
    lateinit var textViewHeureDépart : TextView
    lateinit var textViewNomVilleDépart : TextView
    lateinit var textViewCodeAéroportDépart : TextView
    lateinit var textViewHeureArrivée : TextView
    lateinit var textViewNomVilleArrivée : TextView
    lateinit var textViewCodeAéroportArrivée : TextView
    lateinit var textViewDurée : TextView
    lateinit var textViewNomDestination : TextView
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
        textViewHeureDépart = vue.findViewById( R.id.textViewHeureDépart )
        textViewNomVilleDépart = vue.findViewById( R.id.textViewNomVilleDépart )
        textViewCodeAéroportDépart = vue.findViewById( R.id.textViewCodeAéroportDépart )
        textViewHeureArrivée = vue.findViewById( R.id.textViewHeureArrivée )
        textViewNomVilleArrivée = vue.findViewById( R.id.textViewNomVilleArrivée )
        textViewCodeAéroportArrivée = vue.findViewById( R.id.textViewCodeAéroportArrivée )
        textViewDurée = vue.findViewById( R.id.textViewDurée )
        textViewNomDestination = vue.findViewById( R.id.textViewNomDestination )
        navController = Navigation.findNavController( vue )
        présentateur?.traiterDémarage()
    }

    override fun miseEnPlace( volChoixClassOTD: VolChoixClassOTD ) {
        textViewPrixÉconomique.text = volChoixClassOTD.prixÉconomique
        textViewHeureDépart.text = volChoixClassOTD.heureDépart
        textViewNomVilleDépart.text = volChoixClassOTD.nomVilleDépart
        textViewCodeAéroportDépart.text = volChoixClassOTD.codeAéroportDépart
        textViewHeureArrivée.text = volChoixClassOTD.heureArrivée
        textViewNomVilleArrivée.text = volChoixClassOTD.nomVilleArrivée
        textViewCodeAéroportArrivée.text = volChoixClassOTD.codeAéroportArrivée
        textViewDurée.text = volChoixClassOTD.durée
        textViewNomDestination.text =
            "Vol de ${volChoixClassOTD.nomVilleDépart} à ${volChoixClassOTD.nomVilleArrivée}"

        Glide.with(requireContext())
            .load( volChoixClassOTD.urlPhotoVilleArrivé )
            .into( imageViewVillechoisirInformation )
    }

    override fun obtenirChoixClasse(): String {
        TODO("Not yet implemented")
    }

    override fun redirigerChoixInfo() {
        TODO("Not yet implemented")
    }
}