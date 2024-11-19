package com.nicholson.client_reservation_vol.présentation.RéservationSpécifique

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.nicholson.client_reservation_vol.R
import com.nicholson.client_reservation_vol.donnée.fictive.SourceDonnéesFictive
import com.nicholson.client_reservation_vol.présentation.OTD.RéservationSpécifiqueOTD
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale
import com.nicholson.client_reservation_vol.présentation.RéservationSpécifique.ContratVuePrésentateurRéservationSpécifique.*

class RéservationSpécifiqueVue : Fragment(), IRéservationSpécifiqueVue{
    lateinit var textViewSiege : TextView
    lateinit var textViewDateDépart: TextView
    lateinit var textViewDateArrivée: TextView
    lateinit var textViewClasse: TextView
    lateinit var textViewVille: TextView
    lateinit var textViewDateDépartPreview: TextView
    lateinit var textViewNumeroRéservation: TextView
    lateinit var textViewCompteARebours: TextView
    lateinit var progressBarRéservationSpecifique: ProgressBar
    lateinit var textViewHeureArrivéeRéservationSpécifique: TextView
    lateinit var textViewHeureDépartRéservationSpécifique: TextView
    lateinit var imageViewVilleRéservationSpecifique: ImageView
    lateinit var navController: NavController
    var présentateur : IRéservationSpécifiquePrésentateur? = RéservationSpécifiquePrésentateur( this )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_reservation_specifique_vue, container, false)

        textViewSiege = view.findViewById(R.id.textViewSiege)
        textViewDateDépart= view.findViewById(R.id.textViewDateArrivée)
        textViewDateArrivée= view.findViewById(R.id.textViewDateDépart)
        textViewClasse= view.findViewById(R.id.textViewClasse)
        textViewVille= view.findViewById(R.id.textViewVilleRéservationSpécifique)
        textViewDateDépartPreview= view.findViewById(R.id.textViewDateDépartPreview)
        textViewNumeroRéservation= view.findViewById(R.id.textViewNumeroRéservation)
        textViewCompteARebours= view.findViewById(R.id.textViewCompteARebours)
        progressBarRéservationSpecifique = view.findViewById(R.id.progressBarRéservationSpecifique)
        textViewHeureArrivéeRéservationSpécifique= view.findViewById(R.id.textViewHeureArrivéeRéservationSpécifique)
        textViewHeureDépartRéservationSpécifique= view.findViewById(R.id.textViewHeureDépartRéservationSpécifique)
        imageViewVilleRéservationSpecifique= view.findViewById(R.id.imageViewVilleRéservationSpecifique)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        val BtnModifierReservation: ImageButton = view.findViewById(R.id.BtnModifierReservation)
        BtnModifierReservation.setOnClickListener {
            présentateur?.traiterModifier()
        }

        présentateur?.traiterDémarage()
    }

    override fun miseEnPlace(réservationSpécifiqueOTD: RéservationSpécifiqueOTD){
        textViewSiege.text = réservationSpécifiqueOTD.siège
        textViewDateDépart.text = réservationSpécifiqueOTD.dateDepart
        textViewDateArrivée.text = réservationSpécifiqueOTD.dateArrivée
        textViewClasse.text = réservationSpécifiqueOTD.classe
        textViewVille.text = réservationSpécifiqueOTD.nomVille
        textViewDateDépartPreview.text = réservationSpécifiqueOTD.dateDepart
        textViewNumeroRéservation.text = réservationSpécifiqueOTD.numéroRéservation
        textViewCompteARebours.text = réservationSpécifiqueOTD.tempsRestant
        progressBarRéservationSpecifique.progress = réservationSpécifiqueOTD.barProgres.toInt()
        textViewHeureArrivéeRéservationSpécifique.text = réservationSpécifiqueOTD.heureArrivée
        textViewHeureDépartRéservationSpécifique.text = réservationSpécifiqueOTD.heureDepart

        Glide.with(requireContext())
            .load( réservationSpécifiqueOTD.url_photo )
            .into( imageViewVilleRéservationSpecifique )
    }

    override fun redirigerModifier() {
        navController.navigate(R.id.action_réservationSpécifiqueVue_to_modifierReservationVue)
    }

}


