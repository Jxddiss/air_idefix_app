package com.nicholson.client_reservation_vol.donnée

import com.nicholson.client_reservation_vol.domaine.entité.Client
import com.nicholson.client_reservation_vol.domaine.entité.Réservation
import com.nicholson.client_reservation_vol.domaine.entité.Siège

interface SourceDeDonnées {
    fun ajouterClient(client: Client)
    fun obtenirListeClient():MutableList<Client>
    fun obtenirListeRéservation() : MutableList<Réservation>
    fun obtenirRéservationParId(id : Int): Réservation
    fun ajouterRéservation( réservation : Réservation )
    fun obtenirSiegeParId(id : Int): Siège
}
