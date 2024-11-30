package com.nicholson.client_reservation_vol.présentation.RéservationSpécifique

import android.content.Context
import android.content.Intent
import android.provider.CalendarContract
import android.util.Log
import android.widget.Toast
import com.nicholson.client_reservation_vol.présentation.Modèle
import com.nicholson.client_reservation_vol.présentation.OTD.RéservationSpécifiqueOTD
import com.nicholson.client_reservation_vol.présentation.RéservationSpécifique.ContratVuePrésentateurRéservationSpécifique.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.coroutines.CoroutineContext
import java.util.Calendar
import java.util.Locale

class RéservationSpécifiquePrésentateur(
    private val vue : IRéservationSpécifiqueVue = RéservationSpécifiqueVue(),
    private val iocontext : CoroutineContext = Dispatchers.IO
) : IRéservationSpécifiquePrésentateur {

    private val modèle : Modèle = Modèle.obtenirInstance()
    private val formatterDate = DateTimeFormatter.ofPattern( "dd MMMM yyyy" )
    private val formatterHeure = DateTimeFormatter.ofPattern( "HH:MM" )
    private var job : Job? = null

    override fun traiterDémarage() {
        job = CoroutineScope( iocontext ).launch {
            val réservation = modèle.obtenirReservationCourrante()
            val vol = modèle.obtenirVolParId(réservation.idVol)
            val siègeString = StringBuilder()
            réservation.sièges.forEach {
                siègeString.append("${it.numéro} ")

            }
            val tempMtn = LocalDateTime.now()
            var tempsRestant = ChronoUnit.HOURS.between(tempMtn, vol.dateDepart).toString()

            var tempsUnite = "Heures"

            var barProgres : Int

            if(tempsRestant.toLong() > 24){
                tempsRestant = ChronoUnit.DAYS.between(tempMtn, vol.dateDepart).toString()
                tempsUnite = "Jours"
            }

            if(tempsUnite == "Jours") {
                barProgres = 30 - tempsRestant.toInt()
            }
            else{
                barProgres = 29
            }


            val réservationOTD = RéservationSpécifiqueOTD(
                classe = réservation.sièges[0].classe,
                dateArrivée = vol.dateArrivee.format(formatterDate),
                dateDepart = vol.dateDepart.format(formatterDate),
                durée = vol.durée.toComponents {
                        hrs, min, _, _ ->
                    "${hrs}h${min}"
                },
                heureArrivée = vol.dateArrivee.format(formatterHeure),
                heureDepart = vol.dateDepart.format(formatterHeure),
                nomVille = vol.aeroportFin.ville.nom,
                numéroRéservation = réservation.numéroRéservation,
                siège = siègeString.toString(),
                tempsRestant = tempsRestant,
                tempsUnite = tempsUnite,
                barProgres = barProgres.toString(),
                url_photo = vol.aeroportFin.ville.url_photo
            )
            CoroutineScope( Dispatchers.Main ).launch {
                vue.miseEnPlace(réservationOTD)
            }
        }
    }
     override fun traiterCalendrier(réservationSpécifiqueOTD: RéservationSpécifiqueOTD) {
        try {
            val userLocale = Locale.getDefault()
            val dateFormat = SimpleDateFormat("dd MMMM yyyy", userLocale)

            val dateDépart = dateFormat.parse(réservationSpécifiqueOTD.dateDepart)
            val dateArrivée = dateFormat.parse(réservationSpécifiqueOTD.dateArrivée)

            val heureDepartSplit = réservationSpécifiqueOTD.heureDepart.split(":")
            val heureArrivéeSplit = réservationSpécifiqueOTD.heureArrivée.split(":")

            val startCalendar = Calendar.getInstance().apply {
                time = dateDépart
                set(Calendar.HOUR_OF_DAY, heureDepartSplit[0].toInt())
                set(Calendar.MINUTE, heureDepartSplit[1].toInt())
            }

            val endCalendar = Calendar.getInstance().apply {
                time = dateArrivée
                set(Calendar.HOUR_OF_DAY, heureArrivéeSplit[0].toInt())
                set(Calendar.MINUTE, heureArrivéeSplit[1].toInt())
            }

            val eventDetails = mapOf(
                "title" to "Air Idéfix",
                "description" to "🛬",
                "location" to réservationSpécifiqueOTD.nomVille,
                "startTime" to startCalendar.timeInMillis,
                "endTime" to endCalendar.timeInMillis
            )


            vue.ouvrirCalendrier(eventDetails)

        } catch (e: Exception) {
            vue.afficherErreur("Erreur lors de l'ouverture du calendrier : ${e.message}")
        }
    }



    override fun traiterModifier() {
        vue.redirigerModifier()
    }


}