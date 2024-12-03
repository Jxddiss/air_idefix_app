package com.nicholson.client_reservation_vol.domaine.interacteur

import com.nicholson.client_reservation_vol.domaine.entité.Aeroport
import com.nicholson.client_reservation_vol.donnée.ISourceDeDonnéesAeroport
import com.nicholson.client_reservation_vol.donnée.SourceDeDonnées
import com.nicholson.client_reservation_vol.donnée.fictive.SourceDeDonnéesAeroportFictive
import com.nicholson.client_reservation_vol.donnée.fictive.SourceDonnéesFictive

class AeroportService(private val sourceDeDonnées: ISourceDeDonnéesAeroport = SourceDeDonnéesAeroportFictive() ) {
    suspend fun obtenirListeAeroport() : List<Aeroport>{
        return sourceDeDonnées.obtenirListAéroports()
    }
}