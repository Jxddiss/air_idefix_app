package com.nicholson.client_reservation_vol.présentation.ListeReservation

import com.nicholson.client_reservation_vol.domaine.entité.Réservation
import com.nicholson.client_reservation_vol.donnée.exceptions.SourceDeDonnéesException
import com.nicholson.client_reservation_vol.donnée.http.exception.AuthentificationException
import com.nicholson.client_reservation_vol.présentation.ListeReservation.ContratVuePrésentateurListeRéservation.*
import com.nicholson.client_reservation_vol.présentation.Modèle
import com.nicholson.client_reservation_vol.présentation.OTD.RéservationListItemOTD
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.coroutines.CoroutineContext

class ListeRéservationsPrésentateur (
    val vue : IListeDeRéservationsVue,
    private val iocontext : CoroutineContext = Dispatchers.IO ) : IListeDeRéservationsPrésentateur{

    private val modèle : Modèle = Modèle.obtenirInstance()
    private var job : Job? = null

    override fun traiterObtenirRéservation(){
        vue.montrerChargement()
        job = CoroutineScope( iocontext ).launch {
            var listeDeRéservation = listOf<Réservation>()
            try {
                listeDeRéservation = modèle.obtenirListReservation()
            }
            catch(ex : AuthentificationException){
                CoroutineScope(Dispatchers.Main).launch {
                    vue.seConnecter(
                        réussite = {
                            modèle.effectuerLogin( it )
                            traiterObtenirRéservation()
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

            val listeRéservationOTD = listeDeRéservation.map {

                val tempMtn: LocalDateTime = LocalDateTime.now()
                val volDateDepart = modèle.obtenirVolParId(it.idVol).dateDepart

                val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
                val dateFormater = volDateDepart.format(formatter)

                val dateDepart = dateFormater.toString()
                val destination = modèle.obtenirVolParId(it.idVol).aeroportFin.pays
                var tempsRestant = ChronoUnit.HOURS.between(tempMtn, volDateDepart).toString()
                val url_photo = modèle.obtenirVolParId(it.idVol).aeroportFin.ville.url_photo

                var tempsUnite = "Heures"

                var barProgres: Int

                if (tempsRestant.toLong() > 24) {
                    tempsRestant = ChronoUnit.DAYS.between(tempMtn, volDateDepart).toString()
                    tempsUnite = "Jours"
                } else if (tempsRestant.toLong() < 0) {
                    tempsRestant = "Fini"
                    tempsUnite = ""
                }

                if (tempsUnite == "Jours") {
                    barProgres = 30 - tempsRestant.toInt()
                } else if (tempsUnite == "") {
                    barProgres = 30
                } else {
                    barProgres = 29
                }

                RéservationListItemOTD(
                    dateDepart = dateDepart,
                    destination = destination,
                    tempsRestant = tempsRestant,
                    tempsUnite = tempsUnite,
                    url_photo = url_photo,
                    barProgres = barProgres.toString()
                )
            }.toMutableList()

            CoroutineScope(Dispatchers.Main).launch {

                vue.masquerChargement()
                vue.afficherRéservations(listeRéservationOTD)
            }
        }
    }

    override fun traiterRéservationCliqué( index : Int ) {
        modèle.indiceRéservationCourrante = index
        vue.redirigerPageRéservationSpécifique()
    }

    override fun traiterBtnRechercheVolCliqué() {
        vue.redirigerVueRechercherVol()
    }

}
