package com.nicholson.client_reservation_vol.donnée.fictive

import com.nicholson.client_reservation_vol.domaine.entité.Aeroport
import com.nicholson.client_reservation_vol.donnée.ISourceDeDonnéesAeroport

class SourceDeDonnéesAeroportFictive : ISourceDeDonnéesAeroport {
    override suspend fun obtenirListAéroports(): List<Aeroport> {
        return SourceDonnéesFictive.listAeoroport
    }
}