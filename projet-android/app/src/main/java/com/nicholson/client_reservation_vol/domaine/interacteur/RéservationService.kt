package com.nicholson.client_reservation_vol.domaine.interacteur

import com.nicholson.client_reservation_vol.domaine.entité.Réservation
import com.nicholson.client_reservation_vol.donnée.SourceDeDonnées
import com.nicholson.client_reservation_vol.donnée.fictive.SourceDonnéesFictive

class RéservationService(private val sourceDeDonnées: SourceDeDonnées = SourceDonnéesFictive() ) {
    fun obtenirRéservationParid(id : Int ) =
        sourceDeDonnées.obtenirRéservationParId( id )

    fun obtenirListeRéservation() =
        sourceDeDonnées.obtenirListeRéservation()

    fun ajouterRéservation( réservation : Réservation ) =
        sourceDeDonnées.ajouterRéservation( réservation )
}