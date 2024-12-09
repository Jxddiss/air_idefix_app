package com.nicholson.client_reservation_vol.domaine.interacteur

import com.nicholson.client_reservation_vol.domaine.entité.Réservation
import com.nicholson.client_reservation_vol.donnée.ISourceDeDonnéesRéservation
import com.nicholson.client_reservation_vol.donnée.fictive.SourceDeDonnéesRéservationFictive

class ManipulerReservation {
    companion object{
        var sourceDeDonnées : ISourceDeDonnéesRéservation = SourceDeDonnéesRéservationFictive()

        suspend fun ajouterRéservation(réservation: Réservation){
            sourceDeDonnées.ajouterRéservation(réservation)
        }
    }
}