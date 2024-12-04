package com.nicholson.client_reservation_vol.donnée.fictive

import com.nicholson.client_reservation_vol.domaine.entité.Client
import com.nicholson.client_reservation_vol.donnée.ISourceDeDonéesClient

class SourceDeDonnéesClientsFictive : ISourceDeDonéesClient {
    override suspend fun modifierClient(client: Client) {
        val clientExistant = SourceDonnéesFictive.listClients.first {
            it.id == client.id
        }

        clientExistant.nom = client.nom
        clientExistant.prénom = client.prénom
        clientExistant.adresse = client.adresse
        clientExistant.email = client.email
        clientExistant.numéroPasseport = client.numéroPasseport
        clientExistant.numéroTéléphone = client.numéroTéléphone
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