package com.nicholson.client_reservation_vol.domaine.interacteur

import com.nicholson.client_reservation_vol.domaine.entité.Client
import com.nicholson.client_reservation_vol.donnée.ISourceDeDonéesClient
import com.nicholson.client_reservation_vol.donnée.fictive.SourceDeDonnéesClientsFictive

class ModifierClient {
    companion object {
        var sourceDeDonnées : ISourceDeDonéesClient = SourceDeDonnéesClientsFictive()

        suspend fun modifierClient( client : Client ) {
            sourceDeDonnées.modifierClient( client )
        }
    }
}