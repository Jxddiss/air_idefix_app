package com.nicholson.client_reservation_vol.domaine.interacteur

import com.nicholson.client_reservation_vol.domaine.entité.Aeroport
import com.nicholson.client_reservation_vol.donnée.ISourceDeDonnéesAeroport
import com.nicholson.client_reservation_vol.donnée.fictive.SourceDeDonnéesAeroportFictive

class ObtenirAéroport {
    companion object {
        var sourceDeDonnées: ISourceDeDonnéesAeroport = SourceDeDonnéesAeroportFictive()

        suspend fun obtenirListeAeroport() : List<Aeroport>{
            return sourceDeDonnées.obtenirListAéroports()
        }
    }
}