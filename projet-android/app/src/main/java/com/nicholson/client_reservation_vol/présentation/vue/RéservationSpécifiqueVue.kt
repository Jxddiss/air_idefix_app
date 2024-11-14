package com.nicholson.client_reservation_vol.présentation.vue

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.nicholson.client_reservation_vol.R
import com.nicholson.client_reservation_vol.donnée.fictive.SourceDonnéesFictive
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

class RéservationSpécifiqueVue : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_reservation_specifique_vue, container, false)

        val textViewSiege: TextView = view.findViewById(R.id.textViewSiege)
        val textViewDateDépart: TextView = view.findViewById(R.id.textViewDateArrivée)
        val textViewDateArrivée: TextView = view.findViewById(R.id.textViewDateDépart)
        val textViewClasse: TextView = view.findViewById(R.id.textViewClasse)
        val textViewVille: TextView = view.findViewById(R.id.textViewVilleRéservationSpécifique)
        val textViewDateDépartPreview: TextView = view.findViewById(R.id.textViewDateDépartPreview)
        val textViewNumeroRéservation: TextView = view.findViewById(R.id.textViewNumeroRéservation)
        val textViewCompteARebours: TextView = view.findViewById(R.id.textViewCompteARebours)
        val progressBarRéservationSpecifique: ProgressBar = view.findViewById(R.id.progressBarRéservationSpecifique)
        val textViewHeureArrivéeRéservationSpécifique: TextView = view.findViewById(R.id.textViewHeureArrivéeRéservationSpécifique)
        val textViewHeureDépartRéservationSpécifique: TextView = view.findViewById(R.id.textViewHeureDépartRéservationSpécifique)
        val imageViewVilleRéservationSpecifique: ImageView = view.findViewById(R.id.imageViewVilleRéservationSpecifique)




        val réservation = SourceDonnéesFictive.listeRéservation[0]
        val vol = SourceDonnéesFictive.listVol.first {
            it.id == réservation.idVol
        }
        val timeRemaining: Long = ChronoUnit.DAYS.between(LocalDateTime.now(), vol.dateDepart)
        val siege = réservation.sièges[0]
        val formatterDate = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("fr"))
        val formatterHeure = DateTimeFormatter.ofPattern("HH:mm", Locale("fr")) // Changed to HH:mm

        textViewSiege.text = siege.numéro
        textViewDateDépart.text = vol.dateDepart.format(formatterDate)
        textViewDateArrivée.text = vol.dateArrivee.format(formatterDate)
        textViewClasse.text = "${siege.classe}"
        textViewNumeroRéservation.text = réservation.numéroRéservation
        textViewVille.text = vol.aeroportFin.ville.nom
        textViewDateDépartPreview.text = vol.dateDepart.format(formatterDate)
        textViewCompteARebours.text = timeRemaining.toString()
        progressBarRéservationSpecifique.progress = 30 - timeRemaining.toInt()
        textViewHeureArrivéeRéservationSpécifique.text = vol.dateArrivee.format(formatterHeure)
        textViewHeureDépartRéservationSpécifique.text = vol.dateDepart.format(formatterHeure)
        Glide.with(requireContext())
            .load(vol.aeroportFin.ville.url_photo)
            .into(imageViewVilleRéservationSpecifique)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val BtnModifierReservation: ImageButton = view.findViewById(R.id.BtnModifierReservation)
        BtnModifierReservation.setOnClickListener {
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_réservationSpécifiqueVue_to_modifierReservationVue)
        }
    }
}


