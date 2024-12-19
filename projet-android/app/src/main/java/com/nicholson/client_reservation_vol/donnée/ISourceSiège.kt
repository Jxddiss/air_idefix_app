package com.nicholson.client_reservation_vol.donnée

import com.nicholson.client_reservation_vol.domaine.entité.Siège

interface ISourceSiège {
    suspend fun obtenirSiègesParVolID( idVol : Int ) : List<Siège>
}