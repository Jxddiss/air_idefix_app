package com.nicholson.client_reservation_vol.domaine.interacteur

import com.nicholson.client_reservation_vol.domaine.entité.Aeroport
import com.nicholson.client_reservation_vol.donnée.SourceDeDonnées
import com.nicholson.client_reservation_vol.donnée.fictive.SourceDonnéesFictive

class AeroportService(private val sourceDeDonnées: SourceDeDonnées = SourceDonnéesFictive() ) {
    fun obtenirListeAeroport() : List<Aeroport>{
        return sourceDeDonnées.obtenirListAéroports()
    }
}