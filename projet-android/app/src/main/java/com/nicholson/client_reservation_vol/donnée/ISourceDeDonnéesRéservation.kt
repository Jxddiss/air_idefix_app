package com.nicholson.client_reservation_vol.donnée

import com.nicholson.client_reservation_vol.domaine.entité.Réservation

interface ISourceDeDonnéesRéservation {
    suspend fun obtenirListeRéservation() : List<Réservation>
    suspend fun obtenirRéservationParId(id : Int): Réservation
    suspend fun ajouterRéservation( réservation : Réservation)
}