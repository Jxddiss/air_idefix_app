package com.nicholson.client_reservation_vol.donnée.fictive

import com.nicholson.client_reservation_vol.domaine.entité.Siège
import com.nicholson.client_reservation_vol.donnée.ISourceSiège

class SourceSiègeFictive : ISourceSiège {
    override suspend fun obtenirSiègesParVolID(idVol: Int): List<Siège> {
        return SourceDonnéesFictive.listSièges
    }
}