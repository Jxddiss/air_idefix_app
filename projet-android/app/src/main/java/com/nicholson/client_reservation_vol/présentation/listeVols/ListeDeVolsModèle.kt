package com.nicholson.client_reservation_vol.présentation.listeVols

import com.nicholson.client_reservation_vol.donnée.fictive.SourceDonnéesFictive

class ListeDeVolsModèle {

    fun obtenirVols() = SourceDonnéesFictive.listVol

    fun obtenirVolParId( id : Int ) = SourceDonnéesFictive.listVol.firstOrNull {
        it.id == id
    }
}