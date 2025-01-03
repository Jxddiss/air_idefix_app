package com.nicholson.client_reservation_vol.présentation.RéservationSpécifique

import com.nicholson.client_reservation_vol.domaine.entité.Réservation
import com.nicholson.client_reservation_vol.domaine.entité.Vol
import com.nicholson.client_reservation_vol.donnée.exceptions.SourceDeDonnéesException
import com.nicholson.client_reservation_vol.donnée.http.exception.AuthentificationException
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

        vue.montrerChargement()
        job = CoroutineScope( iocontext ).launch {
            var réservation : Réservation? = null
            var vol : Vol? = null
            try{
                réservation = modèle.obtenirReservationCourrante()
                vol = modèle.obtenirVolParId(réservation.idVol)
            }
            catch(ex : AuthentificationException){
                CoroutineScope(Dispatchers.Main).launch {
                    vue.seConnecter(
                        réussite = {
                            modèle.effectuerLogin( it )
                            traiterDémarage()
                        },
                        échec = {
                            modèle.messageErreurRéseauExistant = true
                            vue.redirigerBienvenueErreur()
                        }

                    )
                }
            }
            catch(ex : SourceDeDonnéesException){
                modèle.messageErreurRéseauExistant = true
                CoroutineScope(Dispatchers.Main).launch {
                    vue.redirigerBienvenueErreur()
                }
            }
            if(vol != null && réservation != null){
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
                    classe = réservation.siège?.classe ?: "Économique",
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
                    siège = réservation.siège?.numéro ?: "",
                    tempsRestant = tempsRestant,
                    tempsUnite = tempsUnite,
                    barProgres = barProgres.toString(),
                    url_photo = vol.aeroportFin.ville.url_photo
                )
                CoroutineScope( Dispatchers.Main ).launch {
                    vue.masquerChargement()
                    vue.miseEnPlace(réservationOTD)
                }

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