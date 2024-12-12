package com.nicholson.client_reservation_vol.domaine.interacteur

import com.nicholson.client_reservation_vol.domaine.entité.Réservation
import com.nicholson.client_reservation_vol.donnée.ISourceDeDonnéesRéservation
import com.nicholson.client_reservation_vol.donnée.fictive.SourceDeDonnéesRéservationFictive

class ObtenirReservation {
    companion object{
        var sourceDeDonnées : ISourceDeDonnéesRéservation = SourceDeDonnéesRéservationFictive()

        suspend fun obtenirListeRéservation() : List<Réservation>{
            return sourceDeDonnées.obtenirListeRéservation()
        }

        suspend fun obtenirDétailsRéservation(id : Int) : Réservation {
            return sourceDeDonnées.obtenirRéservationParId(id)
        }
    }
}