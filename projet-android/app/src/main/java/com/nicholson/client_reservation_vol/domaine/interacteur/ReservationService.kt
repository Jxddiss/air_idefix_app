package com.nicholson.client_reservation_vol.domaine.interacteur

import com.nicholson.client_reservation_vol.domaine.entité.Réservation
import com.nicholson.client_reservation_vol.donnée.SourceDeDonnées
import com.nicholson.client_reservation_vol.donnée.fictive.SourceDonnéesFictive

class ReservationService( private val sourceDeDonnées: SourceDeDonnées = SourceDonnéesFictive() ) {
    fun obtenirReservationParid( id : Int ) =
        sourceDeDonnées.obtenirReservationParId( id )

    fun obtenirListReservation() =
        sourceDeDonnées.obtenirListRéservation()
}