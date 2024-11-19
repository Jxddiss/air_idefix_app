package com.nicholson.client_reservation_vol.domaine.interacteur

import com.nicholson.client_reservation_vol.domaine.entité.Client
import com.nicholson.client_reservation_vol.donnée.SourceDeDonnées
import com.nicholson.client_reservation_vol.donnée.fictive.SourceDonnéesFictive

class ClientService(private val sourceDeDonnées: SourceDeDonnées = SourceDonnéesFictive() ) {
    fun ajouterClient(client : Client){
        sourceDeDonnées.ajouterClient(client)
    }

    fun obtenirListeClient() : MutableList<Client>{
        return sourceDeDonnées.obtenirListeClient()
    }
}