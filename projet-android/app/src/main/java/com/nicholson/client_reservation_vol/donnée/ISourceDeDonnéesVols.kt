package com.nicholson.client_reservation_vol.donnée

import com.nicholson.client_reservation_vol.domaine.entité.Vol
import com.nicholson.client_reservation_vol.présentation.OTD.FiltreRechercheVol

interface ISourceDeDonnéesVols {
    suspend fun obtenirListeVolParFiltre( filtre : FiltreRechercheVol) : List<Vol>
    suspend fun obtenirVolParId( id : Int ) : Vol
}