package com.nicholson.client_reservation_vol.présentation.RéservationSpécifique

import android.content.Context
import android.content.Intent
import android.provider.CalendarContract
import android.util.Log
import android.widget.Toast
import com.nicholson.client_reservation_vol.présentation.Modèle
import com.nicholson.client_reservation_vol.présentation.OTD.RéservationSpécifiqueOTD
import com.nicholson.client_reservation_vol.présentation.RéservationSpécifique.ContratVuePrésentateurRéservationSpécifique.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.Locale

class RéservationSpécifiquePrésentateur(
    private val vue : IRéservationSpécifiqueVue = RéservationSpécifiqueVue()
) : IRéservationSpécifiquePrésentateur {
    private val modèle : Modèle = Modèle.obtenirInstance()
    private val formatterDate = DateTimeFormatter.ofPattern( "dd MMMM yyyy" )
    private val formatterHeure = DateTimeFormatter.ofPattern( "HH:MM" )

    override fun traiterDémarage() {
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
        vue.miseEnPlace(réservationOTD)
    }
    fun openCalendarApp(réservationSpécifiqueOTD: RéservationSpécifiqueOTD,context: Context) {
        val userLocale = Locale.getDefault()
        val dateFormat = SimpleDateFormat("dd MMMM yyyy", userLocale)

        try {

            val dateDépart = dateFormat.parse(réservationSpécifiqueOTD.dateDepart)
            val dateArrivée = dateFormat.parse(réservationSpécifiqueOTD.dateArrivée)


            val heureDepart = réservationSpécifiqueOTD.heureDepart
            val heureArrivée = réservationSpécifiqueOTD.heureArrivée


            val heureDepartSplit = heureDepart.split(":")
            val heureArrivéeSplit = heureArrivée.split(":")

            val hourDepart = heureDepartSplit[0].toInt()
            val minuteDepart = heureDepartSplit[1].toInt()

            val hourArrivée = heureArrivéeSplit[0].toInt()
            val minuteArrivée = heureArrivéeSplit[1].toInt()


            val startCalendar = Calendar.getInstance()
            startCalendar.time = dateDépart
            startCalendar.set(Calendar.HOUR_OF_DAY, hourDepart)
            startCalendar.set(Calendar.MINUTE, minuteDepart)


            val endCalendar = Calendar.getInstance()
            endCalendar.time = dateArrivée
            endCalendar.set(Calendar.HOUR_OF_DAY, hourArrivée)
            endCalendar.set(Calendar.MINUTE, minuteArrivée)

            val eventLocation = réservationSpécifiqueOTD.nomVille
            val emojieAvion ="🛬"
            val startTime = startCalendar.timeInMillis
            val endTime = endCalendar.timeInMillis

            val intent = Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime)
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime)
                .putExtra(CalendarContract.Events.TITLE, "Air Idéfix")
                .putExtra(CalendarContract.Events.DESCRIPTION, emojieAvion)
                .putExtra(CalendarContract.Events.EVENT_LOCATION, eventLocation)

            context.startActivity(intent)

        } catch (e: Exception) {
            Toast.makeText(context, "Erreur lors de l'ouverture du calendrier : ${e.message}", Toast.LENGTH_LONG).show()
        }

    }


    override fun traiterModifier() {
        vue.redirigerModifier()
    }


}