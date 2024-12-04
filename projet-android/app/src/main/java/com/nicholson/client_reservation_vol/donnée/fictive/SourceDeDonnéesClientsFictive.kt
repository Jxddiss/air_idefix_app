package com.nicholson.client_reservation_vol.donnée.fictive

import com.nicholson.client_reservation_vol.domaine.entité.Client
import com.nicholson.client_reservation_vol.donnée.ISourceDeDonéesClient

class SourceDeDonnéesClientsFictive : ISourceDeDonéesClient {
    override suspend fun ajouterClient(client: Client) {
        SourceDonnéesFictive.listClients.add(client)
    }

    override suspend fun obtenirClient( id : Int ): Client? {
        return SourceDonnéesFictive.listClients.firstOrNull {
            it.id == id
        }
    }

    override suspend fun obtenirClientCourrant(): Client {
        return SourceDonnéesFictive.listClients[0]
    }

}