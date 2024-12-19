package com.nicholson.client_reservation_vol.domaine.interacteur

import com.nicholson.client_reservation_vol.domaine.entité.Siège
import com.nicholson.client_reservation_vol.donnée.ISourceSiège
import com.nicholson.client_reservation_vol.donnée.fictive.SourceSiègeFictive

class ObtenirSièges {
    companion object {
        var sourceDeDonnées : ISourceSiège = SourceSiègeFictive()

        suspend fun obtenirSiègesParIdVol( idVol : Int ) : List<Siège> {
            return sourceDeDonnées.obtenirSiègesParVolID( idVol )
        }
    }
}