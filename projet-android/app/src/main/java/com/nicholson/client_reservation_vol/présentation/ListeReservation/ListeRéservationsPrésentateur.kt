package com.nicholson.client_reservation_vol.présentation.ListeReservation

import com.nicholson.client_reservation_vol.présentation.ListeReservation.ContratVuePrésentateurListeRéservation.*
import com.nicholson.client_reservation_vol.présentation.Modèle
import com.nicholson.client_reservation_vol.présentation.OTD.RéservationListItemOTD
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class ListeRéservationsPrésentateur (val vue : IListeDeRéservationsVue) : IListeDeRéservationsPrésentateur{
    val modèle : Modèle = Modèle.obtenirInstance()

    override fun traiterObtenirRéservation(){
        val listeDeRéservation = modèle.listeRéservation
        val listeDeVols = modèle.listeVolAller
        val listeRéservationOTD = listeDeRéservation.map {

        val tempMtn : LocalDateTime = LocalDateTime.now()
        val volDateDepart = modèle.obtenirVolParId( it.idVol ).dateDepart

        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
        val dateFormater = volDateDepart.format(formatter)

        val dateDepart = dateFormater.toString()
        val destination = modèle.obtenirVolParId( it.idVol ).aeroportFin.pays
        var tempsRestant = ChronoUnit.HOURS.between(tempMtn, volDateDepart).toString()
        val url_photo = modèle.obtenirVolParId( it.idVol ).aeroportFin.ville.url_photo

        var tempsUnite = "Heures"

        var barProgres : Int

        if(tempsRestant.toLong() > 24){
            tempsRestant = ChronoUnit.DAYS.between(tempMtn, volDateDepart).toString()
            tempsUnite = "Jours"
        }

        if(tempsUnite == "Jours") {
            barProgres = 30 - tempsRestant.toInt()
        }
        else{
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

        vue.afficherRéservations(listeRéservationOTD)
    }

    override fun traiterRéservationCliqué( index : Int ) {
        modèle.indiceRéservationCourrante = index
        vue.redirigerPageRéservationSpécifique()
    }

    override fun traiterBtnRechercheVolCliqué() {
        vue.redirigerVueRechercherVol()
    }

}
