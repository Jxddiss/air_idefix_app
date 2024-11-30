package com.nicholson.client_reservation_vol.domaine.interacteur

import com.nicholson.client_reservation_vol.domaine.entité.Vol
import com.nicholson.client_reservation_vol.donnée.ISourceDeDonnéesVols
import com.nicholson.client_reservation_vol.donnée.fictive.SourceDonnéeVolsFictive
import com.nicholson.client_reservation_vol.présentation.OTD.FiltreRechercheVol

class RechercherVol {
    companion object{
        var sourceDeDonnéesVol : ISourceDeDonnéesVols = SourceDonnéeVolsFictive()

        fun rechercherVolParFiltre( filtre : FiltreRechercheVol) : List<Vol> {
            return sourceDeDonnéesVol.obtenirListeVolParFiltre( filtre )
        }

        fun obtenirDétailVol( id : Int ) : Vol {
            return sourceDeDonnéesVol.obtenirVolParId( id )
        }
    }
}